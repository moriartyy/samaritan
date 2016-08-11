package org.samaritan.transport.nio;

import com.google.common.collect.Lists;
import org.samaritan.transport.Channel;
import org.samaritan.transport.ChannelHandler;
import org.samaritan.transport.Node;
import org.samaritan.transport.NodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Loster on 2016/8/11.
 */
public class NioNetNode implements Node {

    private List<MonitorTask> tasks = Lists.newLinkedList();
    private ExecutorService tasksExecutor;
    private static Logger logger = LoggerFactory.getLogger(NioNetNode.class);

    @Override
    public Channel connect(InetSocketAddress address) {
        return null;
    }

    @Override
    public InetSocketAddress address() {
        return null;
    }

    @Override
    public void monitor(InetSocketAddress address, ChannelHandler channelHandler) throws NodeException {
        try {
            doMonitor(address, channelHandler);
        } catch (Exception e) {
            throw new NodeException("Failed to monitor address [" + address + "]", e);
        }
    }

    private void doMonitor(InetSocketAddress address, ChannelHandler channelHandler) throws Exception {
        this.tasks.add(MonitorTask.start(address, channelHandler));
    }

    @Override
    public void start() {
        tasksExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void stop() {
        tasksExecutor.shutdownNow();
        tasks.forEach(MonitorTask::stop);
    }

    private static class MonitorTask {

        private final ChannelHandler channelHandler;
        private ServerSocketChannel serverChannel;
        private Selector selector;
        private final InetSocketAddress monitoringAddress;
        private Thread bossThread;
        private AtomicBoolean running = new AtomicBoolean(false);

        static MonitorTask start(InetSocketAddress address, ChannelHandler channelHandler) throws Exception {
            MonitorTask task = new MonitorTask(address, channelHandler);
            task.start();
            return task;
        }

        MonitorTask(InetSocketAddress monitoringAddress, ChannelHandler channelHandler) {
            this.monitoringAddress = monitoringAddress;
            this.channelHandler = channelHandler;
        }

        void start() throws Exception {
            running.set(true);
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(monitoringAddress);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            bossThread = new Thread(() -> {
                while(running.get()) {
                    try {
                        selector.select();
                        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            keyIterator.remove();
                            if (key.isAcceptable()) {
                                ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
                                SocketChannel clientChannel = serverChannel.accept();
                                clientChannel.configureBlocking(false);
                                NioChannel channel = new NioChannel(clientChannel);
                                clientChannel.register(selector, SelectionKey.OP_READ, channel);
                                channelHandler.onConnected(channel);
                            } else if (key.isReadable()) {
                                NioChannel channelIn = (SocketChannel) key.attachment());
                                channelHandler.onReceived(channelIn);
                            }
                        }
                    } catch (IOException e) {
                        logger.error("Error happened", e);
                        break;
                    }
                }
            });
            bossThread.start();
        }

        private void handleChannelRead(SocketChannel clientChannel) {
            NioChannel channel = new NioChannel(clientChannel);
            channelHandler.
        }

        void stop() {
            running.set(false);
            bossThread.interrupt();
            try {
                selector.close();
            } catch (IOException ignored) {
            }
            try {
                serverChannel.close();
            } catch (IOException ignored) {
            }
        }
    }
}

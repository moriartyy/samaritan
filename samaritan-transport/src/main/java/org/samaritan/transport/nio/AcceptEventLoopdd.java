package org.samaritan.transport.nio;

import org.samaritan.transport.ChannelHandler;
import org.samaritan.transport.ChannelListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Loster on 2016/8/12.
 */
public class AcceptEventLoopdd implements Runnable {

    private final ChannelListener channelListener;
    private final IOEventLoop ioEventLoop;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private final InetSocketAddress monitoringAddress;
    private Thread bossThread;
    private AtomicBoolean running = new AtomicBoolean(false);

    public AcceptEventLoopdd(InetSocketAddress monitoringAddress, ChannelListener channelListener, IOEventLoop ioEventLoop) {
        this.monitoringAddress = monitoringAddress;
        this.channelListener = channelListener;
        this.ioEventLoop = ioEventLoop;
    }

    public void start() throws Exception {
        running.set(true);
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(monitoringAddress);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        bossThread = new Thread(() -> {
            while (running.get()) {
                try {
                    selector.select();
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
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
                    NioNode.logger.error("Error happened", e);
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

    @Override
    public void run() {

    }
}

package org.samaritan.transport.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Loster on 2016/8/12.
 */
public class IOEventLoop {

    private final int threadsNumber;
    private Selector selector;
    private ExecutorService eventExecutor;
    private volatile Set<SelectionKey> selectionKeys;

    public IOEventLoop(int maxThreadsNumber) {
        this.threadsNumber = maxThreadsNumber;
    }

    public IOEventLoop() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public void register(NioChannel channel) {

    }

    public void start() {
        this.eventExecutor = Executors.newFixedThreadPool(this.threadsNumber);
    }

    public void stop() {
        List<Runnable> executingTasks = eventExecutor.shutdownNow();
        for (Runnable executingTask : executingTasks) {

        }
    }


    public void next() {
        final Selector selector = this.selector;
        try {
            selector.select();
            Iterator<SelectionKey> i = selector.selectedKeys().iterator();
            while (i.hasNext()) {
                SelectionKey key = i.next();
                i.remove();
//                if (key.isAcceptable()) {
//                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
//                    SocketChannel clientChannel = serverChannel.accept();
//                    clientChannel.configureBlocking(false);
//                    NioChannel channel = new NioChannel(clientChannel);
//                    clientChannel.register(selector, SelectionKey.OP_READ, channel);
//                    channelHandler.onConnected(channel);
//                } else if (key.readyOps().isReadable()) {
//                    NioChannel channelIn = (SocketChannel) key.attachment());
//                    channelHandler.onReceived(channelIn);
//                }
            }
        } catch (IOException e) {
//            NioNode.logger.error("Error happened", e);
        }
    }
}

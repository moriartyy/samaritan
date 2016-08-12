package org.samaritan.transport.nio;

import org.samaritan.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Loster on 2016/8/11.
 */
public class NioNode implements Node {

    private AcceptEventLoop acceptEventLoop;
    private IOEventLoop ioEventLoop;
    private static Logger logger = LoggerFactory.getLogger(NioNode.class);

    @Override
    public Channel connect(InetSocketAddress address, ChannelListener listener) {
        return null;
    }

    @Override
    public InetSocketAddress address() {
        return null;
    }

    public static NioNode newNode() {
        return new NioNode();
    }

    private NioNode() {}

    @Override
    public void serve(InetSocketAddress address, ChannelListener listener) throws NodeException {
        try {
            doMonitor(address, listener);
        } catch (Exception e) {
            throw new NodeException("Failed to serve address [" + address + "]", e);
        }
    }

    private void doMonitor(InetSocketAddress address, ChannelListener listener) throws Exception {
        acceptEventLoop.register(new Task(address, listener));
    }

    @Override
    public void start() {
        acceptEventLoop = new AcceptEventLoop();
        acceptEventLoop.start();
        ioEventLoop = new IOEventLoop();
        ioEventLoop.start();
    }

    @Override
    public void stop() {
        acceptEventLoop.stop();
        ioEventLoop.stop();
    }

    class Task {

        public Task(InetSocketAddress address, ChannelListener listener) {

        }
    }

}

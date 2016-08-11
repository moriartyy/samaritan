package org.samaritan.transport.nio;

import org.samaritan.transport.Channel;
import org.samaritan.transport.Node;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author Loster on 2016/8/11.
 */
public class NioChannel implements Channel {

    public NioChannel(SocketChannel socket) {

    }

    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override
    public State state() {
        return null;
    }

    @Override
    public void send(Object obj) {

    }


}

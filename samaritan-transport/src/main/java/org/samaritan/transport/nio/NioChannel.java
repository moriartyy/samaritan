package org.samaritan.transport.nio;

import org.samaritan.transport.Channel;
import org.samaritan.transport.Node;

import java.net.InetSocketAddress;

/**
 * @author Loster on 2016/8/11.
 */
public class NioChannel implements Channel {

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

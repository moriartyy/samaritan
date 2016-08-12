package org.samaritan.transport.nio;

import org.samaritan.transport.Channel;
import org.samaritan.transport.ChannelListener;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Loster on 2016/8/12.
 */
public class NioChannel implements Channel {
    @Override
    public void addListener(ChannelListener channelListener) {

    }

    @Override
    public List<ChannelListener> listeners() {
        return null;
    }

    @Override
    public void close() {

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

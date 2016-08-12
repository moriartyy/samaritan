package org.samaritan.transport;

import java.net.InetSocketAddress;

/**
 * @author Loster on 2016/8/11.
 */
public interface Node {

    Channel connect(InetSocketAddress address, ChannelListener listeners) throws NodeException;

    InetSocketAddress address();

    void serve(InetSocketAddress address, ChannelListener channelListener) throws NodeException;

    void start();

    void stop();
}

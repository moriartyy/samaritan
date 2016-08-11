package org.samaritan.transport;

import java.net.InetSocketAddress;

/**
 * @author Loster on 2016/8/11.
 */
public interface Node {

    Channel connect(InetSocketAddress address) throws NodeException;

    InetSocketAddress address();

    void monitor(InetSocketAddress address, ChannelHandler channelHandler) throws NodeException;

    void start();

    void stop();
}

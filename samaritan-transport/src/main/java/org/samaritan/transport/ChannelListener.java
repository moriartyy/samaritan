package org.samaritan.transport;

/**
 * @author Loster on 2016/8/12.
 */
public interface ChannelListener {

    void objectReceived(Channel channel, Object object);

    void channelConnected(Channel channel);

    void channelDisconnected(Channel channel);
}

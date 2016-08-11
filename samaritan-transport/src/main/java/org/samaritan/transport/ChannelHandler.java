package org.samaritan.transport;

/**
 * @author Loster on 2016/8/11.
 */
public interface ChannelHandler {

    void onConnected(Channel channel);

    void onDisconnected(Channel channel);

    void onError(Channel channel, Exception e);

    void onReceived(Channel channel, Object object);
}

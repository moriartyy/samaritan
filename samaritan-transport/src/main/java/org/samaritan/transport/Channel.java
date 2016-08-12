package org.samaritan.transport;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Loster on 2016/8/11.
 */
public interface Channel {

    void addListener(ChannelListener channelListener);

    List<ChannelListener> listeners();

    void close();

    enum State {Opened, Colsed}

    InetSocketAddress remoteAddress();

    State state();

    void send(Object obj);
}

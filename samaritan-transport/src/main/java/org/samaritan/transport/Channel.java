package org.samaritan.transport;

import java.net.InetSocketAddress;

/**
 * @author Loster on 2016/8/11.
 */
public interface Channel {

    enum State {Opened, Colsed}

    InetSocketAddress remoteAddress();

    State state();

    void send(Object obj);
}

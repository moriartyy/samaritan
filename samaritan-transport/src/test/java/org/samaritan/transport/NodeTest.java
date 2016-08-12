package org.samaritan.transport;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.samaritan.transport.nio.NioNode;

import java.net.InetSocketAddress;

/**
 * @author Loster on 2016/8/12.
 */

public class NodeTest {

    private static Node node;

    @BeforeClass
    public static void startNode() {
        node = NioNode.newNode();
        node.start();
    }

    @AfterClass
    public static void stopNode() {
        node.stop();
    }

    @Test(expected = NodeException.class)
    public void testConnect() throws NodeException {
        InetSocketAddress remote = new InetSocketAddress(6000);
        Channel channel = node.connect(remote, new ChannelHandler());
        channel.send("hello");
        channel.close();

        node.stop();
    }

    @Test
    public void testMonitor() throws NodeException {
        node.serve(InetSocketAddress.createUnresolved("0.0.0.0", 6000), new ChannelListener(){
            @Override
            public void objectReceived(Channel channel, Object object) {

            }

            @Override
            public void channelConnected(Channel channel) {

            }

            @Override
            public void channelDisconnected(Channel channel) {

            }
        });
    }

    class ChannelHandler implements ChannelListener {

        @Override
        public void objectReceived(Channel channel, Object object) {

        }

        @Override
        public void channelConnected(Channel channel) {
        }

        @Override
        public void channelDisconnected(Channel channel) {

        }
    }

}

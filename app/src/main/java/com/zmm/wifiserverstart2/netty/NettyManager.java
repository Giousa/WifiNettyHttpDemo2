package com.zmm.wifiserverstart2.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/6/26
 * Time:上午11:31
 */

public class NettyManager {

    private static NettyManager mInstance;
    private ReadListener mReadListener;

    public interface ReadListener{
        void onReadListener(String msg);
    }

    public void setReadListener(ReadListener readListener) {
        mReadListener = readListener;
    }

//    public static NettyManager getInstance(){
//        if(mInstance == null){
//            mInstance = new NettyManager();
//            mInstance.start();
//        }
//
//        return mInstance;
//    }

    public void start(){

        System.out.println("服务已经开启了哦");

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ServerHandler serverHandler = new ServerHandler();
                            ch.pipeline().addLast(serverHandler);
                            serverHandler.setServerReadListener(new ServerHandler.ServerReadListener() {
                                @Override
                                public void onServerReadListener(String msg) {
                                    if(mReadListener != null){
                                        System.out.println("NettyManager  "+msg);
                                        mReadListener.onReadListener(msg);
                                    }
                                }
                            });
                        }
                    });

            Channel ch = bootstrap.bind(8844).sync().channel();

            System.out.println("------Server Start------");

            ch.closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

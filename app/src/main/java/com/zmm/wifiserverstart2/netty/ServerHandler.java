package com.zmm.wifiserverstart2.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ServerReadListener mServerReadListener;

    public interface ServerReadListener{
        void onServerReadListener(String msg);
    }

    public void setServerReadListener(ServerReadListener serverReadListener) {
        mServerReadListener = serverReadListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        System.out.println("客户端msg :::: "+msg);

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String data = buf.toString(CharsetUtil.UTF_8).substring(12);
//            String substring = data.substring(12);
//            DeviceModel deviceModel = JSON.parseObject(substring, DeviceModel.class);

            System.out.println("ServerHandler "+data);
            if(mServerReadListener != null){
                mServerReadListener.onServerReadListener(data);
            }
            buf.release();

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }

}
package com.aylib.httpnetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    private ChannelInitLinstener mlinstener;

    public MyChannelInitializer(ChannelInitLinstener linstener) {
        this.mlinstener=linstener;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("connected:" + ch.remoteAddress());
        HttpMsgHandler  handler=new HttpMsgHandler();
        mlinstener.callBackHandler(handler);
        // 建立管道
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline
                .addLast(new HttpRequestDecoder())
                .addLast(new HttpResponseEncoder())
                .addLast(new HttpObjectAggregator(1024 * 1024 * 4))
                .addLast(handler);
    }
}

package com.aylib.httpnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class HttpNettyServer implements NettyServer {
    private volatile static HttpNettyServer singleton;
    private HttpMsgHandler mHttpMsgHandler;

    private HttpNettyServer(InnerConnectLinstener linstener) {
        this.mLinstener = linstener;
        init();
    }

    public static HttpNettyServer getInstance(InnerConnectLinstener linstener) {
        if (singleton == null) {
            synchronized (HttpNettyServer.class) {
                if (singleton == null) {
                    singleton = new HttpNettyServer(linstener);
                }
            }
        }
        return singleton;
    }


    private ChannelFuture mChannelFuture;
    private ServerBootstrap mBootstrap;
    private EventLoopGroup mWorkerGroup;
    private EventLoopGroup mGroup;
    private InnerConnectLinstener mLinstener;


    /**
     * 初始化
     */
    private void init() {
        mBootstrap = new ServerBootstrap();
        mWorkerGroup = new NioEventLoopGroup();
        mGroup = new NioEventLoopGroup();
        MyChannelInitializer myChannelInitializer = new MyChannelInitializer(new ChannelInitLinstener() {
            @Override
            public void callBackHandler(HttpMsgHandler handler) {
                mHttpMsgHandler=handler;
                mHttpMsgHandler.setmListener(mLinstener);
            }
        });

        mBootstrap.group(mGroup, mWorkerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true) // 消息立即发出去
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持长链接
//                .handler(new LoggingHandler(LogLevel.INFO))
//                .childHandler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    protected void initChannel(SocketChannel ch) throws Exception {
//                        System.out.println("connected:" + ch.remoteAddress());
//                        // 建立管道
//                        ChannelPipeline channelPipeline = ch.pipeline();
//                        mHttpMsgHandler = new HttpMsgHandler(mLinstener);
//                        channelPipeline
//                                .addLast(new HttpRequestDecoder())
//                                .addLast(new HttpResponseEncoder())
//                                .addLast(new HttpObjectAggregator(1024 * 1024 * 4))
//                                .addLast(mHttpMsgHandler);
//                    }
//                });
                .childHandler(myChannelInitializer);
    }

    @Override
    public void start(int port) {
        try {
            mChannelFuture = mBootstrap.bind(port).addListener(future -> {
                if (future.isSuccess()) {
//                    System.out.println("Server start success.");
                    if (mLinstener != null) mLinstener.serverStart(true);
                } else {
//                    System.out.println("Server start failed.");
                    if (mLinstener != null) mLinstener.serverStart(false);
                }
            }).sync();
            mChannelFuture.channel().closeFuture().sync();
//            System.out.println("Server connection is closed.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mGroup.shutdownGracefully();
            mWorkerGroup.shutdownGracefully();
        }
    }

    public void writeResponse(String msg) {
        if (mHttpMsgHandler != null && mHttpMsgHandler.getmCtx() != null) {
//            System.out.println("writeResponse ==> msg=  "+msg);
            //创建一个默认的响应对象
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            //写入数据
            response.content().writeBytes(msg.getBytes(StandardCharsets.UTF_8));
            //设置响应头--content-type
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
            //设置内容长度--content-length
            HttpUtil.setContentLength(response, response.content().readableBytes());
//        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
//        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");
//        }
            mHttpMsgHandler.getmCtx().writeAndFlush(response);
        }
    }
}

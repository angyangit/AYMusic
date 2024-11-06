package com.aylib.httpnetty;

public interface NettyServer {
    /**
     * 启动服务
     *
     * @param port 监听端口
     */
    void start(int port,InnerConnectLinstener mLinstener);
}

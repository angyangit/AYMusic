package com.aylib.httpnetty;

public interface InnerConnectLinstener {
    void requestData( String uri,String requestData);
    void requestComplete();
    void requestException( String exceptionMsg);
    void connectInitActive( );
    void connectInactive();
    void readeTriggered( Object evt);
    void ServerStart(boolean succ);
}

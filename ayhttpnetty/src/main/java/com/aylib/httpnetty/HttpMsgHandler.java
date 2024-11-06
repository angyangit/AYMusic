package com.aylib.httpnetty;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.IOException;
import java.nio.charset.Charset;


/**
 * http协议格式数据的处理器
 */
public class HttpMsgHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private InnerConnectLinstener mListener;
    private ChannelHandlerContext mCtx;


    public HttpMsgHandler(InnerConnectLinstener mListener) {
        this.mListener = mListener;
    }

    public ChannelHandlerContext getmCtx() {
        return mCtx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {

        if (mListener != null) {
            mCtx = ctx;
            String requestData = httpRequest.content().toString(Charset.forName("GBK"));
            String uri = httpRequest.uri();
            mListener.requestData(uri, requestData);
        }

//        String resBody = "";
//        String uri = httpRequest.uri();
//        Log.d("httpRequest uri  ", uri);
//        String jsonStr = httpRequest.content().toString(StandardCharsets.UTF_8);
//
//        if ("/facereq/heartbeat".equals(uri)) {
//            resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/heartbeat\"}";
//            Log.d("HttpMsgHandler", "00-----");
//        } else if ("/facereq/capture".equals(uri)) {
//            resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/capture\"}";
//            Log.d("HttpMsgHandler", "11-----");
//        }
//        writeResponse(ctx, httpRequest, HttpResponseStatus.OK, resBody);
        //1.根据请求类型做出处理
//        HttpMethod type = httpRequest.method();
//        if (type.equals(HttpMethod.GET)) {
//            //Get请求
//            String getRespBody = parseGet(httpRequest);
//            if (!StringUtil.isNullOrEmpty(getRespBody)) {
//                resBody = getRespBody;
//            }
//        } else if (type.equals(HttpMethod.POST)) {
//            parsePost(httpRequest);
//            Log.d("httpRequest   ", JSONObject.toJSONString(httpRequest));
//            Log.d("httpRequest headers  ", JSONObject.toJSONString(httpRequest.headers()));
//            String uri = httpRequest.uri();
//            Log.d("httpRequest uri  ", uri);
//            if ("/facereq/heartbeat".equals(uri)) {
//                resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/heartbeat\"}";
//                Log.d("HttpMsgHandler", "00-----");
//            } else if ("/facereq/capture".equals(uri)) {
//                resBody = "{\"isSuccess\":true,\"reqUrl\":\"/facereq/capture\"}";
//                Log.d("HttpMsgHandler", "11-----");
//            }
//            writeResponse(ctx, httpRequest, HttpResponseStatus.OK, resBody);
//        } else {
//            Log.d("HttpMsgHandler", "不支持的请求方式,{}" + type);
//        }
    }


    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
//        Log.d("HttpMsgHandler", "channelReadComplete");
//        ctx.flush();
        if (mListener != null) {
            mListener.requestComplete();
        }
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
//        Log.d("HttpMsgHandler", "exceptionCaught: {}" + cause.toString());
        ctx.close();//抛出异常，断开与客户端的连接
        if (mListener != null) {
            mListener.requestException(cause.getMessage());
        }
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelActive(ctx);
//        ctx.channel().read();
//        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
//        String clientIp = insocket.getAddress().getHostAddress();
//        //此处不能使用ctx.close()，否则客户端始终无法与服务端建立连接
//        Log.d("HttpMsgHandler", "channelActive:" + clientIp + ctx.name());
        if (mListener != null) {
            mListener.connectInitActive();
        }
    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
//        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
//        String clientIp = insocket.getAddress().getHostAddress();
//        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
//        Log.d("HttpMsgHandler", "channelInactive:" + clientIp);
        if (mListener != null) {
            mListener.connectInactive();
        }
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
//        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
//        String clientIp = insocket.getAddress().getHostAddress();
//        ctx.close();//超时时断开连接
//        Log.d("HttpMsgHandler", "userEventTriggered:" + clientIp);
        if (mListener != null) {
            mListener.readeTriggered(evt);
        }
    }


    /**
     * 给客户端响应
     *
     * @param ctx
     * @param fullHttpRequest
     * @param status
     * @param msg
     */
//    private void writeResponse(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest, HttpResponseStatus status, String msg) {
//        //创建一个默认的响应对象
//        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
//        //写入数据
//        response.content().writeBytes(msg.getBytes(StandardCharsets.UTF_8));
//        //设置响应头--content-type
//        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
//        //设置内容长度--content-length
//        HttpUtil.setContentLength(response, response.content().readableBytes());
//        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
//        if (keepAlive) {
//            response.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");
//        }
//        ctx.writeAndFlush(response);
//    }

    /**
     * 处理get请求
     *
     * @param httpRequest
     */
//    private String parseGet(FullHttpRequest httpRequest) {
//        Log.d("request uri: {}", httpRequest.uri());
//        //通过请求url获取参数信息
//        Map<String, String> paramMap = parseKvStr(httpRequest.uri(), true);
//
//        String responseBody = null;
//
//        if (httpRequest.uri().contains("/ping?flag=")) {
//            String flag = paramMap.get("flag");
//            Log.d("flag: {}", flag);
//            responseBody = flag;
//
//            return responseBody;
//        }
//
//        return responseBody;
//    }

    /**
     * 从url中获取参数信息
     *
     * @param uri     请求的url
     * @param hasPath
     */
//    private Map<String, String> parseKvStr(String uri, boolean hasPath) {
//        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri, StandardCharsets.UTF_8, hasPath);
//        Map<String, List<String>> parameters = queryStringDecoder.parameters();
//        Map<String, String> queryParams = new HashMap<>();
//        for (Map.Entry<String, List<String>> attr : parameters.entrySet()) {
//            for (String attrVal : attr.getValue()) {
//                queryParams.put(attr.getKey(), attrVal);
//            }
//        }
//        return queryParams;
//    }

    /**
     * 处理post请求
     * application/json
     * application/x-www-form-urlencoded
     * multipart/form-data
     *
     * @param httpRequest
     */
//    private void parsePost(FullHttpRequest httpRequest) {
//        String contentType = getContentType(httpRequest);
//
//        switch (contentType) {
//            case "application/json":
//                parseJson(httpRequest);
//                break;
//            case "application/x-www-form-urlencoded":
//                parseFormData(httpRequest);
//                break;
//            case "multipart/form-data":
//                parseMultipart(httpRequest);
//                break;
//            default:
//                Log.d("不支持的数据类型:{}", contentType);
//                break;
//        }
//
//    }


    /**
     * 处理文件上传
     * 在该方法中的解析方式，同样也适用于解析普通的表单提交请求
     * 通用（普通post，文件上传）
     *
     * @param httpRequest
     */
//    private void parseMultipart(FullHttpRequest httpRequest) {
//        HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(httpRequest);
//        //判断是否是multipart
//        if (httpPostRequestDecoder.isMultipart()) {
//            //获取body中的数据
//            List<InterfaceHttpData> bodyHttpDatas = httpPostRequestDecoder.getBodyHttpDatas();
//            for (InterfaceHttpData dataItem : bodyHttpDatas) {
//                //判断表单项的类型
//                InterfaceHttpData.HttpDataType httpDataType = dataItem.getHttpDataType();
//                if (httpDataType.equals(InterfaceHttpData.HttpDataType.Attribute)) {
//                    //普通表单项,直接获取数据
//                    Attribute attribute = (Attribute) dataItem;
//                    try {
//                        Log.d("HttpMsgHandler", "表单项名称:{},表单项值:{}" + attribute.getName() + "   " + attribute.getValue());
//                    } catch (IOException e) {
//                        Log.d("HttpMsgHandler", "获取表单项数据错误,msg={}" + e.getMessage());
//                    }
//                } else if (httpDataType.equals(InterfaceHttpData.HttpDataType.FileUpload)) {
//                    //文件上传项，将文件保存到磁盘
//                    FileUpload fileUpload = (FileUpload) dataItem;
//                    //获取原始文件名称
//                    String filename = fileUpload.getFilename();
//                    //获取表单项名称
//                    String name = fileUpload.getName();
//                    Log.d("HttpMsgHandler", "文件名称:{},表单项名称:{}" + filename + "   " + name);
//                    //将文件保存到磁盘
//                    if (fileUpload.isCompleted()) {
//                        try {
//                            String path = DiskFileUpload.baseDirectory + File.separator + filename;
//                            System.out.println("path: " + path);
//                            fileUpload.renameTo(new File(path));
//                        } catch (IOException e) {
//                            Log.d("HttpMsgHandler", "文件转存失败,msg={}" + e.getMessage());
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * 处理表单数据
     *
     * @param httpRequest
     */
//    private void parseFormData(FullHttpRequest httpRequest) {
//        //两个部分有数据  uri，body
//        parseKvStr(httpRequest.uri(), true);
//        parseKvStr(httpRequest.content().toString(StandardCharsets.UTF_8), false);
//    }

    /**
     * 处理json数据
     *
     * @param httpRequest
     */
//    private void parseJson(FullHttpRequest httpRequest) {
//        String jsonStr = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            jsonStr = httpRequest.content().toString(StandardCharsets.UTF_8);
//        }
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//            for (String key : jsonObject.keySet()) {
//                Log.d("HttpMsgHandler", "key=  " + key);
//                Log.d("HttpMsgHandler", "val=   " + jsonObject.get(key
//                ));
//            }
//        }
//    }

    /**
     * 获取请求数据类型
     *
     * @param httpRequest
     * @return
     */
//    private String getContentType(FullHttpRequest httpRequest) {
//        HttpHeaders headers = httpRequest.headers();
//        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
//        return contentType.split(";")[0];
//    }

}

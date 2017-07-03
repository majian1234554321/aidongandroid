package com.leyuan.aidong.http.api.exception;

/**
 * 对服务器返回的错误码进行统一处理
 */
public class ServerException extends RuntimeException {



    public ServerException(String detailMessage) {
        super(detailMessage);
    }


}


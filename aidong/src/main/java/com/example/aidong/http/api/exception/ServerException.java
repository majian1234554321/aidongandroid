package com.example.aidong.http.api.exception;

/**
 * 对服务器返回的错误码进行统一处理
 */
public class ServerException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;

    public ServerException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ServerException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code 状态码
     * @return 错误信息
     */
    private static String getApiExceptionMessage(int code){
        String message;
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            default:
                message = "未知错误";
                break;
        }
        return message;
    }
}


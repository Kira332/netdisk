package com.small2.result;

public class ResultFactory {
    public static Result buildSuccessResult(Object data) {
        return buildResult(400, "Success", data);
    }

    public static Result buildFailResult(String message) {
        return buildResult(200, message, null);
    }

    public static Result buildResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }
}

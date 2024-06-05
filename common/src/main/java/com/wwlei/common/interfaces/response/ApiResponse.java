package com.wwlei.common.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static ApiResponse<?> success(Object data) {
        return new ApiResponse<>(200, "success", data);
    }
}

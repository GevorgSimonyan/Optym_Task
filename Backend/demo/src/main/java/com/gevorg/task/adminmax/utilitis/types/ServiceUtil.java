package com.gevorg.task.adminmax.utilitis.types;

public class ServiceUtil {
    public static <T> ServiceResponse<T> wrapToServiceResult(T data, String message, int status) {
        ServiceResponse<T> resp = new ServiceResponse<>(data, message, status);
        return resp;
    }
}

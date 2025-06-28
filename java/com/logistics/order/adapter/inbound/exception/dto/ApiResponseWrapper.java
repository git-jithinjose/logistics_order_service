package com.logistics.order.adapter.inbound.exception.dto;

import java.util.Collections;
import java.util.List;

public record ApiResponseWrapper <T>(boolean success, T data, List<ApiError> errors) {



 public static <T> ApiResponseWrapper<T> success(T data) {
     return new ApiResponseWrapper<>(true, data, Collections.emptyList());
 }

 public static <T> ApiResponseWrapper<T> failure(T data) {
     return new ApiResponseWrapper<>(false, null, null);
 }

 public static <T> ApiResponseWrapper<T> failure(List<ApiError> errors) {
     return new ApiResponseWrapper<>(false, null, errors);
 }


}


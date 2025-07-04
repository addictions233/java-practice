package com.one.es.v7.dto;

import lombok.Data;

@Data
public class ResultDto<T> {

    private boolean success;

    private int httpCode;

    private String message;

    private String Tag;

    private T data;
}
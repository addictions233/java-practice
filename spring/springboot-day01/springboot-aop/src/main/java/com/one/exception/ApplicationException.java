package com.one.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: ApplicationException
 * @Description: 自定义项目中可以使用的异常
 * @Author: one
 * @Date: 2022/03/24
 */
@Data
@Setter
@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {
    /**
     * 异常状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;
}

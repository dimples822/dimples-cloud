package com.dimples.common.exception;

import com.dimples.common.eunm.CodeAndMessageEnum;
import com.dimples.common.transport.ResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础异常捕捉
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/27
 */
@Slf4j
public class BaseExceptionHandler {

    /**
     * Exception
     *
     * @param e Exception
     * @return ResponseDTO
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handleException(Exception e) {
        log.error("系统内部异常,异常信息: ", e);
        return ResponseDTO.failed(CodeAndMessageEnum.SERVER_ERROR);
    }

    /**
     * BizException
     *
     * @param e BizException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handleBizException(BizException e) {
        log.error("业务错误", e);
        return ResponseDTO.failed(e.getMessage());
    }

    /**
     * AccessDeniedException
     *
     * @param e AccessDeniedException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDTO handleAccessDeniedException() {
        return ResponseDTO.failed(CodeAndMessageEnum.NOT_AUTH);
    }

}
















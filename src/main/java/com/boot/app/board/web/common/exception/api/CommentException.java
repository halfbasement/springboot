package com.boot.app.board.web.common.exception.api;

import com.boot.app.board.web.comment.CommentApiController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(assignableTypes = CommentApiController.class)
public class CommentException {

    @Data
    @AllArgsConstructor
    static class ErrorResult{
        String errorCode;
        String message;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e){
        log.error("Comment Exception",e);

        return new ErrorResult("500",e.getMessage());
    }
}

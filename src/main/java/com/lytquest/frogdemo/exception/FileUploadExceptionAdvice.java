package com.lytquest.frogdemo.exception;

import com.lytquest.frogdemo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class FileUploadExceptionAdvice extends Exception {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApiResponse handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return new ApiResponse("File too large!");
    }
}

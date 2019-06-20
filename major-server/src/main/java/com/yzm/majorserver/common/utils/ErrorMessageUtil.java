package com.yzm.majorserver.common.utils;

import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 19:00 2019/6/20
 * ===========================
 */

public class ErrorMessageUtil {

    public static String getErrorMsg(List<ObjectError> allErrors) {
        List<String> errorMsg = new ArrayList<>();
        allErrors.forEach(error -> {
            errorMsg.add(error.getDefaultMessage());
        });
        return errorMsg.toString();

    }
}

package manager.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = {"manager.controller"})
public class ErrorControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e){
        Map<String, Object> errorAttributes = new HashMap<>();
        String errorcode = e.getMessage();

        ErrorEnum errorEnum = ErrorEnum.getByCode(errorcode);
        errorAttributes.put("message",errorEnum.getMessage() );
        errorAttributes.put("code",errorEnum.getCode() );
        errorAttributes.put("canRetry",errorEnum.isCanRetry() );
        //这里再抛一个异常就到basicerror里了
        Assert.isNull(errorAttributes,"advice" );
        errorAttributes.put("type","advice");

        return new ResponseEntity(errorAttributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

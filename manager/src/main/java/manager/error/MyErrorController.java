package manager.error;


import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MyErrorController extends BasicErrorController{
    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        errorAttributes.remove("timestamp");
        errorAttributes.remove("error");
        errorAttributes.remove("path");
        String message = (String)errorAttributes.get("message");
        errorAttributes.remove("message");
        errorAttributes.remove("status");

        ErrorEnum errorEnum = ErrorEnum.getByCode(message);
        errorAttributes.put("message",errorEnum.getMessage() );
        errorAttributes.put("code",errorEnum.getCode() );
        errorAttributes.put("canRetry",errorEnum.isCanRetry() );

        return errorAttributes;
    }
}

package sw.melody.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/***
 * Created by ping on 2018/2/26
 *
 * 系统级异常处理器
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public Map<String, Object> jsonErrorHandler(HttpServletRequest req, GlobalException e) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("code", GlobalException.ErrorCode);
        map.put("url", req.getRequestURL().toString());
        map.put("data", "Unknown data");
        return map;
    }
}

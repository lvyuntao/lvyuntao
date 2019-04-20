package com.lvyuntao.aop;
import com.alibaba.fastjson.JSONObject;
import com.lvyuntao.model.base.BaseMessage;
import com.lvyuntao.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * AOPLog 功能
 */
@Aspect
@Configuration
public class AopLog {

    private static Logger logger=LoggerFactory.getLogger(AopLog.class);
    @Pointcut("execution(* com.lvyuntao.api..*(..)) && !@annotation(com.lvyuntao.annotation.NoAopFitter)")
    public void log() {
    }

    @Around("log()")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String url = "";
        String parmsStr = "";
        Object result = null;
        String resultStr = "";
        String requestIp = "";

        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            if (ra != null) {
                ServletRequestAttributes sra = (ServletRequestAttributes) ra;
                HttpServletRequest request = sra.getRequest();
                if (request != null) {
                    requestIp = getIpAdrress(request);
                }
            }

        } catch (Exception ex) {
            //获取失败  不做其他处理
        }

        try {
            String[] methodMapping = null;
            String[] classMapping = null;
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            //找放方法的mrp值;
            RequestMapping mrp = realMethod.getAnnotation(RequestMapping.class);
            if (mrp != null) {//如果不存在RequestMapping注解则在接口中找
                methodMapping = mrp.value();
            } else {
                Class[] classes = joinPoint.getTarget().getClass().getInterfaces();
                if (classes != null && classes.length > 0) {
                    Class clazz = classes[0];//取第一个接口
                    Method method1 = clazz.getMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
                    mrp = method1.getAnnotation(RequestMapping.class);
                    if (mrp != null) {
                        methodMapping = mrp.value();
                    }
                }
            }
            //找放类的mrp值;
            RequestMapping crp = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
            if (crp != null) {//如果不存在RequestMapping注解则在接口中找
                classMapping = crp.value();
            } else {
                Class[] classes = joinPoint.getTarget().getClass().getInterfaces();
                if (classes != null && classes.length > 0) {
                    Class clazz = classes[0];//取第一个接口
                    crp = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
                    if (crp != null) {
                        classMapping = mrp.value();
                    }
                }
            }
            if (classMapping != null && classMapping.length > 0) {
                url += classMapping[0];
            }
            if (methodMapping != null && methodMapping.length > 0) {
                url += methodMapping[0];
            }
            String[] parmsName = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            if (parmsName != null && parmsName.length > 0) {
                Map<String, Object> parms = new HashMap<>();
                for (int i = 0; i < parmsName.length; i++) {
                    try {
//                        //如果是是字符串 转换成 utf-8
//                        if (args[i] instanceof String) {
//                            parms.put(parmsName[i], StringUtils.toUtf8((String) args[i]));
//                        } else {
                            parms.put(parmsName[i], args[i]);
//                        }
                    } catch (Exception e) {
                    }
                }
                try {
                    parmsStr = JSONObject.toJSONString(parms);//防止二进制流无法转化
                } catch (Exception e) {
                    parmsStr = "参数无法转化为Json对象";
                }
            }
        } catch (Exception e) {

        }
        Exception exception = null;
        //执行代理方法返回值
        try {
            result = joinPoint.proceed(args);
        } catch (Exception ex) {
            exception = ex;

        } finally {
            if (exception == null) {
                try {
                    resultStr = JSONObject.toJSONString(result);
                    //去除结果中的 body 等值
                    try {
                        JSONObject jsonObject = JSONObject.parseObject(resultStr);
                        if (jsonObject.containsKey("body") && jsonObject.containsKey("statusCode")
                                && "OK".equals(jsonObject.getString("statusCode"))) {
                            resultStr = jsonObject.getString("body");
                        }
                    } catch (Exception ex) {
                        //处理失败不做其他处理
                    }

                } catch (Exception e) {
                    resultStr = "结果无法转化为Json对象";
                }
                if (resultStr != null && resultStr.length() > 1000) {
                    resultStr = resultStr.substring(0, 1000);
                }
                //记录日志
                String logInfo = CommonUtil.format("请求url：{0}，请求参数：{1}，客户端地址 {2}，返回值：{3}", url, parmsStr, requestIp, resultStr);
                logger.info(logInfo);
            } else {
                result = BaseMessage.error(500,"未知异常",getStackTrace(exception));
                //记录日志
                String logInfo = CommonUtil.format("请求url：{0}，请求参数：{1}，客户端地址 {2}，返回值：{3}", url, parmsStr, requestIp, resultStr);
                logger.error(logInfo, exception);
            }
            return result;
        }


    }


    private String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

    /**
     * 获取错误的堆栈信息
     * @param throwable
     * @return
     */
    private String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();
        }
    }
}

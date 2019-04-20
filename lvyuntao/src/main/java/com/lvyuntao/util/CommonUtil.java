package com.lvyuntao.util;

import org.apache.commons.lang.time.DateUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * Created by SF on 2019/3/24.
 */
public class CommonUtil {


    /**
     * 格式化字符串 支持常用的格式
     *
     * @param pattern 需要格式化的字符串
     * @param params  格式化参数
     * @return 返回字符串   如 format("hell {0}。", "张三") => "hello 张三。"
     */
    public static String format(String pattern, String... params) {
        pattern = pattern == null ? "" : pattern;
        if (null == params || params.length == 0) {
            return pattern;
        }
        for (int i = 0; i < params.length; ++i) {
            String item = params[i];
            String parm;
            if (item == null) {
                parm = "";
            } else {
                parm = item;
            }
            if (parm == null || "null".equals(parm)) {
                parm = "";
            }
            pattern = pattern.replace(String.format("{%d}", i), parm);
        }
        return pattern;
    }

    public static String getStackTrace(Throwable throwable)
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

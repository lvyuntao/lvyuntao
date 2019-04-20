package com.lvyuntao.annotation;

import java.lang.annotation.*;

/**
 * Created by SF on 2019/3/24.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoAopFitter {
}

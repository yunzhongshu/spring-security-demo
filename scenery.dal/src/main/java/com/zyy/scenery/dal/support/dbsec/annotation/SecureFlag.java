package com.zyy.scenery.dal.support.dbsec.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * Encrypt.java
 *
 * 字段加密
 *
 * 该annotation 标记在类上面表示该类的所有字段都要解密和加密
 * 否则标记在字段上则字段解密和加密
 *
 * </pre>
 *
 * @author zhouxiaofeng 5/26/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface SecureFlag {


    String[] secFieldName() default {};


    /**
     * 取值:
     * <p/>
     * bean javabean的类，就是DO
     * criteria 条件查询的类
     *
     * @return
     */
    String flag() default "bean";

}

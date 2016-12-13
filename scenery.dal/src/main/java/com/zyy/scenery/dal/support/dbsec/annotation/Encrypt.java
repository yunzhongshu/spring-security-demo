package com.zyy.scenery.dal.support.dbsec.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Encrypt.java
 *
 * 字段加密
 *
 * 该annotation 标记在类上面表示该类的所有字段都要加密
 * 否则标记在字段上则字段加密
 *
 * </pre>
 *
 * @author zhouxiaofeng 5/26/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Encrypt {
    /**
     * 需要加解密处理的字段名称
     *
     * @return
     */
    String[] secNameArray();
}

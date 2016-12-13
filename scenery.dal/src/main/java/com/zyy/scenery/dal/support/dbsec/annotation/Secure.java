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
@Target({ElementType.FIELD, ElementType.TYPE})
@Inherited
public @interface Secure {
}

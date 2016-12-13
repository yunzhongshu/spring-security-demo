package com.zyy.scenery.dal.support.dbsec;

import com.llq.commons.annotation.dbsec.Secure;
import com.zyy.scenery.dal.support.dbsec.annotation.Encrypt;
import com.zyy.scenery.dal.support.dbsec.annotation.SecureFlag;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * <pre>
 * DBSecInterceptor.java
 *
 *  数据库字段加密
 *
 *  Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 *  ParameterHandler (getParameterObject, setParameters)
 *  ResultSetHandler (handleResultSets, handleOutputParameters)
 *  StatementHandler (prepare, parameterize, batch, update, query)
 *
 * </pre>
 *
 * @author zhouxiaofeng 5/26/15
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class,
                CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class DBSecInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(DBSecInterceptor.class);

    public static final String UPDATE       = "update";
    public static final String QUERY        = "query";
    public static final String CRITERIA     = "criteria";
    public static final String BEAN         = "bean";
    public static final String CONDITION    = "condition";
    public static final String VALUE        = "value";
    public static final String SECOND_VALUE = "secondValue";

    //条件的名称
    private String criteriaListName = "oredCriteria";
    private String criteriaName     = "criteria";

    private DBSecTools secTools;

    private Map<Class, List<Field>> fieldsMap = new WeakHashMap<Class, List<Field>>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Method method = invocation.getMethod();

        Object[] args = invocation.getArgs();

        if (method != null) {

            String name = method.getName();
            Type[] types = method.getParameterTypes();

            if (name.equals(UPDATE)) {// 加密
                if (args.length == 2) {

                    Object updateObject = args[1];
                    if (updateObject != null) {
                        // 加密
                        ecryptField(updateObject);
                    }

                    if (args[1] != null && args[1] instanceof Map) {

                        Map local = ((Map) args[1]);
                        if (local.containsKey("example")) {
                            final Object example = local.get("example");

                            if (example != null) {
                                final SecureFlag secureFlag = example.getClass().getAnnotation(SecureFlag.class);

                                if (secureFlag != null) {
                                    modifyCriteria(example, secureFlag);
                                }
                            }
                        }

                    }
                }

            } else if (name.equals(QUERY)) {// 解密
                switch (args.length) {

                    case 4:
                    case 6:

                        // 请求加密
                        Object queryArg = args[1];

                        if (queryArg != null) {

                            // 加密
                            ecryptField(queryArg);
                        }

                        Object proceed = invocation.proceed();

                        // 处理结果解密
                        if (proceed instanceof List) {

                            List result = (List) proceed;

                            for (Object obj : result) {
                                if (obj == null) {
                                    continue;
                                }

                                // 解密
                                decryptField(obj);
                            }
                        }

                        return proceed;
                }
            }

        }

        return invocation.proceed();
    }

    private void ecryptField(Object paramObj) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {


        final SecureFlag secureFlag = paramObj.getClass().getAnnotation(SecureFlag.class);

        if (secureFlag != null) {


            if (secureFlag.flag().equals(BEAN)) {
                List<Field> fieldList = getSecFields(paramObj);
                if (fieldList != null && fieldList.size() > 0) {
                    for (Field field : fieldList) {

                        String plaintxt = BeanUtils.getProperty(paramObj, field.getName());

                        if (StringUtils.isNotBlank(plaintxt)) {

                            final String encode = secTools.encode(plaintxt);

                            if (StringUtils.isNotBlank(encode)) {
                                BeanUtils.setProperty(paramObj, field.getName(), secTools.encode(plaintxt));
                            }
                        }
                    }
                }
            } else if (secureFlag.flag().equals(CRITERIA)) {

                modifyCriteria(paramObj, secureFlag);
            }

        }

    }

    private void modifyCriteria(Object paramObj, SecureFlag secureFlag) {

        final String[] secFieldName = secureFlag.secFieldName();
        if (secFieldName == null || secFieldName.length == 0) {
            return;
        }

        Object criteriaList = null;
        try {
            criteriaList = FieldUtils.readField(paramObj, criteriaListName, true);

            if (criteriaList != null && criteriaList instanceof List) {
                for (Object obj : (List) criteriaList) {

                    if (obj != null) {
                        final Object criteria = FieldUtils.readField(obj, criteriaName, true);
                        if (criteria != null && criteria instanceof List) {

                            for (Object criterItem : (List) criteria) {

                                if (criterItem != null) {

                                    final Object condition = FieldUtils.readField(criterItem, CONDITION, true);

                                    if (condition != null && condition instanceof String) {

                                        final String conditionstr = String.valueOf(condition);

                                        for (String secField : secFieldName) {

                                            if (conditionstr.contains(secField)) {
                                                final Object value = FieldUtils.readField(criterItem, VALUE, true);

                                                if (value != null && value instanceof String) {
                                                    final String encode = secTools.encode(String.valueOf(value));
                                                    if (StringUtils.isNotBlank(encode)) {
                                                        FieldUtils.writeField(criterItem, VALUE, encode, true);
                                                    }
                                                }

                                                final Object secondValue = FieldUtils.readField(criterItem, SECOND_VALUE, true);

                                                if (secondValue != null && secondValue instanceof String) {
                                                    final String encode = secTools.encode(String.valueOf(value));

                                                    if (StringUtils.isNotBlank(encode)) {
                                                        FieldUtils.writeField(criterItem, SECOND_VALUE, encode, true);
                                                    }
                                                }
                                                break;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            logger.error("Read criterial field exception.", e);
        }
    }

    private void decryptField(Object paramObj) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        final SecureFlag secureFlag = paramObj.getClass().getAnnotation(SecureFlag.class);

        if (secureFlag != null) {
            List<Field> fieldList = getSecFields(paramObj);
            if (fieldList != null && fieldList.size() > 0) {

                for (Field field : fieldList) {

                    String ciphertext = BeanUtils.getProperty(paramObj, field.getName());

                    if (StringUtils.isNotBlank(ciphertext)) {

                        final String decode = secTools.decode(ciphertext);
                        if (StringUtils.isNotBlank(decode)) {
                            BeanUtils.setProperty(paramObj, field.getName(), decode);
                        }
                    }
                }
            }
        }

    }

    private List<Field> getSecFields(Object object) {

        if (fieldsMap.containsKey(object.getClass())) {
            return fieldsMap.get(object.getClass());
        }

        List<Field> fieldList = new ArrayList<Field>();

        Class<?> loopClass = object.getClass();
        while (loopClass != null) {
            Field[] fields = loopClass.getDeclaredFields();

            object.getClass().getSuperclass();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Encrypt.class) || field.isAnnotationPresent(Secure.class)) {
                    fieldList.add(field);
                    field.setAccessible(true);
                }
            }
            fieldsMap.put(object.getClass(), fieldList);

            loopClass = loopClass.getSuperclass();
        }
        return fieldList;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public void setSecTools(DBSecTools secTools) {
        this.secTools = secTools;
    }
}

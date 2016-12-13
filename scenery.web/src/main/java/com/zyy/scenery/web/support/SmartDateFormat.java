/*
 * Copyright 2015 linglingqi Group Holding Ltd.
 */
package com.zyy.scenery.web.support;



import com.zyy.scenery.common.util.MyDateUtil;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类SmartDateFormat.java的实现描述：TODO 类实现描述
 * 
 * @author zhangyunyun Oct 7, 2015 6:03:35 PM
 */
public class SmartDateFormat extends SimpleDateFormat {

    /*
     * (non-Javadoc)
     * @see java.text.SimpleDateFormat#format(java.util.Date, java.lang.StringBuffer, java.text.FieldPosition)
     */
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        return new StringBuffer(MyDateUtil.smartFormat(date));
    }

    /*
     * (non-Javadoc)
     * @see java.text.DateFormat#parse(java.lang.String)
     */
    @Override
    public Date parse(String source) throws ParseException {
        return MyDateUtil.smartFormat(source);
    }
}

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.googlecode.httl.support.formatters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.googlecode.httl.Configurable;
import com.googlecode.httl.support.Formatter;
import com.googlecode.httl.support.util.DateUtils;

/**
 * DateFormatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class DateFormatter implements Formatter<Date>, Configurable {
    
    private String dateFormat;
    
    public void configure(Map<String, String> config) {
        String format = config.get(DATE_FORMAT);
        if (format != null && format.trim().length() > 0) {
            format = format.trim();
            new SimpleDateFormat(format).format(new Date());
            this.dateFormat = format;
        }
    }
    
    public String format(Date value) {
        return DateUtils.formatDate(value, dateFormat);
    }
    
}

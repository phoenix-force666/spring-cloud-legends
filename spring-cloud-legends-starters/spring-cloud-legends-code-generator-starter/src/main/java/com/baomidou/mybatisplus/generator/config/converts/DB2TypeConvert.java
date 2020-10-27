/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config.converts;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.*;

/**
 * DB2 字段类型转换
 *
 * @author zhanyao, hanchunlin
 * @since 2018-05-16
 */
public class DB2TypeConvert implements ITypeConvert {
    public static final DB2TypeConvert INSTANCE = new DB2TypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("char", "text", "json", "enum").then(STRING))
            .test(TypeConverts.contains("bigint").then(LONG))
            .test(TypeConverts.contains("smallint").then(BASE_SHORT))
            .test(TypeConverts.contains("int").then(INTEGER))
            .test(TypeConverts.containsAny("date", "time", "year").then(DATE))
            .test(TypeConverts.contains("bit").then(BOOLEAN))
            .test(TypeConverts.contains("decimal").then(BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(CLOB))
            .test(TypeConverts.contains("blob").then(BLOB))
            .test(TypeConverts.contains("binary").then(BYTE_ARRAY))
            .test(TypeConverts.contains("float").then(FLOAT))
            .test(TypeConverts.contains("double").then(DOUBLE))
            .or(STRING);
    }

}

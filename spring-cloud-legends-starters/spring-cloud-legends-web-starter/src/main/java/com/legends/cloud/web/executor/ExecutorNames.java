package com.legends.cloud.web.executor;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/17.
 */
@Component("executorNames")
public class ExecutorNames {
    public static final String PRIMARY_EXECUTOR = "primaryThreadPool";
    public static final String SECONDARY_EXECUTOR = "secondaryThreadPool";
}

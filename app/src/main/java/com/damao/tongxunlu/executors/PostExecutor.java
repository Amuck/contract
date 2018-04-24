package com.damao.tongxunlu.executors;

import io.reactivex.Scheduler;

public interface PostExecutor {
    Scheduler getScheduler();
}

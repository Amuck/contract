package com.damao.tongxunlu.retrofit;


import com.damao.tongxunlu.executors.JobExecutor;
import com.damao.tongxunlu.executors.PostExecutor;
import com.damao.tongxunlu.executors.ThreadExecutor;
import com.damao.tongxunlu.executors.UIExecutor;

/**
 * Created by chenlong on 2017/3/28.
 * <p>
 * used to access data from the net interface
 */
public class ApiWrapper extends RetrofitUtil {
    private PostExecutor mPostExecutor;
    private ThreadExecutor mThreadExecutor;
    private static ApiWrapper INSTANCE = new ApiWrapper();

    private ApiWrapper() {
        mThreadExecutor = JobExecutor.getInstance();
        mPostExecutor = UIExecutor.getInstance();
    }

    public static ApiWrapper getInstance() {
        return INSTANCE;
    }


}

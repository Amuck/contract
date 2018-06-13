package com.damao.tongxunlu.util;

import android.content.Context;
import android.os.Bundle;

import com.damao.tongxunlu.BaseApplication;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class VoiceUtils {
    private static VoiceUtils mVoice;

    public static VoiceUtils getInstance(Context context) {
        synchronized (VoiceUtils.class) {
            if (null == mVoice) {
                mVoice = new VoiceUtils();
                init(context);
            }
            return mVoice;
        }
    }

    // 语音合成对象
    private static SpeechSynthesizer mTts;
    // 默认发音人
    private static String voicer = "xiaoyan";
    //初始化监听器
    private InitListener Listener;
    //播放监听器
    private SynthesizerListener mSynthesizerListener;

    private static InitListener myInitListener = code -> {  };

    private static void init(Context context) {

        if (null == mTts) {
            mTts = SpeechSynthesizer.createSynthesizer(context, myInitListener);
        }
        if (null == mTts)
            loadLib(context);

        mTts = SpeechSynthesizer.createSynthesizer(context, myInitListener);
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "2");
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
    }


    private static void loadLib(Context context) {
        String processName = BaseApplication.getInstance().getProcessName(context, android.os.Process.myPid());
        if (processName != null) {

            boolean defaultProcess = processName.contains("com.damao.tongxunlu");
            if (defaultProcess) {
                //当前应用的初始化
                SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5a94b84c");//初始化讯飞语音
            }
        }
    }

    public void stopSpeaking() {
        if (null != mTts) {
            mTts.stopSpeaking();
        }
    }

    public void startSpeaking(final String msg,final Context context) {
        if (null == mSynthesizerListener) {
            mSynthesizerListener = new SynthesizerListener() {
                @Override
                public void onSpeakBegin() {
                }

                @Override
                public void onBufferProgress(int i, int i1, int i2, String s) {
                }

                @Override
                public void onSpeakPaused() {
                }

                @Override
                public void onSpeakResumed() {
                }

                @Override
                public void onSpeakProgress(int i, int i1, int i2) {
                }

                @Override
                public void onCompleted(SpeechError speechError) {
                }

                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {
                }
            };
        }

        if (null != mTts) {
            mTts.startSpeaking(msg, mSynthesizerListener);
        }
    }
}

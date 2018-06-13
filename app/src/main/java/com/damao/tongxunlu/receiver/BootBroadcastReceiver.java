package com.damao.tongxunlu.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.damao.tongxunlu.util.VoiceUtils;

public class BootBroadcastReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(new PhoneListener(context), PhoneStateListener.LISTEN_CALL_STATE);
    }

    private final class PhoneListener extends PhoneStateListener {
        private Context context;

        PhoneListener(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:   //来电
                        VoiceUtils.getInstance(context).startSpeaking(incomingNumber, context);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:   //接通电话
                    case TelephonyManager.CALL_STATE_IDLE:  //挂掉电话
                        VoiceUtils.getInstance(context).stopSpeaking();
                        break;
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
}

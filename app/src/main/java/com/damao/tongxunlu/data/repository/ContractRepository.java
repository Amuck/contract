package com.damao.tongxunlu.data.repository;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;

import com.damao.tongxunlu.BaseApplication;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.entity.BaseEntity;
import com.damao.tongxunlu.entity.CallEntity;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;
import com.damao.tongxunlu.entity.CllLogEntity;
import com.damao.tongxunlu.executors.RxSchedulersHelper;
import com.damao.tongxunlu.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by chenlong on 2018/3/5.
 * 通话记录
 */

public class ContractRepository {
    /**
     * 获取通话记录
     * @param type
     * @return
     */
    private Flowable<CllLogEntity> getData(int type) {
        return Flowable.create(e -> {
            e.onNext(initListItem(type));
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * @param type -1为全部通话; 未接MISSED_TYPE = 3
     * @return
     */
    public Flowable<CllLogEntity> getCallList(int type) {
        return getData(type)
                .compose(RxSchedulersHelper.io_main());
    }

    /**
     * @param filterType
     * @return
     */
    private TreeMap<String, ArrayList<CallEntity>> getCallLogs(int filterType) {
        TreeMap<String, ArrayList<CallEntity>> callLogs = new TreeMap<>();
        String lastPhone = "";
        //用于记录每个号码的最后通话日期
        ContentResolver cr = BaseApplication.getAppContext().getContentResolver();
        Cursor cursor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (BaseApplication.getAppContext().checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                cursor = cr.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                        null, null, null, CallLog.Calls.NUMBER + "," + CallLog.Calls.DATE + " DESC");
            } else {
                Util.showToast(R.string.phone_log);
            }
        } else {
            cursor = cr.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                    null, null, null, CallLog.Calls.NUMBER + "," + CallLog.Calls.DATE + " DESC");
        }
        if (cursor == null) {
            return callLogs;
        }
        while (cursor.moveToNext()) {
            CallEntity callEntity = new CallEntity();
            //备注名称
            callEntity.setRemarkName(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
            //通话类型
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

            // 未接来电
            if (filterType == CallLog.Calls.MISSED_TYPE) {
                if (type != CallLog.Calls.MISSED_TYPE) {
                    continue;
                }
            }
            if (filterType == CallLog.Calls.OUTGOING_TYPE) {
                if (type != CallLog.Calls.OUTGOING_TYPE) {
                    continue;
                }
            }

            callEntity.setType(type);
            //通话日期
            String date = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE));
            callEntity.setDates(date);
            //通话时间
            callEntity.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION)));
            //通话号码
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)).trim();

            //如果号码存在就继续存通话记录
            if (number.equals(lastPhone)) {
                ArrayList<CallEntity> callLogBeens = callLogs.get(number);
                callLogBeens.add(callEntity);
            } else {
                ArrayList<CallEntity> callLogBeens = new ArrayList<>();
                callLogBeens.add(callEntity);
                callLogs.put(number, callLogBeens);
            }
            lastPhone = number;
        }
        cursor.close();
        return callLogs;
    }

    private CllLogEntity initListItem(int type) {
        ArrayList<CallLogNeedAllBean> result = new ArrayList<>();
        TreeMap<String, ArrayList<CallEntity>> treeMap = getCallLogs(type);
        Set<Map.Entry<String, ArrayList<CallEntity>>> entry = treeMap.entrySet();
        for (Map.Entry<String, ArrayList<CallEntity>> date : entry) {
            // 电话号码
            String number = date.getKey();
            // 通话记录列表
            ArrayList<CallEntity> callEntities = date.getValue();
            //通话次数
            int count = callEntities.size();

            Collections.sort(callEntities);

            //备注名称
            String remarkName = callEntities.get(0).getRemarkName();
            //最后通话日期
            Long lastDate = Long.valueOf(callEntities.get(0).getDates());
            //最后通话类型
            int lastType = callEntities.get(0).getType();//因为已经排好序了,所以直接拿第一个就可以拿到最后通话类型

            CallLogNeedAllBean callLogNeedAllBean = new CallLogNeedAllBean();
            callLogNeedAllBean.setNumber(number);
            callLogNeedAllBean.setType(lastType);
            callLogNeedAllBean.setRemarkName(remarkName);
            callLogNeedAllBean.setCount(count);
            callLogNeedAllBean.setLastDateLong(lastDate);
            //最后通话日期,把他变成date对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = new Date(lastDate);
            String strDate = sdf.format(date1);
            callLogNeedAllBean.setLastDate(strDate);
            callLogNeedAllBean.setCallEntities(callEntities);

            result.add(callLogNeedAllBean);
        }

        Collections.sort(result);
        CllLogEntity cllLogEntity = new CllLogEntity();
        cllLogEntity.setCallLogs(result);
        cllLogEntity.setCode(BaseEntity.ENTITY_RESULT_SUCCESS);
        return cllLogEntity;
    }
}

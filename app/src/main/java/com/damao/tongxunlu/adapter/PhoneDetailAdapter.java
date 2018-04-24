package com.damao.tongxunlu.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damao.tongxunlu.R;
import com.damao.tongxunlu.entity.CallEntity;
import com.damao.tongxunlu.util.NumberUtil;
import com.damao.tongxunlu.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenlong on 2018/1/12.
 * 通话记录详情中的列表
 */

public class PhoneDetailAdapter extends RecyclerView.Adapter {
    /**
     * 数据源
     */
    private List<CallEntity> entities = new ArrayList<>();
    private Context context;

    public PhoneDetailAdapter(Context context, List<CallEntity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phone_detail_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        CallEntity callEntity = entities.get(position);
        String duration = callEntity.getDuration();
        String date = callEntity.getDates();
        String submit = "";
        String descripe = "";
        int type = callEntity.getType();
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                submit = "呼入";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                submit = "呼出";
                break;
            case CallLog.Calls.MISSED_TYPE:
                submit = "未接";
                descripe = "响铃";
                break;
            case CallLog.Calls.REJECTED_TYPE:
            case CallLog.Calls.BLOCKED_TYPE:
                submit = "拒接";
                descripe = "响铃";
                break;
        }
        if (TextUtils.isEmpty(duration) || !NumberUtil.isBiggerThan(0, duration)) {
            itemViewHolder.duration.setText("未接通");
        } else {
            duration = descripe + duration + "秒";
            itemViewHolder.duration.setText(duration);
        }
        itemViewHolder.state.setText(submit);
        if (NumberUtil.isLong(date))
            date = TimeUtil.getSMillon(Long.valueOf(date));
        itemViewHolder.time.setText(date);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    /**
     * 刷新数据源
     *
     * @param datas
     */
    public void refreshDate(ArrayList<CallEntity> datas) {
        entities.clear();
        if (datas != null && datas.size() > 0) {
            this.entities.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addList(ArrayList<CallEntity> list) {
        if (null == list || list.size() <= 0)
            return;
        int lastIndex = this.entities.size();
        entities.addAll(list);
        notifyItemRangeInserted(lastIndex, list.size());
    }

    /**
     * 获取对应的项目对象, 没有的话返回null
     *
     * @param position
     * @return
     */
    public CallEntity getItem(int position) {
        if (position >= 0 && entities.size() > 0 && position < entities.size())
            return entities.get(position);
        else
            return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.state)
        TextView state;
        @BindView(R.id.duration)
        TextView duration;
        @BindView(R.id.time)
        TextView time;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

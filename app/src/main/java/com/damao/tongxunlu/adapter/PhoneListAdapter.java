package com.damao.tongxunlu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.damao.tongxunlu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenlong on 2018/1/12.
 * 通话记录详情中的号码列表
 */

public class PhoneListAdapter extends RecyclerView.Adapter {
    public interface MyItemClickListener {
        void onCallClick(String phone, int position);

        void onMsgClick(String phone, int position);
    }

    /**
     * 数据源
     */
    private List<String> numbers = new ArrayList<>();
    private Context context;
    private MyItemClickListener myItemClickListener = null;

    public PhoneListAdapter(Context context, List<String> numbers, MyItemClickListener myItemClickListener) {
        this.context = context;
        this.numbers = numbers;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_phone_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        String num = numbers.get(position);
        itemViewHolder.call.setText(num);
        itemViewHolder.call.setOnClickListener(v -> {
            if (null != myItemClickListener)
                myItemClickListener.onCallClick(num, position);
        });
        itemViewHolder.msg.setOnClickListener(v -> {
            if (null != myItemClickListener)
                myItemClickListener.onMsgClick(num, position);
        });
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.call)
        TextView call;
        @BindView(R.id.msg)
        ImageView msg;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

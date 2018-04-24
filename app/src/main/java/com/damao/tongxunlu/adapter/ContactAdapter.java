package com.damao.tongxunlu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damao.tongxunlu.R;
import com.damao.tongxunlu.entity.FriendEntity;

import java.util.ArrayList;
import java.util.List;


/**
 */

public class ContactAdapter extends BaseRecyclerAdapter<FriendEntity,BaseHolder> {
    public interface MyItemClickListener {
        void onItemClick(ArrayList<String> phoneNumbers, int position);
    }

    /**
     * 点击事件
     */
    private MyItemClickListener itemClickListener = null;

    public ContactAdapter(Context context) {
        super(context);
    }

    public ContactAdapter(Context context, List<FriendEntity> dataList) {
        super(context, dataList);
    }

    public MyItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BaseHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(parent, R.layout.contact_item);
    }

    @Override
    public void bindCustomViewHolder(BaseHolder holder, int position) {
        FriendEntity friend=getItem(position);
        ((TextView)holder.getView(R.id.name)).setText(friend.getAccount());
        if (position==0){
            holder.getView(R.id.stick_container).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.header)).setText(friend.getFirstPinyin());
        }else {
            if (!TextUtils.equals(friend.getFirstPinyin(),getItem(position-1).getFirstPinyin())){
                holder.getView(R.id.stick_container).setVisibility(View.VISIBLE);
                ((TextView)holder.getView(R.id.header)).setText(friend.getFirstPinyin());
            }else {
                holder.getView(R.id.stick_container).setVisibility(View.GONE);
            }
        }
        holder.itemView.setContentDescription(friend.getFirstPinyin());

        holder.getView(R.id.name_panel).setOnClickListener(v -> {
            if (null != itemClickListener)
                itemClickListener.onItemClick(friend.getPhoneNumbers(), position);
        });
    }

    @Override
    protected int getCustomViewType(int position) {
        return 0;
    }
}

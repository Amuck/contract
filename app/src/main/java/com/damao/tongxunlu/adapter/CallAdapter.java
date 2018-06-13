package com.damao.tongxunlu.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.damao.tongxunlu.R;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;
import com.damao.tongxunlu.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenlong on 2018/1/12.
 * 来电拨号记录
 */

public class CallAdapter extends RecyclerView.Adapter {
//    private static final int ITEM_TYPE_FOOTER = 0;

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
        void detailClick(int position);
    }

    /**
     * 数据源
     */
    private ArrayList<CallLogNeedAllBean> entities = new ArrayList<>();
    private Context context;

//    private FooterViewHolder footerViewHolder;
    /**
     * 点击事件
     */
    private MyItemClickListener itemClickListener = null;

    public CallAdapter(Context context, MyItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == getItemCount() - 1) {
//            return ITEM_TYPE_FOOTER;
//        } else {
//            return 1;
//        }
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == ITEM_TYPE_FOOTER) {
//            if (footerViewHolder == null) {
//                View view = LayoutInflater.from(context).inflate(R.layout.layout_footer, parent, false);
//                footerViewHolder = new FooterViewHolder(view);
//                footerViewHolder.setViesible(false);
//            }
//            return footerViewHolder;
//        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_call_simple_item, parent, false);
            return new ItemViewHolder(view, itemClickListener);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            CallLogNeedAllBean callEntity = entities.get(position);
            String name = callEntity.getRemarkName();
            String number = callEntity.getNumber();
            String date = callEntity.getLastDate();
            String location = callEntity.getLocation();
            String submit = "";
            int type = callEntity.getType();
            int img = R.drawable.icon_huru;
            int color = R.color.text_color_333;
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    submit = "呼入";
                    img = R.drawable.icon_huru;
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    submit = "呼出";
                    img = R.drawable.icon_huchu;
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    submit = "未接";
                    color = R.color.not_get;
                    img = R.drawable.icon_huru;
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                case CallLog.Calls.BLOCKED_TYPE:
                    submit = "拒接";
                    img = R.drawable.icon_huru;
                    break;
            }
            if (!TextUtils.isEmpty(location))
                submit += " " +location + " " + date;
            else
                submit += " " +date;
            itemViewHolder.tvTitle.setTextColor(Util.getColor(color));
            if (TextUtils.isEmpty(name)){
                itemViewHolder.tvTitle.setText(number);
            } else {
                itemViewHolder.tvTitle.setText(name);
            }
            itemViewHolder.submit.setText(date);
            itemViewHolder.ivNext.setImageResource(img);
//            itemViewHolder.ivNext.setOnClickListener(v -> {
//                if (itemClickListener != null)
//                    itemClickListener.detailClick(position);
//            });
//        } else if (holder instanceof FooterViewHolder) {
//            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
//            footerViewHolder.title.setText(context.getString(R.string.load_new));
//        }
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
    public void refreshDate(ArrayList<CallLogNeedAllBean> datas) {
        entities.clear();
        if (datas != null && datas.size() > 0) {
            this.entities.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addList(ArrayList<CallLogNeedAllBean> list) {
        if (null == list || list.size() <= 0)
            return;
        int lastIndex = this.entities.size();
        entities.addAll(list);
        notifyItemRangeInserted(lastIndex, list.size());
    }

    /**
     * @return 是否正在加载， TRUE： 正在加载
     */
//    public boolean isLoadingNow() {
//        return footerViewHolder.isVisible();
//    }

    /**
     * 加载更多
     */
//    public void startLoad() {
//        footerViewHolder.setViesible(true);
//    }

    /**
     * 获取对应的项目对象, 没有的话返回null
     * @param position
     * @return
     */
    public CallLogNeedAllBean getItem(int position) {
        if (position >= 0 && entities.size() > 0 && position < entities.size())
            return entities.get(position);
        else
            return null;
    }

    /**
     * 加载完成
     */
//    public void loadFinish() {
//        footerViewHolder.setViesible(false);
//    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.reward)
        TextView reward;
        @BindView(R.id.iv_next)
        ImageView ivNext;
        @BindView(R.id.submit)
        TextView submit;
        @BindView(R.id.iv_type)
        ImageView iv_type;


        public ItemViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (myItemClickListener != null)
                    myItemClickListener.onItemClick(v, getAdapterPosition());
            });
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView title;

        View itemView;
        private boolean isVisible = false;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.itemView = itemView;
            setViesible(false);
        }

        /**
         * is footview visible now
         *
         * @return
         */
        public boolean isVisible() {
            return isVisible;
        }

        /**
         * change the footview visible state
         *
         * @param isVisible
         */
        void setViesible(boolean isVisible) {
            this.isVisible = isVisible;
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }
    }
}

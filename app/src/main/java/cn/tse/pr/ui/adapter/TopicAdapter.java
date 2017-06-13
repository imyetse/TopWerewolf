package cn.tse.pr.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.entity.topic.TopicBean;
import cn.tse.pr.ui.widget.CircleImageView;

/**
 * Created by xieye on 2017/6/3.
 */

public class TopicAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, TopicBean> {
    public final static int TOPIC_TYPE_NORMAL = 0;
    public final static int TOPIC_TYPE_NORMAL_PIC = 1;
    public final static int TOPIC_TPPE_AD_U2 = 2;
    public final static int TOPIC_TYPE_OTHER = 3;

    private Context context;

    public TopicAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getLayout(int viewType) {
        if (viewType == TOPIC_TYPE_NORMAL_PIC) {
            return R.layout.item_topic;
        } else if (viewType == TOPIC_TPPE_AD_U2) {
            return R.layout.item_topic_u2;
        } else if (viewType == TOPIC_TYPE_NORMAL) {
            return R.layout.item_topic_txt;
        } else {
            return R.layout.item_topic;
        }
    }

    @Override
    public RecyclerView.ViewHolder getHolder(int viewType) {
        if (viewType == TOPIC_TYPE_NORMAL_PIC) {
            return new TopicHolder(getRootView(viewType));
        } else if (viewType == TOPIC_TPPE_AD_U2) {
            return new TopicU2Holder(getRootView(viewType));
        } else if (viewType == TOPIC_TYPE_NORMAL) {
            return new TopicTxtHolder(getRootView(viewType));
        } else {
            return new TopicHolder(getRootView(viewType));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, TopicBean topicBean) {
        if (holder instanceof TopicHolder) {
            TopicHolder topicHolder = (TopicHolder) holder;
            topicHolder.tvTitle.setText(Html.fromHtml(topicBean.getTopicTitle()));
            topicHolder.tvJoin.setText(topicBean.getJoinCount() + "人次参与讨论");

            if (!TextUtils.isEmpty(topicBean.getDisplayUrl())) {
                Glide.with(context).load(topicBean.getDisplayUrl()).asBitmap().
                        into(topicHolder.ivBackground);
            }

            AVUser avUser = topicBean.getAvUser();
            if (avUser != null && !TextUtils.isEmpty(avUser.getString("avatar"))) {
                Glide.with(context).load(avUser.getString("avatar")).asBitmap().
                        into(((TopicHolder) holder).ivAvatar);
            }

        } else if (holder instanceof TopicU2Holder) {
            TopicU2Holder topicHolder = (TopicU2Holder) holder;
            topicHolder.tvTitle.setText(Html.fromHtml(topicBean.getTopicTitle()));
            topicHolder.tvItemLocation.setText(topicBean.getTopicDetail());

            if (!TextUtils.isEmpty(topicBean.getDisplayUrl())) {
                Glide.with(context).load(topicBean.getDisplayUrl()).asBitmap().
                        into(topicHolder.ivBackground);
            }
        } else if (holder instanceof TopicTxtHolder) {
            TopicTxtHolder topicHolder = (TopicTxtHolder) holder;
            topicHolder.tvTitle.setText(Html.fromHtml(topicBean.getTopicTitle()));
            topicHolder.tvContent.setText(topicBean.getTopicDetail());
            topicHolder.tvJoin.setText(topicBean.getJoinCount() + "人次参与讨论");

            AVUser avUser = topicBean.getAvUser();
            if (avUser != null && !TextUtils.isEmpty(avUser.getString("avatar"))) {
                Glide.with(context).load(avUser.getString("avatar")).asBitmap().
                        into(topicHolder.ivAvatar);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }


    public static class TopicHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_background)
        ImageView ivBackground;
        @Bind(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_join)
        TextView tvJoin;
        @Bind(R.id.view_click)
        View viewClick;

        public TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TopicTxtHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_join)
        TextView tvJoin;
        @Bind(R.id.view_click)
        View viewClick;

        public TopicTxtHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static class TopicU2Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_background)
        ImageView ivBackground;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_item_location)
        TextView tvItemLocation;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.view_click)
        View viewClick;

        public TopicU2Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

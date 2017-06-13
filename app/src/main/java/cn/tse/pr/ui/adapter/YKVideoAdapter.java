package cn.tse.pr.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.entity.news.YKVideoEntity;

/**
 * Created by xieye on 2017.04.16.
 */

public class YKVideoAdapter extends BaseAdapter {
    private List<NewsItemEntity> list = new ArrayList<>();
    private Context context;

    public void setDatas(List<NewsItemEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NewsItemEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            context = parent.getContext();
            view = LayoutInflater.from(context).inflate(R.layout.item_news_text_video, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        NewsItemEntity videoEntity = getItem(position);
        if (videoEntity == null) {
            return view;
        }
        String img = videoEntity.getImg();
        if (!TextUtils.isEmpty(img)) {
            if (!img.startsWith("http:")) {
                img = new StringBuilder("http:").append(img).toString();
            }
            Glide.with(context).load(img).into(viewHolder.ivRight);
        }
        viewHolder.tvTitle.setText(videoEntity.getTitle());
        if (!TextUtils.isEmpty(videoEntity.getCommentCount()) && !videoEntity.getCommentCount().equals("0")) {
            viewHolder.tvBrowse.setText(videoEntity.getCommentCount() + "人看过");
        }

        if (!TextUtils.isEmpty(videoEntity.getTimeStr())) {
            viewHolder.tvTime.setText(videoEntity.getTimeStr());
        }

        viewHolder.tvDuration.setText(videoEntity.getVideoDurationStr());
        viewHolder.tvAuthor.setText(videoEntity.getAuthor());

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.iv_right)
        ImageView ivRight;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_browse)
        TextView tvBrowse;
        @Bind(R.id.tv_duration)
        TextView tvDuration;

        public ViewHolder(View rootView) {
            ButterKnife.bind(this, rootView);
        }
    }
}

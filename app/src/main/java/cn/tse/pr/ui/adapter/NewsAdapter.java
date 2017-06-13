package cn.tse.pr.ui.adapter;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.entity.BaseEntity;
import cn.tse.pr.entity.news.NewsItemEntity;

/**
 * Created by xieye on 2017/4/10.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int VIEW_TYPE_NEWS = 0;
    public final static int VIEW_TYPE_VIDEO = 1;
    public final static int VIEW_TYPE_IMG = 2;
    public final static int VIEW_TYPE_IMGS = 3;
    public final static int VIEW_TYPE_FAQ = 4;
    public final static int VIEW_TYPE_GROUP = 5;


    private List<NewsItemEntity> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mContext = parent.getContext();
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        return getHolder(viewType);
    }

    public void setDatas(List<NewsItemEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsItemEntity itemEntity = mDatas.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_IMG:
                bindNewsImg((NewsImgHolder) holder, itemEntity);
                break;
            case VIEW_TYPE_IMGS:
                bindNewsImgs((NewsImgsHolder) holder, itemEntity);
                break;
            case VIEW_TYPE_FAQ:
                break;
            case VIEW_TYPE_GROUP:
                break;
            case VIEW_TYPE_VIDEO:
                bindNewsVideo((videoHolder) holder, itemEntity);
                break;
            default:
            case VIEW_TYPE_NEWS:
                bindNewsText((NewsHolder) holder, itemEntity);
                break;
        }
    }

    /**
     * 有视频
     *
     * @param holder
     * @param itemEntity
     */
    private void bindNewsVideo(videoHolder holder, final NewsItemEntity itemEntity) {
        if (itemEntity == null) {
            return;
        }
        String img = itemEntity.getImg();
        if (!TextUtils.isEmpty(img)) {
            if (!img.startsWith("http:")) {
                img = new StringBuilder("http:").append(img).toString();
            }
            Glide.with(mContext).load(img).into(holder.ivRight);
        }
        holder.tvTitle.setText(itemEntity.getTitle());
        if (!TextUtils.isEmpty(itemEntity.getCommentCount()) && !itemEntity.getCommentCount().equals("0")) {
            holder.tvBrowse.setText(itemEntity.getCommentCount() + "人看过");
        }

        if (!TextUtils.isEmpty(itemEntity.getTimeStr())) {
            holder.tvTime.setText(itemEntity.getTimeStr());
        }

        holder.tvDuration.setText(itemEntity.getVideoDurationStr());
        holder.tvAuthor.setText(itemEntity.getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNewsClick != null) {
                    onNewsClick.OnItemClick(itemEntity);
                }
            }
        });
    }

    /**
     * 有多张图
     *
     * @param holder
     * @param itemEntity
     */
    private void bindNewsImgs(NewsImgsHolder holder, final NewsItemEntity itemEntity) {
        if (itemEntity == null) {
            return;
        }
        List<String> imgs = itemEntity.getImgs();
        if (imgs != null && imgs.size() != 0) {
            int index = 0;
            for (String img : imgs) {
                if (!img.startsWith("http:")) {
                    img = new StringBuilder("http:").append(img).toString();
                }
                if (index == 0) {
                    Glide.with(mContext).load(img).into(holder.ivCenter1);
                } else if (index == 1) {
                    Glide.with(mContext).load(img).into(holder.ivCenter2);
                } else if (index == 2) {
                    Glide.with(mContext).load(img).into(holder.ivCenter3);
                }
                index++;
            }
        }
        if (!TextUtils.isEmpty(itemEntity.getCommentCount()) && !itemEntity.getCommentCount().equals("0")) {
            holder.tvBrowse.setText(itemEntity.getCommentCount() + "人看过");
        }

        if (!TextUtils.isEmpty(itemEntity.getTimeStr())) {
            holder.tvTime.setText(itemEntity.getTimeStr());
        }
        holder.tvTitle.setText(itemEntity.getTitle());
        holder.tvAuthor.setText(itemEntity.getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNewsClick != null) {
                    onNewsClick.OnItemClick(itemEntity);
                }
            }
        });
    }

    /**
     * 有一张图
     *
     * @param holder
     * @param itemEntity
     */
    private void bindNewsImg(NewsImgHolder holder, final NewsItemEntity itemEntity) {
        if (itemEntity == null) {
            return;
        }
        String img = itemEntity.getImg();
        if (!TextUtils.isEmpty(img)) {
            if (!img.startsWith("http:")) {
                img = new StringBuilder("http:").append(img).toString();
            }
            Glide.with(mContext).load(img).into(holder.ivRight);
        }
        if (!TextUtils.isEmpty(itemEntity.getCommentCount()) && !itemEntity.getCommentCount().equals("0")) {
            holder.tvBrowse.setText(itemEntity.getCommentCount() + "人看过");
        }

        if (!TextUtils.isEmpty(itemEntity.getTimeStr())) {
            holder.tvTime.setText(itemEntity.getTimeStr());
        }
        holder.tvTitle.setText(itemEntity.getTitle());
        holder.tvAuthor.setText(itemEntity.getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNewsClick != null) {
                    onNewsClick.OnItemClick(itemEntity);
                }
            }
        });
    }

    /**
     * 没有图
     *
     * @param holder
     * @param itemEntity
     */
    private void bindNewsText(NewsHolder holder, final NewsItemEntity itemEntity) {
        if (itemEntity == null) {
            return;
        }
        if (!TextUtils.isEmpty(itemEntity.getCommentCount()) && !itemEntity.getCommentCount().equals("0")) {
            holder.tvBrowse.setText(itemEntity.getCommentCount() + "人看过");
        }

        if (!TextUtils.isEmpty(itemEntity.getTimeStr())) {
            holder.tvTime.setText(itemEntity.getTimeStr());
        }
        holder.tvTitle.setText(itemEntity.getTitle());
        holder.tvAuthor.setText(itemEntity.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNewsClick != null) {
                    onNewsClick.OnItemClick(itemEntity);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        NewsItemEntity entity = mDatas.get(position);
        if (entity != null) {
            if (entity.isHasVideo()) {
                return VIEW_TYPE_VIDEO;
            }
            if (entity.getImgs() != null && entity.getImgs().size() > 0) {
                //多图模式
                return VIEW_TYPE_IMGS;
            }
            if (!TextUtils.isEmpty(entity.getImg())) {
                return VIEW_TYPE_IMG;
            }
            return VIEW_TYPE_NEWS;
        }
        return VIEW_TYPE_IMG;
    }

    private RecyclerView.ViewHolder getHolder(int viewType) {
        View view;
        switch (viewType) {

            case VIEW_TYPE_IMG:
                view = mLayoutInflater.inflate(R.layout.item_news_text_img, null);

                return new NewsImgHolder(view);
            case VIEW_TYPE_IMGS:
                view = mLayoutInflater.inflate(R.layout.item_news_text_imgs, null);

                return new NewsImgsHolder(view);
            case VIEW_TYPE_FAQ:
                view = mLayoutInflater.inflate(R.layout.item_news_faq, null);

                return new NewsAdapter.FaqHold(view);
            case VIEW_TYPE_GROUP:
                view = mLayoutInflater.inflate(R.layout.item_news_text_img, null);

                return new NewsImgHolder(view);
            case VIEW_TYPE_VIDEO:
                view = mLayoutInflater.inflate(R.layout.item_news_text_video, null);

                return new videoHolder(view);
            default:
            case VIEW_TYPE_NEWS:
                view = mLayoutInflater.inflate(R.layout.item_news_text, null);
                return new NewsHolder(view);
        }

    }

    static class NewsHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_browse)
        TextView tvBrowse;

        NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class NewsImgHolder extends RecyclerView.ViewHolder {

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

        NewsImgHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    static class NewsImgsHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_center1)
        ImageView ivCenter1;
        @Bind(R.id.iv_center2)
        ImageView ivCenter2;
        @Bind(R.id.iv_center3)
        ImageView ivCenter3;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_browse)
        TextView tvBrowse;


        NewsImgsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    static class videoHolder extends RecyclerView.ViewHolder {

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

        videoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    static class FaqHold extends RecyclerView.ViewHolder {

        FaqHold(View itemView) {
            super(itemView);
        }
    }

    private OnNewsClick onNewsClick;

    public void setOnNewsClick(OnNewsClick onNewsClick) {
        this.onNewsClick = onNewsClick;
    }

    public interface OnNewsClick {
        void OnItemClick(NewsItemEntity entity);
    }
}

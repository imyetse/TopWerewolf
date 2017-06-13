package cn.tse.pr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.entity.CollectionEntity;
import cn.tse.pr.ui.web.WebViewActivity;

/**
 * Created by xieye on 2017/4/14.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {

    private List<CollectionEntity> list = new ArrayList<>();

    public void setData(List<CollectionEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public CollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, null));
    }

    @Override
    public void onBindViewHolder(CollectionHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionEntity entity = list.get(position);
                WebViewActivity.openWebView(view.getContext(), entity.getLink(), entity.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CollectionHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;

        public CollectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}



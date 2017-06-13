package cn.tse.pr.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tse.pr.handle.LVOnItemClick;

/**
 * Created by xieye on 2017/6/6.
 */

public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder, U> extends RecyclerView.Adapter<T> {
    protected List<U> dataList = new ArrayList<>();
    protected Map<Integer, View> views = new HashMap<>();
    protected Context context;


    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<U> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    protected View getRootView(int viewType) {
//        if (!views.containsKey(viewType) || views.get(viewType) == null) {
        View rootView = LayoutInflater.from(context).inflate(getLayout(viewType), null);
        views.put(viewType, rootView);
//        }
        //       return views.get(viewType);
        return rootView;
    }

    public abstract int getLayout(int viewType);

    public abstract T getHolder(int viewType);

    public abstract void onBindViewHolder(T holder, int position, U u);

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return getHolder(viewType);
    }

    @Override
    public void onBindViewHolder(T holder, final int position) {
        if (onItemClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(getItemViewType(position), position, dataList.get(position));
                }
            });
        }
        onBindViewHolder(holder, position, dataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected LVOnItemClick<U> onItemClick;

    public void setOnItemClick(LVOnItemClick<U> onItemClick) {
        this.onItemClick = onItemClick;
    }
}

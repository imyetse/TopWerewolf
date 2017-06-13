package cn.tse.pr.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.tse.pr.R;
import cn.tse.pr.handle.LVOnItemClick;

/**
 * Created by xieye on 2017/6/7.
 */

public abstract class BaseListViewAdapter<T> extends BaseAdapter {
    private LayoutInflater mInflater;
    protected List<T> dataList = new ArrayList<>();
    protected Context context;

    public void setDataList(List<T> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract BaseItem<T> createItem(int type);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            context = parent.getContext();
            mInflater = LayoutInflater.from(context);
        }

        final BaseItem<T> item;
        if (convertView == null) {
            item = createItem(getItemViewType(position));
            convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
            convertView.setTag(R.id.tag_name, item); // get item

            item.bindViews(convertView);
            item.setViews();
        } else {
            item = (BaseItem) convertView.getTag(R.id.tag_name); // save item
        }
        item.handleData(getConvertedData(dataList.get(position), getItemViewType(position)), position);
        return convertView;
    }

    protected T getConvertedData(T data, Object type) {
        return data;
    }

    public abstract class BasedItem implements BaseItem<T> {
        View rootView;

        @Override
        public void handleData(final T t, final int position) {
            if (rootView != null && onItemClick != null) {
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onItemClick(getItemViewType(position), position, t);
                    }
                });
            }
        }
    }

    public interface BaseItem<T> {
        /**
         * @return item布局文件的layoutId
         */
        @LayoutRes
        int getLayoutResId();

        /**
         * 初始化views
         */
        void bindViews(final View root);

        /**
         * 设置view的参数
         */
        void setViews();

        /**
         * 根据数据来设置item的内部views
         *
         * @param t        数据list内部的model
         * @param position 当前adapter调用item的位置
         */
        void handleData(T t, int position);
    }

    protected LVOnItemClick<T> onItemClick;

    public void setOnItemClick(LVOnItemClick<T> onItemClick) {
        this.onItemClick = onItemClick;
    }
}

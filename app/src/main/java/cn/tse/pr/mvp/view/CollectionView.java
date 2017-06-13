package cn.tse.pr.mvp.view;

import java.util.List;

import cn.tse.pr.entity.CollectionEntity;

/**
 * Created by xieye on 2017/4/14.
 */

public interface CollectionView extends BaseView {

    void onListLoaded(List<CollectionEntity> list);
}

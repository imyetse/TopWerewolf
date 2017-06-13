package cn.tse.pr.mvp.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.yuzhi.fine.Lg.Lg;

import java.util.List;

import cn.tse.pr.db.AppDao;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.CollectionEntity;
import cn.tse.pr.mvp.view.CollectionView;
import cn.tse.pr.utils.SimpleRxSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xieye on 2017/4/14.
 */

public class CollectionPresenterImpl extends MVPPresenter<CollectionView> implements CollectionPresenter {


    public CollectionPresenterImpl(CollectionView view) {
        super(view);
    }

    @Override
    public void getMyCollection() {
        AVQuery<AVObject> avQuery = new AVQuery<>("Collection");
        avQuery.orderByDescending("createdAt");
        avQuery.whereContains("uid", MyInfo.getIntance().getUser().getObjectId());
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Lg.e("成功了");
                    AppDao.getIntance().setCollection(list).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleRxSubscriber<List<CollectionEntity>>(){
                                @Override
                                public void onNext(List<CollectionEntity> collectionEntities) {
                                    getView().onListLoaded(collectionEntities);
                                }
                            });
                } else {
                    Lg.e("读取失败了");
                    getView().onListLoaded(null);
                }
            }
        });
    }
}

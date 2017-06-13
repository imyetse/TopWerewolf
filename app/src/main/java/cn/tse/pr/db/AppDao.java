package cn.tse.pr.db;

import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.signature.Base64Decoder;
import com.yuzhi.fine.Lg.Lg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.tse.pr.WWApp;
import cn.tse.pr.entity.CollectionEntity;
import cn.tse.pr.entity.topic.CommentBean;
import cn.tse.pr.entity.topic.TopicBean;
import cn.tse.pr.utils.AppUtils;
import cn.tse.pr.utils.Prefs;
import cn.tse.pr.utils.SimpleRxSubscriber;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xieye on 2017/4/14.
 */

public class AppDao {
    private final static String COLLECT_KEY = "collect_key";

    private static AppDao appDap;
    private List<CollectionEntity> collections;

    public static AppDao getIntance() {
        synchronized (AppDao.class) {
            if (appDap == null) {
                appDap = new AppDao();
            }
        }
        return appDap;
    }

    public List<CollectionEntity> getCollections() {
        if (collections == null) {
            collections = JSONArray.parseArray(Prefs.with(WWApp.getInstance()).read(COLLECT_KEY + MyInfo.getIntance().getUser().getObjectId(), "[]"), CollectionEntity.class);
        }
        return collections;
    }

    public void loadCollections() {
        AVQuery<AVObject> avQuery = new AVQuery<>("Collection");
        avQuery.orderByDescending("createdAt");
        avQuery.whereContains("uid", MyInfo.getIntance().getUser().getObjectId());
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Lg.e("成功了");
                    AppDao.getIntance().setCollection(list).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleRxSubscriber<List<CollectionEntity>>() {
                                @Override
                                public void onNext(List<CollectionEntity> collectionEntities) {
                                    cleanCollection();
                                    addCollection(collectionEntities);
                                }
                            });
                } else {
                    Lg.e("读取失败了");
                }
            }
        });
    }

    public Observable<List<CollectionEntity>> setCollection(List<AVObject> list) {


        return Observable.just(list).map(new Func1<List<AVObject>, List<CollectionEntity>>() {
            @Override
            public List<CollectionEntity> call(List<AVObject> list) {
                List<CollectionEntity> result = new ArrayList<>();
                if (list == null || list.size() == 0) {
                    return result;
                }
                for (AVObject object : list) {
                    CollectionEntity entity = new CollectionEntity();
                    String title = object.getString("title");
                    String link = object.getString("link");
                    String uid = object.getString("uid");
                    String objectId = object.getObjectId();
                    entity.setLink(link);
                    entity.setTitle(title);
                    entity.setUid(uid);
                    entity.setObjectId(objectId);
                    result.add(entity);
                }
                return result;
            }
        })
                .subscribeOn(Schedulers.computation());
    }


    public Observable<List<TopicBean>> setTopic(List<AVObject> list) {

        return Observable.just(list)
                .map(new Func1<List<AVObject>, List<TopicBean>>() {
                    @Override
                    public List<TopicBean> call(List<AVObject> list) {
                        List<TopicBean> result = new ArrayList<>();
                        if (list == null || list.size() == 0) {
                            return result;
                        }
                        for (AVObject object : list) {
                            TopicBean entity = new TopicBean();
                            String title = object.getString("topicTitle");
                            String detail = object.getString("topicDetail");
                            String displayUrl = object.getString("displayUrl");
                            String redirectUrl = object.getString("url");
                            AVUser owner = object.getAVUser("owner");
                            int joinCount = object.getInt("joinCount");
                            int type = object.getInt("type");
                            String objectId = object.getObjectId();
                            entity.setAvUser(owner);
                            entity.setJoinCount(joinCount);
                            entity.setDisplayUrl(displayUrl);
                            entity.setRedirectUrl(redirectUrl);
                            entity.setTopicDetail(detail);
                            entity.setTopicTitle(title);
                            entity.setId(objectId);
                            entity.setType(type);
                            result.add(entity);
                        }
                        return result;
                    }
                })
                .subscribeOn(Schedulers.computation());
    }

    public Observable<List<CommentBean>> setComment(List<AVObject> list) {

        return Observable.just(list)
                .map(new Func1<List<AVObject>, List<CommentBean>>() {
                    @Override
                    public List<CommentBean> call(List<AVObject> list) {
                        List<CommentBean> result = new ArrayList<>();
                        if (list == null || list.size() == 0) {
                            return result;
                        }
                        for (AVObject object : list) {
                            CommentBean entity = new CommentBean();
                            String title = object.getString("title");
                            String content = object.getString("content");
                            int likeNum = object.getInt("likeNum");
                            AVUser owner = object.getAVUser("owner");
                            AVUser reviewer = object.getAVUser("reviewer");
                            AVObject topic = object.getAVObject("TargetTopic");
                            String objectId = object.getObjectId();
                            Date createDate = object.getCreatedAt();
                            String dateString = AppUtils.getCommentTime(createDate.getTime());

                            entity.setCreateAt(dateString);
                            entity.setId(objectId);
                            entity.setLikeNum(likeNum);
                            entity.setContent(content);
                            entity.setTitle(title);
                            entity.setLike(false);
                            entity.setUser(owner);
                            entity.setReplyUser(reviewer);

                            result.add(entity);
                        }
                        return result;
                    }
                })
                .subscribeOn(Schedulers.computation());
    }


    public Observable<List<CommentBean>> setLike(final List<CommentBean> commentList, List<AVObject> list) {

        return Observable.just(list)
                .map(new Func1<List<AVObject>, List<CommentBean>>() {
                    @Override
                    public List<CommentBean> call(List<AVObject> list) {
                        if (list == null || list.size() == 0) {
                            return commentList;
                        }
                        for (CommentBean commentBean : commentList) {
                            for (int i = 0; i < list.size(); i++) {
                                AVObject object = list.get(i);
                                AVObject comment = object.getAVObject("targetComment");
                                String commentId = comment.getObjectId();
                                if (commentBean.getId().equals(commentId)) {
                                    commentBean.setLike(true);
                                }
                            }
                        }

                        return commentList;
                    }
                })
                .subscribeOn(Schedulers.computation());
    }

    public void addCollection(final CollectionEntity entity) {
        addCollection(new ArrayList<CollectionEntity>() {
            {
                add(entity);
            }
        });
    }

    public CollectionEntity isCollect(String link) {
        List<CollectionEntity> list = getCollections();
        if (link == null || list.size() == 0) {
            return null;
        }
        for (CollectionEntity entity : list) {
            String _link = entity.getLink();
            if (link.equals(_link)) {
                return entity;
            }
        }
        return null;
    }

    public void addCollection(List<CollectionEntity> entitys) {
        List<CollectionEntity> cols = getCollections();
        if (cols == null) {
            cols = new ArrayList<>();
        }
        for (CollectionEntity entity1 : entitys) {
            cols.add(entity1);
        }

        this.collections = cols;
        Prefs.with(WWApp.getInstance()).write(COLLECT_KEY + MyInfo.getIntance().getUser().getObjectId(), JSON.toJSONString(collections));
    }

    public void cleanCollection() {
        this.collections = new ArrayList<>();
        Prefs.with(WWApp.getInstance()).write(COLLECT_KEY + MyInfo.getIntance().getUser().getObjectId(), JSON.toJSONString(collections));
    }
}

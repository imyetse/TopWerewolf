package cn.tse.pr.mvp.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVSaveOption;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.yuzhi.fine.Lg.Lg;

import java.util.List;

import cn.tse.pr.db.AppDao;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.CollectionEntity;
import cn.tse.pr.entity.topic.CommentBean;
import cn.tse.pr.entity.topic.TopicBean;
import cn.tse.pr.mvp.view.TopicDetailView;
import cn.tse.pr.mvp.view.TopicView;
import cn.tse.pr.utils.SimpleRxSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xieye on 2017/6/3.
 */

public class TopicPresenterImpl<T extends TopicView> extends MVPPresenter<T> implements TopicPresenter {
    public TopicPresenterImpl(T topicView) {
        super(topicView);
    }

    private List<CommentBean> comments;

    @Override
    public void getTopic(int page) {
        AVQuery<AVObject> avQuery = new AVQuery<>("Topic");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.limit(10);
        avQuery.skip((page - 1) * 10);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Lg.e("话题获取成功了");
                    AppDao.getIntance().setTopic(list).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleRxSubscriber<List<TopicBean>>() {
                                @Override
                                public void onNext(List<TopicBean> topicBeen) {
                                    getView().onTopicGet(topicBeen);
                                }
                            });
                } else {
                    Lg.e("话题读取失败了");
                    getView().onTopicGet(null);
                }
            }
        });
//        AVObject product = new AVObject("Topic");
//        product.put("topicTitle", mTitleEdit.getText().toString());
//        product.put("description", mDiscriptionEdit.getText().toString());
//        product.put("price", Integer.parseInt(mPriceEdit.getText().toString()));
//        product.put("owner", AVUser.getCurrentUser());
//        product.put("image", new AVFile("productPic", mImageBytes));
//        product.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    mProgerss.setVisibility(View.GONE);
//                    PublishActivity.this.finish();
//                } else {
//                    mProgerss.setVisibility(View.GONE);
//                    Toast.makeText(PublishActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public void addTopic(String title, String content, String displayUrl, int type) {

    }

    @Override
    public void getTopicDetail(String topicId) {

    }

    @Override
    public void getTopicComment(final String topicId) {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.orderByDescending("createdAt");
        query.include("owner");//关键代码 把pointer数据查询出来
        query.whereEqualTo("TargetTopic", AVObject.createWithoutData("Topic", topicId));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Lg.e("评论获取成功了");
                    AppDao.getIntance().setComment(list).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleRxSubscriber<List<CommentBean>>() {
                                @Override
                                public void onNext(List<CommentBean> commentList) {
                                    Lg.e("评论转换成功了>>" + commentList.size());
                                    if (getView() instanceof TopicDetailView) {
                                        getLike(commentList, topicId);
                                        ((TopicDetailView) getView()).onGetComment(commentList);
                                    }
                                }
                            });
                } else {
                    Lg.e("评论读取失败了");
                    if (getView() instanceof TopicDetailView) {
                        ((TopicDetailView) getView()).onGetComment(null);
                    }
                }
            }
        });
    }

    @Override
    public void commentTopic(CommentBean commentBean) {
        AVObject comment = new AVObject("Comment");//构建Comment对象
        comment.put("like", true);
        comment.put("title", commentBean.getTitle());
        comment.put("content", commentBean.getContent());
        comment.put("TargetTopic", AVObject.createWithoutData("Topic", commentBean.getTopicBean().getId()));
        comment.put("reviewer", commentBean.getReplyUser());
        comment.put("owner", MyInfo.getIntance().getUser());
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Lg.e("评论成功");
                } else {
                    Lg.e("评论失败>" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void getLike(final List<CommentBean> commentList, String topicId) {
        AVQuery<AVObject> query = new AVQuery<>("Like");
        query.whereEqualTo("targetTopic", AVObject.createWithoutData("Topic", topicId));
        query.whereEqualTo("owner", MyInfo.getIntance().getUser());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    //获取赞成功了
                    AppDao.getIntance().setLike(commentList, list).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleRxSubscriber<List<CommentBean>>() {
                                @Override
                                public void onNext(List<CommentBean> commentWithLikes) {
                                    Lg.e("赞转换成功了>>" + commentWithLikes.size());
                                    if (getView() instanceof TopicDetailView) {
                                        ((TopicDetailView) getView()).onGetLike(commentWithLikes);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Lg.e("赞转换失败了>>" + e.getMessage());
                                }
                            });
                } else {
                    //获取赞失败了
                    Lg.e("赞转换失败了>>" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void likeComment(final String commentId, String topicId, final int likeNum) {
        //先修改like数据表 再修改comment数据表
        AVObject like = new AVObject("Like");//Like
        like.put("like", true);
        like.put("targetComment", AVObject.createWithoutData("Comment", commentId));
        like.put("targetTopic", AVObject.createWithoutData("Topic", topicId));
        like.put("owner", MyInfo.getIntance().getUser());
        like.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Lg.e("赞成功");
                    AVObject todo = AVObject.createWithoutData("Comment", commentId);
                    // 修改 赞的数量
                    todo.put("likeNum", likeNum + 1);
                    todo.saveInBackground();
                } else {
                    Lg.e("赞失败>" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void joinTopic(final String topicId, final int joinCount) {
        Observable.create(new Observable.OnSubscribe<AVObject>() {
            @Override
            public void call(Subscriber<? super AVObject> subscriber) {
                AVQuery<AVObject> query = new AVQuery<>("Topic");
                int joinCount_;
                try {
                    AVObject object = query.get(topicId);
                    //joinCount_ = object.getInt("joinCount");
                    subscriber.onNext(object);
                } catch (AVException e) {
                    e.printStackTrace();
                    // joinCount_ = joinCount;
                    subscriber.onError(null);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<AVObject>() {
                    @Override
                    public void onNext(AVObject topic) {
                        topic.increment("joinCount");
                        topic.setFetchWhenSave(true);
                        topic.saveInBackground();
                    }
                });

    }

}

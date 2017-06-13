package cn.tse.pr.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.http.HttpResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.tse.pr.entity.news.JRTTEntity;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.entity.news.SHEntity;
import cn.tse.pr.entity.news.YKVideoAlbumListEntity;
import cn.tse.pr.entity.news.YKVideoEntity;
import cn.tse.pr.entity.news.YKVideoListEntity;
import cn.tse.pr.handle.HtmlParseToEntityHandle;
import cn.tse.pr.handle.HtmlTestHandle;
import cn.tse.pr.http.Http;
import cn.tse.pr.mvp.view.NewsView;
import cn.tse.pr.utils.SimpleRxSubscriber;
import okhttp3.Request;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static cn.tse.pr.handle.HtmlTestHandle.bdUrl;
import static cn.tse.pr.handle.HtmlTestHandle.shUrl;
import static cn.tse.pr.handle.HtmlTestHandle.zhUrl;

/**
 * Created by xieye on 2017/4/11.
 */

public class NewsPresenterImpl extends MVPPresenter<NewsView> implements NewsPresenter {

    private List<String> wordWiths = new ArrayList<String>() {
        {
            add("规则");
            add("复盘");
            add("面杀");
            add("申屠");
            add("手狼");
            add("玩家");
            add("四阶");
            add("进阶");
            add("居然");
            add("震惊");
            add("u2");
        }
    };

    public NewsPresenterImpl(NewsView newsView) {
        super(newsView);
    }


    @Override
    public void getNewsFromJRRT(String keyWord, int offset, int count, int tab) {
        if (TextUtils.isEmpty(keyWord)) {
            keyWord = "狼人杀";
            Random random = new Random();
            int wordWithCount = random.nextInt(3);
            //关键字挖掘
            for (; wordWithCount > 0; wordWithCount--) {
                String wordWith = wordWiths.get(random.nextInt(wordWiths.size()));
                keyWord = keyWord + "," + wordWith;
            }
        } else {
            keyWord = "狼人杀," + keyWord;
        }


        Http.getJRTTList(keyWord, offset, count, tab, new HttpResponseHandler(JRTTEntity.class) {
            @Override
            public void onSuccess(Object response) {
                super.onSuccess(response);
                Lg.e(JSON.toJSONString(response));
                JRTTEntity jrttEntity = (JRTTEntity) response;
                parseJRRT2NewsList(jrttEntity);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                Lg.e(e.getMessage());
                getView().onListLoaded(null, NewsView.FAIL);
            }
        });
    }

    private void parseJRRT2NewsList(JRTTEntity jrttEntity) {
        Observable.just(jrttEntity)
                .map(new Func1<JRTTEntity, List<NewsItemEntity>>() {

                    @Override
                    public List<NewsItemEntity> call(JRTTEntity jrttEntity) {
                        List<JRTTEntity.DataBean> datas = jrttEntity.getData();
                        List<NewsItemEntity> resultList = new ArrayList<>();
                        for (JRTTEntity.DataBean dataBean : datas) {
                            NewsItemEntity itemEntity = new NewsItemEntity();
                            if (TextUtils.isEmpty(dataBean.getTitle())) {
                                continue;
                            }
                            itemEntity.setTitle(dataBean.getTitle());
                            itemEntity.setImg(dataBean.getImage_url());
                            List<String> imgs = new ArrayList<>();
                            if (dataBean.getImage_list() != null) {
                                for (JRTTEntity.Img img : dataBean.getImage_list()) {
                                    imgs.add(img.getUrl());
                                }
                            }

                            itemEntity.setImgs(imgs);
                            itemEntity.setAuthor(dataBean.getMedia_name());
                            itemEntity.setLink(dataBean.getUrl());
                            itemEntity.setTimeStr(dataBean.getDatetime());
                            itemEntity.setCommentCount(String.valueOf(dataBean.getComment_count()));
                            itemEntity.setHasVideo(dataBean.isHas_video());
                            itemEntity.setVideoDurationStr(dataBean.getVideo_duration_str());
                            resultList.add(itemEntity);
                        }
                        return resultList;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<List<NewsItemEntity>>() {
                    @Override
                    public void onNext(List<NewsItemEntity> newsItemEntities) {
                        getView().onListLoaded(newsItemEntities, NewsView.JRTT);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }


    private void parseSH2NewsList(SHEntity shEntity) {
        Observable.just(shEntity)
                .filter(new Func1<SHEntity, Boolean>() {
                    @Override
                    public Boolean call(SHEntity shEntity) {
                        return shEntity.getCode() == 200;
                    }
                })
                .map(new Func1<SHEntity, List<NewsItemEntity>>() {

                    @Override
                    public List<NewsItemEntity> call(SHEntity shEntity) {
                        List<SHEntity.ListBean> datas = shEntity.getList();
                        List<NewsItemEntity> resultList = new ArrayList<>();
                        for (SHEntity.ListBean dataBean : datas) {
                            NewsItemEntity itemEntity = new NewsItemEntity();
                            itemEntity.setTitle(dataBean.getTitle());
                            itemEntity.setImg(dataBean.getPicUrl());
                            itemEntity.setImgs(dataBean.getThumbnails());
                            itemEntity.setAuthor(dataBean.getAuthor());
                            itemEntity.setLink(dataBean.getPath());
                            itemEntity.setTimeStr(dataBean.getPostTimeAsString());
                            itemEntity.setCommentCount(String.valueOf(0));
                            itemEntity.setHasVideo(dataBean.isHasVideo());
                            resultList.add(itemEntity);
                        }
                        return resultList;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<List<NewsItemEntity>>() {
                    @Override
                    public void onNext(List<NewsItemEntity> newsItemEntities) {
                        getView().onListLoaded(newsItemEntities, NewsView.SH);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }


    private void parseYKAlbum2NewsList(YKVideoAlbumListEntity albumListEntity) {
        Observable.just(albumListEntity)
                .map(new Func1<YKVideoAlbumListEntity, List<NewsItemEntity>>() {

                    @Override
                    public List<NewsItemEntity> call(YKVideoAlbumListEntity ykVideoAlbumListEntity) {
                        List<YKVideoAlbumListEntity.ResultsBean> datas = ykVideoAlbumListEntity.getResults();
                        List<NewsItemEntity> resultList = new ArrayList<>();
                        for (YKVideoAlbumListEntity.ResultsBean dataBean : datas) {
                            NewsItemEntity itemEntity = new NewsItemEntity();
                            itemEntity.setTitle(dataBean.getTitle());
                            itemEntity.setImg(dataBean.getThumburl());
                            itemEntity.setAuthor(dataBean.getUsername());
                            itemEntity.setLink(dataBean.getPlaylistid());
                            itemEntity.setTimeStr(dataBean.getPublish_time());
                            itemEntity.setCommentCount(String.valueOf(0));
                            resultList.add(itemEntity);
                        }
                        return resultList;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<List<NewsItemEntity>>() {
                    @Override
                    public void onNext(List<NewsItemEntity> newsItemEntities) {
                        getView().onListLoaded(newsItemEntities, NewsView.YK_ALBUM);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }


    private void parseYKList2NewsList(YKVideoListEntity albumListEntity) {
        Observable.just(albumListEntity)
                .map(new Func1<YKVideoListEntity, List<NewsItemEntity>>() {

                    @Override
                    public List<NewsItemEntity> call(YKVideoListEntity ykVideoListEntity) {
                        List<YKVideoListEntity.ShowsBean> datas = ykVideoListEntity.getResultList().get(0).getShows();
                        List<NewsItemEntity> resultList = new ArrayList<>();
                        for (YKVideoListEntity.ShowsBean dataBean : datas) {
                            NewsItemEntity itemEntity = new NewsItemEntity();
                            itemEntity.setTitle(dataBean.getTitle());
                            itemEntity.setImg(dataBean.getThumburl());
                            itemEntity.setAuthor(dataBean.getUsername());
                            itemEntity.setLink(dataBean.getVideoid());
                            itemEntity.setVideoDurationStr(dataBean.getDuration());
                            itemEntity.setTimeStr(dataBean.getPublish_time());
                            itemEntity.setCommentCount(String.valueOf(0));
                            resultList.add(itemEntity);
                        }
                        return resultList;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<List<NewsItemEntity>>() {
                    @Override
                    public void onNext(List<NewsItemEntity> newsItemEntities) {
                        getView().onListLoaded(newsItemEntities, NewsView.YK_PLAYLIST);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }

    @Override
    public void getNewsFromSH(String tagId, int pNo, int pSize) {
        Http.getSHList("67121", pNo, pSize, new HttpResponseHandler(SHEntity.class) {
            @Override
            public void onSuccess(Object response) {
                super.onSuccess(response);
                Lg.e(JSON.toJSONString(response));
                SHEntity shEntity = (SHEntity) response;
                parseSH2NewsList(shEntity);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                Lg.e(e.getMessage());
                getView().onListLoaded(null, NewsView.FAIL);
            }
        });
    }

    @Override
    public void getNewsFromZH(int page) {
        zhUrl = zhUrl + "?page=" + page;
        HtmlParseToEntityHandle handle = new HtmlParseToEntityHandle(zhUrl, HtmlTestHandle.getZhihuEntity());
        handle.parse()
                .map(new Func1<List<NewsItemEntity>, List<NewsItemEntity>>() {
                    @Override
                    public List<NewsItemEntity> call(List<NewsItemEntity> newsItemEntities) {
                        for (NewsItemEntity itemEntity : newsItemEntities) {
                            String link = itemEntity.getLink();
                            if (!TextUtils.isEmpty(link) && (!link.startsWith("http://") || !link.startsWith("https://"))) {
                                itemEntity.setLink(new StringBuilder("http://www.zhihu.com").append(link).toString());
                            }
                        }
                        return newsItemEntities;
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<List<NewsItemEntity>>() {
                    @Override
                    public void onNext(List<NewsItemEntity> newsItemEntities) {
                        Lg.e("Next>>>" + JSON.toJSONString(newsItemEntities) + ">>");
                        getView().onListLoaded(newsItemEntities, NewsView.ZH);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }

    @Override
    public void getNewsFromDB() {

    }

    @Override
    public void getYKVideoAlbums(String keyWord) {
        if (TextUtils.isEmpty(keyWord)) {
            keyWord = "狼人杀";
        }
        Observable.just(keyWord)
                .map(new Func1<String, YKVideoAlbumListEntity>() {
                    @Override
                    public YKVideoAlbumListEntity call(String s) {
                        try {
                            String responseStr = Http.getYKVideoAlbums(s).body().string();
                            YKVideoAlbumListEntity list = JSONObject.parseObject(responseStr, YKVideoAlbumListEntity.class);
                            return list;
                        } catch (Exception e) {
                            return new YKVideoAlbumListEntity();
                        }

                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<YKVideoAlbumListEntity>() {
                    @Override
                    public void onNext(YKVideoAlbumListEntity listEntity) {
                        parseYKAlbum2NewsList(listEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Lg.e(e.getMessage());
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }

    @Override
    public void getYKVideoPlayList(String playListId) {
        Observable.just(playListId)
                .map(new Func1<String, YKVideoListEntity>() {
                    @Override
                    public YKVideoListEntity call(String s) {
                        try {
                            String responseStr = Http.getYKVideoPlayList(s).body().string();
                            YKVideoListEntity list = JSONObject.parseObject(responseStr, YKVideoListEntity.class);
                            return list;
                        } catch (Exception e) {
                            return new YKVideoListEntity();
                        }

                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<YKVideoListEntity>() {
                    @Override
                    public void onNext(YKVideoListEntity listEntity) {
                        parseYKList2NewsList(listEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Lg.e(e.getMessage());
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }

    @Override
    public void getNewsFromBD(String keyWord, int page) {
        if (TextUtils.isEmpty(keyWord)) {
            keyWord = "狼人杀";
            Random random = new Random();
            String wordWith = wordWiths.get(random.nextInt(wordWiths.size()));
            keyWord = keyWord + wordWith;
        } else {
            keyWord = "狼人杀," + keyWord;
        }
        String url = String.format(bdUrl, keyWord, String.valueOf(page));
        HtmlParseToEntityHandle handle = new HtmlParseToEntityHandle(url, HtmlTestHandle.getBaiduEntity());
        handle.parse()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<List<NewsItemEntity>>() {
                    @Override
                    public void onNext(List<NewsItemEntity> newsItemEntities) {
                        Lg.e("Next>>>" + JSON.toJSONString(newsItemEntities) + ">>");
                        getView().onListLoaded(newsItemEntities, NewsView.ZH);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Lg.e("Next>>>" + e.getMessage());
                        getView().onListLoaded(null, NewsView.FAIL);
                    }
                });
    }

    @Override
    public void getYKVideoInfo(String videoId) {
        Observable.just(videoId)
                .map(new Func1<String, YKVideoEntity>() {

                    @Override
                    public YKVideoEntity call(String s) {
                        try {
                            String responseStr = Http.getYKVideoPlayList(s).body().string();
                            YKVideoEntity list = JSONObject.parseObject(responseStr, YKVideoEntity.class);
                            return list;
                        } catch (Exception e) {
                            return new YKVideoEntity();
                        }

                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }


}

package cn.tse.pr.handle;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.signature.Base64Decoder;
import com.tencent.smtt.sdk.TbsVideo;
import com.yuzhi.fine.Lg.Lg;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tse.pr.entity.news.JRTTEntity;
import cn.tse.pr.entity.news.JRTTVideoEntity;
import cn.tse.pr.entity.news.YKVideoAlbumListEntity;
import cn.tse.pr.entity.news.YKVideoEntity;
import cn.tse.pr.entity.news.YKVideoListEntity;
import cn.tse.pr.http.Http;
import cn.tse.pr.ui.web.WebViewActivity;
import cn.tse.pr.utils.SimpleRxSubscriber;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xieye on 2017/4/13.
 */

public class HtmlHandle {


    /**
     * 打开今日头条的视频
     *
     * @param context
     * @param url     原始url
     */
    public static void openJRTTVideo(final Context context, final String url, final String title) {
        if (!TbsVideo.canUseTbsPlayer(context)) {
            WebViewActivity.openWebView(context, url, title);
            return;
        }
        HtmlHandle.getHtml(url)
                .map(new Func1<Document, String>() {
                    @Override
                    public String call(Document document) {
                        String documentStr = document.toString();
                        if (TextUtils.isEmpty(documentStr)) {
                            return "";
                        }
                        List<String> results = new ArrayList<>();
                        Pattern pattern = Pattern.compile("(?<=videoid:\')[\\w]*(?=\')");
                        Matcher matcher = pattern.matcher(documentStr);
                        while (matcher.find()) {
                            results.add(matcher.group());
                        }
                        if (results.size() >= 1) {
                            return results.get(0);
                        }
                        return "";
                    }
                })
                .map(new Func1<String, String>() {

                    @Override
                    public String call(String s) {
                        try {
                            String responseStr = Http.getJRTTVideo(s).body().string();
                            JRTTVideoEntity videoEntity = JSONObject.parseObject(responseStr, JRTTVideoEntity.class);
                            JRTTVideoEntity.DataBean.VideoListBean videoList = videoEntity.getData().getVideo_list();

                            String url_origin_1 = videoList.getVideo_1().getMain_url();
                            String url_origin_2 = videoList.getVideo_2().getMain_url();
                            String url_origin_3 = videoList.getVideo_3().getMain_url();

                            String videoDecode1 = Base64Decoder.decode(url_origin_1);
                            String videoDecode2 = Base64Decoder.decode(url_origin_2);
                            String videoDecode3 = Base64Decoder.decode(url_origin_3);

                            if (!TextUtils.isEmpty(videoDecode3))
                                return videoDecode3;
                            if (!TextUtils.isEmpty(videoDecode2))
                                return videoDecode2;
                            if (!TextUtils.isEmpty(videoDecode1))
                                return videoDecode1;
                            return "";
                        } catch (Exception e) {
                            return "";
                        }
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<String>() {
                    @Override
                    public void onNext(String videoUrl) {
                        if (TbsVideo.canUseTbsPlayer(context)) {
                            Lg.e("TbsVideo.canUseTbsPlayer>>>" + videoUrl);
                            TbsVideo.openVideo(context, videoUrl);
                        }
                        Lg.e("videoUrl>>>" + videoUrl);
                    }

                    @Override
                    public void onError(Throwable e) {
                        WebViewActivity.openWebView(context, url, title);
                    }
                });

    }

    /**
     * 打开优酷的视频
     *
     * @param context
     * @param videoId 视频id
     */
    public static void openYKVideo(final Context context, final String videoId, final String title) {
        if (!TbsVideo.canUseTbsPlayer(context)) {
            Toast.makeText(context, "播放器初始化失败，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        Observable.just(videoId)
                .map(new Func1<String, YKVideoEntity>() {
                    @Override
                    public YKVideoEntity call(String s) {
                        try {
                            String responseStr = Http.getYKVideoInfo(s).body().string();
                            YKVideoEntity list = JSONObject.parseObject(responseStr, YKVideoEntity.class);
                            return list;
                        } catch (Exception e) {
                            return new YKVideoEntity();
                        }

                    }
                })
                .map(new Func1<YKVideoEntity, String>() {

                    @Override
                    public String call(YKVideoEntity ykVideoEntity) {
                        String videoUrl = ykVideoEntity.getData().getStream().get(0).getSegs().get(0).getCdn_url();
                        return videoUrl;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleRxSubscriber<String>() {
                    @Override
                    public void onNext(String videoUrl) {
                        if (TbsVideo.canUseTbsPlayer(context)) {
                            Lg.e("TbsVideo.canUseTbsPlayer>>>" + videoUrl);
                            TbsVideo.openVideo(context, videoUrl);
                        }
                        Lg.e("videoUrl>>>" + videoUrl);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Lg.e("throwable>>>" + e.getMessage());
                        Toast.makeText(context, "播放器初始化失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public static Observable<YKVideoListEntity> getYouKuVideo(String keyWord) {
        return Observable.just(keyWord)
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
                .map(new Func1<YKVideoAlbumListEntity, YKVideoListEntity>() {
                    @Override
                    public YKVideoListEntity call(YKVideoAlbumListEntity ykVideoAlbumListEntity) {
                        if (ykVideoAlbumListEntity != null && ykVideoAlbumListEntity.getResults() != null && ykVideoAlbumListEntity.getResults().size() > 0) {
                            try {
                                String responseStr = Http.getYKVideoPlayList(ykVideoAlbumListEntity.getResults().get(0).getPlaylistid()).body().string();
                                YKVideoListEntity list = JSONObject.parseObject(responseStr, YKVideoListEntity.class);
                                return list;
                            } catch (Exception e) {
                                return null;
                            }

                        } else {
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.computation());
    }


    /**
     * 拿到原始html数据
     *
     * @param url
     * @return
     */
    public static Observable<Document> getHtml(final String url) {
        return Observable.create(new Observable.OnSubscribe<Document>() {
            @Override
            public void call(Subscriber<? super Document> subscriber) {
                //
//                WebClient webClient = new WebClient();
//                webClient.getOptions().setJavaScriptEnabled(true);
//                webClient.getOptions().setCssEnabled(false);
//                webClient.getOptions().setThrowExceptionOnScriptError(false);////js运行错误时，是否抛出异常
//                webClient.getOptions().setTimeout(5000);
//                HtmlPage htmlPage = webClient.getPage(url);
//                String pageXml = htmlPage.asXml();

                try {
                    Connection connection = Jsoup.connect(url);
                    Document doc = Jsoup.connect(url).timeout(5000).get();
                    Document content = Jsoup.parse(doc.toString());
//                Document content = Jsoup.parse(pageXml);
                    subscriber.onNext(content);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        })
                .subscribeOn(Schedulers.newThread());

    }
}

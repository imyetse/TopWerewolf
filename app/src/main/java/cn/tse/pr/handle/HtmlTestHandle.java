package cn.tse.pr.handle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.tencent.smtt.sdk.TbsVideo;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.http.HttpResponseHandler;

import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tse.pr.entity.html.HtmlBaseEntity;
import cn.tse.pr.entity.html.HtmlXmlAgreeEntity;
import cn.tse.pr.entity.html.HtmlXmlAuthorEntity;
import cn.tse.pr.entity.html.HtmlXmlCommentEntity;
import cn.tse.pr.entity.html.HtmlXmlImgEntity;
import cn.tse.pr.entity.html.HtmlXmlImgsEntity;
import cn.tse.pr.entity.html.HtmlXmlLayerEntity;
import cn.tse.pr.entity.html.HtmlXmlLinkEntity;
import cn.tse.pr.entity.html.HtmlXmlTimeEntity;
import cn.tse.pr.entity.html.HtmlXmlTitleEntity;
import cn.tse.pr.entity.news.JRTTEntity;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.http.Http;
import okhttp3.Request;

/**
 * Created by xieye on 2017/4/10.
 */

public class HtmlTestHandle {

    public static String jrttUrl = "http://www.toutiao.com/search_content/?offset=0&format=json&keyword=%E7%8B%BC%E4%BA%BA%E6%9D%80&autoload=true&count=20&cur_tab=1";
    public static String shUrl = "http://mt.sohu.com/tags/67121.shtml";
    public static String zhUrl = "https://www.zhihu.com/topic/19846199/top-answers";
    public static String bdUrl = "http://news.baidu.com/ns?word=%1$s&pn=%2$s&cl=2&ct=0&tn=news&rn=20&ie=utf-8&bt=0&et=0";


    public static void getTouTiao() {
        Http.getJRTTList("狼人杀", 0, 10, 1, new HttpResponseHandler(JRTTEntity.class) {
            @Override
            public void onSuccess(Object response) {
                super.onSuccess(response);
                Lg.e(JSON.toJSONString(response));
                JRTTEntity jrttEntity = (JRTTEntity) response;
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                Lg.e(e.getMessage());
                Log.e(">>onFailure", e.getMessage());
            }
        });
    }

    public static HtmlXmlLayerEntity getZhihuEntity() {
        final HtmlXmlLayerEntity layerEntity = new HtmlXmlLayerEntity();
        layerEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        layerEntity.setClassName("feed-item");

        HtmlXmlAuthorEntity authorEntity = new HtmlXmlAuthorEntity();
        authorEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        authorEntity.setClassName("author-link");

        HtmlXmlLinkEntity linkEntity = new HtmlXmlLinkEntity();
        linkEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        linkEntity.setClassName("question_link");

        HtmlXmlTitleEntity titleEntity = new HtmlXmlTitleEntity();
        titleEntity.setGetBy(HtmlBaseEntity.GETBYATTRIVALUE);
        titleEntity.setAttributeName("data-za-element-name");
        titleEntity.setAttributeValue("Title");

        HtmlXmlImgEntity imgEntity = new HtmlXmlImgEntity();
        imgEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        imgEntity.setClassName("origin_image");

        HtmlXmlAgreeEntity agreeEntity = new HtmlXmlAgreeEntity();
        agreeEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        agreeEntity.setClassName("count");

        HtmlXmlCommentEntity commentEntity = new HtmlXmlCommentEntity();
        commentEntity.setGetBy(HtmlBaseEntity.GETBYATTRIVALUE);
        commentEntity.setAttributeName("itemprop");
        commentEntity.setAttributeValue("answerCount");
        commentEntity.setDirectValue("content");

        layerEntity.setAuthorEntity(authorEntity);
        layerEntity.setTitleEntity(titleEntity);
        layerEntity.setLinkEntity(linkEntity);
        layerEntity.setImgEntity(imgEntity);
        layerEntity.setAgreeEntity(agreeEntity);
        layerEntity.setCommentEntity(commentEntity);

        return layerEntity;


    }

    public static HtmlXmlLayerEntity getBaiduEntity() {
        final HtmlXmlLayerEntity layerEntity = new HtmlXmlLayerEntity();
        layerEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        layerEntity.setClassName("result");

        HtmlXmlAuthorEntity authorEntity = new HtmlXmlAuthorEntity();
        authorEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        authorEntity.setClassName("c-author");

        HtmlXmlLinkEntity linkEntity = new HtmlXmlLinkEntity();
        linkEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        linkEntity.setClassName("c-title");

        HtmlXmlTitleEntity titleEntity = new HtmlXmlTitleEntity();
        titleEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        titleEntity.setClassName("c-title");

        HtmlXmlImgEntity imgEntity = new HtmlXmlImgEntity();
        imgEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        imgEntity.setClassName("c_photo");

        HtmlXmlAgreeEntity agreeEntity = new HtmlXmlAgreeEntity();
        agreeEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        agreeEntity.setClassName("");

        HtmlXmlCommentEntity commentEntity = new HtmlXmlCommentEntity();
        commentEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        commentEntity.setClassName("");

        layerEntity.setAuthorEntity(authorEntity);
        layerEntity.setTitleEntity(titleEntity);
        layerEntity.setLinkEntity(linkEntity);
        layerEntity.setImgEntity(imgEntity);

        return layerEntity;


    }

    public static HtmlXmlLayerEntity getSHEntity() {
        final HtmlXmlLayerEntity layerEntity = new HtmlXmlLayerEntity();
        layerEntity.setGetBy(HtmlBaseEntity.GETBYATTRIVALUE);
        layerEntity.setAttributeName("data-role");
        layerEntity.setAttributeValue("news-item");

        HtmlXmlAuthorEntity authorEntity = new HtmlXmlAuthorEntity();
        authorEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        authorEntity.setClassName("name");

        HtmlXmlLinkEntity linkEntity = new HtmlXmlLinkEntity();
        linkEntity.setGetBy(HtmlBaseEntity.GETBYTAG);
        linkEntity.setTagName("h4");

        HtmlXmlTimeEntity timeEntity = new HtmlXmlTimeEntity();
        timeEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        timeEntity.setClassName("time");
        timeEntity.setDirectValue("data-val");

        HtmlXmlTitleEntity titleEntity = new HtmlXmlTitleEntity();
        titleEntity.setGetBy(HtmlBaseEntity.GETBYTAG);
        titleEntity.setTagName("h4");

        HtmlXmlImgEntity imgEntity = new HtmlXmlImgEntity();
        imgEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        imgEntity.setClassName("pic");

        HtmlXmlImgsEntity imgsEntity = new HtmlXmlImgsEntity();
        imgsEntity.setGetBy(HtmlBaseEntity.GETBYCLASS);
        imgsEntity.setClassName("pic-group");

        layerEntity.setAuthorEntity(authorEntity);
        layerEntity.setTimeEntity(timeEntity);
        layerEntity.setTitleEntity(titleEntity);
        layerEntity.setLinkEntity(linkEntity);
        layerEntity.setImgEntity(imgEntity);
        layerEntity.setImgsEntity(imgsEntity);

        return layerEntity;
    }
}

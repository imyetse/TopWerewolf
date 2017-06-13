package cn.tse.pr.ui;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.Lg.Lg;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tse.pr.entity.html.HtmlBaseEntity;
import cn.tse.pr.entity.html.HtmlXmlAuthorEntity;
import cn.tse.pr.entity.html.HtmlXmlImgEntity;
import cn.tse.pr.entity.html.HtmlXmlImgsEntity;
import cn.tse.pr.entity.html.HtmlXmlLayerEntity;
import cn.tse.pr.entity.html.HtmlXmlLinkEntity;
import cn.tse.pr.entity.html.HtmlXmlTimeEntity;
import cn.tse.pr.entity.html.HtmlXmlTitleEntity;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.handle.HtmlParseToEntityHandle;
import rx.schedulers.Schedulers;

/**
 * Created by xieye on 2017/4/6.
 */

public class testJava {
    private static HtmlParseToEntityHandle handle;
    private static String jrttUrl = "http://www.toutiao.com/search/?keyword=%E7%8B%BC%E4%BA%BA%E6%9D%80";
    private static String shUrl = "http://mt.sohu.com/tags/67121.shtml";

    public static void main(String[] args) {
        final HtmlXmlLayerEntity layerEntity = new HtmlXmlLayerEntity();
        handle = new HtmlParseToEntityHandle(jrttUrl, layerEntity);
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

        //System.out.println(JSON.toJSONString(layerEntity));
//
//        HtmlParseToEntityHandle handle = new HtmlParseToEntityHandle("http://mt.sohu.com/tags/67121.shtml", layerEntity);
//        handle.parse().subscribeOn(Schedulers.single()).subscribe(new Consumer<List<NewsItemEntity>>() {
//            @Override
//            public void accept(List<NewsItemEntity> newsItemEntities) throws Exception {
//                //System.out.print(JSON.toJSONString(newsItemEntities));
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                System.out.print(throwable.getMessage());
//            }
//        });

        getRegex();
    }

    private static String input = "\n" +
            "player={\n" +
            "videoid:'93cf04b4bf66455c8c8fde852e708945',\n" +
            "    share_url:'http://www.toutiao.com/group/6399831242040148482/',\n" +
            "    abstract:\"\",\n" +
            "    group_id:'6399831242040148482',\n" +
            "    item_id:'6399831242040148482',\n" +
            "    repin:0,\n" +
            "    bury_count:'75',\n" +
            "    digg_count:'476',\n" +
            "    is_bury:0,\n" +
            "    is_digg:0,\n" +
            "    nextSiblings:siblingList\n" +
            "  };";

    private static void getRegex() {
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=videoid:\')[\\w]*(?=\')");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            results.add(matcher.group());
        }
        System.out.println(JSON.toJSONString(results));
    }

    private static void test2() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (; i < 300; i++) {
            String xml_dp = "<dimen name=\"dp_%1$s\">%2$sdp</dimen>\n";
            xml_dp = String.format(xml_dp, String.valueOf(i), String.valueOf(i));
            stringBuilder.append(xml_dp);
        }
        System.out.println(stringBuilder.toString());
    }

    public static void test1() {
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

        System.out.println(JSON.toJSONString(layerEntity));
    }
}

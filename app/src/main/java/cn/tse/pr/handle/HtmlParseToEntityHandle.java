package cn.tse.pr.handle;

import android.text.TextUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
import cn.tse.pr.entity.news.NewsItemEntity;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static cn.tse.pr.entity.html.HtmlBaseEntity.ATTRIBUTE_HREF;
import static cn.tse.pr.entity.html.HtmlBaseEntity.ATTRIBUTE_SRC;
import static cn.tse.pr.entity.html.HtmlBaseEntity.TAG_A;
import static cn.tse.pr.entity.html.HtmlBaseEntity.TAG_A_SELECT;
import static cn.tse.pr.entity.html.HtmlBaseEntity.TAG_IMG;
import static cn.tse.pr.entity.html.HtmlBaseEntity.TAG_IMG_SELECT;
import static cn.tse.pr.handle.HtmlHandle.getHtml;

/**
 * Created by xieye on 2017/4/6.
 */

public class HtmlParseToEntityHandle {

    private String originUrl;
    private HtmlXmlLayerEntity layerEntitys;
    private String originHtml;
    private Document originDoc;
    /**
     * 结果
     */
    private List<NewsItemEntity> newsList = new ArrayList<>();

    public HtmlParseToEntityHandle(String url, HtmlXmlLayerEntity entity) {
        this.layerEntitys = entity;
        this.originUrl = url;
    }

    public HtmlParseToEntityHandle(String url) {
        this.originUrl = url;
    }

    /**
     * starting transform html to entity
     *
     * @return
     */
    public Observable<List<NewsItemEntity>> parse() {

        return getHtml(originUrl).map(new Func1<Document, List<NewsItemEntity>>() {
            @Override
            public List<NewsItemEntity> call(Document document) {
                originDoc = document;
                originHtml = document.toString();
                Elements elements = new Elements();

                switch (layerEntitys.getGetBy()) {
                    //item最外层xml

                    case HtmlXmlLayerEntity.GETBYATTRIVALUE:
                        elements = originDoc.getElementsByAttributeValue(layerEntitys.getAttributeName(), layerEntitys.getAttributeValue());
                        break;
                    case HtmlXmlLayerEntity.GETBYCLASS:
                        elements = originDoc.getElementsByClass(layerEntitys.getClassName());
                        break;
                    case HtmlXmlLayerEntity.GETBYID:
                        elements.add(originDoc.getElementById(layerEntitys.getIdName()));
                        break;
                    case HtmlXmlLayerEntity.GETBYTAG:
                        elements = originDoc.getElementsByTag(layerEntitys.getTagName());
                        break;
                    case HtmlXmlLayerEntity.GETBYATTRIBUTE:
                    default:
                        elements = originDoc.getElementsByAttribute(layerEntitys.getAttributeName());
                        break;
                }

                for (Element element : elements) {
                    newsList.add(parseNewsItem(element, layerEntitys));
                }
                return newsList;
            }
        }).subscribeOn(Schedulers.newThread());
    }

    /**
     * item最外层 转为一个bean
     *
     * @param element
     * @param htmlXmlLayerEntity
     * @return
     */
    private NewsItemEntity parseNewsItem(Element element, HtmlXmlLayerEntity htmlXmlLayerEntity) {
        NewsItemEntity itemEntity = new NewsItemEntity();
        itemEntity.setAuthor((String) parseElement(element, layerEntitys.getAuthorEntity()));
        itemEntity.setTitle((String) parseElement(element, layerEntitys.getTitleEntity()));
        itemEntity.setLink((String) parseElement(element, layerEntitys.getLinkEntity()));
        itemEntity.setTimeStr((String) parseElement(element, layerEntitys.getTimeEntity()));
        itemEntity.setImg((String) parseElement(element, layerEntitys.getImgEntity()));
        itemEntity.setAgreeCount((String) parseElement(element, layerEntitys.getAgreeEntity()));
        itemEntity.setCommentCount((String) parseElement(element, layerEntitys.getCommentEntity()));

        if (parseElement(element, layerEntitys.getImgsEntity()) instanceof ArrayList) {
            itemEntity.setImgs((ArrayList) parseElement(element, layerEntitys.getImgsEntity()));
        }

        return itemEntity;
    }


    /**
     * 直接从属性获取值
     *
     * @param elements
     * @return
     */
    private String getValueByAttributeValue(Elements elements, String attribute) {
        for (Element element : elements) {
            if (element.hasAttr(attribute)) {
                return element.attr(attribute);
            }
        }
        return "null";
    }


    /**
     * 获取作者
     *
     * @param elements
     * @return
     */
    private String getAuthorByElements(Elements elements) {
        for (Element element : elements) {
            if (element.hasText()) {
                return element.text();
            }
        }
        return "null";
    }

    /**
     * 获取文内容
     *
     * @param elements
     * @return
     */
    private String getValueByContent(Elements elements) {
        for (Element element : elements) {
            if (element.hasText()) {
                String text = element.text();
                String content = element.data();
                String ownText = element.ownText();
                return text;
            }
        }
        return "null";
    }

    /**
     * 获取文章链接
     *
     * @param elements
     * @return
     */
    private String getLinkByElements(Elements elements) {
        Elements links = elements.select(TAG_A_SELECT);
        for (Element element : links) {
            if (element.hasAttr(ATTRIBUTE_HREF)) {
                return element.attr(ATTRIBUTE_HREF);
            }
        }
        return "null";
    }


    /**
     * 获取文章图集
     *
     * @param elements
     * @return
     */
    private List<String> getImgsByElements(Elements elements) {
        List<String> imgs = new ArrayList<>();
        Elements aElements = elements.select(TAG_IMG_SELECT);
        for (Element element : aElements) {
            if (element.hasAttr(ATTRIBUTE_SRC)) {
                imgs.add(element.attr(ATTRIBUTE_SRC));
            }
        }
        return imgs;
    }

    /**
     * 获取文章单张图片
     *
     * @param elements
     * @return
     */
    private String getImgByElements(Elements elements) {
        Elements aElements = elements.select(TAG_IMG_SELECT);
        for (Element element : aElements) {
            if (element.hasAttr(ATTRIBUTE_SRC)) {
                return element.attr(ATTRIBUTE_SRC);
            }
        }
        return "";
    }


    private Object parseElement(Element element, HtmlXmlLayerEntity entity) {
        Elements elements = new Elements();

        if (entity != null) {
            //子节点的属性规则
            if (TextUtils.isEmpty(entity.getGetBy())) {
                return "";
            }
            switch (entity.getGetBy()) {
                case HtmlXmlLayerEntity.GETBYATTRIVALUE:
                    if (TextUtils.isEmpty(entity.getAttributeName()) || TextUtils.isEmpty(entity.getAttributeValue())) {
                        return "";
                    }
                    elements = element.getElementsByAttributeValue(entity.getAttributeName(), entity.getAttributeValue());
                    break;
                case HtmlXmlLayerEntity.GETBYCLASS:
                    if (TextUtils.isEmpty(entity.getClassName())) {
                        return "";
                    }
                    elements = element.getElementsByClass(entity.getClassName());
                    break;
                case HtmlXmlLayerEntity.GETBYID:
                    if (TextUtils.isEmpty(entity.getIdName())) {
                        return "";
                    }
                    elements.add(element.getElementById(entity.getIdName()));
                    break;
                case HtmlXmlLayerEntity.GETBYTAG:
                    if (TextUtils.isEmpty(entity.getTagName())) {
                        return "";
                    }
                    elements = element.getElementsByTag(entity.getTagName());
                    break;
                case HtmlXmlLayerEntity.GETBYATTRIBUTE:
                default:
                    if (TextUtils.isEmpty(entity.getAttributeName())) {
                        return "";
                    }
                    elements = element.getElementsByAttribute(entity.getAttributeName());
                    break;
            }
        } else {
            return "";
        }

        if (elements == null || elements.size() == 0) {
            return "";
        }


        if (!TextUtils.isEmpty(entity.getDirectValue())) {
            return getValueByAttributeValue(elements, entity.getDirectValue());
        }

        if (entity instanceof HtmlXmlTitleEntity || entity instanceof HtmlXmlAgreeEntity) {
            return getValueByContent(elements);
        }

        if (entity instanceof HtmlXmlAuthorEntity) {
            return getAuthorByElements(elements);
        }

        if (entity instanceof HtmlXmlImgsEntity) {
            return getImgsByElements(elements);
        }

        if (entity instanceof HtmlXmlImgEntity) {
            return getImgByElements(elements);
        }

        if (entity instanceof HtmlXmlLinkEntity) {
            return getLinkByElements(elements);
        }

        if (entity instanceof HtmlXmlCommentEntity) {
            return getAuthorByElements(elements);
        }

        return null;

    }


    public List<NewsItemEntity> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsItemEntity> newsList) {
        this.newsList = newsList;
    }
}

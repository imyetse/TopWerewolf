package cn.tse.pr.entity.html;


/**
 * Created by xieye on 2017/4/6.
 */

public class HtmlXmlLayerEntity extends HtmlBaseEntity {


    private String getBy;//通过哪个属性去获取节点
    private String attributeName;//属性名称
    private String attributeValue;//属性内容
    private String tagName;//标签名
    private String className;//class名
    private String idName;//id名
    private String directValue;//直接获取属性值

    private HtmlXmlTitleEntity titleEntity;//题目的
    private HtmlXmlImgEntity imgEntity;//图片
    private HtmlXmlImgsEntity imgsEntity;//图集
    private HtmlXmlAuthorEntity authorEntity;//作者
    private HtmlXmlTimeEntity timeEntity;//时间目的
    private HtmlXmlLinkEntity linkEntity;//链接目的
    private HtmlXmlCommentEntity commentEntity;//评论数量
    private HtmlXmlAgreeEntity agreeEntity;//知乎专用

    public String getDirectValue() {
        return directValue;
    }

    public void setDirectValue(String directValue) {
        this.directValue = directValue;
    }

    public HtmlXmlCommentEntity getCommentEntity() {
        return commentEntity;
    }

    public void setCommentEntity(HtmlXmlCommentEntity commentEntity) {
        this.commentEntity = commentEntity;
    }

    public HtmlXmlAgreeEntity getAgreeEntity() {
        return agreeEntity;
    }

    public void setAgreeEntity(HtmlXmlAgreeEntity agreeEntity) {
        this.agreeEntity = agreeEntity;
    }

    public HtmlXmlTitleEntity getTitleEntity() {
        return titleEntity;
    }

    public void setTitleEntity(HtmlXmlTitleEntity titleEntity) {
        this.titleEntity = titleEntity;
    }

    public HtmlXmlImgEntity getImgEntity() {
        return imgEntity;
    }

    public void setImgEntity(HtmlXmlImgEntity imgEntity) {
        this.imgEntity = imgEntity;
    }

    public HtmlXmlImgsEntity getImgsEntity() {
        return imgsEntity;
    }

    public void setImgsEntity(HtmlXmlImgsEntity imgsEntity) {
        this.imgsEntity = imgsEntity;
    }

    public HtmlXmlAuthorEntity getAuthorEntity() {
        return authorEntity;
    }

    public void setAuthorEntity(HtmlXmlAuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    public HtmlXmlTimeEntity getTimeEntity() {
        return timeEntity;
    }

    public void setTimeEntity(HtmlXmlTimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

    public HtmlXmlLinkEntity getLinkEntity() {
        return linkEntity;
    }

    public void setLinkEntity(HtmlXmlLinkEntity linkEntity) {
        this.linkEntity = linkEntity;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getGetBy() {
        return getBy;
    }

    public void setGetBy(String getBy) {
        this.getBy = getBy;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}

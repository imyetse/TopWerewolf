package cn.tse.pr.entity.html;

import java.io.Serializable;

/**
 * Created by xieye on 2017/4/6.
 */

public class HtmlBaseEntity implements Serializable {

    public final static String GETBYCLASS = "class";
    public final static String GETBYID = "id";
    public final static String GETBYATTRIVALUE = "attribute_value";
    public final static String GETBYATTRIBUTE = "attribute";
    public final static String GETBYTAG = "tag";


    public final static String TITLE = "title";
    public final static String IMGS = "imgs";
    public final static String LINK = "link";
    public final static String AUTHOR = "author";
    public final static String TIME = "time";


    public final static String TAG_A = "a";
    public final static String TAG_A_SELECT = "a[href]";
    public final static String TAG_IMG = "img";
    public final static String TAG_IMG_SELECT = "img[src]";
    public final static String ATTRIBUTE_HREF = "href";
    public final static String ATTRIBUTE_SRC = "src";
}

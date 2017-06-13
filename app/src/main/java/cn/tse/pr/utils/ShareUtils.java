package cn.tse.pr.utils;

import android.content.Context;
import android.content.Intent;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by xieye on 2017.04.15.
 */

public class ShareUtils {

    public static void showShare(Context context, String title, String content, String url, String imgUrl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        oks.setImageUrl(imgUrl);
        // url仅在微信（包括好友和朋友圈）中使用+
        oks.setUrl(url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("WereWolf news");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(context);
    }

}

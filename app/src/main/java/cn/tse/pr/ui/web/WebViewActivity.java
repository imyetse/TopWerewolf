package cn.tse.pr.ui.web;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuzhi.fine.Lg.Lg;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.db.AppDao;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.share.ShareInfo;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;
import cn.tse.pr.ui.my.MyActivity;
import cn.tse.pr.ui.widget.loading.ConfirmDialog;
import cn.tse.pr.utils.ShareUtils;

/**
 * Created by xieye on 2017/4/11.
 */

public class WebViewActivity extends SwipeBackActivity {
    public final static String WEB_URL = "web_url";
    public final static String WEB_TITLE = "web_title";
    public final static String WEB_IMG = "web_img";
    public final static String SHARE_INFO = "web_share_info";

    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_collect)
    ImageView ivCollect;

    String title, url, originUrl, imgUrl;
    boolean needRefresh = false;
    String function;
    private Handler mHandler = new Handler();
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private boolean isShare;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SELF_PAGE = 1;
    private int type = TYPE_NONE;
    private ShareInfo mShareInfo;


    public static void openWebView(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(WEB_URL, url);
        bundle.putString(WEB_TITLE, title);
        bundle.putString(WEB_IMG, "http://p1.pstatp.com/list/1680000082e8e85d47fc");
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Intent it = getIntent();
        url = it.getStringExtra(WEB_URL);
        title = it.getStringExtra(WEB_TITLE);
        imgUrl = it.getStringExtra(WEB_IMG);
        mShareInfo = (ShareInfo) it.getSerializableExtra(SHARE_INFO);


        if (title != null) {
            setTitle(title);
        }

        //TODO
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("?")) {
                sb.append(url).append("&");
            } else {
                sb.append(url).append("?");
            }
        } else {
            finish();
            return;
        }
        if (!url.startsWith("http:") && !url.startsWith("https:")) {
            url = new StringBuilder("http:").append(url).toString();
        }
        setCollectEnable(false, false);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new AppWebViewClient());
        mWebView.setWebChromeClient(new AppWebChromeClient());
        initWebView();
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new jsAction() {
        }, "android");
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }


    private void setCollectEnable(final boolean force, final boolean collect) {
        if (force && collect || (AppDao.getIntance().isCollect(url) != null && !force)) {
            ivCollect.setImageResource(R.mipmap.ico_collect_ed);
            ivCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //已收藏
                    if (!MyInfo.getIntance().isLogin(WebViewActivity.this)) {
                        return;
                    }
                    AVQuery.doCloudQueryInBackground("delete from Collection where uid = '" + MyInfo.getIntance().getUser().getObjectId() + "'+objectId='" + AppDao.getIntance().isCollect(url).getObjectId() + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                        @Override
                        public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                            if (e == null) {
                                Lg.e("删除成功了哈哈");
                                AppDao.getIntance().loadCollections();
                                setCollectEnable(true, false);
                            } else {
                                Lg.e("删除失败了哈哈:" + e.getMessage());
                            }
                        }
                    });
                }
            });
        } else {
            ivCollect.setImageResource(R.mipmap.ico_collect_not);
            ivCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!MyInfo.getIntance().isLogin(WebViewActivity.this)) {
                        return;
                    }
                    AVObject object = new AVObject("Collection");
                    object.put("title", title);
                    object.put("link", url);
                    object.put("uid", MyInfo.getIntance().getUser().getObjectId());
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Lg.e("保存成功了");
                                AppDao.getIntance().loadCollections();
                                setCollectEnable(true, true);
                            } else {
                                Lg.e("保存失败了");
                            }
                        }
                    });
                }
            });

        }
    }

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                ShareUtils.showShare(this, title, "", url, imgUrl);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
//            if (mWebView.canGoBack()) {
//                mWebView.goBack();
//            } else {
//                finish();
//            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }

        tvTitle.setText(title);
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
        // 缓存模式
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()
                + APP_CACAHE_DIRNAME;
        // 设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        useView();
    }

    private class AppWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(originUrl)) {
                originUrl = url;
            }
            if (url.equals(originUrl)) {

            }
            if (!url.startsWith("http")) {
                return true;
            }
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showTools.showLoading("加载中");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            showTools.disLoading();
            super.onPageFinished(view, url);
            //这句是针对运营商劫持添加js广告块的行为进行处理
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.loadUrl("javascript:function setTop(){document.querySelector('.ad-footer').style.display=\"none\"; var x = document.getElementsByClassName(\"banner-top\");\n" +
                            "    x[0].style.display=\"none\";}setTop();");
                }
            }, 500);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            showTools.showToast("请检查您的网络设置");
            //  mWebView.loadUrl("file:///android_asset/404.html");
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class AppWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String webTitle) {
            title = webTitle;

            super.onReceivedTitle(view, webTitle);
        }


        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            showTools.showAlert(message);
            return true;
        }

        @Override
        public void onProgressChanged(final WebView view, int newProgress) {
            //这句是针对运营商劫持添加js广告块的行为进行处理
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.loadUrl("javascript:function setTop(){document.querySelector('.ad-footer').style.display=\"none\"; var x = document.getElementsByClassName(\"banner-top\");\n" +
                            "    x[0].style.display=\"none\";}setTop();");
                }
            }, 500);
            super.onProgressChanged(view, newProgress);
        }
    }


    /************************×××××××××××××以下为适配而生******************************************/


    /**
     * 支持viewPort
     */

    private void useView() {
        WebSettings settings = mWebView.getSettings();
        //  settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
    }


    /************************××××××××××××以上为适配而生******************************************/
    /**
     * 恢复页面的时候发现有变化需不要调用
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (needRefresh) {
            if (!TextUtils.isEmpty(function)) {
                mHandler.post(new Runnable() {
                    public void run() {
                        // 调用客户端方法
                        mWebView.loadUrl(function);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001
                && resultCode == RESULT_OK) {
            finish();
        }
    }


    class jsAction {
        /**
         * 跳转到某Activity
         *
         * @param viewName Activity名称
         * @param key1
         * @param value1   字符串参数
         * @param key2
         * @param value2   整型参数
         */
        @JavascriptInterface
        public void openActivity(String viewName, String key1, String value1,
                                 String key2, Integer value2) {
            Class<?> cls = null;
            try {
                cls = Class.forName("com.linkshow.cityleader.app." + viewName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (cls != null) {
                Intent it = new Intent(WebViewActivity.this, cls);
                if (key1 != null && value1 != null) {
                    it.putExtra(key1, value1);

                }
                if (key2 != null && value2 != null) {
                    it.putExtra(key2, value2);
                }
                startActivity(it);
            }

        }

        /**
         * 弹窗提示并可跳转至某activity页面
         *
         * @param title
         * @param content
         * @param okBtnTitle
         * @param cancelBtnTitle
         * @param viewName       activity页面名称
         */
        @JavascriptInterface
        public void showDialog(final String title, final String content,
                               final String okBtnTitle, String cancelBtnTitle,
                               final String viewName) {
            ConfirmDialog dialog = new ConfirmDialog(WebViewActivity.this,
                    title, content,
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            openActivity(viewName, null, null, null, null);
                        }
                    }, null);
            if (okBtnTitle != "" && okBtnTitle != null) {
                dialog.setOkBtn(okBtnTitle);
            }
            if (cancelBtnTitle != "" && cancelBtnTitle != null) {
                dialog.setCancelBtn(cancelBtnTitle);
            }
            dialog.show();
        }

        /**
         * Toast消息提示
         *
         * @param msg
         */
        @JavascriptInterface
        public void showToast(String msg) {
            showTools.showToast(msg);
        }

        /**
         * 获取对应的平台版本
         *
         * @return
         */
        @JavascriptInterface
        public String getjsver() {
            PackageInfo info;
            try {
                info = getPackageManager().getPackageInfo(getPackageName(), 0);
                return info.versionCode + "";
            } catch (PackageManager.NameNotFoundException e) {
            }
            return "1.0.0";
        }

        /**
         * 是否需要在resume的时候刷新数据
         */
        @JavascriptInterface
        public void refershAfterChange(final String func) {
            needRefresh = true;
            function = func;
        }


        /**
         * 刷新当前页面
         */
        @JavascriptInterface
        public void refresh() {
            mHandler.post(new Runnable() {
                public void run() {
                    mWebView.loadUrl(url);
                }
            });
        }

    }

    private Intent createIntent(Class<?> cla) {
        return new Intent(this, cla);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

}

package cn.tse.pr.ui.my;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.utils.DeviceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.entity.news.YKVideoListEntity;
import cn.tse.pr.handle.HtmlHandle;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;
import cn.tse.pr.ui.news.NewsFragment;
import cn.tse.pr.utils.AppUtils;

/**
 * Created by xieye on 2017/4/12.
 */

public class MyActivity extends SwipeBackActivity {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initUser();
    }

    private void initUser() {
        AVUser user = AVUser.getCurrentUser();
        if (user == null) {
            tvName.setText("你的名字");
            Glide.with(this).load(R.mipmap.ico_default_avatar1).into(ivAvatar);
            return;
        }
        tvName.setText((String) user.get("nickname"));
        Glide.with(this).load((String) user.get("avatar")).into(ivAvatar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLogined(AVUser user) {
        initUser();
    }

    private void loadData() {

    }


    @OnClick({R.id.iv_back, R.id.ll_my
            , R.id.ll_feedback, R.id.ll_collect, R.id.ll_message, R.id.ll_about, R.id.ll_contact})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_message:
                showMsg("暂无消息");
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.ll_collect:
                if (MyInfo.getIntance().isLogin(this)) {
                    startActivity(new Intent(MyActivity.this, MyCollectionActivity.class));
                } else {
                    showMsg("请先登录");
                }

                break;
            case R.id.ll_about:
                startActivity(new Intent(MyActivity.this, AboutActivity.class));
                break;
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.ll_my:
                showLoginDialog();
                break;
            case R.id.ll_contact:
                showContactDialog();
                break;
        }
    }

    Tencent mTencent;
    IUiListener loginListener;

    private void showLoginDialog() {
        if (AVUser.getCurrentUser() != null) {
            new AlertDialog.Builder(this).setTitle(MyInfo.getIntance().getUser().getString("nickname")).setMessage("是否退出登录?")
                    .setCancelable(true).setNegativeButton("取消", null)
                    .setPositiveButton("退出登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyInfo.getIntance().logout();
                            initUser();
                        }
                    }).create().show();
            return;
        }
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_login_qq))
                .setGravity(Gravity.CENTER)
                .setExpanded(false)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        login();
                        dialog.dismiss();
                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }


    private void showContactDialog() {
        new AlertDialog.Builder(this).setTitle("联系作者").setMessage("吐槽一下作者")
                .setCancelable(true).setNegativeButton("取消", null)
                .setPositiveButton("去吐槽", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.sendEmail(MyActivity.this, "狼人杀头条", "我有话想对你说");
                    }
                }).create().show();
//        new ConfirmDialog(this, "联系作者", "吐槽一下作者", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppUtils.sendEmail(MyActivity.this, "狼人杀头条", "我有话想对你说");
//            }
//        }, null).setCancelBtn("取消").setOkBtn("去吐槽").show();
    }

    public void login() {

        mTencent = Tencent.createInstance("1106028103", this.getApplicationContext());
        if (!mTencent.isSessionValid()) {
            loginListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    JSONObject object = (JSONObject) o;

                    try {

                        String accessToken = object.getString("access_token");

                        String expires = object.getString("expires_in");

                        String openID = object.getString("openid");

                        mTencent.setAccessToken(accessToken, expires);

                        mTencent.setOpenId(openID);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }
                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            };
            mTencent.login(this, "all", loginListener);
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.REQUEST_LOGIN) {

            if (resultCode == -1) {

                Lg.e(data.getExtras().toString() + ">>> >>");

                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);

                Tencent.handleResultData(data, loginListener);

                UserInfo info = new UserInfo(this, mTencent.getQQToken());


                info.getUserInfo(new IUiListener() {

                    @Override

                    public void onComplete(Object o) {

                        try {

                            JSONObject info = (JSONObject) o;

                            String nickName = info.getString("nickname");//获取用户昵称

                            String iconUrl = info.getString("figureurl_qq_2");//获取用户头像的url

                            String qqOpenID = mTencent.getQQToken().getOpenId();

                            Lg.e(info.toString() + ">>> >>");

                            MyInfo.getIntance().qqLogin(qqOpenID, nickName, iconUrl);

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                    }


                    @Override

                    public void onError(UiError uiError) {


                    }


                    @Override

                    public void onCancel() {


                    }

                });

            }

        }

    }
}

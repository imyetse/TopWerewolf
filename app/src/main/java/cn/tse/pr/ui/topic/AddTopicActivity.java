package cn.tse.pr.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.yuzhi.fine.Lg.Lg;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.tse.pr.R;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.event.OnAddTopicEvent;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;
import cn.tse.pr.utils.QiNiuHelper;
import cn.tse.pr.utils.luban.Luban;
import cn.tse.pr.utils.luban.OnCompressListener;

import static cn.tse.pr.ui.adapter.TopicAdapter.TOPIC_TYPE_NORMAL;
import static cn.tse.pr.ui.adapter.TopicAdapter.TOPIC_TYPE_NORMAL_PIC;

/**
 * Created by xieye on 2017/6/5.
 */

public class AddTopicActivity extends SwipeBackActivity {
    @Bind(R.id.edit_topic)
    EditText etTitle;
    @Bind(R.id.edit_topic_detail)
    EditText etContent;
    @Bind(R.id.iv_add_pic)
    ImageView ivAdd;
    @Bind(R.id.iv_close)
    ImageView ivClose;

    private String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_topic);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (getIntent() == null) {
            return;
        }
        if (getIntent().getBooleanExtra("imgMode", false)) {
            select();
        }
    }

    public static void startActivity(Context context, boolean imgMode) {
        if (!MyInfo.getIntance().isLogin(context)) {
            Toast.makeText(context, "你还没有登陆哦", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, AddTopicActivity.class);
        intent.putExtra("imgMode", imgMode);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_add_pic, R.id.iv_close, R.id.iv_back, R.id.tv_submit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_pic:
                select();
                break;
            case R.id.iv_close:
                Glide.with(AddTopicActivity.this).load(R.mipmap.pic_add_avatar).into(ivAdd);
                ivClose.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_submit:
                addTopic();
                break;
        }
    }

    private void addTopic() {
        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            showMsg("请键入话题");
            return;
        }
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            showMsg("请键入话题内容");
            return;
        }
        if (etContent.getText().toString().length() < 20) {
            showMsg("再说多点");
            return;
        }
        AVObject product = new AVObject("Topic");
        product.put("topicTitle", "#" + etTitle.getText().toString() + "#");
        product.put("topicDetail", etContent.getText().toString());
        product.put("displayUrl", picUrl);
        product.put("type", TextUtils.isEmpty(picUrl) ? TOPIC_TYPE_NORMAL : TOPIC_TYPE_NORMAL_PIC);
        product.put("owner", AVUser.getCurrentUser());
        //新建一个 ACL 实例
        AVACL acl = new AVACL();
        acl.setPublicReadAccess(true);// 设置公开的「读」权限，任何人都可阅读
        acl.setPublicWriteAccess(true);// 为当前用户赋予「写」权限，有且仅有当前用户可以修改这条 Post
        product.setACL(acl);// 将 ACL 实例赋予 Post对象
        product.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnAddTopicEvent());
                    finish();
                } else {
                    showMsg("发布失败..请稍后重试");
                }
            }
        });
    }

    private void select() {
        RxGalleryFinal.with(this)
                .image()
                .radio()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        String path = imageRadioResultEvent.getResult().getThumbnailBigPath();
                        Glide.with(AddTopicActivity.this).load(path).into(ivAdd);
                        ivClose.setVisibility(View.VISIBLE);
                        Luban.get(AddTopicActivity.this)
                                .load(new File(path))
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                        Lg.e("Luban onStart>>");
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        Lg.e("Luban result>>" + file.getPath());
                                        Lg.e("Luban result exit>>" + new File(file.getPath()).exists());
                                        QiNiuHelper.getHelper().toUpload(file.getPath(), new QiNiuHelper.UploadListener() {
                                            @Override
                                            public void onSuccess(String url) {
                                                picUrl = url;
                                            }

                                            @Override
                                            public void onFailed(String msg) {
                                                showMsg(msg);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Lg.e("Luban error>>" + e.getMessage());
                                    }
                                })
                                .launch();

                    }
                }).openGallery();
    }

    private void upload(String originPath) {

    }


}

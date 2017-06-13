package cn.tse.pr.ui.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;

/**
 * Created by xieye on 2017.04.15.
 */

public class FeedbackActivity extends SwipeBackActivity {

    @Bind(R.id.et_content)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_submit)
    public void onViewClick() {
        String content = editText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showMsg("请输入你想反馈的内容...");
            return;
        }
        if (content.length() > 200) {
            showMsg("反馈内容不要超过200字...");
            return;
        }
        AVObject object = new AVObject("Feedback");
        object.put("content", content);
        object.put("contact", content);
        object.put("uid", MyInfo.getIntance().getUser().getObjectId());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    FeedbackActivity.this.finish();
                    showMsg("反馈成功，谢谢你的反馈");
                } else {
                    showMsg("反馈失败，稍后重试");
                }
            }
        });
    }
}

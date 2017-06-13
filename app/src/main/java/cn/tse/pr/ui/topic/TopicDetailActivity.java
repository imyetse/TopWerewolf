package cn.tse.pr.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.topic.CommentBean;
import cn.tse.pr.entity.topic.LikeEventBean;
import cn.tse.pr.entity.topic.TopicBean;
import cn.tse.pr.handle.LVOnItemClick;
import cn.tse.pr.mvp.presenter.TopicDetailPresenter;
import cn.tse.pr.mvp.presenter.TopicPresenter;
import cn.tse.pr.mvp.view.TopicDetailView;
import cn.tse.pr.mvp.view.TopicView;
import cn.tse.pr.ui.adapter.CommentAdapter;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;
import cn.tse.pr.ui.widget.CircleImageView;
import cn.tse.pr.ui.widget.FullListView;
import cn.tse.pr.ui.widget.WindowManagerView;
import cn.tse.pr.utils.AppUtils;

/**
 * Created by xieye on 2017/6/6.
 */

public class TopicDetailActivity extends SwipeBackActivity<TopicDetailPresenter, TopicDetailView> implements TopicDetailView {

    @Bind(R.id.rl_above)
    View viewAbove;
    @Bind(R.id.rl_to_input)
    View viewToInput;
    @Bind(R.id.flv_comment)
    FullListView flvComment;
    @Bind(R.id.iv_display)
    ImageView ivDisplay;
    @Bind(R.id.civ_avatar)
    CircleImageView civAvatar;
    @Bind(R.id.tv_topic_title)
    TextView tvTitle;
    @Bind(R.id.tv_topic_detail)
    TextView tvDetail;

    WindowManagerView viewInput;
    View viewTouch;
    EditText etInput;
    TextView tvSubmitComment;
    private WindowManager manager;
    private CommentAdapter commentAdapter;
    private TopicBean topicBean;
    private AVUser currentReplyUser;
    private List<CommentBean> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void likeCommentEvent(LikeEventBean eventBean) {
        getPresenter().likeComment(eventBean.commentId, topicBean.getId(), eventBean.likeNum);
    }

    private void initView() {
        final String topicStr = getIntent().getStringExtra("topicStr");
        topicBean = JSON.parseObject(topicStr, TopicBean.class);
        if (topicBean == null) {
            return;
        }
        Glide.with(this).load(topicBean.getDisplayUrl()).into(ivDisplay);
        Glide.with(this).load(topicBean.getAvUser().getString("avatar")).into(civAvatar);
        tvTitle.setText(topicBean.getTopicTitle());
        tvDetail.setText(topicBean.getTopicDetail());
        manager = getWindowManager();
        viewInput = getEditView();
        viewTouch = viewInput.findViewById(R.id.view_touch);
        etInput = (EditText) viewInput.findViewById(R.id.et_input);
        tvSubmitComment = (TextView) viewInput.findViewById(R.id.tv_submit);

        viewInput.setKeyEvent(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    showEditor(null);
                }
                return false;
            }
        });

        viewTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditor(null);
            }
        });

        tvSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyInfo.getIntance().isLogin(TopicDetailActivity.this)) {
                    showMsg("请先登录！");
                    return;
                }
                if (TextUtils.isEmpty(etInput.getText().toString())) {
                    showMsg("请输入评论内容");
                    return;
                }
                CommentBean commentBean = new CommentBean();
                commentBean.setTopicBean(topicBean);
                commentBean.setLike(false);
                commentBean.setTitle("title");
                commentBean.setUser(MyInfo.getIntance().getUser());
                commentBean.setReplyUser(currentReplyUser);
                commentBean.setContent(etInput.getText().toString());
                commentList.add(0, commentBean);
                commentAdapter.setDataList(commentList);
                getPresenter().commentTopic(commentBean);
                etInput.setText("");
                showEditor(null);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            viewAbove.setPadding(viewAbove.getPaddingLeft(), viewAbove.getPaddingTop() + getStatusBarHeight(), +viewAbove.getPaddingRight(), viewAbove.getPaddingBottom());
            ViewGroup.LayoutParams layoutParams = viewAbove.getLayoutParams();
            layoutParams.height = layoutParams.height + getStatusBarHeight();
            viewAbove.setLayoutParams(layoutParams);
        }

        commentAdapter = new CommentAdapter();
        flvComment.setAdapter(commentAdapter);
        commentAdapter.setDataList(commentList);
        commentAdapter.setOnItemClick(new LVOnItemClick<CommentBean>() {
            @Override
            public void onItemClick(int type, int position, CommentBean commentBean) {
                if (commentBean.getUser().getObjectId().equals(MyInfo.getIntance().getUser().getObjectId())) {
                    showMsg("不能评论自己");
                    return;
                }
                showEditor(commentBean.getUser());
            }
        });
    }

    private void loadData() {
        getPresenter().getTopicComment(topicBean.getId());
    }

    @OnClick({R.id.rl_to_input, R.id.iv_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_to_input:
                showEditor(null);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private boolean isEditorShown = false;

    public void showEditor(AVUser toUser) {
        if (!isEditorShown) {
            //自定义的编辑控件，可以是一个EditText
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.gravity = Gravity.BOTTOM;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.RGBA_8888;
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

            etInput.requestFocus();
            etInput.setVisibility(View.INVISIBLE);
            currentReplyUser = toUser;
            if (toUser != null) {
                etInput.setHint("回复 " + toUser.getString("nickname"));
            } else {
                etInput.setHint("写评论");
            }
            manager.addView(viewInput, params);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    etInput.setVisibility(View.VISIBLE);
                }
            }, 200);

            isEditorShown = true;
        } else {
            etInput.clearFocus();
            manager.removeView(viewInput);
            isEditorShown = false;
            AppUtils.HideKeyboard(etInput);
        }
    }

    public static void startActivity(Context context, String topicStr) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra("topicStr", topicStr);
        context.startActivity(intent);
    }


    @Override
    protected boolean fitWindow() {
        return false;
    }

    public WindowManagerView getEditView() {
        return (WindowManagerView) LayoutInflater.from(this).inflate(R.layout.layout_input_bottom, null);
    }

    @Override
    public void onGetComment(List<CommentBean> list) {
        this.commentList = list;
        commentAdapter.setDataList(commentList);
    }

    @Override
    public void onComment(boolean success, CommentBean commentBean) {
        if (!success) {
            showMsg("评论失败,请稍后重试");
        }
    }

    @Override
    public void onGetLike(List<CommentBean> commentWithLikes) {
        this.commentList = commentWithLikes;
        commentAdapter.setDataList(commentList);
    }


    @Override
    public void onTopicGet(List<TopicBean> topics) {
    }
}

package cn.tse.pr.ui.adapter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.db.MyInfo;
import cn.tse.pr.entity.topic.CommentBean;
import cn.tse.pr.entity.topic.LikeEventBean;
import cn.tse.pr.ui.widget.CircleImageView;
import cn.tse.pr.ui.widget.LikeView;

/**
 * Created by xieye on 2017/6/7.
 */

public class CommentAdapter extends BaseListViewAdapter<CommentBean> {
    @Override
    protected BaseItem<CommentBean> createItem(int type) {
        return new CommentItem();
    }

    class CommentItem extends BasedItem {
        @Bind(R.id.likeView)
        LikeView likeView;
        @Bind(R.id.iv_header)
        CircleImageView civHeader;
        @Bind(R.id.tv_user_name)
        TextView tvUserName;
        @Bind(R.id.tv_reply)
        TextView tvReply;
        @Bind(R.id.tv_comment_content)
        TextView tvContent;
        @Bind(R.id.tv_comment_date)
        TextView tvCommentTime;

        @Override
        public int getLayoutResId() {
            return R.layout.item_comment;
        }

        @Override
        public void bindViews(View root) {
            rootView = root;
            ButterKnife.bind(this, root);
        }

        @Override
        public void setViews() {
        }

        @Override
        public void handleData(final CommentBean commentBean, int position) {
            super.handleData(commentBean, position);
            likeView.switchWithoutAni(commentBean.isLike());
            AVUser user = commentBean.getUser();
            AVUser replyUser = commentBean.getReplyUser();
            if (user != null) {
                Glide.with(context).load(commentBean.getUser().getString("avatar")).into(civHeader);
                tvUserName.setText(user.getString("nickname"));
            }
            if (!MyInfo.getIntance().isLoginPure(context)) {
                likeView.setLikeEnable(false);
            }
            if (replyUser != null) {
                tvReply.setText("回复 " + replyUser.getString("nickname"));
                tvReply.setVisibility(View.VISIBLE);
            } else {
                tvReply.setVisibility(View.GONE);
            }
            tvContent.setText(commentBean.getContent());
            tvCommentTime.setText(commentBean.getCreateAt());
            likeView.setNum(commentBean.getLikeNum());
            likeView.setClickListener(new LikeView.LikeClickListener() {
                @Override
                public void onLike(boolean toLike) {
                    if (toLike) {
                        //赞 false就是取消赞
                        LikeEventBean eventBean = new LikeEventBean();
                        eventBean.commentId = commentBean.getId();
                        eventBean.likeNum = commentBean.getLikeNum();
                        EventBus.getDefault().post(eventBean);
                    }
                }

                @Override
                public void onRefuse() {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

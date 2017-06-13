package cn.tse.pr.ui.widget;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.utils.DeviceUtil;

import cn.tse.pr.R;

/**
 * Created by xieye on 2017/6/7.
 */

public class LikeView extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private ImageView likeView;
    private TextView tvLikeNum;

    private int[] like_icons = new int[]{R.mipmap.ico_like, R.mipmap.ico_liked};
    private int[] like_txt_colors = new int[2];
    private int currentIndex = 0;
    private boolean isEnable = true;
    private boolean unlikeEnable = false;//是可以取消赞
    private boolean likeSet = false;//是否改变了状态
    private int currentNum = 0;

    private LikeClickListener clickListener;

    public LikeView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LikeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void setUnlikeEnable(boolean enable) {
        this.unlikeEnable = enable;
    }

    public void setLikeEnable(boolean enable) {
        this.isEnable = enable;
    }

    private void init() {
        like_txt_colors[0] = context.getResources().getColor(R.color.txt_black);
        like_txt_colors[1] = context.getResources().getColor(R.color.red_like);

        likeView = new ImageView(context);
        tvLikeNum = new TextView(context);
        int width = DeviceUtil.dp2px(context, 20);
        int height = DeviceUtil.dp2px(context, 20);

        RelativeLayout.LayoutParams layoutParams = new LayoutParams(width, height);
        likeView.setLayoutParams(layoutParams);
        likeView.setId(R.id.view1);
        likeView.setBackgroundResource(R.mipmap.ico_like);

        RelativeLayout.LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.RIGHT_OF, R.id.view1);
        textParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textParams.leftMargin = DeviceUtil.dp2px(context, 3);
        tvLikeNum.setLayoutParams(textParams);
        tvLikeNum.setText("0");
        tvLikeNum.setTextColor(like_txt_colors[0]);
        tvLikeNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        addView(likeView);
        addView(tvLikeNum);

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void switchWithoutAni(boolean like) {
        if (like) {
            if (currentIndex == 1) {
                return;
            }
            currentIndex = 1;
        } else {
            if (currentIndex == 0) {
                return;
            }
            currentIndex = 0;
        }
        likeView.setBackgroundResource(like_icons[currentIndex]);
        tvLikeNum.setTextColor(like_txt_colors[currentIndex]);
    }

    public void setNum(int likeNum) {
        this.currentNum = likeNum;
        tvLikeNum.setText(String.valueOf(currentNum));
    }

    public void switchLike() {
        if (currentIndex == 1) {
            //点赞状态
            if (!unlikeEnable) {
                return;
            }
            if (clickListener != null) {
                clickListener.onLike(false);
            }
            currentIndex = 0;
            likeView.setBackgroundResource(like_icons[currentIndex]);
            tvLikeNum.setTextColor(like_txt_colors[currentIndex]);
        } else {
            if (clickListener != null) {
                clickListener.onLike(true);
            }
            currentIndex = 1;
            setNum(currentNum + 1);
            build().start();
        }
    }

    private ObjectAnimator build() {
        likeSet = false;
        PropertyValuesHolder[] holders = new PropertyValuesHolder[3];

        Keyframe[] alphaFrames = new Keyframe[3];
        alphaFrames[0] = Keyframe.ofFloat(0f, 1f);
        alphaFrames[1] = Keyframe.ofFloat(0.5f, 0.2f);
        alphaFrames[2] = Keyframe.ofFloat(1f, 1f);
        holders[0] = PropertyValuesHolder.ofKeyframe("alpha", alphaFrames);

        Keyframe[] scaleXFrames = new Keyframe[3];
        scaleXFrames[0] = Keyframe.ofFloat(0f, 1f);
        scaleXFrames[1] = Keyframe.ofFloat(0.5f, 0.5f);
        scaleXFrames[2] = Keyframe.ofFloat(1f, 1f);
        holders[1] = PropertyValuesHolder.ofKeyframe("scaleX", scaleXFrames);

        Keyframe[] scaleYFrames = new Keyframe[3];
        scaleYFrames[0] = Keyframe.ofFloat(0f, 1f);
        scaleYFrames[1] = Keyframe.ofFloat(0.5f, 0.5f);
        scaleYFrames[2] = Keyframe.ofFloat(1f, 1f);
        holders[2] = PropertyValuesHolder.ofKeyframe("scaleY", scaleYFrames);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(likeView, holders);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedFraction() >= 0.5f && !likeSet) {
                    likeSet = true;
                    likeView.setBackgroundResource(like_icons[currentIndex]);
                    tvLikeNum.setTextColor(like_txt_colors[currentIndex]);
                }
            }
        });
        return animator;
    }

    @Override
    public void onClick(View v) {
        if (!isEnable) {
            if (clickListener != null) {
                clickListener.onRefuse();
            }
            return;
        }
        switchLike();
    }

    public void setClickListener(LikeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface LikeClickListener {
        void onLike(boolean toLike);

        void onRefuse();
    }
}

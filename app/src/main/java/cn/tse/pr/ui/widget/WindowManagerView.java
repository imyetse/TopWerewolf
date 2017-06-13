package cn.tse.pr.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 监听输入法弹出上的keyEvent
 * <p>
 * Created by xieye on 2017/6/6.
 */

public class WindowManagerView extends LinearLayout {
    public WindowManagerView(Context context) {
        super(context);
    }

    public WindowManagerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {

        if (keyListener != null) {
            keyListener.onKey(this, event.getKeyCode(), event);
        }

        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    public void setKeyEvent(OnKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    private OnKeyListener keyListener;

}

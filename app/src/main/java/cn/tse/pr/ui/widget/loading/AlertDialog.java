package cn.tse.pr.ui.widget.loading;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.tse.pr.R;


public class AlertDialog extends Dialog {

    WindowManager wManager;
    private Button okBtn;
    private Context ctx;
    private String message, title;
    private View.OnClickListener okClickListener;
    private boolean edited;
    private String edittext;
    private String okBtnTitle = "确定";

    public void setOkBtn(String content) {
        okBtnTitle = content;
    }

    public AlertDialog(Context context, String message) {
        super(context, R.style.AppTheme_Dialog);
        wManager = ((Activity) context).getWindowManager();
        ctx = context;
        this.edited = false;
        this.edittext = null;
        okClickListener = null;
        this.message = message;
        textView = (TextView) findViewById(R.id.tx_alertdialog_msg);
        this.setCanceledOnTouchOutside(false);
    }


    public AlertDialog(Context context, String message,
                       View.OnClickListener _okClickListener) {
        super(context, R.style.AppTheme_Dialog);
        wManager = ((Activity) context).getWindowManager();
        ctx = context;
        this.edited = false;
        this.edittext = null;
        okClickListener = _okClickListener;
        this.message = message;
        textView = (TextView) findViewById(R.id.tx_alertdialog_msg);
        this.setCanceledOnTouchOutside(false);
    }

    public AlertDialog(Context context, String message,
                       String title) {
        super(context, R.style.AppTheme_Dialog);
        wManager = ((Activity) context).getWindowManager();
        ctx = context;
        this.edited = false;
        this.edittext = null;
        okClickListener = null;
        this.message = message;
        this.title = title;
        textView = (TextView) findViewById(R.id.tx_alertdialog_msg);
        this.setCanceledOnTouchOutside(false);
    }


    public void closeDialog() {
        this.dismiss();
    }

    public EditText editText;
    TextView textView, titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        final View root = inflater.inflate(R.layout.dialog_alert,
                (ViewGroup) findViewById(R.id.llyt_alert_dialog));
        setCanceledOnTouchOutside(false);
        this.setContentView(root);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        Display d = wManager.getDefaultDisplay();
        params.width = (int) (d.getWidth() * 0.8);
        if (edited) {
            editText = (EditText) findViewById(R.id.edt_alertdialog);
            editText.setVisibility(View.VISIBLE);
            editText.setText(edittext);
            Editable b = editText.getText();
            editText.setSelection(b.length());
        }

        textView = (TextView) findViewById(R.id.tx_alertdialog_msg);
        titleTv = (TextView) findViewById(R.id.tx_alertdialog_title);
        if (TextUtils.isEmpty(this.title)) {
            titleTv.setVisibility(View.GONE);
        } else
            titleTv.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(message)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(textView, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
        getWindow().setAttributes(params);
        okBtn = (Button) findViewById(R.id.btn_alertdialog_ok);
        textView.setText(this.message);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
                if (okClickListener != null)
                    okClickListener.onClick(v);
            }
        });
        setCancelable(true);
        okBtn.setText(okBtnTitle);
        setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                return false;
            }
        });
    }
}

package cn.tse.pr.ui.my;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.BuildConfig;
import cn.tse.pr.R;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;

/**
 * Created by xieye on 2017.04.15.
 */

public class AboutActivity extends SwipeBackActivity {

    @Bind(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        tvVersion.setText(BuildConfig.VERSION_NAME);
    }
}

package cn.tse.pr;

import android.app.Application;
import android.text.TextUtils;

import com.avos.avoscloud.AVOSCloud;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.Lg.LgImpl;
import com.yuzhi.fine.common.AppContext;
import com.yuzhi.fine.common.AppException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xieye on 2017/4/8.
 */

public class WWApp extends Application {

    private static WWApp app;

    public WWApp() {
        app = this;
    }

    public static synchronized WWApp getInstance() {
        if (app == null) {
            app = new WWApp();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerUncaughtExceptionHandler();
        LgImpl.init(this);
        initLeanCloud();
        initX5();
        initBugly();
        initShare();
    }


    private void initShare(){
        ShareSDK.initSDK(this);
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        // 获取当前包名
        String packageName = getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        //只在主进程进行数据上报
        userStrategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), "569e92d298", BuildConfig.DEBUG, userStrategy);
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Lg.d("app>>> onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, "9tUg3Makp2zzPggJSeQ43w8h-gzGzoHsz", "PqlU7p3NspkKmqryBEdBnyRX");
        AVOSCloud.setDebugLogEnabled(true);
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }
}

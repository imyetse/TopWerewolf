package cn.tse.pr.db;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.signature.Base64Encoder;
import com.yuzhi.fine.Lg.Lg;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.UUID;

/**
 * Created by xieye on 2017/4/14.
 */

public class MyInfo {

    private static MyInfo myInfo;
    private AVUser avUser;

    public static MyInfo getIntance() {
        synchronized (AppDao.class) {
            if (myInfo == null) {
                myInfo = new MyInfo();
            }
        }
        return myInfo;
    }

    public AVUser getUser() {
        if (avUser == null) {
            avUser = AVUser.getCurrentUser();
        }
        if (avUser == null) {
            return new AVUser();
        }
        return avUser;
    }

    public void qqLogin(final String openId, final String nickname, final String avatar) {
        AVUser.getQuery().whereContains("username", openId).findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null) {
                    Lg.e("查询成功");
                    if (list.size() > 0) {
                        Lg.e("帐号存在  开始登录");
                        login(openId);
                    } else {
                        register(openId, nickname, avatar);
                    }
                } else {
                    Lg.e("查询失败了哈哈:" + e.getMessage());
                }
            }
        });


    }

    public boolean isLogin(Context context) {
        boolean isLogin = !getUser().getObjectId().equals("");
        if (!isLogin) {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }
        return isLogin;
    }


    public boolean isLoginPure(Context context) {
        return !getUser().getObjectId().equals("");
    }

    public void register(String openId, String nickName, String avatar) {
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(openId);// 设置用户名
        user.setPassword(Base64Encoder.encode(openId));// 设置密码
        user.put("avatar", avatar);//设置头像
        user.put("nickname", nickName);//设置昵称
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    Lg.e("注册成功:" + AVUser.getCurrentUser().getUsername());
                    EventBus.getDefault().post(AVUser.getCurrentUser());
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    Lg.e("注册失败:" + e.getMessage());
                }
            }
        });
    }

    public void login(String username) {
        AVUser.logInInBackground(username, Base64Encoder.encode(username), new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    // 登录成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    Lg.e("登录成功:" + AVUser.getCurrentUser().getUsername());
                    EventBus.getDefault().post(AVUser.getCurrentUser());
                } else {
                    // 失败
                    Lg.e("登录失败:" + e.getMessage());
                }
            }
        });
    }


    public void logout() {
        AVUser.logOut();
    }

}

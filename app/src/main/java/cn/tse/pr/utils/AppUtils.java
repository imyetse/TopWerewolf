package cn.tse.pr.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by xieye on 2017/6/8.
 */

public class AppUtils {
    //隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    //显示虚拟键盘
    public static void ShowKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    public static void sendEmail(Context context, String title, String content) {

        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:yeshieh@163.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, title);
        data.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(data);
    }

    public static String getCommentTime(long time) {
        long currentTime = System.currentTimeMillis();

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);

        long differTime = currentTime - time;
        long timeInWeek = 7 * 24 * 60 * 60 * 1000;
        long timeInDay = 24 * 60 * 60 * 1000;

        System.out.println("differTime>>" + differTime);
        String dateString;
        if (differTime > timeInWeek) {
            //一周前评论 显示完整日期
            System.out.println("完整日期>>");
            return getDateStr(calendar);
        }

        if (differTime > timeInDay) {
            int day = (int) ((double) differTime / (double) (24 * 60 * 60 * 1000)) + 1;
            return day + "天前";
        }

        long timeInHour = 60 * 60 * 1000;
        long timeInMin = 60 * 1000;
        long hour = (differTime % timeInDay);
        long min = (differTime % timeInHour);

        if (differTime > timeInHour) {
            return (hour / timeInHour + 1) + "小时前";
        }

        return (min / timeInMin + 1) + "分钟前";

    }


    public static String getDateStr(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        String dateStr = year + "-";
        if (month < 10) {
            dateStr += "0" + month + "-";
        } else {
            dateStr += month + "-";
        }
        if (day < 10) {
            dateStr += "0" + day;
        } else {
            dateStr += day;
        }
        dateStr = dateStr + " ";
        if (hour < 10) {
            dateStr += "0" + hour + ":";
        } else {
            dateStr += hour + ":";
        }
        if (min < 10) {
            dateStr += "0" + min;
        } else {
            dateStr += min;
        }

        return dateStr;
    }

}

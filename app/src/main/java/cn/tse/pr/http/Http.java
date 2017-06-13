package cn.tse.pr.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.http.HttpResponseHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import cn.tse.pr.WWApp;
import cn.tse.pr.entity.news.JRTTEntity;
import okhttp3.Request;
import okhttp3.Response;

import static com.yuzhi.fine.http.HttpClient.get;
import static com.yuzhi.fine.http.HttpClient.getWithHeader;
import static com.yuzhi.fine.http.HttpClient.post;

/**
 * Created by xieye on 2017/4/8.
 */

public class Http {

    private static final String JRTT_SEARCH_URL = "http://www.toutiao.com/search_content/";
    private static final String SH_SERACH_URL = "http://apiv2.sohu.com/apiV2/re/tag/news";
    private static final String JRTT_VIDEO_DETAIL_BASE = "http://ib.365yg.com";
    private static final String JRTT_VIDEO_DETAIL_URL = "/video/urls/v/1/toutiao/mp4/%1$s";

    /**
     * 优酷关键字搜索播放单 参数keyword 关键字
     */
    //private static final String YK_SEARCH_KEYWORD_URL = "http://api.appsdk.soku.com/i/s?keyword=%1$s&pid=bb2388e929bc3038&guid=3e65d836776c5b5d4229ab78c91bf0ab&_t_=1492138671&e=md5&_s_=8583e139cfd0bdc4300503553c79973e&ftype=0&cateId=2004&seconds=0&seconds_end=0&ob=0&pg=1&aaid=149213866319885943314&brand=Meizu&btype=PRO+6&sdkver=26&apad=0&utdid=V3PPEzbapRcDACcQGwD4Riyp&srid=2&area_code=1&cookie=&userType=guest";
    private static final String YK_SEARCH_KEYWORD_URL = "http://acs.youku.com/gw/mopen.youku.soku.sdk.isearch/1.0/?data={%22guid%22:%223e65d836776c5b5d4229ab78c91bf0ab%22,%22userType%22:%22guest%22,%22e%22:%22md5%22,%22utdid%22:%22V3PPEzbapRcDACcQGwD4Riyp%22,%22apad%22:%220%22,%22_t_%22:%221497255870%22,%22seconds_end%22:%220%22,%22imei%22:%22869011028895289%22,%22srid%22:%223%22,%22keyword%22:%22%E7%8B%BC%E4%BA%BA%E6%9D%80%22,%22cateId%22:%222004%22,%22aaid%22:%22149725583831242898002%22,%22pid%22:%22bb2388e929bc3038%22,%22btype%22:%22PRO%2B6%22,%22seconds%22:%220%22,%22area_code%22:%221%22,%22mac%22:%2202:00:00:00:00:00%22,%22pg%22:%221%22,%22network%22:%22WIFI%22,%22operator%22:%22CMCC_46002%22,%22brand%22:%22Meizu%22,%22ob%22:%220%22,%22ftype%22:%220%22,%22sdkver%22:%2230%22,%22_s_%22:%222a632d973c5a6fc1e0f4dde1cb4061f3%22,%22ver%22:%226.7.1.3%22}";
    /**
     * 优酷播放清单中的播放列表 参数playlistid 列表id
     */
    private static final String YK_SEATCH_LIST_URL = "http://api.appsdk.soku.com/p/d?pid=bb2388e929bc3038&guid=3e65d836776c5b5d4229ab78c91bf0ab&_t_=1492139126&e=md5&_s_=eaf1197b44271f495a77e6228abfed90&operator=CMCC_46002&network=WIFI&playlistid=%1$s&cookie=&userType=guest";
    /**
     * 获取优酷视频详情 参数vid 视频id
     */
   // private static final String YK_GET_VIDEO_INFO = "http://ups.youku.com/ups/get.json?ckey=BBXS_8BDkrqPxi5jFJwKhEzU3o2gVCVlh4aS0WxyUFUSQfHHQCawQ7ud8TQKWn%2FkcJnLjGfO5FVzeax3pZtDEEBE4brY5R27y9tIQSFgVnd78mzeWY9E4Otj220omk7JWCG9WfHKXgoVGbNbvBQGQ6w2ytQIsWnKDcjG43WikURRemd4vjL1Oae7yNvtI6NXorXFatfT1q6%2BK1A5l6GaaTtBgLUQf%2FNckVf1l9rt3ZfeuxPhSjeKVuMKfGsmLGEPmFqUSITGgu7KQXht0oGVm7QbI7ks1SPFzTkzHoQHGgIRmBrI00Gy5u9AbdLq5X%2FEB4RafrYmJU6aBIZZtMKpAC4T5sZ5nXN%2BsljnNKgn3HuZ4MxppAYDli1nUthtISYyeS4F%2BYOnnFrXfIY5RvreL2IKjPQ%3D%3D%26PZM9_a0010d246157b0c68a302ed5e8617c0a41cc6080a4108&client_ip=192.168.1.104&client_ts=1492100745&utid=&ccode=01010101&h265=0&point=1&audiolang=1&media_type=standard,audio&os_ver=6.0&app_ver=6.4.6&device_type=phone&guid=e463bfca66ae36d4d6c77c9485f5f265&site=1&utdid=V3PPEzbapRcDACcQGwD4Riyp&player_type=mdevice&is_fullscreen=0&position=7&vc=0&adext=&version=1.0&ss=4.6&pid=bb2388e929bc3038&ev=&rst=flv&dq=mp4&isvert=0&dprm=3000&d=PMTA3Njg3NDYw&aid=acd7141a4c37775";
    private static final String YK_GET_VIDEO_INFO = "https://ups.youku.com/ups/get.json?ccode=0402&client_ip=192.168.1.1&utid=FwhAEaGROBICATo%2FAzIC63m9&client_ts=1496225006&ob=1";
    /**
     * 优酷关键字搜索播放单 关键字
     *
     * @param keyWord 关键字
     * @return
     */
    public static Response getYKVideoAlbums(String keyWord) {
        if (!isNetAvailable()) {
            return null;
        }
       // String url = String.format(YK_SEARCH_KEYWORD_URL, keyWord);
        return getWithHeader(YK_SEARCH_KEYWORD_URL);
    }

    /**
     * 优酷播放清单中的播放列表 关键字
     *
     * @param playlistid 列表id
     * @return
     */
    public static Response getYKVideoPlayList(String playlistid) {
        if (!isNetAvailable()) {
            return null;
        }
        String url = String.format(YK_SEATCH_LIST_URL, playlistid);
        return get(url);
    }

    /**
     * 优酷播放清单中的播放列表 关键字
     *
     * @param vid 视频id
     * @return
     */
    public static Response getYKVideoInfo(String vid) {
        if (!isNetAvailable()) {
            return null;
        }
        String url = YK_GET_VIDEO_INFO + "&vid=" + vid;
        return get(url);
    }

    /**
     * 获取今日头条的视频详情
     *
     * @param videoId
     * @return
     */
    public static Response getJRTTVideo(String videoId) {
        if (!isNetAvailable()) {
            return null;
        }

        String crcUrl = String.format(JRTT_VIDEO_DETAIL_URL, videoId);
        crcUrl = crcUrl + "?r=7937864853677161";

        CRC32 crc32 = new CRC32();
        crc32.update(crcUrl.getBytes());
        long crcCode = crc32.getValue();

        crcUrl = crcUrl + "&s=" + crcCode;
        return get(JRTT_VIDEO_DETAIL_BASE + crcUrl);
    }

    /**
     * 今日头条json数据
     *
     * @param keyWord
     * @param offset
     * @param count
     * @param tab
     * @param httpResponseHandler
     */
    public static void getJRTTList(String keyWord, int offset, int count, int tab, HttpResponseHandler httpResponseHandler) {
        if (!isNetAvailable()) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyWord);
        params.put("format", "json");
        params.put("offset", String.valueOf(offset));
        params.put("autoload", String.valueOf(false));
        params.put("count", String.valueOf(count));
        params.put("cur_tab", String.valueOf(tab));
        get(JRTT_SEARCH_URL, params, httpResponseHandler);
    }

    /**
     * SoHu json数据
     *
     * @param tagId               #67121 狼人杀标签的id
     * @param pNo                 page页数
     * @param pSize               一页的数量
     * @param httpResponseHandler
     */
    public static void getSHList(String tagId, int pNo, int pSize, HttpResponseHandler httpResponseHandler) {
        if (!isNetAvailable()) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("tagId", tagId);
        params.put("pno", String.valueOf(pNo));
        params.put("psize", String.valueOf(pSize));
        get(SH_SERACH_URL, params, httpResponseHandler);
    }


    private static boolean isNetAvailable() {
        if (!isNetworkAvailable()) {
            Toast.makeText(WWApp.getInstance(), com.yuzhi.fine.R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) WWApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            Log.v("ConnectivityManager", e.getMessage());
        }
        return false;
    }
}

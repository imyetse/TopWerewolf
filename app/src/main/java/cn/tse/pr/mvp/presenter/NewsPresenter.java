package cn.tse.pr.mvp.presenter;

/**
 * Created by xieye on 2017/4/11.
 */

public interface NewsPresenter extends BasePresenter {
    void getNewsFromJRRT(String keyWord, int offset, int count, int tab);

    void getNewsFromSH(String tagId, int pNo, int pSize);

    void getNewsFromZH(int page);

    void getNewsFromDB();

    void getYKVideoAlbums(String keyWord);

    void getYKVideoPlayList(String playListId);

    void getNewsFromBD(String keyword, int page);

    void getYKVideoInfo(String videoId);
}

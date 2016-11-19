package cl.telematica.android.certamen3.views;

import java.util.List;

import cl.telematica.android.certamen3.models.Feed;

/**
 * Created by cristian on 18-11-2016.
 */

public interface MainView {
    void createMyRecyclerView();
    List<Feed> getFeeds(String result);
    void imprimeBd();

}

package cl.telematica.android.certamen3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cl.telematica.android.certamen3.R;
import cl.telematica.android.certamen3.models.Feed;
import cl.telematica.android.certamen3.presenters.MyAsyncTaskExecutor;
import cl.telematica.android.certamen3.views.MainView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm realm;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMyRecyclerView();
        MyAsyncTaskExecutor.getInstance().executeMyAsynctask(this, mRecyclerView);


        realm = Realm.getDefaultInstance();
        // ... Do something ...
        File outFile = this.getDatabasePath("default.realm");
        String outFileName = outFile.getPath();
        System.out.println(outFile);
    }

    public void createMyRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public List<Feed> getFeeds(String result) {
        List<Feed> feeds = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject responseData = jsonObject.getJSONObject("responseData");
            JSONObject feedObj = responseData.getJSONObject("feed");

            JSONArray entries = feedObj.getJSONArray("entries");
            int size = entries.length();
            for(int i = 0; i < size; i++){
                JSONObject entryObj = entries.getJSONObject(i);
                Feed feed = new Feed();

                feed.setTitle(entryObj.optString("title"));
                feed.setLink(entryObj.optString("link"));
                feed.setAuthor(entryObj.optString("author"));
                feed.setPublishedDate(entryObj.optString("publishedDate"));
                feed.setContent(entryObj.optString("content"));
                feed.setImage(entryObj.optString("image"));

                feeds.add(feed);
            }

            return feeds;
        } catch (JSONException e) {
            e.printStackTrace();
            return feeds;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            /**
             * You should manage the action to show the favorite items saved by the user
             * boton para mostrar los favoritos
             */

                Intent intent = new Intent(MainActivity.this,Favoritos.class);
                startActivity(intent);
                return true;
            }

            return super.onOptionsItemSelected(item);


            //imprimeBd();// imprime por consola la bd

    }


    @Override
    public void imprimeBd() {

        RealmResults<Feed> results = realm.where(Feed.class)
                //.equalTo("", "")
                //.or()
                //.equalTo("", "")
                .findAll();
        for (int i = 0; i < results.size(); i++) {
            Feed u = results.get(i);

            //u.getAuthor().toString()
        }
       System.out.println(results);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

package cl.telematica.android.certamen3.activities;

import android.os.Bundle;

import cl.telematica.android.certamen3.models.Feed;
import io.realm.Realm;
import io.realm.RealmResults;
import cl.telematica.android.certamen3.R;

/**
 * Created by cristian on 18-11-2016.
 */

public class Favoritos extends MainActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos);

        realm = Realm.getDefaultInstance();

        RealmResults<Feed> favoritos = realm.where(Feed.class)
                .findAll();
        System.out.println(favoritos);


    }


}

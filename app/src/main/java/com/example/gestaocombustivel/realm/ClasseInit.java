package com.example.gestaocombustivel.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ClasseInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration configuracaoRealm = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration( new GerenciadorRealm() )
                .build();

        Realm.setDefaultConfiguration( configuracaoRealm );
        Realm.getInstance( configuracaoRealm );
    }
}

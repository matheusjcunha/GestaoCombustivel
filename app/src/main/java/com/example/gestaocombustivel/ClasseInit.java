package com.example.gestaocombustivel;

import android.app.Application;

import io.realm.Realm;

public class ClasseInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

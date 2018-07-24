package com.master.killercode.libloginverifier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.master.killercode.loginverifier.LoginVerifier;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final LoginVerifier log = new LoginVerifier(Splash.this);
        log.getLogin(Main2Activity.class , MainActivity.class);

    }
}

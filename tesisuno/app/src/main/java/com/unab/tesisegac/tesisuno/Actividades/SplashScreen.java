package com.unab.tesisegac.tesisuno.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;

import com.unab.tesisegac.tesisuno.Registro.PreRegistro;
import com.unab.tesisegac.tesisuno.R;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ConstraintLayout layout_splash= findViewById(R.id.layout_splash);
        AnimationDrawable animationDrawable= (AnimationDrawable)  layout_splash.getBackground();
        animationDrawable.setEnterFadeDuration(200);
        animationDrawable.setExitFadeDuration(400);
        animationDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this, PreRegistro.class);
                startActivity(intent);
                finish();
            }
        },1200);

    }
}

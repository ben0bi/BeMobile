package com.bbs.bemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
    }

    public void cassaClick(View view)
    {
        StaticValues.playSound(this, R.raw.btn_tick);
        Intent in = new Intent(this, CassaActivity.class);
        startActivity(in);
    }
}

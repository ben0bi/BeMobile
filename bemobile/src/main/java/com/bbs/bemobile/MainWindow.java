package com.bbs.bemobile;

import android.content.Intent;
import android.net.Uri;
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

    public void starforceClick(View view)
    {
        goToUrl("https://benis-bastelschuppen.github.io");
    }

    public void cassaListClick(View view)
    {
        // don't show the list when there is nothing to show.
   /*     if(StaticValues.getActualBillItemCount()<=0)
        {
            StaticValues.playSound(this, R.raw.btn_no_action_done);
            return;
        }
*/
        StaticValues.playSound(this, R.raw.btn_tick);
        Intent in = new Intent(this, BillListActivity.class);
        startActivity(in);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}

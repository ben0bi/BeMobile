package com.bbs.bemobile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Log.d("MAIN", "Welcome to BeMobile");
    }

    // click on the cassa button
    public void cassaClick(View view)
    {
        StaticValues.playSound(this, R.raw.btn_tick);
        Intent in = new Intent(MainWindow.this, CassaActivity.class);
        startActivity(in);
    }

    // click on the link button
    public void starforceClick(View view)
    {
        goToUrl("https://benis-bastelschuppen.github.io");
    }

    // click on the complete bill button.
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
        Intent in = new Intent(MainWindow.this, BillListActivity.class);
        startActivity(in);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}

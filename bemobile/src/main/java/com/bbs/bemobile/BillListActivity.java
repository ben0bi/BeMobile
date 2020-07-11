package com.bbs.bemobile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class BillListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill_list);

        // load the list in.
        ListView list = (ListView)findViewById(R.id.listview_billlist);
        final CCompleteBillListAdapter adapter = new CCompleteBillListAdapter(getApplicationContext(), StaticValues.getCompleteBillList());
        list.setAdapter(adapter);
    }
}
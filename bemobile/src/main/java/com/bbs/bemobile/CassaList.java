package com.bbs.bemobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CassaList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cassa_list);

        // load the list in.
        ListView list = (ListView)findViewById(R.id.listview_cassalist);
        CBillItemListAdapter adapter = new CBillItemListAdapter(getApplicationContext(), StaticValues.getActualBillItems());
        list.setAdapter(adapter);
    }
}

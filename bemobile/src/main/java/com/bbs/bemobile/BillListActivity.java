package com.bbs.bemobile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.ListIterator;

public class BillListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill_list);

        // load the list in.
        ListView list = (ListView)findViewById(R.id.listview_billlist);
        final CCompleteBillListAdapter adapter = new CCompleteBillListAdapter(getApplicationContext(), StaticValues.getCompleteBillList());
        list.setAdapter(adapter);

        // set the total
        setTotalToText();
    }

    protected void setTotalToText() {
        TextView t = (TextView) findViewById(R.id.completebilllist_totalText);
        Long total = (long)0;

        LinkedList<CCompleteBill> list = StaticValues.getCompleteBillList();
        ListIterator<CCompleteBill> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            CCompleteBill b = listIterator.next();
            total += b.getTotal();
        }

        t.setText("TOTAL: "+StaticValues.getConvertedValue(total));
    }
}
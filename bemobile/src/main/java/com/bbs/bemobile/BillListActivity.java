package com.bbs.bemobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.ListIterator;

public class BillListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill_list);

        // load the list from the file
        StaticValues.readBillFile(BillListActivity.this);

        // load the list in.
        ListView list = (ListView)findViewById(R.id.listview_billlist);
        final CCompleteBillListAdapter adapter = new CCompleteBillListAdapter(getApplicationContext(), StaticValues.getCompleteBillList());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(BillListActivity.this);
                CCompleteBill bill=StaticValues.getCompleteBillList().get(position);
                adb.setTitle("Delete #"+(position+1)+"?");
                adb.setMessage(bill.getDate()+": "+StaticValues.getConvertedValue(bill.getTotal()));
                final int positionToRemove = position;
                adb.setNegativeButton("NO", null);
                adb.setPositiveButton("YES", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StaticValues.getCompleteBillList().remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                        setTotalToText();
                        StaticValues.writeBillFile(BillListActivity.this,BillListActivity.this);
                    }});
                adb.show();
            }
        });

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
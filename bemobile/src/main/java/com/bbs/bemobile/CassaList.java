package com.bbs.bemobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CassaList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cassa_list);

        // load the list in.
        ListView list = (ListView)findViewById(R.id.listview_cassalist);
        final CBillItemListAdapter adapter = new CBillItemListAdapter(getApplicationContext(), StaticValues.getActualBillItems());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(CassaList.this);
                CBillItem bill=StaticValues.getActualBillItems().get(position);
                adb.setTitle("Delete #"+(position+1)+"?");
                adb.setMessage(bill.getName()+": "+StaticValues.getConvertedValue(bill.getValue()));
                final int positionToRemove = position;
                adb.setNegativeButton("NO", null);
                adb.setPositiveButton("YES", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StaticValues.getActualBillItems().remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                        setTotalToText();
                    }});
                adb.show();
            }
        });

        setTotalToText();
    }

    // combine all bills and show that value.
    protected void setTotalToText()
    {
        TextView t = (TextView)findViewById(R.id.cassalist_totalText);
        Long total = (long)0;

        List<CBillItem> list = StaticValues.getActualBillItems();
        ListIterator<CBillItem> listIterator = list.listIterator();
        while (listIterator.hasNext())
        {
            CBillItem b = listIterator.next();
            total+=b.getValue();
        }

        t.setText("= "+StaticValues.getConvertedValue(total));
    }
}

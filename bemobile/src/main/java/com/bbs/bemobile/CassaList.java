package com.bbs.bemobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

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
                adb.setMessage(bill.getName()+": "+bill.getConvertedValue());
                final int positionToRemove = position;
                adb.setNegativeButton("NO", null);
                adb.setPositiveButton("YES", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StaticValues.getActualBillItems().remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });
    }
}

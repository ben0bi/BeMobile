package com.bbs.bemobile;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CassaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature( Window.FEATURE_NO_TITLE );

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_cassa);
        updateNumbers();
    }

    // click on a number button.
    public void numberClick(View view)
    {
        String tag = view.getTag().toString();

        // do not delete the total if it is a 0 value.
        if((tag.equals("0") || tag.equals("00")) && (StaticValues.isShowingTotal || StaticValues.getCassaNumber()==0))
        {
            StaticValues.playSound(this,R.raw.btn_no_action_done);
            return;
        }

        StaticValues.playSound(this,R.raw.btn_tick);
        if(StaticValues.isShowingTotal==true)
            StaticValues.setCassaNumberString("");
        StaticValues.isShowingTotal = false;
        StaticValues.addCharsToCassaNumberString(tag);
        updateNumbers();
    }

    // click on the delete button.
    public void deleteClick(View view)
    {
        // do not delete the total :) and do not delete 0 values.
        if(StaticValues.isShowingTotal || StaticValues.getCassaNumber() == 0)
        {
            StaticValues.playSound(this,R.raw.btn_no_action_done);
            return;
        }

        StaticValues.playSound(this, R.raw.btn_tick);
        if(StaticValues.isShowingTotal==true)
            StaticValues.setCassaNumberString("");
        else
            StaticValues.removeLastCharFromCassaNumberString();
        StaticValues.isShowingTotal = false;
        updateNumbers();
    }

    // click on the ok button.
    public void okBtnClick(View view)
    {
        // its the total or its a null entry. Do nothing then.
        if(StaticValues.isShowingTotal == true || StaticValues.getCassaNumber() == 0)
        {
            StaticValues.playSound(this,R.raw.btn_no_action_done);
            return;
        }

        StaticValues.playSound(this, R.raw.cashregister_ok);
        StaticValues.addCassaToTotal(); // add the entry.
        updateNumbers();
    }

    // click on the total button.
    public void totalBtnClick(View view)
    {
        // add the last value to the total if there is one.
        if(StaticValues.isShowingTotal==false && StaticValues.getCassaNumber() != 0)
            StaticValues.addCassaToTotal();

        // do not make total if it is already a total or if the total amount is 0.
        if(StaticValues.isShowingTotal == true || StaticValues.getCassaTotalNumber() == 0)
        {
            StaticValues.playSound(this,R.raw.btn_no_action_done);
            return;
        }

        StaticValues.playSound(this, R.raw.cashregister_total);
        StaticValues.setCassaNumberString(StaticValues.getCassaTotalNumberString());

        StaticValues.createNewCassaBill();
        StaticValues.isShowingTotal = true;
        updateNumbers();

        // write the bill file.
        StaticValues.writeBillFile(CassaActivity.this, this);
    }

    // update the number text views.
    private void updateNumbers()
    {
        // set the text for the actual number.
        TextView t = (TextView) findViewById(R.id.text_actualNumber);

        if(StaticValues.isShowingTotal)
            t.setTextColor(Color.BLUE);
        else
            t.setTextColor(Color.BLACK);

        t.setText(getStrValueOf(StaticValues.getCassaNumberString()));
        // set the text for the subtotal number.
        TextView tt = (TextView) findViewById(R.id.text_totalNumber);
        tt.setText(getStrValueOf(StaticValues.getCassaTotalNumberString())+" +");
    }

    // click on the top number, shows list with all active bills.
    public void showActualBillList(View v)
    {
        // don't show the list when there is nothing to show.
        if(StaticValues.getActualBillItemCount()<=0)
        {
            StaticValues.playSound(this, R.raw.btn_no_action_done);
            return;
        }

        StaticValues.playSound(this, R.raw.btn_tick);
        Intent in = new Intent(this, CassaList.class);
        startActivity(in);
    }

    /* get the string value of another string with a point 0.0x, 0.xx and x.xx prefixing. */
    public String getStrValueOf(String integerText)
    {
        String result = "0.00";
        if(integerText.length()==1)
            result = "0.0"+integerText;
        if(integerText.length()==2)
            result = "0."+integerText;
        if(integerText.length()>2)
        {
            result=integerText.substring(0,integerText.length()-2)+"."+integerText.substring(integerText.length()-2);
        }
        return result;
    }

 /*   Camera m_camera;
    private boolean safeCameraOpen(int id)
    {
        boolean camopened = false;
        try
        {
            releaseCameraAndPreview();
            m_camera = Camera.open(id);
            camopened = (m_camera != null);
        }catch(Exception ex){
            Log.e(getString(R.string.app_name), "failed to open Camera");
            ex.printStackTrace();
        }
        return camopened;
    }

    private void releaseCameraAndPreview() {
        preview.setCamera();
        if(m_camera!=null)
        {
            m_camera.release();
            m_camera = null;
        }
    }
    */
}

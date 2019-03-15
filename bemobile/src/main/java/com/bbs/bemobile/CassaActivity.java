package com.bbs.bemobile;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CassaActivity extends AppCompatActivity {

    private MediaPlayer m_sound = null;
    private int m_actualSoundID = 0;
    private boolean m_isTotal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_sound = MediaPlayer.create(this, R.raw.cashregister_ok);
        m_actualSoundID=R.raw.cashregister_ok;

        requestWindowFeature( Window.FEATURE_NO_TITLE );

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_cassa);
        updateNumbers();
    }

    // click on a number button.
    public void numberClick(View view)
    {
        if(m_isTotal==true)
            StaticValues.setCassaNumberString("");
        m_isTotal = false;
        StaticValues.addCharsToCassaNumberString(view.getTag().toString());
        updateNumbers();
    }

    // click on the delete button.
    public void deleteClick(View view)
    {
        if(m_isTotal==true)
            StaticValues.setCassaNumberString("");
        else
            StaticValues.removeLastCharFromCassaNumberString();
        m_isTotal = false;
        updateNumbers();
    }

    // click on the ok button.
    public void okBtnClick(View view)
    {
        if(m_isTotal == true || StaticValues.getCassaNumber() == 0)
        {
            StaticValues.playSound(this,R.raw.btn_no_action_done);
            return;
        }
        StaticValues.playSound(this, R.raw.cashregister_ok);
        StaticValues.addCassaToTotal();
        updateNumbers();
    }

    // click on the total button.
    public void totalBtnClick(View view)
    {
        if(m_isTotal == true || StaticValues.getCassaTotalNumber() == 0)
        {
            StaticValues.playSound(this,R.raw.btn_no_action_done);
            return;
        }
        StaticValues.playSound(this, R.raw.cashregister_total);
        StaticValues.setCassaNumberString(StaticValues.getCassaTotalNumberString());
        StaticValues.setCassaTotalNumberString("");
        m_isTotal = true;
        updateNumbers();
    }

    // update the number text views.
    private void updateNumbers()
    {
        // set the text for the actual number.
        TextView t = (TextView) findViewById(R.id.text_actualNumber);

        String pre = "";
        if(m_isTotal)
            pre="T: ";

        t.setText(pre+getStrValueOf(StaticValues.getCassaNumberString()));
        // set the text for the subtotal number.
        TextView tt = (TextView) findViewById(R.id.text_totalNumber);
        tt.setText(getStrValueOf(StaticValues.getCassaTotalNumberString())+" +");
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
            result=integerText.substring(0,integerText.length()-2)+"."+integerText.substring(integerText.length()-2,integerText.length());
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

package com.bbs.bemobile;

//import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_main);
    }

    String m_numberText = "";

    public void numberClick(View view)
    {
        m_numberText+=view.getTag();
        updateNumbers();
    }

    public void deleteClick(View view)
    {
        if(m_numberText.length()>1)
            m_numberText=m_numberText.substring(0,m_numberText.length()-1);
        else
            m_numberText="";
        updateNumbers();
    }

    private void updateNumbers()
    {
        TextView t = (TextView) findViewById(R.id.text_actualNumber);
        t.setText(getStrValueOf(m_numberText));
    }

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

package com.bbs.bemobile;

import android.content.Context;
import android.graphics.Camera;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

/*public class CCamPreview extends ViewGroup implements SurfaceHolder.Callback {
    SurfaceView m_surfaceView;
    SurfaceHolder m_holder;
    Camera m_Camera;

    CCamPreview(Context context)
    {
        super(context);
        m_surfaceView = new SurfaceView(context);
        addView(m_surfaceView);

        // add callback
        m_holder = m_surfaceView.getHolder();
        m_holder.addCallback(this);
        //@deprecated m_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera camera)
    {
        if(m_Camera == camera) {return;}
        stopPreviewAndFreeCamera();
        m_Camera = camera;
        if(m_Camera!=null){
            List<Size> localSizes = m_Camera.getParameters().getSupportedPreviewSizes();
            supportedPreviewSizes = localSizes;
            requestLayout();
            try{
                m_Camera.setPreviewDisplay(holder);
            }catch(IOException ex){
                ex.printStackTrace();
            }
            m_Camera.startPreview();
        }
    }
}
*/
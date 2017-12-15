package pl.olszak.michal.opencvplayground

import android.app.Application
import android.util.Log
import org.opencv.android.OpenCVLoader

/**
 * @author molszak
 *         created on 13.12.2017.
 */
class OpenCvPlayground : Application(){

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        init {
            if(!OpenCVLoader.initDebug()){
                Log.d(TAG, "OpenCV didn't load")
            }else{
                System.loadLibrary("detection-tracker")
            }

        }
    }
}
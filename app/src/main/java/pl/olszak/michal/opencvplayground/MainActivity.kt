package pl.olszak.michal.opencvplayground

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    companion object {
        val TAG = MainActivity::class.java.simpleName
        val FACE_RECT_COLOR = Scalar(0.0, 255.0, 0.0, 255.0)
        val RELATIVE_FACE_SIZE = 0.5f
    }

    private var absoluteFaceSize: Int = 0

    private var nativeDetector: DetectionBasedTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 555)
        } else {
            loadCascade()
            camera_view.visibility = CameraBridgeViewBase.VISIBLE
            camera_view.setCvCameraViewListener(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 555) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        camera_view.disableView()
    }

    override fun onDestroy() {
        super.onDestroy()
        camera_view.disableView()
    }

    private fun loadCascade() {
        try {
            val inputStream = resources.openRawResource(R.raw.lbpcascade_frontalface)
            val cascadeDir = getDir("cascade", Context.MODE_PRIVATE)
            val cascadeFile = File(cascadeDir, "lbpcascade_frontalface.xml")
            val outputStream = FileOutputStream(cascadeFile)

            val buffer = ByteArray(4096)
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer)
            }

            inputStream.close()
            outputStream.close()

            nativeDetector = DetectionBasedTracker(cascadeFile.absolutePath, 0)

            cascadeDir.delete()
        } catch (ex: IOException) {
            Log.e(TAG, ex.message, ex)
        }

        camera_view.enableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        nativeDetector?.start()
    }

    override fun onCameraViewStopped() {
        nativeDetector?.stop()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        val rgba = inputFrame.rgba()
        val gray = inputFrame.gray()

        if (absoluteFaceSize == 0) {
            val height = gray.rows()
            val rounded = Math.round(height * RELATIVE_FACE_SIZE)
            if (rounded > 0) {
                absoluteFaceSize = rounded
            }
        }

        val faces = MatOfRect()
        nativeDetector?.detect(gray, faces)

        val facesArray = faces.toArray()
        for (rect in facesArray) {
            Imgproc.rectangle(rgba, rect.tl(), rect.br(), FACE_RECT_COLOR, 3)
        }

        return rgba
    }

}

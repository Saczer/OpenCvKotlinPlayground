package pl.olszak.michal.opencvplayground.scanner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import pl.olszak.michal.opencvplayground.DetectionBasedTracker
import pl.olszak.michal.opencvplayground.R
import pl.olszak.michal.opencvplayground.util.PermissionManager
import javax.inject.Inject


class ScannerActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    companion object {
        val FACE_RECT_COLOR = Scalar(0.0, 255.0, 0.0, 255.0)
        val RELATIVE_FACE_SIZE = 0.5f
    }

    private var absoluteFaceSize: Int = 0
    private var nativeDetector: DetectionBasedTracker? = null

    @Inject
    lateinit var permissionManager : PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        permissionManager.checkCameraPermission(this, {
            loadCascade()
            camera_view.visibility = CameraBridgeViewBase.VISIBLE
            camera_view.setCvCameraViewListener(this)
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionManager.permissionResult(this, requestCode, permissions, grantResults)
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

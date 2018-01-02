package pl.olszak.michal.opencvplayground.scanner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.merge_scanner_view.view.*
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import pl.olszak.michal.opencvplayground.R
import pl.olszak.michal.opencvplayground.detector.EdgeDetector
import pl.olszak.michal.opencvplayground.scanner.view.ScannerView
import pl.olszak.michal.opencvplayground.util.PermissionManager
import javax.inject.Inject


class ScannerActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    @Inject
    lateinit var permissionManager: PermissionManager
    @Inject
    lateinit var edgeDetector: EdgeDetector

    lateinit var scannerView: ScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        scannerView = scanner_view
    }

    override fun onResume() {
        super.onResume()

        permissionManager.checkCameraPermission(this, {
            scannerView.camera_view.visibility = CameraBridgeViewBase.VISIBLE
            scannerView.camera_view.setCvCameraViewListener(this)
            enableCamera()
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionManager.permissionResult(this, requestCode, grantResults)
    }

    override fun onPause() {
        super.onPause()
        scannerView.camera_view.disableView()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.camera_view.disableView()
    }

    private fun enableCamera() {
        scannerView.camera_view.enableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        val rgba = inputFrame.rgba()
        val gray = inputFrame.gray()

        edgeDetector.detect(rgba, gray)

        return rgba
    }

}

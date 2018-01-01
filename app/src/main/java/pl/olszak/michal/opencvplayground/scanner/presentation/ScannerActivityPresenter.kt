package pl.olszak.michal.opencvplayground.scanner.presentation

import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import pl.olszak.michal.opencvplayground.domain.interactor.scanner.ScanFrame
import pl.olszak.michal.opencvplayground.scanner.view.ScannerDisplayer
import javax.inject.Inject

/**
 * Created by molszak.
 * 01.01.2018
 */
class ScannerActivityPresenter @Inject constructor(
        private val scanFrame: ScanFrame) : ScannerPresenter, CameraBridgeViewBase.CvCameraViewListener2 {

    var display: ScannerDisplayer? = null
        set(display) {
            if (display != null) {
                field = display
                display.attach(listener)
            }
        }

    private val listener: ScannerDisplayer.InteractionListener = object : ScannerDisplayer.InteractionListener {

        override fun provideCVCameraViewListener(): CameraBridgeViewBase.CvCameraViewListener2 {
            return this@ScannerActivityPresenter
        }

    }

    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        TODO("did not think it out at all, it's new year after all :) preferences listener could be" +
                "done asynchronously, this is asynchronous operation but should provide synchronous response ")
    }

    override fun dispose() {
        scanFrame.dispose()
    }

}
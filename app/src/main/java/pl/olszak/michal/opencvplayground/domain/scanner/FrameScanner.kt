package pl.olszak.michal.opencvplayground.domain.scanner

import io.reactivex.Single
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat

/**
 * Created by molszak.
 * 31.12.2017
 */
interface FrameScanner {

    fun scan(inputFrame: CameraBridgeViewBase.CvCameraViewFrame) : Single<Mat>

}
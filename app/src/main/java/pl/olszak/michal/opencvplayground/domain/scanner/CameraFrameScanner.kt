package pl.olszak.michal.opencvplayground.domain.scanner

import io.reactivex.Single
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import javax.inject.Inject

/**
 * Created by molszak.
 * 31.12.2017
 */
class CameraFrameScanner @Inject constructor() : FrameScanner {

    override fun scan(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Single<Mat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
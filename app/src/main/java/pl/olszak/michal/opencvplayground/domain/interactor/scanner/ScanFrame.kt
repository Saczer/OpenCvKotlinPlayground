package pl.olszak.michal.opencvplayground.domain.interactor.scanner

import io.reactivex.Single
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import pl.olszak.michal.opencvplayground.concurrent.SchedulersFacade
import pl.olszak.michal.opencvplayground.domain.interactor.SingleUseCase
import pl.olszak.michal.opencvplayground.domain.scanner.FrameScanner
import javax.inject.Inject

/**
 * Created by molszak.
 * 01.01.2018
 */
class ScanFrame @Inject constructor(
        schedulersFacade: SchedulersFacade,
        private val frameScanner: FrameScanner) : SingleUseCase<Mat, CameraBridgeViewBase.CvCameraViewFrame>(schedulersFacade) {

    override fun buildObservable(params: CameraBridgeViewBase.CvCameraViewFrame?): Single<Mat> {
        if (params != null) {
            return frameScanner.scan(params)
        }
        return Single.just(null)
    }

}
package pl.olszak.michal.opencvplayground.detector

import org.opencv.core.Mat
import javax.inject.Inject

/**
 * @author molszak
 *         created on 02.01.2018.
 */
class EdgeDetector @Inject constructor() {

    private var nativeDetector = createObject()

    fun detect(imageRgb: Mat, imageGray: Mat) {
        nativeDetect(nativeDetector, imageRgb.nativeObjAddr, imageGray.nativeObjAddr)
    }

    fun release(){
        nativeDestroyObject(nativeDetector)
    }

    private external fun createObject(): Long
    private external fun nativeDetect(thiz: Long, imageRgb: Long, imageGray: Long)
    private external fun nativeDestroyObject(thiz: Long)
}
package pl.olszak.michal.opencvplayground

import org.opencv.core.Mat
import org.opencv.core.MatOfRect

/**
 * @author molszak
 *         created on 13.12.2017.
 */
class DetectionBasedTracker constructor(cascade: String, minFaceSize: Int) {

    private var nativeObj = nativeCreateObject(cascade, minFaceSize)

    fun start() {
        nativeStart(nativeObj)
    }

    fun stop() {
        nativeStop(nativeObj)
    }

    fun setMinFaceSize(size: Int) {
        nativeSetFaceSize(nativeObj, size)
    }

    fun detect(imageGray: Mat, faces: MatOfRect){
        nativeDetect(nativeObj, imageGray.nativeObjAddr, faces.nativeObjAddr)
    }

    fun release(){
        nativeDestroyObject(nativeObj)
        nativeObj = 0
    }

    private external fun nativeCreateObject(cascade: String, minFaceSize: Int): Long
    private external fun nativeDestroyObject(thiz: Long)
    private external fun nativeStart(thiz: Long)
    private external fun nativeStop(thiz: Long)
    private external fun nativeSetFaceSize(thiz: Long, size: Int)
    private external fun nativeDetect(thiz: Long, inputImage: Long, faces: Long)
}
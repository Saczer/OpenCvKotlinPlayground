package pl.olszak.michal.opencvplayground.scanner.view

import org.opencv.android.CameraBridgeViewBase

/**
 * Created by molszak.
 * 01.01.2018
 */
interface ScannerDisplayer {

    fun attach(listener: InteractionListener)

    fun detach(listener: InteractionListener)

    interface InteractionListener {

        fun provideCVCameraViewListener() : CameraBridgeViewBase.CvCameraViewListener2

    }

}
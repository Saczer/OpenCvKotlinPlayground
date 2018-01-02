package pl.olszak.michal.opencvplayground.scanner.presenter

import pl.olszak.michal.opencvplayground.scanner.view.ScannerDisplayer
import javax.inject.Inject

/**
 * @author molszak
 *         created on 02.01.2018.
 */
class ScannerPresenterImpl @Inject constructor() : ScannerPresenter {

    var display: ScannerDisplayer? = null
        set(display) {
            if (display != null) {
                field = display
                display.attach(listener)
            }
        }

    private val listener: ScannerDisplayer.InteractionListener = object : ScannerDisplayer.InteractionListener {

    }

    override fun dispose() {

    }
}
package pl.olszak.michal.opencvplayground.scanner.view

/**
 * @author molszak
 *         created on 02.01.2018.
 */
interface ScannerDisplayer {

    fun attach(listener: InteractionListener)

    fun detach(listener: InteractionListener)

    interface InteractionListener {

    }

}
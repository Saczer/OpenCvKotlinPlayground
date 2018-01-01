package pl.olszak.michal.opencvplayground.scanner.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.merge_scanner_view.view.*
import pl.olszak.michal.opencvplayground.R

/**
 * Created by molszak.
 * 01.01.2018
 */
class ScannerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ScannerDisplayer {

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.merge_scanner_view, this)
    }

    override fun attach(listener: ScannerDisplayer.InteractionListener) {
        camera_view.setCvCameraViewListener(listener.provideCVCameraViewListener())
    }

    override fun detach(listener: ScannerDisplayer.InteractionListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
package pl.olszak.michal.opencvplayground.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import javax.inject.Inject

/**
 * Created by molszak.
 * 31.12.2017
 */
class PermissionManager @Inject constructor() {

    fun <T> checkCameraPermission(activity: Activity, body: () -> T) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE)
        } else {
            body()
        }
    }

    fun permissionResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_CODE ->
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        activity.finish()
                    }
                }
        }
    }


    companion object {
        val CAMERA_PERMISSION_CODE = 555
    }

}
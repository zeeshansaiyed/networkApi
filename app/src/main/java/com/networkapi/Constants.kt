package com.networkapi

import java.util.*

/**
 * Created by zeeshan on 28/10/17.
 */
object Constants {

    val locale = Locale.getDefault().language
    const val platform = "Android"
    val osVersion = android.os.Build.VERSION.SDK_INT.toString()
    val device = android.os.Build.MODEL
    val appVersion = BuildConfig.VERSION_NAME


    val headers = hashMapOf<String, String>(
        Keys.X_LOCALIZATION to locale,
        Keys.X_PLATFORM to platform,
        Keys.X_OSVERSION to osVersion,
        Keys.X_DEVICE to device,
        Keys.X_APP_VERSION to appVersion
    )
}
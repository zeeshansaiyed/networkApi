package com.networkapi

object Keys {
    //Status codes
    const val NETWORK_UNREACHABLE = "4001"
    const val UNAUTHORIZED = "401"
    const val SUCCESS = "200"
    const val FAILIURE = "400"
    const val NOT_FOUND = "404"

    //Headers
    const val X_LOCALIZATION = "X-localization"
    const val X_PLATFORM = "X-platform"
    const val X_OSVERSION = "X-OSVersion"
    const val X_DEVICE = "X-device"
    const val X_APP_VERSION = "X-appVersion"
    const val X_AUTHORIZATION = "token"
    const val X_TYPE = "X-type"

    //Response
    const val KEY_STATUS = "code"
    const val KEY_MESSAGE = "message"
    const val KEY_DATA = "data"
}
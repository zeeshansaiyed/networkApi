package com.networkapi

import com.google.gson.annotations.SerializedName

/**
 * Created by zeeshan on 20/9/17.
 */
open class ResponseStatus {

    @SerializedName(BuildConfig.status)
    var status: String = ""

    @SerializedName(BuildConfig.message)
    var message: String = ""
}
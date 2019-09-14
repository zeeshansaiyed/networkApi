package com.networkapi

import android.view.View

/**
 * This extension function returns the Retrofit client object
 * of given API interface
 * @receiver Class<T>
 * @return T
 */
inline fun <reified T : Any> Class<T>.api(): T = NetworkApi.getClient().create(T::class.java)

fun View.setVisible(flag: Boolean, isInvisible: Boolean = false) {
    visibility = if (flag) View.VISIBLE else if (isInvisible) View.INVISIBLE else View.GONE
}


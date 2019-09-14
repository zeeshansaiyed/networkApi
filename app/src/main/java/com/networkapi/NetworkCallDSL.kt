package com.networkapi

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CallHandler<DATA : Any> {
    lateinit var client: Deferred<Response<DATA>>
    var name: String? = null


    @Suppress("UNCHECKED_CAST")
    fun makeCall(): MutableLiveData<Resource<DATA>> {
        val result = MutableLiveData<Resource<DATA>>()
        result.setValue(Resource.loading(null))

        GlobalScope.launch {
            try {
                val response = client.awaitResult().getOrThrow() as DATA

                withContext(Dispatchers.Main) {
                    result.value = Resource.success(response)
                }
            } catch (e: Throwable) {
                println("exception" + e)
                withContext(Dispatchers.Main) {
                    when (e) {
                        is UnknownHostException -> result.value = Resource.error(ResponseStatus().apply {
                            status = Keys.NETWORK_UNREACHABLE
                            message = "No Internet Connection!"
                        })

                        is ConnectException -> result.value = Resource.error(ResponseStatus().apply {
                            status = Keys.NETWORK_UNREACHABLE
                            message = "No Internet Connection!"
                        })
                        is SocketTimeoutException -> result.value = Resource.error(ResponseStatus().apply {
                            status = Keys.FAILIURE
                            message = "Server is Unreachable!"

                        })
                        is HttpException -> {
                            try {
                                val statusMessage = e.response().errorBody()!!.string()
                                val mJsonObject = JSONObject(statusMessage)
                                val statusCode = e.response().code()
                                val msg =
                                    if (!TextUtils.isEmpty(mJsonObject.optString(BuildConfig.message))) mJsonObject.optString(
                                        BuildConfig.message
                                    ) else "Error!"
                                val res = ResponseStatus().apply {
                                    status = statusCode.toString()
                                    message = msg

                                }
                                result.value = Resource.error(res)
                            }catch (e:Exception){
                                result.value = Resource.error(ResponseStatus().apply {
                                    status = Keys.FAILIURE
                                    message = "Server error"
                                })
                            }
                        }
                        else -> result.value = Resource.error(ResponseStatus().apply {
                            status = Keys.FAILIURE
                            message = "There is some problem in connecting server, Please try after sometime."
                        })
                    }
                }
                e.printStackTrace()
            }
        }

        return result
    }
}

fun <DATA : Any> networkCall(block: CallHandler<DATA>.() -> Unit):
        MutableLiveData<Resource<DATA>> =
    CallHandler<DATA>().apply(block).makeCall()

interface DataResponse<T> {
    fun retrieveData(): T
}

package com.maggnet.domain.usecase


import android.content.ContentValues.TAG
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.*
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.ui.extensions.empty
import com.maggnet.ui.extensions.safeGet
import com.maggnet.utils.AppLogger
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class OptimizedRequestBodyUseCase<T : ResponseBody, Params>(private val postExecutionThread: PostExecutionThread) {

    companion object {
        private const val SOMETHING_WENT_WRONG = "Something went wrong , please try again later"
        private const val RESPONSE_ERROR = "RESPONSE_ERROR"
        private const val NETWORK_ERROR = "NETWORK_ERROR"
    }

    private val threadScheduler: Scheduler

    private val useCaseListener: UseCaseListener? = null

    init {
        threadScheduler = Schedulers.io()
    }

    abstract fun buildUseCase(params: Params?): Single<T>

    fun execute(
        callbackWrapper: OptimizedCallbackWrapper<T>?,
        params: Params? = null
    ): Disposable? {

        if (callbackWrapper == null) {
            return null
        }

        val single = buildUseCase(params)
            .subscribeOn(threadScheduler)
            .observeOn(postExecutionThread.scheduler())

        useCaseListener?.onPreExecute()

        return single.subscribe({ result ->

            useCaseListener?.onPostExecute()

            callbackWrapper.onApiSuccess(result)

        }, { exception ->

            useCaseListener?.onPostExecute()
            val baseError: BaseError
            when (exception) {
                is HttpException -> {
                    exception.response().errorBody().run {
                        val error = this?.string().safeGet()
                        AppLogger.e(
                            TAG,
                            "Retrofit exception $error with error code ${exception.code()}"
                        )

                        when (exception.response().code()) {
                            // 429 - Too many request code
                            429 -> handleResponseError(
                                error,
                                ResponseErrors.HTTP_TOO_MANY_REQUEST,
                                callbackWrapper
                            )
                            500 -> handleResponseError(
                                error,
                                ResponseErrors.INTERNAL_SERVER_ERROR,
                                callbackWrapper
                            )
                            else -> handleResponseError(
                                error = error,
                                callbackWrapper = callbackWrapper
                            )
                        }
                    }
                }

                is ServerNotAvailableException -> {
                    baseError = BaseError(
                        errorMessage = "Server not available",
                        errorCode = ResponseErrors.HTTP_UNAVAILABLE
                    )
                    callbackWrapper.onApiError(baseError)
                }

                is HTTPNotFoundException -> {
                    baseError = BaseError(
                        errorMessage = "Server not available",
                        errorCode = ResponseErrors.HTTP_NOT_FOUND
                    )
                    callbackWrapper.onApiError(baseError)
                }

                is UnknownHostException,
                is NetworkException,
                is ConnectException -> {
                    callbackWrapper.onApiError(
                        BaseError(
                            errorMessage = "Internet not available",
                            errorCode = ResponseErrors.CONNECTIVITY_EXCEPTION
                        )
                    )
                }

                is IOException,
                is TimeoutException -> {
                    baseError = if (exception.localizedMessage != null) {
                        BaseError(
                            errorMessage = exception.localizedMessage!!,
                            errorCode = ResponseErrors.UNKNOWN_EXCEPTION
                        )
                    } else {
                        BaseError(
                            errorMessage = SOMETHING_WENT_WRONG,
                            errorCode = ResponseErrors.UNKNOWN_EXCEPTION
                        )
                    }
                    callbackWrapper.onApiError(baseError)
                }

                is HTTPBadRequest -> {
                    callbackWrapper.onApiError(
                        BaseError(
                            errorMessage = SOMETHING_WENT_WRONG,
                            errorCode = ResponseErrors.HTTP_BAD_REQUEST
                        )
                    )
                }

                is JsonParseException -> {
                    callbackWrapper.onApiError(
                        BaseError(
                            errorMessage = SOMETHING_WENT_WRONG,
                            errorCode = ResponseErrors.INTERNAL_SERVER_ERROR
                        )
                    )
                }
            }
        })

    }

    private fun getErrorMessageFromResponse(error: String): String {
        var errorMessage: String = String.empty
        try {
            val baseError = Gson().fromJson(
                error,
                BaseError::class.java
            )

            if (baseError != null) {
                baseError.apply {
                    errorMessage = if (this.message.isNotBlank()) {
                        this.message
                    } else {
                        this.errorMessage
                    }
                }
            } else {
                errorMessage = SOMETHING_WENT_WRONG
            }

        } catch (e: Exception) {
            errorMessage = SOMETHING_WENT_WRONG
        } finally {
            return errorMessage
        }
    }

    private fun handleResponseError(
        error: String,
        errorCode: ResponseErrors = ResponseErrors.RESPONSE_ERROR,
        callbackWrapper: OptimizedCallbackWrapper<T>?
    ) {
        callbackWrapper?.onApiError(
            error = BaseError(
                errorBody = error,
                errorMessage = getErrorMessageFromResponse(error),
                errorCode = errorCode
            )
        )
    }
}
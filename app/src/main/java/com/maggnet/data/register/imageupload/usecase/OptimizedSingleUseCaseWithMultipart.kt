package com.maggnet.data.register.imageupload.usecase


import android.content.ContentValues.TAG
import com.google.gson.Gson
import com.maggnet.data.register.imageupload.model.UploadImageRequest
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.remote.*
import com.maggnet.domain.rxcallback.OptimizedCallbackWrapper
import com.maggnet.domain.usecase.UseCaseListener
import com.maggnet.ui.extensions.empty
import com.maggnet.ui.extensions.safeGet
import com.maggnet.utils.AppLogger
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeoutException

abstract class OptimizedSingleUseCaseWithMultipart<T : BaseResponse, Params>(private val postExecutionThread: PostExecutionThread) {

    companion object {
        private const val SOMETHING_WENT_WRONG = "Something went wrong , please try again later"
    }

    private val threadScheduler: Scheduler = Schedulers.io()

    private val useCaseListener: UseCaseListener? = null

    abstract fun buildUseCase(imageRequest: UploadImageRequest): Single<T>

    fun execute(
        callbackWrapper: OptimizedCallbackWrapper<T>?,
        imageRequest: UploadImageRequest
    ): Disposable? {

        if (callbackWrapper == null) {
            return null
        }

        val single = buildUseCase(imageRequest)
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
                            429 -> callbackWrapper.onApiError(
                                BaseError(
                                    errorMessage = getErrorMessageFromResponse(error),
                                    errorCode = ResponseErrors.HTTP_TOO_MANY_REQUEST,
                                    errorBody = error
                                )
                            )
                            else -> handleResponseError(error, callbackWrapper)
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

    private fun handleResponseError(error: String, callbackWrapper: OptimizedCallbackWrapper<T>?) {
        callbackWrapper?.onApiError(
            error = BaseError(
                errorMessage = getErrorMessageFromResponse(error),
                errorCode = ResponseErrors.RESPONSE_ERROR
            )
        )
    }
}
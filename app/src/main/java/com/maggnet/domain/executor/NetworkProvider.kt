package com.maggnet.domain.executor


import com.google.gson.Gson
import com.maggnet.BuildConfig
import com.maggnet.di.qualifier.NetworkInfo
import com.maggnet.domain.SessionManager
import com.maggnet.domain.remote.AuthorizationException
import com.maggnet.domain.remote.HTTPNotFoundException
import com.maggnet.domain.remote.NetworkException
import com.maggnet.domain.remote.ServerNotAvailableException
import com.maggnet.ui.extensions.safeGet
import com.maggnet.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection.*
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkProvider @Inject constructor(gson: Gson, @NetworkInfo private var endPoint: String) {

    //region VARIABLES
    private var retrofit: Retrofit

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @Inject
    lateinit var sessionManager: SessionManager

    //endregion

    // region COMPANION OBJECT
    companion object {
        private const val HEADER_KEY_CONTENT_TYPE = "Content-Type"
        private const val HEADER_KEY_CONTENT_TYPE_VALUE = "application/json"
        private const val HEADER_KEY_CONTENT_AUTHORIZATION = "Authorization"

        private const val CONNECT_TIME_OUT = 90L
        private const val READ_TIME_OUT = 90L

    }
    //endregion

    //region INIT + RETROFIT BUILDER

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(endPoint)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //endregion

    //region OK_HTTP
    private fun getOkHttpClient(): OkHttpClient {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpBuilder.addNetworkInterceptor(networkInterceptor())
            .addInterceptor(responseHandlerInterceptor())
            .addInterceptor(headerInterceptor())
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)

        return okHttpBuilder.build()
    }

    private fun networkInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return loggingInterceptor
    }


    private fun headerInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request()
                .newBuilder()
                .addHeader(HEADER_KEY_CONTENT_TYPE, HEADER_KEY_CONTENT_TYPE_VALUE)
            preferenceManager.getToken()?.let { token ->
                requestBuilder.addHeader(HEADER_KEY_CONTENT_AUTHORIZATION, "Bearer $token")
            }
            it.proceed(requestBuilder.build())
        }
    }

    private fun responseHandlerInterceptor(): Interceptor {
        return Interceptor {
            var response: Response? = null

            try {

                response = it.proceed(it.request()) as Response

                when (response.code) {
                    HTTP_UNAVAILABLE -> {
                        throw ServerNotAvailableException("Server not available , please try again later")
                    }

                    HTTP_UNAUTHORIZED -> {
                        sessionManager.appTokenExpired()
                        throw AuthorizationException(response.body?.string().safeGet())
                    }

                    HTTP_FORBIDDEN -> {
                        throw Exception("Forbidden")
                    }

                    HTTP_NOT_FOUND -> {
                        throw HTTPNotFoundException("Http not found")
                    }
                }
                response
            } catch (e: Exception) {
                response?.let { responseEnclosed ->
                    try {
                        responseEnclosed.close()
                    } catch (e: Exception) {
                        // don't crash
                    }
                }
                e.printStackTrace()
                when (e) {
                    is UnknownHostException -> throw NetworkException(e)
                    is HttpException,
                    is EOFException,
                    is HTTPNotFoundException,
                    is ConnectException,
                    is IOException -> throw e
                    else -> throw NetworkException(e)
                }
            }
        }
    }
    //endregion
}
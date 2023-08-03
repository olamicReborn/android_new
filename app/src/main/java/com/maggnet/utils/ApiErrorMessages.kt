package com.maggnet.utils

import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.maggnet.domain.remote.BaseError
import com.maggnet.domain.remote.ResponseErrors

object ApiErrorMessages {

    fun getErrorMessage(error: BaseError): String {
        return when (error.errorCode) {
            ResponseErrors.RESPONSE_ERROR -> error.errorMessage
            ResponseErrors.CONNECTIVITY_EXCEPTION -> MaggnetApplication.instance.getString(R.string.error_internet_not_available)
            ResponseErrors.HTTP_NOT_FOUND,
            ResponseErrors.HTTP_UNAVAILABLE,
            -> MaggnetApplication.instance.getString(R.string.error_cannot_reach_server)
            ResponseErrors.HTTP_BAD_REQUEST -> "Bad request"
            ResponseErrors.UNKNOWN_EXCEPTION -> ""
            else -> MaggnetApplication.instance.getString(R.string.error_something_went_wrong)
        }
    }
}
package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.model.VerifyDeviceRequest
import com.maggnet.data.redeem.model.VerifyDeviceResponse
import io.reactivex.Single

interface VerifyDeviceRepository {

    fun verifyDevice(verifyDeviceRequest: VerifyDeviceRequest): Single<VerifyDeviceResponse>

}
package com.maggnet.data.redeem.remote

import com.maggnet.data.redeem.model.VerifyDeviceRequest
import com.maggnet.data.redeem.model.VerifyDeviceResponse
import com.maggnet.data.redeem.service.RedeemApiService
import io.reactivex.Single
import javax.inject.Inject

class VerifyDeviceDataRepository @Inject constructor(
    private val redeemApiService: RedeemApiService
) : VerifyDeviceRepository {
    override fun verifyDevice(verifyDeviceRequest: VerifyDeviceRequest): Single<VerifyDeviceResponse> {
        return redeemApiService.verifyDevice(verifyDeviceRequest)
    }
}
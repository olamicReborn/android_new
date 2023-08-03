package com.maggnet.data.coupons.usecase

import com.maggnet.data.coupons.model.CouponsListRequest
import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.data.coupons.remote.CouponsRepository
import com.maggnet.data.register.login.model.LoginUserRequest
import com.maggnet.data.register.login.model.LoginUserResponse
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.data.register.login.usecase.LoginUseCase
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.domain.usecase.OptimizedRequestBodyUseCase
import com.maggnet.domain.usecase.OptimizedSingleUseCase
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class CouponUseCase @Inject constructor(
    private val couponsRepository: CouponsRepository,
    private val postExecutionThread: PostExecutionThread
) : OptimizedSingleUseCase<CouponsListResponse, CouponUseCase.Params>(postExecutionThread) {


    override fun buildUseCase(params: Params?): Single<CouponsListResponse> {
        return couponsRepository.getCouponsList(params!!.couponsListRequest)
    }

    data class Params constructor(
        val couponsListRequest: CouponsListRequest
    ) {
        companion object {
            fun create(couponsListRequest: CouponsListRequest) =
                Params(
                    couponsListRequest
                )
        }
    }
}
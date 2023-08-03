package com.maggnet.data.settings.service

import com.maggnet.data.register.otp.model.RegisterUserResponse
import com.maggnet.data.settings.model.*
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import retrofit2.http.*

interface SettingsApiServices {

    @POST("api/query/raise")
    fun queryRaise(@Body helpSettingsRequest: HelpSettingsRequest): Single<HelpSettingResponse>

    @GET("api/query/topic/get-all")
    fun getIssuesList(): Single<HelpIssuesListResponse>

    @POST("api/user/invite")
    fun inviteFriend(@Body inviteSettingsRequest : InviteSettingsRequest): Single<InviteSettingResponse>

    @POST("api/user/change-password")
    fun changePassword(@Body changePasswordRequest : ChangePasswordRequest): Single<BaseResponse>

    @PUT("api/user/{userId}")
    fun updateProfile( @Path("userId") userId: String, @Body updateProfileRequest: UserProfileRequest): Single<RegisterUserResponse>

    @POST("api/user/get/rewards")
    fun getRewardsHistory(@Body rewardsHistoryRequest : RewardsHistoryRequest): Single<RewardsHistoryResponse>

    @POST("api/user/transfer-reward")
    fun transferRewards(@Body transferRewardsRequest: TransferRewardsRequest): Single<TransferRewardsResponse>

    @POST("api/user/switch-notifications")
    fun switchNotificationVisibility(@Body switchNotificationRequest: SwitchNotificationRequest): Single<BaseResponse>

}
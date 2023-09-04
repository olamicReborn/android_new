package com.maggnet.di

import com.maggnet.data.coupons.remote.*
import com.maggnet.data.languagechange.remote.AppLanguageDataRepository
import com.maggnet.data.languagechange.remote.AppLanguageRepository
import com.maggnet.data.notifications.remote.NotificationListDataRepository
import com.maggnet.data.notifications.remote.NotificationListRepository
import com.maggnet.data.qrcode.remote.QRCodeByImeiRepository
import com.maggnet.data.qrcode.remote.QRCodeDataByImeiRepository
import com.maggnet.data.qrcode.remote.QRCodeDataRepository
import com.maggnet.data.qrcode.remote.QRCodeRepository
import com.maggnet.data.redeem.remote.CouponValidityDataRepository
import com.maggnet.data.redeem.remote.CouponValidityRepository
import com.maggnet.data.redeem.remote.RedeemDataRepository
import com.maggnet.data.redeem.remote.RedeemRepository
import com.maggnet.data.redeem.remote.RewardDataRepository
import com.maggnet.data.redeem.remote.RewardRepository
import com.maggnet.data.redeem.remote.VerifyDeviceDataRepository
import com.maggnet.data.redeem.remote.VerifyDeviceRepository
import com.maggnet.data.register.fcm.remote.GeneralDataRepository
import com.maggnet.data.register.fcm.remote.GeneralRepository
import com.maggnet.data.register.imageupload.remote.ImageDataRepository
import com.maggnet.data.register.imageupload.remote.ImageRepository
import com.maggnet.data.register.login.remote.ForgotPasswordDataRepository
import com.maggnet.data.register.login.remote.ForgotPasswordRepository
import com.maggnet.data.register.login.remote.LoginUserDataRepository
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.data.register.login.remote.SendOtpDataRepository
import com.maggnet.data.register.login.remote.SendOtpRepository
import com.maggnet.data.register.login.remote.VerifyOtpDataRepository
import com.maggnet.data.register.login.remote.VerifyOtpRepository
import com.maggnet.data.register.otp.remote.CheckRegisteredUserDataRepository
import com.maggnet.data.register.otp.remote.CheckRegisteredUserRepository
import com.maggnet.data.register.otp.remote.DeleteUserDataRepository
import com.maggnet.data.register.otp.remote.DeleteUserRepository
import com.maggnet.data.register.otp.remote.RegisterUserDataRepository
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.data.settings.remote.*
import com.maggnet.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @Binds
    abstract fun bindCheckRegisteredUserRepository(checkRegisteredUserDataRepository: CheckRegisteredUserDataRepository): CheckRegisteredUserRepository

    @Binds
    abstract fun bindCheckDeleteUserRepository(deleteUserDataRepository: DeleteUserDataRepository): DeleteUserRepository

    @Binds
    abstract fun bindRegisterUserRepository(registeredUserDataRepository: RegisterUserDataRepository): RegisterUserRepository

    @Binds
    abstract fun bindLoginUserRepository(loginUserDataRepository: LoginUserDataRepository): LoginUserRepository

    @Binds
    abstract fun bindForgotPasswordRepository(forgotPasswordDataRepository: ForgotPasswordDataRepository): ForgotPasswordRepository


    @Binds
    abstract fun bindSendOtpRepository(sendOtpRepository: SendOtpDataRepository): SendOtpRepository
    @Binds
    abstract fun bindVerifyOtpRepository(verifyOtpDataRepository: VerifyOtpDataRepository): VerifyOtpRepository

    @Binds
    abstract fun bindSaveFCMTokenRepository(generalDataRepository: GeneralDataRepository): GeneralRepository

    @Binds
    abstract fun bindGetCouponsListRepository(couponsDataRepository: CouponsDataRepository): CouponsRepository

    @Binds
    abstract fun bindGetHelpIssuesListRepository(helpSettingRepository: HelpSettingDataRepository): HelpSettingRepository

    @Binds
    abstract fun bindInviteFriendRepository(inviteSettingDataRepository: InviteSettingDataRepository): InviteSettingRepository

    @Binds
    abstract fun bindAddToCartRepository(addToCartDataRepository: AddToCartDataRepository): AddToCartRepository

    @Binds
    abstract fun bindAddToWishListRepository(addToWishListDataRepository: AddToWishListDataRepository): AddToWishListRepository

    @Binds
    abstract fun bindGetWishListRepository(getWishListItemsDataRepository: GetWishListItemsDataRepository): GetWishListItemsRepository

    @Binds
    abstract fun bindChangePasswordRepository(changePasswordDataRepository: ChangePasswordDataRepository): ChangePasswordRepository

    @Binds
    abstract fun bindShopDetailDataRepository(shopDetailDataRepository: ShopDetailDataRepository): ShopDetailRepository

    @Binds
    abstract fun bindQRCodeDataRepository(qrCodeDataRepository: QRCodeDataRepository): QRCodeRepository
    @Binds
    abstract fun bindgetQRCodeDataRepository(qrCodeDataByImeiRepository: QRCodeDataByImeiRepository): QRCodeByImeiRepository

    @Binds
    abstract fun bindImageUploadDataRepository(imageDataRepository: ImageDataRepository): ImageRepository

    @Binds
    abstract fun bindUpdateProfileDataRepository(userProfileSettingDataRepository: UserProfileSettingDataRepository): UserProfileSettingRepository

    @Binds
    abstract fun bindRecentListDataRepository(getRecentsListDataRepository: GetRecentsListDataRepository): GetRecentsListRepository

    @Binds
    abstract fun bindRewardsPointsDataRepository(rewardsPointsDataRepository: RewardsPointsDataRepository): RewardsPointsRepository

    @Binds
    abstract fun bindTransferRewardsDataRepository(transferRewardsDataRepository: TransferRewardsDataRepository): TransferRewardsRepository

    @Binds
    abstract fun bindRewardsHistoryDataRepository(rewardsHistoryDataRepository: RewardsHistoryDataRepository): RewardsHistoryRepository

    @Binds
    abstract fun bindNotificationListDataRepository(notificationListDataRepository: NotificationListDataRepository): NotificationListRepository

    @Binds
    abstract fun bindViewAllCategoryDataRepository(viewAllDataRepository: ViewAllDataRepository): ViewAllRepository

    @Binds
    abstract fun bindSwitchNotificationDataRepository(switchNotificationDataRepository: SwitchNotificationDataRepository): SwitchNotificationRepository

    @Binds
    abstract fun bindShopsListDataRepository(shopsListDataRepository: ShopsListDataRepository): ShopsListRepository

    @Binds
    abstract fun bindAppLanguageChangeDataRepository(appLanguageRepository: AppLanguageDataRepository): AppLanguageRepository


    @Binds
    abstract fun bindVerifyDeviceDataRepository(verifyDeviceDataRepository: VerifyDeviceDataRepository): VerifyDeviceRepository

    @Binds
    abstract fun bindRewardCouponDataRepository(redeemDataRepository: RewardDataRepository): RewardRepository

    @Binds
    abstract fun bindRedeemCouponDataRepository(redeemDataRepository: RedeemDataRepository): RedeemRepository

    @Binds
    abstract fun bindCouponValidityDataRepository(couponValidityDataRepository: CouponValidityDataRepository): CouponValidityRepository



}
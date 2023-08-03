package com.maggnet.di

import com.maggnet.data.coupons.remote.*
import com.maggnet.data.coupons.usecase.*
import com.maggnet.data.languagechange.remote.AppLanguageRepository
import com.maggnet.data.languagechange.usecase.AppLanguageChangeUseCase
import com.maggnet.data.notifications.remote.NotificationListRepository
import com.maggnet.data.notifications.usecase.DeleteNotificationUseCase
import com.maggnet.data.notifications.usecase.NotificationUseCase
import com.maggnet.data.qrcode.remote.QRCodeRepository
import com.maggnet.data.qrcode.usecase.ResetQRCodeUseCase
import com.maggnet.data.redeem.remote.CouponValidityRepository
import com.maggnet.data.redeem.remote.RedeemRepository
import com.maggnet.data.redeem.remote.RewardRepository
import com.maggnet.data.redeem.remote.VerifyDeviceRepository
import com.maggnet.data.redeem.usecase.CouponValidityUseCase
import com.maggnet.data.redeem.usecase.RedeemCouponUseCase
import com.maggnet.data.redeem.usecase.RewardCouponUseCase
import com.maggnet.data.redeem.usecase.VerifyDeviceUseCase
import com.maggnet.data.register.fcm.remote.GeneralRepository
import com.maggnet.data.register.fcm.usecase.FCMTokenUseCase
import com.maggnet.data.register.imageupload.remote.ImageRepository
import com.maggnet.data.register.imageupload.usecase.OptimizeImageUseCase
import com.maggnet.data.register.login.remote.ForgotPasswordRepository
import com.maggnet.data.register.login.remote.LoginUserRepository
import com.maggnet.data.register.login.remote.SendOtpRepository
import com.maggnet.data.register.login.remote.VerifyOtpRepository
import com.maggnet.data.register.login.usecase.ForgotPasswordUseCase
import com.maggnet.data.register.login.usecase.LoginUseCase
import com.maggnet.data.register.login.usecase.SendOtpUseCase
import com.maggnet.data.register.login.usecase.VerifyOtpUseCase
import com.maggnet.data.register.otp.remote.CheckRegisteredUserRepository
import com.maggnet.data.register.otp.remote.RegisterUserRepository
import com.maggnet.data.register.otp.usecase.CheckRegisteredUserUseCase
import com.maggnet.data.register.otp.usecase.RegisterUserUseCase
import com.maggnet.data.settings.remote.*
import com.maggnet.data.settings.usecase.*
import com.maggnet.domain.executor.PostExecutionThread
import com.maggnet.ui.welcome.activity.coupon.CouponListUseCase
import com.maggnet.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCheckRegisteredUseCase(
        checkRegisteredUserRepository: CheckRegisteredUserRepository,
        postExecutionThread: PostExecutionThread
    ) = CheckRegisteredUserUseCase(
        checkRegisteredUserRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(
        registerUserRepository: RegisterUserRepository,
        postExecutionThread: PostExecutionThread
    ) = RegisterUserUseCase(registerUserRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideLoginUserUseCase(
        loginUserRepository: LoginUserRepository,
        postExecutionThread: PostExecutionThread
    ) = LoginUseCase(loginUserRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideForgotPasswordUseCase(
        forgotPasswordRepository: ForgotPasswordRepository,
        postExecutionThread: PostExecutionThread
    ) = ForgotPasswordUseCase(forgotPasswordRepository, postExecutionThread = postExecutionThread)

  @Provides
    @Singleton
    fun provideSendOtpUseCase(
        sendOtpRepository: SendOtpRepository,
        postExecutionThread: PostExecutionThread
    ) = SendOtpUseCase(sendOtpRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideVerifyOtpUseCase(
        verifyOtpRepository: VerifyOtpRepository,
        postExecutionThread: PostExecutionThread
    ) = VerifyOtpUseCase(verifyOtpRepository, postExecutionThread = postExecutionThread)


    @Provides
    @Singleton
    fun provideFCMTokenUseCase(
        generalRepository: GeneralRepository,
        postExecutionThread: PostExecutionThread
    ) = FCMTokenUseCase(generalRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideCouponUseCase(
        couponsRepository: CouponsRepository,
        postExecutionThread: PostExecutionThread
    ) = CouponUseCase(couponsRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideBannerUseCase(
        couponsRepository: CouponsRepository,
        postExecutionThread: PostExecutionThread
    ) = BannerUseCase(couponsRepository, postExecutionThread = postExecutionThread)

     @Provides
    @Singleton
    fun provideCouponListUseCase(
        couponsRepository: CouponsRepository,
        postExecutionThread: PostExecutionThread
    ) = CouponListUseCase(couponsRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideHelpIssueListUseCase(
        helpSettingRepository: HelpSettingRepository,
        postExecutionThread: PostExecutionThread
    ) = HelpIssueListUseCase(helpSettingRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideHelpSettingsUseCase(
        helpSettingRepository: HelpSettingRepository,
        postExecutionThread: PostExecutionThread
    ) = HelpSettingsUseCase(helpSettingRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideInviteFriendUseCase(
        inviteSettingRepository: InviteSettingRepository,
        postExecutionThread: PostExecutionThread
    ) = InviteSettingUseCase(inviteSettingRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideAddToCartUseCase(
        addToCartRepository: AddToCartRepository,
        postExecutionThread: PostExecutionThread
    ) = AddToCartUseCase(addToCartRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideAddToWishListUseCase(
        addToWishListRepository: AddToWishListRepository,
        postExecutionThread: PostExecutionThread
    ) = AddToWishListUseCase(addToWishListRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideGetWishListItemsUseCase(
        getWishListItemsRepository: GetWishListItemsRepository,
        postExecutionThread: PostExecutionThread
    ) = GetWishListDataUseCase(
        getWishListItemsRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideGetCartListUseCase(
        couponsRepository: CouponsRepository,
        postExecutionThread: PostExecutionThread
    ) = GetCartListUseCase(couponsRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(
        changePasswordRepository: ChangePasswordRepository,
        postExecutionThread: PostExecutionThread
    ) = ChangePasswordUseCase(changePasswordRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideShopDetailUseCase(
        shopDetailRepository: ShopDetailRepository,
        postExecutionThread: PostExecutionThread
    ) = ShopDetailUseCase(
        shopDetailRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideResetQRCodeUseCase(
        qrCodeRepository: QRCodeRepository,
        postExecutionThread: PostExecutionThread
    ) = ResetQRCodeUseCase(
        qrCodeRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideImageUploadUseCase(
        imageUploadRepository: ImageRepository,
        postExecutionThread: PostExecutionThread
    ) = OptimizeImageUseCase(
        imageUploadRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        userProfileSettingRepository: UserProfileSettingRepository,
        postExecutionThread: PostExecutionThread
    ) = UserProfileUseCase(
        userProfileSettingRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideRecentListUseCase(
        getRecentsListRepository: GetRecentsListRepository,
        postExecutionThread: PostExecutionThread
    ) = RecentsListUseCase(
        getRecentsListRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideRewardsPointsUseCase(
        rewardsPointsRepository: RewardsPointsRepository,
        postExecutionThread: PostExecutionThread
    ) = RewardsPointsUseCase(
        rewardsPointsRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideTransferRewardsUseCase(
        transferRewardsRepository: TransferRewardsRepository,
        postExecutionThread: PostExecutionThread
    ) = TransferRewardsUseCase(
        transferRewardsRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideGetRewardsHistoryUseCase(
        rewardsHistoryRepository: RewardsHistoryRepository,
        postExecutionThread: PostExecutionThread
    ) = GetRewardsHistoryUseCase(
        rewardsHistoryRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideNotificationUseCase(
        notificationListRepository: NotificationListRepository,
        postExecutionThread: PostExecutionThread
    ) = NotificationUseCase(
        notificationListRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideViewAllCategoriesUseCase(
        viewAllRepository: ViewAllRepository,
        postExecutionThread: PostExecutionThread
    ) = CategoryViewAllUseCase(
        viewAllRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideSwitchNotificationUseCase(
        searchPeopleNearByRepository: SwitchNotificationRepository, postExecutionThread: PostExecutionThread
    ) = SwitchNotificationUseCase(searchPeopleNearByRepository, postExecutionThread = postExecutionThread)

    @Provides
    @Singleton
    fun provideDeleteWishListUseCase(
        getWishListItemsRepository: GetWishListItemsRepository,
        postExecutionThread: PostExecutionThread
    ) = DeleteWishListUseCase(
        getWishListItemsRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideDeleteNotificationUseCase(
        notificationListRepository: NotificationListRepository,
        postExecutionThread: PostExecutionThread
    ) = DeleteNotificationUseCase(
        notificationListRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideDeleteCouponUseCase(
        couponsRepository: CouponsRepository,
        postExecutionThread: PostExecutionThread
    ) = DeleteCouponUseCase(
        couponsRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideShopsListUseCase(
        shopsListRepository: ShopsListRepository,
        postExecutionThread: PostExecutionThread
    ) = ShopsListUseCase(
        shopsListRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideShopsViewAllUseCase(
        shopsListRepository: ShopsListRepository,
        postExecutionThread: PostExecutionThread
    ) = ShopViewAllUseCase(
        shopsListRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideUserCouponUseCase(
        shopDetailRepository: ShopDetailRepository,
        postExecutionThread: PostExecutionThread
    ) = UserCouponUseCase(
        shopDetailRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideWalletViewAllUseCase(
        couponsRepository: CouponsRepository,
        postExecutionThread: PostExecutionThread
    ) = WalletViewAllUseCase(
        couponsRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideBusinessNotificationAllUseCase(
        shopDetailRepository: ShopDetailRepository,
        postExecutionThread: PostExecutionThread
    ) = BusinessNotificationUseCase(
        shopDetailRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideChangeAppLanguageUseCase(
        appLanguageRepository: AppLanguageRepository,
        postExecutionThread: PostExecutionThread
    ) = AppLanguageChangeUseCase(
        appLanguageRepository,
        postExecutionThread = postExecutionThread
    )
    @Provides
    @Singleton
    fun provideVerifyDeviceUseCase(
        verifyDeviceRepository: VerifyDeviceRepository,
        postExecutionThread: PostExecutionThread
    ) = VerifyDeviceUseCase(
        verifyDeviceRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideRewardCouponUseCase(
        redeemRepository: RewardRepository,
        postExecutionThread: PostExecutionThread
    ) = RewardCouponUseCase (
        redeemRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideRedeemCouponUseCase(
        redeemRepository: RedeemRepository,
        postExecutionThread: PostExecutionThread
    ) = RedeemCouponUseCase (
        redeemRepository,
        postExecutionThread = postExecutionThread
    )

    @Provides
    @Singleton
    fun provideCouponValidityUseCase(
        couponValidityRepository: CouponValidityRepository,
        postExecutionThread: PostExecutionThread
    ) = CouponValidityUseCase (
        couponValidityRepository,
        postExecutionThread = postExecutionThread
    )
}
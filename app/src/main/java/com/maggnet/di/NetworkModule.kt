package com.maggnet.di


import com.maggnet.data.coupons.services.CouponsApiServices
import com.maggnet.data.languagechange.services.AppLanguageChangeServices
import com.maggnet.data.notifications.service.NotificationApiServices
import com.maggnet.data.qrcode.service.QRCodeApiServices
import com.maggnet.data.redeem.service.RedeemApiService
import com.maggnet.data.register.fcm.service.GeneralApiService
import com.maggnet.data.register.imageupload.service.ImageApiServices
import com.maggnet.data.register.login.service.LoginApiServices
import com.maggnet.data.register.otp.service.RegisterApiServices
import com.maggnet.data.settings.service.SettingsApiServices
import com.maggnet.domain.executor.NetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRegisterService(networkProvider: NetworkProvider) =
        networkProvider.create(RegisterApiServices::class.java)

    @Provides
    fun provideLoginService(networkProvider: NetworkProvider) =
        networkProvider.create(LoginApiServices::class.java)

    @Provides
    fun provideGeneralApiService(networkProvider: NetworkProvider) =
        networkProvider.create(GeneralApiService::class.java)

    @Provides
    fun provideCouponApiService(networkProvider: NetworkProvider) =
        networkProvider.create(CouponsApiServices::class.java)

    @Provides
    fun provideSettingsApiService(networkProvider: NetworkProvider) =
        networkProvider.create(SettingsApiServices::class.java)

    @Provides
    fun provideQRCodeApiService(networkProvider: NetworkProvider) =
        networkProvider.create(QRCodeApiServices::class.java)

    @Provides
    fun provideImageUploadService(networkProvider: NetworkProvider) =
        networkProvider.create(ImageApiServices::class.java)

    @Provides
    fun provideNotificationApiService(networkProvider: NetworkProvider) =
        networkProvider.create(NotificationApiServices::class.java)

    @Provides
    fun provideChangeAppLanguageApiService(networkProvider: NetworkProvider) =
        networkProvider.create(AppLanguageChangeServices::class.java)

    @Provides
    fun provideRedeemApiService(networkProvider: NetworkProvider) =
        networkProvider.create(RedeemApiService::class.java)
}

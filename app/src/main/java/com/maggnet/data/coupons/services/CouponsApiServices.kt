package com.maggnet.data.coupons.services

import com.maggnet.data.coupons.model.*
import com.maggnet.domain.remote.BaseResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface CouponsApiServices {
    @POST("api/business/coupon/search-by-shops")
    fun getCouponsList(@Body couponsListRequest: CouponsListRequest): Single<CouponsListResponse>


   @POST("api/user/get-coupons-by-imei")
    fun getCouponsListByImei(@Body couponsListImeiRequest: CouponsListImeiRequest): Single<CouponsListImeiResponse>

    @GET("api/banner/get-all")
    fun getBanner(): Single<BannerListResponse>

    @POST("api/coupon/add-to-wallet")
    fun addToCart(@Body addToCartRequest: AddToCartRequest): Single<BaseResponse>

    @POST("api/user/wishlist/add")
    fun addToWishList(@Body addToWishListRequest: AddToWishListRequest): Single<BaseResponse>

    @POST("api/user/wishlist")
    fun getWishListItems(@Body getWishListRequest: GetWishListRequest): Single<GetWishListResponse>

    @POST("api/user/wallet")
    fun getCartList(@Body getCartListRequest: GetCartListRequest): Single<CouponsListResponse>

    @POST("api/shop-details/{id}")
    fun getShopDetails(@Path("id") id: String, @Body shopDetailRequest: ShopDetailRequest): Single<ShopDetailResponse>

    @POST("api/user/recent-coupons")
    fun getRecentList(@Body getRecentListRequest: GetRecentListRequest): Single<CouponsListResponse>

    @POST("api/get/user-shop-reward")
    fun getRewardsPoints(@Body rewardsPointsRequest: RewardsPointsRequest): Single<RewardsPointsResponse>

    @POST("api/business/coupon/search-by-shops")
    fun getViewAllCategories(@Body viewAllCategoriesRequest: ViewAllCategoriesRequest): Single<ViewAllCategoriesResponse>

    @HTTP(method = "DELETE", path = "api/delete/wishlist", hasBody = true)
    fun deleteWishList(@Body deleteCouponRequest : DeleteWishListRequest):  Single<BaseResponse>

    @POST("api/remove-wallet-coupon")
    fun deleteCoupon(@Body deleteCouponRequest : DeleteCouponRequest):  Single<BaseResponse>

    @POST("api/get/shop-list")
    fun getShopsList(@Body shopListRequest : ShopListRequest):  Single<CouponsListResponse>

    @POST("api/get/shop-list")
    fun getShopsViewAll(@Body shopListRequest : ShopListRequest):  Single<ViewAllCategoriesResponse>

    @GET("api/user-coupon/{id}")
    fun userCouponView(@Path("id") id: String):  Single<UserCouponResponse>

    @POST("api/user/wallet")
    fun getViewAllWalletCoupons(@Body getCartListRequest: GetCartListRequest): Single<ViewAllCategoriesResponse>

    @POST("api/disable-business-notification")
    fun businessNotification(@Body businessNotificationRequest: BusinessNotificationRequest): Single<BaseResponse>


}
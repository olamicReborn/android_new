package com.maggnet.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.preference.PreferenceManager
import com.google.firebase.messaging.Constants.MessageNotificationKeys.IMAGE_URL
import com.maggnet.ui.extensions.empty
import com.maggnet.ui.extensions.safeGet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PreferenceManager constructor(val context: Context) {

    private val sharedPreferences by lazy {
        val preferences =
            context.getSharedPreferences(MAGGNET_PREFERENCES_FILE, Context.MODE_PRIVATE)
        preferences
    }

    companion object {
        private const val MAGGNET_PREFERENCES_FILE = "maggnet_prefs_file"
        private const val OTP_TIMESTAMP = "otp_timestamp"
        private const val LANGUAGE = "language"
        private const val OTP_REQUESTED = "otp_requested"
        private const val FIRST_OTP_EXHAUST_TIME_INTERVAL = "first_otp_exhaust_time_interval"
        private const val USER_MOBILE_NUMBER = "user_mobile_number"
        private const val USER_ID = "user_id"
        private const val USER_EMAIL = "user_email"
        private const val USER_IMAGE = "user_image"
        private const val USER_NAME = "user_name"
        private const val FCM_TOKEN = "fcm_token"
        private const val TOKEN = "token"
        private const val QR_CODE = "qr_code"
        private const val IS_NOTIFICATION_ENABLE = "is_notification_enable"
        private const val SMS_OTP_EXHAUST = "sms_otp_exhaust"
        private const val OTP_EXHAUST_TIME_INTERVAL = "otp_exhaust_time_interval"
        private const val IS_CHANGE_LANGUAGE = "is_change_language"
        private const val IS_MUTE_NOTIFICATION = "is_mute_notification"
        private const val IMEI_NUMBER = "imei_number"
    }

    // For language change
    fun setLanguagePreference(language: String) {
        setStringPolicy(LANGUAGE, language)
    }

    fun getLanguagePreference(): AppLanguageUtils.Language {
        val appLanguage = getStringPolicy(LANGUAGE)

        return if (appLanguage.isNullOrEmpty()) {
            AppLanguageUtils.Language.ENGLISH
        } else {
            if (appLanguage == AppLanguageUtils.Language.ARABIC.code) {
                AppLanguageUtils.Language.ARABIC
            } else {
                AppLanguageUtils.Language.ENGLISH
            }
        }
    }


    // region STRING POLICIES
    private fun setStringPolicy(key: String, value: String) {
        try {
            val data = value
            sharedPreferences.edit().putString(key, data).apply()
        } catch (e: Exception) {
            AppLogger.d(TAG, "Exception in setStringPolicy = $e")
        }
    }

    private fun getStringPolicy(key: String): String? {
        val concatData = sharedPreferences.getString(key, null)

        return if (concatData.isNullOrBlank().not())
            concatData
        else
            String.empty
    }

    /**
     * Store otp request timestamp
     * @param timestamp otp request timestamp
     * */
    fun setOtpRequestedTimestamp(timestamp: Long) {
        setLongPolicy(OTP_TIMESTAMP, timestamp)
    }

    /**
     * Get otp request timestamp
     * @return otp request timestamp
     * */
    fun getOtpRequestedTimestamp(): Long {
        return getLongPolicy(OTP_TIMESTAMP)
    }

    fun setOtpRequested(otpRequested: Boolean) {
        setBooleanPolicy(OTP_REQUESTED, otpRequested)
    }

    fun setIMEINumber(userId: String) {
        setStringPolicy(IMEI_NUMBER, userId)
    }

    /**
     * Get imei_number stored in preferences
     * @return imei_number from preferences , return null if not found
     * */
    fun getIMEINumber(): String? {
        return getStringPolicy(IMEI_NUMBER)
    }

    fun getOtpRequested(): Boolean {
        return getBooleanPolicy(OTP_REQUESTED)
    }

    fun setIsLanguageChange(isChangeLanguage:  Boolean) {
        setBooleanPolicy(IS_CHANGE_LANGUAGE, isChangeLanguage)
    }

    fun getIsLanguageChange(): Boolean {
        return getBooleanPolicy(IS_CHANGE_LANGUAGE)
    }

    fun setIsMuteNotification(key : String, isMuteNotification:  Boolean) {
        setBooleanPolicy(key, isMuteNotification)
    }

    fun getIsMuteNotification(key: String): Boolean {
        return getBooleanPolicyWithDefaultTrue(key)
    }

    /**
     * Time interval till the time otp buttons will be disabled
     * This will be received from API on OTP generate
     * @param timeInterval time interval received from server
     */
    fun setFirstOtpExhaustTimeInterval(timeInterval: Long) {
        setLongPolicy(FIRST_OTP_EXHAUST_TIME_INTERVAL, timeInterval)
    }

    /**
     * time interval till the time send sms & call otp buttons disabled
     */
    fun getFirstOtpExhaustTimeInterval(): Long {
        return getLongPolicy(FIRST_OTP_EXHAUST_TIME_INTERVAL)
    }

    /**
     * Set SMS otp exhaust status boolean
     * @param smsOtpExhausted boolean variable , true if SMS exhaust received from api
     * */
    fun setSmsOtpExhausted(smsOtpExhausted: Boolean) {
        setBooleanPolicy(SMS_OTP_EXHAUST, smsOtpExhausted)
    }

    /**
     * Get SMS otp exhaust status boolean
     * @return SMS otp exhaust status
     * */
    fun getSmsOtpExhausted(): Boolean {
        return getBooleanPolicy(SMS_OTP_EXHAUST)
    }

    /**
     * Time interval till the time otp buttons will be disabled
     * This will be received from API
     * @param timeInterval time interval received from server
     */
    fun setOtpExhaustTimeInterval(timeInterval: Long) {
        setLongPolicy(OTP_EXHAUST_TIME_INTERVAL, timeInterval)
    }

    /**
     * time interval till the time send sms & call otp buttons disabled
     */
    fun getOtpExhaustTimeInterval(): Long {
        return getLongPolicy(OTP_EXHAUST_TIME_INTERVAL)
    }

    /**
     * Get user mobile number store in preference
     * @return mobile number from preferences , return null if not found
     * */
    fun getMobileNumberForRegistration(): String {
        return getStringPolicy(USER_MOBILE_NUMBER).safeGet()
    }

    /**
     * Set user mobile number in preference
     * */
    fun setMobileNumberForRegistration(mobileNumber: String) {
        setStringPolicy(USER_MOBILE_NUMBER, mobileNumber)
    }

    /**
     * Get user email store in preference
     * @return email from preferences , return null if not found
     * */
    fun getUserEmail(): String {
        return getStringPolicy(USER_EMAIL).safeGet()
    }

    /**
     * Set user email in preference
     * */
    fun setUserEmail(email: String) {
        setStringPolicy(USER_EMAIL, email)
    }

    /**
     * Get user name store in preference
     * @return user name from preferences , return null if not found
     * */
    fun getUserName(): String {
        return getStringPolicy(USER_NAME).safeGet()
    }

    /**
     * Set user name in preference
     * */
    fun setUserName(name: String) {
        setStringPolicy(USER_NAME, name)
    }

    /**
     * Get user image store in preference
     * @return user image from preferences , return null if not found
     * */
    fun getUserImage(): String {
        return getStringPolicy(USER_IMAGE).safeGet()
    }

    /**
     * Set user image in preference
     * */
    fun setUserImage(name: String) {
        setStringPolicy(USER_IMAGE, name)
    }

    // region USER ID
    /**
     * Store user id to preferences
     * @param userId UserID
     * */
    fun setUserId(userId: String) {
        setStringPolicy(USER_ID, userId)
    }

    /**
     * Get user id stored in preferences
     * @return user id from preferences , return null if not found
     * */
    fun getUserId(): String? {
        return getStringPolicy(USER_ID)
    }

    // region FCM TOKEN
    /**
     * Store FCM TOKEN to preferences
     * @param fcmToken fcm token
     * */
    fun setFCMToken(fcmToken: String) {
        setStringPolicy(FCM_TOKEN, fcmToken)
    }

    /**
     * Get token stored in preferences
     * @return fcm token from preferences , return null if not found
     * */
    fun getFCMToken(): String {
        return getStringPolicy(FCM_TOKEN).safeGet()
    }

    /**
     * Store token to preferences
     * @param token token
     * */
    fun setToken(token: String) {
        setStringPolicy(TOKEN, token)
    }

    /**
     * Get token stored in preferences
     * @return token from preferences , return null if not found
     * */
    fun getToken(): String? {
        return getStringPolicy(TOKEN)
    }

    /**
     * Store qrCode to preferences
     * @param qrCode
     * */
    fun setQRCode(token: String) {
        setStringPolicy(QR_CODE, token)
    }

    /**
     * Get qrCode stored in preferences
     * @return qrCode from preferences , return null if not found
     * */
    fun getQRCode(): String? {
        return getStringPolicy(QR_CODE)
    }


    // region LONG POLICIES
    private fun setLongPolicy(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    private fun getLongPolicy(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    // region BOOLEAN POLICIES
    private fun setBooleanPolicy(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private fun getBooleanPolicy(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    private fun getBooleanPolicyWithDefaultTrue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    /**
     * To feed the status in shared pref
     */
    fun setImageUrl(status: String) {
        setStringPolicy(IMAGE_URL, status)
    }

    /**
     * To get the status from preference
     */
    fun getImageUrl(): String? {
        return getStringPolicy(IMAGE_URL)
    }

    fun clearSharedPreferences() {
        context.getSharedPreferences(MAGGNET_PREFERENCES_FILE, 0).edit().clear().apply()
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().clear().apply()
    }

    /**
     * To set notification enable
     */
    fun setNotificationStatus(status: String) {
        setStringPolicy(IS_NOTIFICATION_ENABLE, status)
    }

    /**
     * To get notification enable
     */
    fun getNotificationStatus(): String? {
        return getStringPolicy(IS_NOTIFICATION_ENABLE)
    }


}
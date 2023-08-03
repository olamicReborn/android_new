package com.maggnet.ui.base

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Base64
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.maggnet.R
import com.maggnet.data.coupons.model.CouponsListResponse
import com.maggnet.di.IODispatcher
import com.maggnet.di.MainDispatcher
import com.maggnet.utils.mayNavigate
import com.makeramen.roundedimageview.RoundedImageView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.lang.reflect.Type
import javax.inject.Inject

abstract class BaseViewModel<N> : ViewModel() {

    @Inject
    @IODispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    lateinit var navigator: WeakReference<N>

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        with(compositeDisposable) {
            if (isDisposed.not()) {
                dispose()
                compositeDisposable = CompositeDisposable()
            }
        }
        super.onCleared()
    }

    fun getNavigator(): N? {
        return navigator.get()
    }

    fun setNavigator(navigator: N?) {
        this.navigator = WeakReference(navigator)
    }

    fun addDisposable(disposable: Disposable?) {
        disposable?.let {
            if (it.isDisposed.not()) {
                compositeDisposable.add(it)
            }
        }
    }

    fun moveToWishList(fragment: Fragment, itemId : Int){
        fragment.findNavController().navigate(itemId)
    }

    fun moveToNotificationList(fragment: Fragment, itemId : Int){
        fragment.findNavController().navigate(itemId)
    }

    fun getCouponsListData(inputData: List<JsonObject>): List<CouponsListResponse.CouponCategory> {
        val resultList  = ArrayList<CouponsListResponse.CouponCategory>()
        //get first element as raw data and convert to string type
        val rawJsonData = inputData.first().toString()
        val type: Type = object : TypeToken<Map<String, List<CouponsListResponse.CouponsListData>>>() {}.type
        val resultMap : Map<String,List<CouponsListResponse.CouponsListData>> = Gson().fromJson(rawJsonData,type)

        resultMap.forEach{
            resultList.add(
                CouponsListResponse.CouponCategory(
                    couponList = it.value as ArrayList<CouponsListResponse.CouponsListData>,
                    title = it.key,
                    categoryImageUrl = it.value.firstOrNull()?.filterIcon.toString(),
                    id = it.value.firstOrNull()?.categoryId.toString(),
                )
            )
        }
        return resultList
    }

}
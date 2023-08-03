package com.maggnet.ui.base

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.viewbinding.ViewBinding
import com.maggnet.MaggnetApplication
import com.maggnet.di.IODispatcher
import com.maggnet.di.MainDispatcher
import com.maggnet.ui.extensions.empty
import com.maggnet.utils.DialogManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewDataBinding: VB
        get() = _binding as VB


    @Inject
    lateinit var dialogManager: DialogManager

    @Inject
    @IODispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher


    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(layoutInflater)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserInterface(view)
    }

    //region UTIL
    protected abstract fun initUserInterface(view: View?)

    /**
     * To show and hide of progress bar based on api call and response
     */
    //endregion



    override fun onDestroyView() {
        super.onDestroyView()
       // _binding = null
    }

    open fun displayAlert(
        title: Int = 0,
        messageResourceId: Int = 0,
        message: String = String.empty
    ) {
        dialogManager.singleButtonDialog(
            context = requireContext(),
            title = if (title == 0) String.empty else MaggnetApplication.instance.getString(title),
            message = if (messageResourceId == 0) message else MaggnetApplication.instance.getString(
                messageResourceId
            ),
            cancelable = true
        )
    }



    private fun timeConversionFrom24(time12: String): String {
        val inputFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        val date = inputFormat.parse(time12)
        return outputFormat.format(date).toString()
    }



    fun displayQRCode(qrCodeBase64Image: String, imageView: AppCompatImageView) {
        lifecycle.coroutineScope.launch {
            var qrCodeBitmap: Bitmap? = null
            withContext(Dispatchers.Unconfined) {
                val decodedString: ByteArray = Base64.decode(
                    qrCodeBase64Image.substring(qrCodeBase64Image.indexOf(",")),
                    Base64.DEFAULT
                )
                qrCodeBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            }
            withContext(Dispatchers.Main) {
                qrCodeBitmap?.let { bitmap ->
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }

}
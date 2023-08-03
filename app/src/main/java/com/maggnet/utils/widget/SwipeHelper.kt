package com.maggnet.utils.widget


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.maggnet.utils.Constant
import com.maggnet.utils.extensions.toPx
import java.util.*


abstract class SwipeHelper(
    context: Context?,
    private val recyclerView: RecyclerView,
    private val isShopList: Boolean = false
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private var buttons: MutableList<UnderlayButton> = mutableListOf()
    private lateinit var gestureDetector: GestureDetector
    private var swipedPos = -1
    private var swipeThreshold = 0.5f
    private val buttonsBuffer: MutableMap<Int, MutableList<UnderlayButton>>
    private lateinit var recoverQueue: Queue<Int>
    private val gestureListener: GestureDetector.SimpleOnGestureListener =
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                for (button in buttons) {
                    if (button.onClick(e.x, e.y)) break
                }
                return true
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, e ->
        if (swipedPos < 0) return@OnTouchListener false
        var swipedViewHolder: RecyclerView.ViewHolder? = null
        try {
            swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(swipedPos)
            if (!isSwipeAllowed(swipedViewHolder!!)) return@OnTouchListener false
        } catch (e: Exception) {
            //AppLogger.e(TAG, "Exception ${e.localizedMessage}")
        }
        val point = Point(e.rawX.toInt(), e.rawY.toInt())
        val swipedItem = swipedViewHolder?.itemView
        val rect = Rect()
        swipedItem?.getGlobalVisibleRect(rect)
        if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_MOVE) {
            if (rect.top < point.y && rect.bottom > point.y) gestureDetector.onTouchEvent(e) else {
                recoverQueue.add(swipedPos)
                swipedPos = -1
                recoverSwipedItem()
            }
        }
        false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, createSwipeFlags(viewHolder))
    }

    private fun createSwipeFlags(viewHolder: RecyclerView.ViewHolder): Int {
        return when {
            viewHolder.itemViewType == Constant.ITEM_VIEW_TYPE -> ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            isShopList -> ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            else -> 0
        }
    }

    private fun isSwipeAllowed(viewHolder: RecyclerView.ViewHolder): Boolean {
        return if (isShopList) true else viewHolder.itemViewType == Constant.ITEM_VIEW_TYPE
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.absoluteAdapterPosition
        if (swipedPos != pos) recoverQueue.add(swipedPos)
        swipedPos = pos
        if (buttonsBuffer.containsKey(swipedPos)) buttons =
            buttonsBuffer[swipedPos]!! else buttons.clear()
        buttonsBuffer.clear()
        swipeThreshold = 0.5f * buttons.size * BUTTON_WIDTH
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.absoluteAdapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: MutableList<UnderlayButton> = ArrayList()
                instantiateUnderlayButton(viewHolder, buffer)
                buttonsBuffer[pos] = buffer
                if (buttonsBuffer.isNotEmpty())
                    buffer = buttonsBuffer[pos]!!.filter {
                        it.getDirection() == ItemTouchHelper.LEFT
                    } as MutableList<UnderlayButton>
                translationX = dX * buffer.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            } else {
                var buffer: MutableList<UnderlayButton> = ArrayList()
                instantiateUnderlayButton(viewHolder, buffer)
                buttonsBuffer[pos] = buffer
                if (buttonsBuffer.isNotEmpty())
                    buffer = buttonsBuffer[pos]!!.filter {
                        it.getDirection() == ItemTouchHelper.RIGHT
                    } as MutableList<UnderlayButton>
                translationX = dX * buffer.size * BUTTON_WIDTH / itemView.width
                drawButtonsRight(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val pos = recoverQueue.poll()!!
            if (pos > -1) {
                recyclerView.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    private fun drawButtons(
        c: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>,
        pos: Int,
        dX: Float
    ) {
        var right = itemView.right.toFloat()
        var left: Float
        val dButtonWidth = -1 * dX / buffer.size
        for (button in buffer) {
            if (button.getDirection() == ItemTouchHelper.LEFT) {
                left = right - dButtonWidth
                button.onDraw(
                    c,
                    RectF(
                        left,
                        itemView.top.toFloat(),
                        right,
                        itemView.bottom.toFloat()
                    ),
                    pos
                )
                right = left
            }
        }
    }

    private fun drawButtonsRight(
        c: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>,
        pos: Int,
        dX: Float
    ) {
        var right: Float
        var left = itemView.left.toFloat()
        val dButtonWidth = -1 * dX / buffer.size
        for (button in buffer) {
            if (button.getDirection() == ItemTouchHelper.RIGHT) {
                right = left - dButtonWidth
                button.onDrawRight(
                    c,
                    RectF(
                        left,
                        itemView.top.toFloat(),
                        right,
                        itemView.bottom.toFloat()
                    ),
                    pos
                )
                left = right
            }
        }
    }

    private fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(
        viewHolder: RecyclerView.ViewHolder?,
        underlayButtons: MutableList<UnderlayButton>?
    )

    class UnderlayButton(
        private val text: String,
        private val imageResId: Int,
        private val color: Int,
        private val direction: Int,
        private val clickListener: UnderlayButtonClickListener
    ) {
        private var pos = 0
        private var clickRegion: RectF? = null
        fun getDirection(): Int {
            return direction
        }

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) {
                clickListener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(c: Canvas, rect: RectF, pos: Int) {
            val p = Paint()

            // Draw background
            p.color = color
            c.drawRect(rect, p)

            // Draw icon
            val bmp = drawableToBitmap(ContextCompat.getDrawable(MaggnetApplication.instance, imageResId))!!

            // Draw Text
            p.color = Color.WHITE
            p.textSize = 12.toPx().toFloat()
            val r = Rect()
            val cHeight = rect.height()
            val cWidth = rect.width()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)
            val x = cWidth / 2f - r.width() / 2f - r.left
            val y = cHeight / 2f + r.height() / 2f - r.bottom


            val combinedHeight: Float = bmp.height + 20F + r.height().toFloat()

            c.drawBitmap(
                bmp,
                rect.centerX() - bmp.width / 2,
                rect.centerY() - combinedHeight / 2,
                null
            )
            c.drawText(
                text,
                rect.left + x,
                rect.centerY() + combinedHeight / 2,
                p
            )
//            c.drawText(text, rect.left + x, rect.top + y, p)
            clickRegion = rect
            this.pos = pos
        }

        fun onDrawRight(c: Canvas, rect: RectF, pos: Int) {
            val p = Paint()

            // Draw background
            p.color = color
            c.drawRect(rect, p)

            // Draw icon
            val bmp = drawableToBitmap(ContextCompat.getDrawable(MaggnetApplication.instance, imageResId))!!

            // Draw Text
            p.color = Color.WHITE
            p.textSize = 12.toPx().toFloat()
            val r = Rect()
            val cHeight = rect.height()
            val cWidth = rect.width()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)
            val x = cWidth / 2f - r.width() / 2f - r.left
            val y = cHeight / 2f + r.height() / 2f - r.bottom


            val combinedHeight: Float = bmp.height + 20F + r.height().toFloat()

            c.drawBitmap(
                bmp,
                rect.centerX() - bmp.width / 2,
                rect.centerY() - combinedHeight / 2,
                null
            )
            c.drawText(
                text,
                rect.left + x,
                rect.centerY() + combinedHeight / 2,
                p
            )
//            c.drawText(text, rect.left + x, rect.top + y, p)
            clickRegion = rect
            this.pos = pos
        }

    }

    interface UnderlayButtonClickListener {
        fun onClick(pos: Int)
    }

    companion object {
        const val BUTTON_WIDTH = 250
        private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
            if (drawable is BitmapDrawable) {
                if (drawable.bitmap != null) {
                    return drawable.bitmap
                }
            }
            val bitmap: Bitmap =
                if (drawable!!.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                    Bitmap.createBitmap(
                        1,
                        1,
                        Bitmap.Config.ARGB_8888
                    ) // Single color bitmap will be created of 1x1 pixel
                } else {
                    Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                }
            val paint = Paint()
            val filter: ColorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(MaggnetApplication.instance, R.color.white),
                PorterDuff.Mode.SRC_IN
            )
            paint.colorFilter = filter

            val canvas = Canvas(bitmap)
            canvas.drawBitmap(bitmap, 0F, 0F, paint)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }

    init {
        buttons = ArrayList()
        gestureDetector = GestureDetector(context, gestureListener)
        recyclerView.setOnTouchListener(onTouchListener)
        buttonsBuffer = HashMap()
        recoverQueue = object : LinkedList<Int>() {
            override fun add(element: Int): Boolean {
                return if (contains(element)) false else super.add(element)
            }
        }
        attachSwipe()
    }
}
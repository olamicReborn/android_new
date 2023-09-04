package com.maggnet.utils.codescanner;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.maggnet.R;
import com.maggnet.utils.codescanner.ViewFinderView;


/**
 * A view to display code scanner preview
 *
 * @see CodeScanner
 */
public final class CodeScannerView extends RelativeLayout {
    private static final boolean DEFAULT_AUTO_FOCUS_BUTTON_VISIBLE = true;
    private static final boolean DEFAULT_FLASH_BUTTON_VISIBLE = false;
    private static final int DEFAULT_AUTO_FOCUS_BUTTON_VISIBILITY = VISIBLE;
    private static final int DEFAULT_FLASH_BUTTON_VISIBILITY = VISIBLE;
    private static final int DEFAULT_MASK_COLOR = 0x77000000;
    private static final int DEFAULT_FRAME_COLOR = Color.WHITE;
    private static final int DEFAULT_AUTO_FOCUS_BUTTON_COLOR = Color.WHITE;
    private static final float DEFAULT_FRAME_THICKNESS_DP = 2f;
    private static final float DEFAULT_FRAME_ASPECT_RATIO_WIDTH = 1f;
    private static final float DEFAULT_FRAME_ASPECT_RATIO_HEIGHT = 1f;
    private static final float DEFAULT_FRAME_CORNER_SIZE_DP = 50f;
    private static final float DEFAULT_FRAME_CORNERS_RADIUS_DP = 16f;
    private static final float DEFAULT_FRAME_SIZE = 0.99f;
    private static final float BUTTON_SIZE_DP = 56f;
    private static final float FOCUS_AREA_SIZE_DP = 20f;
    private SurfaceView mPreviewView;
    private ViewFinderView mViewFinderView;
    private ImageView mAutoFocusButton;
    private AppCompatImageView imageViewFlashButton;
    private SizeListener mSizeListener;
    private CodeScanner mCodeScanner;
    private int mFocusAreaSize;

    /**
     * A view to display code scanner preview
     *
     * @see CodeScanner
     */
    public CodeScannerView(@NonNull final Context context) {
        super(context);
        initialize(context, null, 0);
    }

    /**
     * A view to display code scanner preview
     *
     * @see CodeScanner
     */
    public CodeScannerView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    /**
     * A view to display code scanner preview
     *
     * @see CodeScanner
     */
    public CodeScannerView(@NonNull final Context context, @Nullable final AttributeSet attrs,
                           @AttrRes final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(@NonNull final Context context, @Nullable final AttributeSet attrs,
                            @AttrRes final int defStyleAttr) {
        mPreviewView = new SurfaceView(context);
        mPreviewView.setLayoutParams(
                new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        mViewFinderView = new ViewFinderView(context);
        mViewFinderView.setLayoutParams(
                new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        final float density = context.getResources().getDisplayMetrics().density;
        int mButtonSize = Math.round(density * BUTTON_SIZE_DP);
        mFocusAreaSize = Math.round(density * FOCUS_AREA_SIZE_DP);
        mAutoFocusButton = new ImageView(context);
        mAutoFocusButton.setLayoutParams(new LayoutParams(mButtonSize, mButtonSize));
        mAutoFocusButton.setScaleType(ImageView.ScaleType.CENTER);
        mAutoFocusButton.setImageResource(R.drawable.ic_baseline_center_focus_strong_24);
        mAutoFocusButton.setOnClickListener(new AutoFocusClickListener());
        AppCompatTextView textView = new AppCompatTextView(context);
        imageViewFlashButton = new AppCompatImageView(context);
        AppCompatImageView imageViewCameraSwitchButton = new AppCompatImageView(context);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
       // textView.setText(context.getString(R.string.qr_code_scanner_text));
        imageViewFlashButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageViewCameraSwitchButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageViewFlashButton.setScaleType(ImageView.ScaleType.CENTER);
        imageViewFlashButton.setImageResource(R.drawable.ic_flash);
        imageViewCameraSwitchButton.setImageResource(R.drawable.ic_camera_switch);
        imageViewFlashButton.setOnClickListener(new FlashClickListener());
        imageViewCameraSwitchButton.setOnClickListener(new CameraSwitchClickListener());
        imageViewCameraSwitchButton.setVisibility(View.VISIBLE);

        textView.setTextColor(Color.WHITE);
        if (attrs == null) {
            mViewFinderView.setFrameAspectRatio(DEFAULT_FRAME_ASPECT_RATIO_WIDTH,
                    DEFAULT_FRAME_ASPECT_RATIO_HEIGHT);
            mViewFinderView.setMaskColor(DEFAULT_MASK_COLOR);
            mViewFinderView.setFrameColor(DEFAULT_FRAME_COLOR);
            mViewFinderView.setFrameThickness(Math.round(DEFAULT_FRAME_THICKNESS_DP * density));
            //mViewFinderView.setFrameCornersSize(Math.round(DEFAULT_FRAME_CORNER_SIZE_DP * density));
            mViewFinderView
                    .setFrameCornersRadius(Math.round(DEFAULT_FRAME_CORNERS_RADIUS_DP * density));
            mViewFinderView.setFrameSize(DEFAULT_FRAME_SIZE);
            mAutoFocusButton.setColorFilter(DEFAULT_AUTO_FOCUS_BUTTON_COLOR);

            mAutoFocusButton.setVisibility(DEFAULT_AUTO_FOCUS_BUTTON_VISIBILITY);
            imageViewFlashButton.setVisibility(DEFAULT_FLASH_BUTTON_VISIBILITY);
        } else {
            TypedArray a = null;
            try {
                a = context.getTheme()
                        .obtainStyledAttributes(attrs, R.styleable.CodeScannerView, defStyleAttr,
                                0);
                setMaskColor(a.getColor(R.styleable.CodeScannerView_maskColor, DEFAULT_MASK_COLOR));
                setFrameColor(
                        a.getColor(R.styleable.CodeScannerView_frameColor, DEFAULT_FRAME_COLOR));
                setFrameThickness(
                        a.getDimensionPixelOffset(R.styleable.CodeScannerView_frameThickness,
                                Math.round(DEFAULT_FRAME_THICKNESS_DP * density)));
                setFrameCornersSize(
                        a.getDimensionPixelOffset(R.styleable.CodeScannerView_frameCornersSize,
                                Math.round(DEFAULT_FRAME_CORNER_SIZE_DP * density)));
                setFrameCornersRadius(
                        a.getDimensionPixelOffset(R.styleable.CodeScannerView_frameCornersRadius,
                                Math.round(DEFAULT_FRAME_CORNERS_RADIUS_DP * density)));
                setFrameAspectRatio(a.getFloat(R.styleable.CodeScannerView_frameAspectRatioWidth,
                        DEFAULT_FRAME_ASPECT_RATIO_WIDTH),
                        a.getFloat(R.styleable.CodeScannerView_frameAspectRatioHeight,
                                DEFAULT_FRAME_ASPECT_RATIO_HEIGHT));
                setFrameSize(a.getFloat(R.styleable.CodeScannerView_frameSize, DEFAULT_FRAME_SIZE));
                setAutoFocusButtonVisible(
                        a.getBoolean(R.styleable.CodeScannerView_autoFocusButtonVisible,
                                DEFAULT_AUTO_FOCUS_BUTTON_VISIBLE));
                setFlashButtonVisible(a.getBoolean(R.styleable.CodeScannerView_flashButtonVisible,
                        DEFAULT_FLASH_BUTTON_VISIBLE));
                setAutoFocusButtonColor(a.getColor(R.styleable.CodeScannerView_autoFocusButtonColor,
                        DEFAULT_AUTO_FOCUS_BUTTON_COLOR));
            } finally {
                if (a != null) {
                    a.recycle();
                }
            }
        }

        textView.setId(View.generateViewId());
        imageViewFlashButton.setId(View.generateViewId());
        imageViewCameraSwitchButton.setId(View.generateViewId());
        addView(mPreviewView);
        addView(mViewFinderView);

        LayoutParams textViewParams = (LayoutParams) textView.getLayoutParams();
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        textViewParams.bottomMargin = (int) (height * 0.8);
        textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        textViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //textViewParams.addRule(RelativeLayout.ABOVE, imageViewFlashButton.getId());
        //textViewParams.bottomMargin = 30;
        textView.setLayoutParams(textViewParams);
        addView(textView);

        LayoutParams flashButtonParams = (LayoutParams) imageViewFlashButton.getLayoutParams();
        flashButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        flashButtonParams.addRule(RelativeLayout.ALIGN_END, textView.getId());


        int height1 = Resources.getSystem().getDisplayMetrics().heightPixels;
        flashButtonParams.bottomMargin = (int) (height1 * 0.2);
        imageViewFlashButton.setLayoutParams(flashButtonParams);
        addView(imageViewFlashButton);

        LayoutParams galleryButtonParams = (LayoutParams) imageViewCameraSwitchButton.getLayoutParams();
        galleryButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        galleryButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        galleryButtonParams.bottomMargin = (int) (height * 0.8);
        galleryButtonParams.rightMargin = 60;
        imageViewCameraSwitchButton.setLayoutParams(galleryButtonParams);
        addView(imageViewCameraSwitchButton);
    }

    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth,
                                 final int oldHeight) {
        final SizeListener listener = mSizeListener;
        if (listener != null) {
            listener.onSizeChanged(width, height);
        }
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(@NonNull final MotionEvent event) {
        final CodeScanner codeScanner = mCodeScanner;
        final Rect frameRect = getFrameRect();
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        if (codeScanner != null && frameRect != null &&
                codeScanner.isAutoFocusSupportedOrUnknown() && codeScanner.isTouchFocusEnabled() &&
                event.getAction() == MotionEvent.ACTION_DOWN && frameRect.isPointInside(x, y)) {
            final int areaSize = mFocusAreaSize;
            codeScanner.performTouchFocus(
                    new Rect(x - areaSize, y - areaSize, x + areaSize, y + areaSize)
                            .fitIn(frameRect));
        }
        return super.onTouchEvent(event);
    }

    /**
     * Set color of the space outside of the framing rect
     *
     * @param color Mask color
     */
    public void setMaskColor(@ColorInt final int color) {
        mViewFinderView.setMaskColor(color);
    }


    /**
     * Set color of the frame
     *
     * @param color Frame color
     */
    public void setFrameColor(@ColorInt final int color) {
        mViewFinderView.setFrameColor(color);
    }


    /**
     * Set frame thickness
     *
     * @param thickness Frame thickness in pixels
     */
    public void setFrameThickness(@Px final int thickness) {
        if (thickness < 0) {
            throw new IllegalArgumentException("Frame thickness can't be negative");
        }
        mViewFinderView.setFrameThickness(thickness);
    }

    /**
     * Set size of the frame corners
     *
     * @param size Size in pixels
     */
    public void setFrameCornersSize(@Px final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Frame corners size can't be negative");
        }
        mViewFinderView.setFrameCornersSize(size);
    }

    /**
     * Set current frame corners radius
     *
     * @param radius Frame corners radius in pixels
     */
    public void setFrameCornersRadius(@Px final int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Frame corners radius can't be negative");
        }
        mViewFinderView.setFrameCornersRadius(radius);
    }


    /**
     * Set relative frame size where 1.0 means full size
     *
     * @param size Relative frame size between 0.1 and 1.0
     */
    public void setFrameSize(@FloatRange(from = 0.1, to = 1) final float size) {
        if (size < 0.1 || size > 1) {
            throw new IllegalArgumentException(
                    "Max frame size value should be between 0.1 and 1, inclusive");
        }
        mViewFinderView.setFrameSize(size);
    }


    /**
     * Set frame aspect ratio (ex. 1:1, 15:10, 16:9, 4:3)
     *
     * @param ratioWidth  Frame aspect ratio width
     * @param ratioHeight Frame aspect ratio height
     */
    public void setFrameAspectRatio(
            @FloatRange(from = 0, fromInclusive = false) final float ratioWidth,
            @FloatRange(from = 0, fromInclusive = false) final float ratioHeight) {
        if (ratioWidth <= 0 || ratioHeight <= 0) {
            throw new IllegalArgumentException(
                    "Frame aspect ratio values should be greater than zero");
        }
        mViewFinderView.setFrameAspectRatio(ratioWidth, ratioHeight);
    }

    /**
     * Set whether auto focus button is visible or not
     *
     * @param visible Visibility
     */
    public void setAutoFocusButtonVisible(final boolean visible) {
        mAutoFocusButton.setVisibility(visible ? VISIBLE : INVISIBLE);
    }

    /**
     * Set whether flash button is visible or not
     *
     * @param visible Visibility
     */
    public void setFlashButtonVisible(final boolean visible) {
        imageViewFlashButton.setVisibility(visible ? VISIBLE : INVISIBLE);
    }

    /**
     * Set auto focus button color
     *
     * @param color Color
     */
    public void setAutoFocusButtonColor(@ColorInt final int color) {
        mAutoFocusButton.setColorFilter(color);
    }

    @NonNull
    SurfaceView getPreviewView() {
        return mPreviewView;
    }

    @Nullable
    Rect getFrameRect() {
        return mViewFinderView.getFrameRect();
    }

    void setPreviewSize() {
        requestLayout();
    }

    void setSizeListener(@Nullable final SizeListener sizeListener) {
        mSizeListener = sizeListener;
    }

    void setCodeScanner(@NonNull final CodeScanner codeScanner) {
        if (mCodeScanner != null) {
            throw new IllegalStateException("Code scanner has already been set");
        }
        mCodeScanner = codeScanner;
        setAutoFocusEnabled();
        setFlashEnabled(codeScanner.isFlashEnabled());
    }

    void setAutoFocusEnabled() {
        mAutoFocusButton.setImageResource(R.drawable.ic_baseline_center_focus_strong_24);
    }

    void setFlashEnabled(final boolean enabled) {
        imageViewFlashButton.setImageResource(enabled ? R.drawable.ic_flash :
                R.drawable.ic_flash_off);
    }

    interface SizeListener {
        void onSizeChanged(int width, int height);
    }

    private final class AutoFocusClickListener implements OnClickListener {
        @Override
        public void onClick(final View view) {
            final CodeScanner scanner = mCodeScanner;
            if (scanner == null || !scanner.isAutoFocusSupportedOrUnknown()) {
                return;
            }
            final boolean enabled = !scanner.isAutoFocusEnabled();
            scanner.setAutoFocusEnabled(enabled);
            setAutoFocusEnabled();
        }
    }

    private final class FlashClickListener implements OnClickListener {
        @Override
        public void onClick(final View view) {
            final CodeScanner scanner = mCodeScanner;
            if (scanner == null || !scanner.isFlashSupportedOrUnknown()) {
                return;
            }
            final boolean enabled = !scanner.isFlashEnabled();
            scanner.setFlashEnabled(enabled);
            setFlashEnabled(enabled);
        }
    }

    private final class CameraSwitchClickListener implements OnClickListener {
        @Override
        public void onClick(final View view) {
            if (mCodeScanner != null && mCodeScanner.qrCodeScannerCallback != null) {
                if(mCodeScanner.getCamera() == CodeScanner.CAMERA_BACK) {
                    mCodeScanner.setCamera(CodeScanner.CAMERA_FRONT);
                } else if(mCodeScanner.getCamera() == CodeScanner.CAMERA_FRONT) {
                    mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
                }
            }
        }
    }
}

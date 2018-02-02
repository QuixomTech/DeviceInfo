package com.quixom.apps.deviceinfo.fragments

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.utilities.Methods
import java.text.DecimalFormat

class DisplayFragment : BaseFragment(){

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var tvScreenSize: TextView? = null
    var tvPhysicalSize: TextView? = null
    var tvRefreshRate: TextView? = null
    var tvXDpi: TextView? = null
    var tvYDpi: TextView? = null
    var tvScreenDensityBucket: TextView? = null
    var tvScreenName: TextView? = null
    var tvScreenDPI: TextView? = null
    var tvScreenLogicalDensity: TextView? = null
    var tvScreenScaledDensity: TextView? = null
    var tvScreenUsableWidth: TextView? = null
    var tvScreenUsableHeight: TextView? = null
    var tvScreenTotalWidth: TextView? = null
    var tvScreenTotalHeight: TextView? = null
    var tvIndependentWidth: TextView? = null
    var tvIndependentHeight: TextView? = null
    var tvScreenDefaultOrientation: TextView? = null
    var tvMaxGpuSize: TextView? = null
    val dm = DisplayMetrics()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)
        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)

        tvScreenSize = view.findViewById(R.id.tv_screen_size)
        tvPhysicalSize = view.findViewById(R.id.tv_physical_size)
        tvRefreshRate = view.findViewById(R.id.tv_refresh_rate)
        tvXDpi = view.findViewById(R.id.tv_xdpi)
        tvYDpi = view.findViewById(R.id.tv_ydpi)
        tvScreenName = view.findViewById(R.id.tv_screen_name)
        tvScreenDensityBucket = view.findViewById(R.id.tv_display_bucket)
        tvScreenDPI = view.findViewById(R.id.tv_display_dpi)
        tvScreenLogicalDensity = view.findViewById(R.id.tv_logical_density)
        tvScreenScaledDensity = view.findViewById(R.id.tv_scaled_density)
        tvScreenUsableWidth = view.findViewById(R.id.tv_usable_width)
        tvScreenUsableHeight = view.findViewById(R.id.tv_usable_height)
        tvScreenTotalWidth = view.findViewById(R.id.tv_screen_total_width)
        tvScreenTotalHeight = view.findViewById(R.id.tv_screen_total_height)
        tvIndependentWidth = view.findViewById(R.id.tv_independent_width)
        tvIndependentHeight = view.findViewById(R.id.tv_independent_height)
        tvScreenDefaultOrientation = view.findViewById(R.id.tv_default_orientation)
        tvMaxGpuSize = view.findViewById(R.id.tv_max_gpu)

        return view
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        getDisplayInfo()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    private fun initToolbar() {
        ivMenu?.visibility = View.VISIBLE
        ivBack?.visibility = View.GONE
        tvTitle?.text = mResources.getString(R.string.display)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getDisplayInfo() {
        val display = (mActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        mActivity.windowManager.defaultDisplay.getMetrics(dm)

        /*** Screen Size */
        val screenSizes: String = when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large screen"
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal screen"
            Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small screen"
            else -> "Screen size is neither large, normal or small"
        }
        tvScreenSize?.text = screenSizes

        /*** Screen physical size */
        mActivity.windowManager.defaultDisplay.getMetrics(dm)

        val realSize = Point()
        Display::class.java.getMethod("getRealSize", Point::class.java).invoke(display, realSize)
        val pWidth = realSize.x
        val pHeight = realSize.y
        val wi = pWidth.toDouble() / dm.xdpi.toDouble()
        val hi = pHeight.toDouble() / dm.ydpi.toDouble()
        val x = Math.pow(wi, 2.0)
        val y = Math.pow(hi, 2.0)
        val screenInches = Math.sqrt(x + y)
        tvPhysicalSize?.text = returnToDecimalPlaces(screenInches).plus(mResources.getString(R.string.inches))

        /*** Screen default orientation */
        when {
            mResources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> tvScreenDefaultOrientation?.text = mResources.getString(R.string.orientation_portrait)
            mResources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> tvScreenDefaultOrientation?.text = mResources.getString(R.string.orientation_landscape)
            mResources.configuration.orientation == Configuration.ORIENTATION_UNDEFINED -> tvScreenDefaultOrientation?.text = mResources.getString(R.string.orientation_undefined)
        }

        /*** Display screen width and height */
            tvScreenTotalWidth?.text = pWidth.toString().plus(mResources.getString(R.string.px))
            tvScreenTotalHeight?.text = pHeight.toString().plus(mResources.getString(R.string.px))

        /*** Screen refresh rate */
            tvRefreshRate?.text = display.refreshRate.toString().plus(mResources.getString(R.string.fps))

        /*** Display name */
            tvScreenName?.text = display.name.toString()

        /*** Max GPU Texture size */

       /* Handler().postDelayed({
            val gpu = GPU(mActivity)
            gpu.loadOpenGLGles10Info { result -> result.toString()
                tvMaxGpuSize?.text = result.GL_MAX_TEXTURE_SIZE.toString().plus(mResources.getString(R.string.x)).plus(result.GL_MAX_TEXTURE_SIZE)
            }
        }, 1000)*/

        /*** Screen Display buckets */
        when {
            mResources.displayMetrics.density == .75f -> tvScreenDensityBucket?.text = mResources.getString(R.string.ldpi)
            mResources.displayMetrics.density == 1.0f -> tvScreenDensityBucket?.text = mResources.getString(R.string.mdpi)
            mResources.displayMetrics.density == 1.5f -> tvScreenDensityBucket?.text = mResources.getString(R.string.hdpi)
            mResources.displayMetrics.density == 2.0f -> tvScreenDensityBucket?.text = mResources.getString(R.string.xhdpi)
            mResources.displayMetrics.density == 3.0f -> tvScreenDensityBucket?.text = mResources.getString(R.string.xxhdpi)
            mResources.displayMetrics.density == 4.0f -> tvScreenDensityBucket?.text = mResources.getString(R.string.xxxhdpi)
        }

        /*** Screen Dpi */
        tvScreenDPI?.text = mResources.displayMetrics.densityDpi.toString().plus(mResources.getString(R.string.dpi))

        /*** Screen logical density */
        tvScreenLogicalDensity?.text = dm.density.toString()

        /*** Screen scaled density */
        tvScreenScaledDensity?.text = dm.scaledDensity.toString()

        /*** Screen xDpi and yDpi */
        tvXDpi?.text = dm.xdpi.toString().plus(mResources.getString(R.string.dpi))
        tvYDpi?.text = dm.ydpi.toString().plus(mResources.getString(R.string.dpi))


        /*** Screen usable width and height */
        val size = Point()
        display.getSize(size)
        tvScreenUsableWidth?.text = size.x.toString().plus(mResources.getString(R.string.px))
        tvScreenUsableHeight?.text = size.y.toString().plus(mResources.getString(R.string.px))

        /*** Screen density independent width and height */
        tvIndependentWidth?.text = Methods.pxToDp(mActivity, dm.widthPixels).toString().plus(mResources.getString(R.string.dp))
        tvIndependentHeight?.text = Methods.pxToDp(mActivity, dm.heightPixels).toString().plus(mResources.getString(R.string.dp))
    }

    private fun returnToDecimalPlaces(values: Double): String {
        val df = DecimalFormat("#.00")
        return df.format(values)
    }
}

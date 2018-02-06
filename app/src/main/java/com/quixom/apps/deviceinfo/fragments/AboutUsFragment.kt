package com.quixom.apps.deviceinfo.fragments

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import com.quixom.apps.deviceinfo.BuildConfig
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.utilities.KeyUtil
import com.quixom.apps.deviceinfo.utilities.RateUsApp
import java.util.*




class AboutUsFragment : BaseFragment(), View.OnClickListener {

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var tvVersion: TextView? = null
    var tvCallIntent: TextView? = null
    var tvAddressIntent: TextView? = null
    var tvWebsiteIntent: TextView? = null
    var rbRatingBar: RatingBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)
        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)

        tvVersion = view.findViewById(R.id.tv_version)
        tvCallIntent = view.findViewById(R.id.tv_call)
        tvAddressIntent = view.findViewById(R.id.tv_address)
        tvWebsiteIntent = view.findViewById(R.id.tv_website)
        rbRatingBar = view.findViewById(R.id.rb_rating)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        tvVersion?.text = mResources.getString(R.string.version).plus(": " + BuildConfig.VERSION_NAME)
        initOnclickListner()
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
        tvTitle?.text = mResources.getString(R.string.about_us)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initOnclickListner() {
        tvCallIntent?.setOnClickListener(this)
        tvAddressIntent?.setOnClickListener(this)
        tvWebsiteIntent?.setOnClickListener(this)

        rbRatingBar?.onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            rbRatingBar?.rating = 5.0f
            RateUsApp.rateUsApp(mActivity)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View?) {
        when (view) {
            tvCallIntent -> checkCameraPermission()
            tvAddressIntent -> mapIntent()
            tvWebsiteIntent -> webIntent()
        }
    }

    private fun callIntent() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mResources.getString(R.string.company_phone)))
        startActivity(intent)
    }

    private fun mapIntent() {
        val uri = String.format(Locale.ENGLISH, "geo:%f,%f", 23.077309, 72.507228)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context?.startActivity(intent)
    }

    private fun webIntent() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.quixom.com"))
        startActivity(browserIntent)
    }

    /**
     * this method will show permission pop up messages to user.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkCameraPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasWriteCameraPermission = mActivity?.checkSelfPermission(Manifest.permission.CAMERA)
            if (hasWriteCameraPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), KeyUtil.KEY_CALL_PERMISSION)
            } else {
                callIntent()
            }
        } else {
            callIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            KeyUtil.KEY_CALL_PERMISSION -> if (permissions.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    tvCallIntent?.isClickable = true
                    callIntent()
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // Should we show an explanation?
                    tvCallIntent?.isClickable = false
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.GET_ACCOUNTS)) {
                        //Show permission explanation dialog...
                        tvCallIntent?.isClickable = false
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
package com.quixom.apps.deviceinfo.fragments

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.utilities.KeyUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.widget.ScrollView
import com.quixom.apps.deviceinfo.utilities.Camera2Utility


class CameraFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var tvCameraFeature: TextView? = null
    var textAreaScroller: ScrollView? = null

    private var rvCameraFeatures: RecyclerView? = null

    var camera: Camera? = null
    private var cameraManager: CameraManager? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_camera, container, false)

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        tvCameraFeature = view.findViewById(R.id.tv_camera_feature)
        textAreaScroller = view.findViewById(R.id.textAreaScroller)

        cameraManager = mActivity.getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        camera = Camera()
        return view
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        rvCameraFeatures?.layoutManager = LinearLayoutManager(mActivity)
        rvCameraFeatures?.hasFixedSize()

        checkCameraPermission()
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
        tvTitle?.text = mResources.getString(R.string.camera)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    /**
     * this method will show permission pop up messages to user.
     */
    private fun checkCameraPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasWriteCameraPermission = mActivity.checkSelfPermission(Manifest.permission.CAMERA)
            if (hasWriteCameraPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), KeyUtil.KEY_CAMERA_CODE)
            } else {
                getCameraFeatures()
            }
        } else {
            getCameraFeatures()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            KeyUtil.KEY_CAMERA_CODE -> if (permissions.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getCameraFeatures()
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.GET_ACCOUNTS)) {
                        //Show permission explanation dialog...
                        Toast.makeText(mActivity, "Need to grant account Permission", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun getCameraFeatures(): Unit {
        var cameraUtility = Camera2Utility(mActivity, false)
        cameraUtility.init()
        cameraUtility.deviceCameraSummary
        tvCameraFeature?.text = cameraUtility.deviceCameraSummary
        tvCameraFeature?.movementMethod = ScrollingMovementMethod()
        cameraUtility.unint()
    }
}



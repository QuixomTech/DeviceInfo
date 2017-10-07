package com.example.akif.devinfo.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.akif.devinfo.MainActivity
import com.example.akif.devinfo.R
import com.example.akif.devinfo.models.CameraFeatures

/**
 * Created by akif on 9/21/17.
 */

class CameraAdapter(internal var appslist: ArrayList<CameraFeatures>, internal var mActivity: MainActivity) : RecyclerView.Adapter<CameraAdapter.DeviceVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CameraAdapter.DeviceVH {
        val itemView = LayoutInflater.from(mActivity).inflate(R.layout.row_camera_item, parent, false)
        return DeviceVH(itemView)
    }

    override fun onBindViewHolder(holder: CameraAdapter.DeviceVH?, position: Int) {
        holder?.bindData(appslist[position], mActivity)
    }

    override fun getItemCount(): Int {
        println("Size == " + appslist.size)
        return appslist.size
    }

    inner class DeviceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(featureCamera: CameraFeatures, c: MainActivity) {

            val tvFeatureName: TextView = itemView.findViewById(R.id.tv_app_name)
            val tvFeatureValue: TextView = itemView.findViewById(R.id.tv_app_package_name)
            tvFeatureName.text = featureCamera.featureLable
            tvFeatureValue.text = featureCamera.featureValue
        }
    }
}
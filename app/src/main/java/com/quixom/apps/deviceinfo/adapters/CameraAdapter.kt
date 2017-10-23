package com.quixom.apps.deviceinfo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quixom.apps.deviceinfo.MainActivity
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.models.FeaturesHW

/**
 * Created by akif on 9/21/17.
 */

class CameraAdapter(internal var appslist: ArrayList<FeaturesHW>, internal var mActivity: MainActivity) : RecyclerView.Adapter<CameraAdapter.DeviceVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CameraAdapter.DeviceVH {
        val itemView = LayoutInflater.from(mActivity).inflate(R.layout.row_camera_item, parent, false)
        return DeviceVH(itemView)
    }

    override fun onBindViewHolder(holder: CameraAdapter.DeviceVH?, position: Int) {
        holder?.bindData(appslist[position])
        println("appList = " + appslist.size)
    }

    override fun getItemCount(): Int = appslist.size


    inner class DeviceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(featureHW: FeaturesHW) {

            val tvFeatureName: TextView = itemView.findViewById(R.id.tv_camera_feature_name)
            val tvFeatureValue: TextView = itemView.findViewById(R.id.tv_camera_feature_value)

            tvFeatureName.text = featureHW.featureLable
            tvFeatureValue.text = featureHW.featureValue
        }
    }
}
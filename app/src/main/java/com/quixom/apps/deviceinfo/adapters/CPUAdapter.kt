package com.quixom.apps.deviceinfo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quixom.apps.deviceinfo.MainActivity
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.models.CPUFeatures
/**
 * Created by akif on 9/21/17.
 */

class CPUAdapter(internal var appslist: ArrayList<CPUFeatures>, internal var mActivity: MainActivity) : RecyclerView.Adapter<CPUAdapter.DeviceVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CPUAdapter.DeviceVH {
        val itemView = LayoutInflater.from(mActivity).inflate(R.layout.row_cpu_item, parent, false)
        return DeviceVH(itemView)
    }

    override fun onBindViewHolder(holder: CPUAdapter.DeviceVH?, position: Int) {
        holder?.bindData(appslist[position], position)
    }

    override fun getItemCount(): Int = appslist.size

    inner class DeviceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(featureCpu: CPUFeatures, position: Int) {

            val tvFeatureName: TextView = itemView.findViewById(R.id.tv_cpu_feature_name)
            val tvFeatureValue: TextView = itemView.findViewById(R.id.tv_cpu_feature_value)
            val viewSeparator: View = itemView.findViewById(R.id.separatorView)

            tvFeatureName.text = featureCpu.featureLable
            tvFeatureValue.text = featureCpu.featureValue

            if (featureCpu.featureLable == mActivity.resources.getString(R.string.processor) && position > 0) {
                viewSeparator.visibility = View.VISIBLE
            } else {
                viewSeparator.visibility = View.GONE
            }
        }
    }
}
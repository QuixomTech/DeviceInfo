package com.quixom.apps.deviceinfo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quixom.apps.deviceinfo.MainActivity
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.models.SimInfo
import com.quixom.apps.deviceinfo.utilities.Methods

/**
 * Created by quixomtech on 1/2/18.
 */
class SimAdapter(private val mainActivity: MainActivity, private var simInformationData: ArrayList<SimInfo>): RecyclerView.Adapter<SimAdapter.SimVH>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimVH {
        val itemView = LayoutInflater.from(mainActivity).inflate(R.layout.row_sim_item, parent, false)
        return SimVH(itemView)
    }

    override fun getItemCount(): Int = simInformationData.size

    override fun onBindViewHolder(holder: SimVH?, position: Int) {
        holder?.bindData(simInformationData[position])
    }

    inner class SimVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(simInfo: SimInfo) {

            val tvLabel: TextView? = itemView.findViewById(R.id.tvLabel)
            val tvSimInformation: TextView? = itemView.findViewById(R.id.tvSimInformation)

            tvLabel?.text = simInfo.simLable
            tvSimInformation?.text = simInfo.simData

            itemView.setOnClickListener({
                Methods.avoidDoubleClicks(itemView)
            })
        }
    }
}
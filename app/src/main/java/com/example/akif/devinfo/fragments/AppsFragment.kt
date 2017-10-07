package com.example.akif.devinfo.fragments

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.akif.devinfo.R
import com.example.akif.devinfo.models.DeviceInfo
import com.example.akif.devinfo.utilities.KeyUtil
import android.support.v7.widget.LinearLayoutManager
import com.example.akif.devinfo.Adapters.DeviceAdapter


class AppsFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBackArrow: ImageView? = null
    var tvTitle: TextView? = null
    var rvAppsList: RecyclerView? = null

    var mode: Int? = 0

    companion object {
        fun getInstance(mode: Int): AppsFragment {
            val appsFragment = AppsFragment()
            val bundle = Bundle()
            bundle.putInt(KeyUtil.KEY_MODE, mode)
            appsFragment.arguments = bundle
            return appsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_apps, container, false)

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBackArrow = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        rvAppsList = view.findViewById(R.id.rv_apps_list)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        initToolbar()
        rvAppsList?.layoutManager = LinearLayoutManager(mActivity)
        rvAppsList?.hasFixedSize()
        initAppsList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    /**
     * Get data from bundle
     */
    private fun getBundleData() {
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(KeyUtil.KEY_MODE)) {
                mode = bundle.getInt(KeyUtil.KEY_MODE)
            }
        }
    }

    private fun initToolbar() {
        ivMenu?.visibility = View.VISIBLE
        ivBackArrow?.visibility = View.GONE
        if (mode?.equals(KeyUtil.IS_USER_COME_FROM_USER_APPS)!!) {
            tvTitle?.text = mResources.getString(R.string.user_apps)
        } else {
            tvTitle?.text = mResources.getString(R.string.system_and_apps)
        }

        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    private fun initAppsList() {

        val lists = ArrayList<DeviceInfo>()
        mActivity.appsList.filterTo(lists) { it.flags == mode }

        //creating our adapter
        val adapter = DeviceAdapter(mode, lists, mActivity)

        //now adding the adapter to RecyclerView
        rvAppsList?.adapter = adapter
    }
}
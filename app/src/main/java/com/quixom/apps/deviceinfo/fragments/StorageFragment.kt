package com.quixom.apps.deviceinfo.fragments

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.StatFs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.lzyzsd.circleprogress.DonutProgress
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.utilities.Methods
import java.text.DecimalFormat


class StorageFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var donutRAMUsage: DonutProgress? = null
    var donutInternalStorage: DonutProgress? = null
    var donutExternalStorage: DonutProgress? = null

    var tvUsedMemory: TextView? = null
    var tvFreeMemory: TextView? = null
    var tvTotalMemory: TextView? = null
    var tvUsedIntMemory: TextView? = null
    var tvFreeIntMemory: TextView? = null
    var tvTotalIntMemory: TextView? = null
    var tvUsedExtMemory: TextView? = null
    var tvFreeExtMemory: TextView? = null
    var tvTotalExtMemory: TextView? = null
    var llExtMemory: LinearLayout? = null
    val df = DecimalFormat("#")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_storage, container, false)

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)

        donutRAMUsage = view.findViewById(R.id.donut_ram_usage)
        donutInternalStorage = view.findViewById(R.id.donut_internal_storage)
        donutExternalStorage = view.findViewById(R.id.donut_external_storage)

        tvUsedMemory = view.findViewById(R.id.tv_used_memory)
        tvFreeMemory = view.findViewById(R.id.tv_free_memory)
        tvTotalMemory = view.findViewById(R.id.tv_total_memory)

        tvUsedIntMemory = view.findViewById(R.id.tv_used_intmemory)
        tvFreeIntMemory = view.findViewById(R.id.tv_free_intmemory)
        tvTotalIntMemory = view.findViewById(R.id.tv_total_intmemory)

        tvUsedExtMemory = view.findViewById(R.id.tv_used_extmemory)
        tvFreeExtMemory = view.findViewById(R.id.tv_free_extmemory)
        tvTotalExtMemory = view.findViewById(R.id.tv_total_extmemory)
        llExtMemory = view.findViewById(R.id.ll_ext_memory)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()

        // Init
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                setUpStorageDetails()
                handler.postDelayed(this, 1000)
            }
        }

        handler.postDelayed(runnable, 1000)
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
        tvTitle?.text = mResources.getString(R.string.storage)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpStorageDetails(){

        /** RAM Usage count */
        val totalRamValue = totalRamMemorySize()
        val freeRamValue = freeRamMemorySize()
        val usedRamValue = totalRamValue - freeRamValue

        tvUsedMemory?.text = mResources.getString(R.string.used_memory) + "\t" + formatSize(usedRamValue)
        tvFreeMemory?.text = mResources.getString(R.string.free) + "\t" + formatSize(freeRamValue)
        tvTotalMemory?.text = mResources.getString(R.string.total) + "\t" + formatSize(totalRamValue)

        donutRAMUsage?.progress = Methods.calculatePercentage(usedRamValue.toDouble(), totalRamValue.toDouble()).toFloat()

        /** Internal Memory usage */
        val totalInternalValue = getTotalInternalMemorySize()
        val freeInternalValue = getAvailableInternalMemorySize()
        val usedInternalValue = totalInternalValue - freeInternalValue
        tvUsedIntMemory?.text = mResources.getString(R.string.used_memory) + "\t" + formatSize(usedInternalValue)
        tvFreeIntMemory?.text = mResources.getString(R.string.free) + "\t" + formatSize(freeInternalValue)
        tvTotalIntMemory?.text = mResources.getString(R.string.total) + "\t" + formatSize(totalInternalValue)

        donutInternalStorage?.progress = Methods.calculatePercentage(usedInternalValue.toDouble(), totalInternalValue.toDouble()).toFloat()


//        val isSDPresent = android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
//          val isSDPresent = ContextCompat.getExternalFilesDirs(mActivity, null).size >= 2
        val isSDPresent = Environment.isExternalStorageRemovable()
        if (isSDPresent) {
            llExtMemory?.visibility = View.VISIBLE
            /** External Memory usage */
            val totalExternalValue = getTotalExternalMemorySize()
            val freeExternalValue = getAvailableExternalMemorySize()
            val usedExternalValue = totalExternalValue - freeExternalValue
            tvUsedExtMemory?.text = mResources.getString(R.string.used_memory) + "\t" + formatSize(usedExternalValue)
            tvFreeExtMemory?.text = mResources.getString(R.string.free) + "\t" + formatSize(freeExternalValue)
            tvTotalExtMemory?.text = mResources.getString(R.string.used) + "\t" + formatSize(totalExternalValue)

            donutExternalStorage?.progress = df.format(Methods.calculatePercentage(usedExternalValue.toDouble(), totalExternalValue.toDouble())).toFloat()
        } else {
            llExtMemory?.visibility = View.GONE
        }
    }

    private fun freeRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)

        return mi.availMem
    }

    private fun totalRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.totalMem
    }

    fun externalMemoryAvailable(): Boolean {
        return android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
    }

    fun getAvailableInternalMemorySize(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong()
        val availableBlocks = stat.availableBlocks.toLong()
        return availableBlocks * blockSize
    }

    fun getTotalInternalMemorySize(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong()
        val totalBlocks = stat.blockCount.toLong()
        return totalBlocks * blockSize
    }

    fun getAvailableExternalMemorySize(): Long {
        if (externalMemoryAvailable()) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            return availableBlocks * blockSize
        } else {
            return 0
        }
    }

    fun getTotalExternalMemorySize(): Long {
        return if (externalMemoryAvailable()) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            totalBlocks * blockSize
        } else {
            0
        }
    }

    private fun formatSize(size: Long): String {
        if (size <= 0)
            return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        println("digitGroups== " + digitGroups)
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    private fun returnToDecimalPlaces(values: Long): String {
        val df = DecimalFormat("#.00")
        return df.format(values)
    }
}
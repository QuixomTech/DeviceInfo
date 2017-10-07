package com.example.akif.devinfo.fragments

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.akif.devinfo.R
import com.github.lzyzsd.circleprogress.ArcProgress
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import android.widget.FrameLayout
import com.example.akif.devinfo.utilities.Methods
import org.jetbrains.anko.textColor
import java.io.*

class CPUFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var arcCpu: ArcProgress? = null
    var arcRAM: ArcProgress? = null
    var tvSystemAppsMemory: TextView? = null
    var tvAvailableRAM: TextView? = null
    var tvTotalRAMSpace: TextView? = null
    var tvCPUModel: TextView? = null
    var tvCores: TextView? = null
    var tvActualRAM: TextView? = null
    var tvPhysicalAvailableRAM: TextView? = null
    var tvAbi: TextView? = null
    var tvCPUVariant: TextView? = null
    var tvSerial: TextView? = null
    var tvCPUImplememter: TextView? = null
    var tvCPUPart: TextView? = null
    var tvCPURevision: TextView? = null
    var tvHardware: TextView? = null
    var tvFeatures: TextView? = null
    var flParent: FrameLayout? = null

    var processBuilder: ProcessBuilder? = null
    var Data: String = ""
//    var HOLDER = arrayOf("/proc/state", "r")
    var HOLDER = arrayOf("/system/bin/cat", "/proc/cpuinfo")
    var inputStream: InputStream? = null
    var process: Process? = null
    var byteArray = ByteArray(1024)


    var activityManager: ActivityManager? = null
    var memoryInfo: ActivityManager.MemoryInfo? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_cpu, container, false)

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        arcCpu = view.findViewById(R.id.arc_cpu)
        arcRAM = view.findViewById(R.id.arc_ram)
        tvSystemAppsMemory = view.findViewById(R.id.tv_system_apps_memory)
        tvAvailableRAM = view.findViewById(R.id.tv_available_ram)
        tvTotalRAMSpace = view.findViewById(R.id.tv_total_ram_space)
        tvCPUModel = view.findViewById(R.id.tv_cpu_model)
        tvCores = view.findViewById(R.id.tv_cores)
        tvActualRAM = view.findViewById(R.id.tv_actual_ram)
        tvPhysicalAvailableRAM = view.findViewById(R.id.tv_physical_available_ram)
        tvAbi = view.findViewById(R.id.tv_abi)
        tvCPUVariant = view.findViewById(R.id.tv_cpu_variant)
        tvSerial = view.findViewById(R.id.tv_serial)
        tvCPUImplememter = view.findViewById(R.id.tv_cpu_implementer)
        tvCPUPart = view.findViewById(R.id.tv_cpu_part)
        tvCPURevision = view.findViewById(R.id.tv_cpu_revision)
        tvHardware = view.findViewById(R.id.tv_hardware)
        tvFeatures = view.findViewById(R.id.tv_features)
        flParent = view.findViewById(R.id.fl_parent)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        getCPUInformation()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getMemoryInfo()
        }

        // Init
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val totalRamValue = totalRamMemorySize()
                val freeRamValue = freeRamMemorySize()
                val usedRamValue = totalRamValue - freeRamValue
                arcRAM?.progress = Methods.calculatePercentage(usedRamValue.toDouble(), totalRamValue.toDouble()).toInt()
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

    private fun initToolbar(): Unit {
        ivMenu?.visibility = View.VISIBLE
        ivBack?.visibility = View.GONE
        tvTitle?.text = mResources.getString(R.string.cpu_t)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun getMemoryInfo(): Unit {
        activityManager = mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        memoryInfo = ActivityManager.MemoryInfo()
        activityManager?.getMemoryInfo(memoryInfo)


        val freeMemory = memoryInfo?.availMem?.div(1048576L)
        val totalMemory = memoryInfo?.totalMem?.div(1048576L)
        val usedMemory = freeMemory?.let { totalMemory?.minus(it) }

        tvSystemAppsMemory?.text = Methods.getSpannableString(mActivity, mResources.getString(R.string.system_and_apps) + ":", usedMemory.toString() + " MB")
        tvAvailableRAM?.text = Methods.getSpannableString(mActivity, mResources.getString(R.string.available_ram) + ":", freeMemory.toString() + " MB")
        tvTotalRAMSpace?.text = Methods.getSpannableString(mActivity, mResources.getString(R.string.total_ram_space) + ":", totalMemory.toString() + " MB")
    }

    private fun getCPUInformation(): Unit {
        try {
            processBuilder = ProcessBuilder(*HOLDER)
            process = processBuilder!!.start()
            inputStream = process?.inputStream

            while (inputStream?.read(byteArray) != -1) {
                Data += String(byteArray)
                val textView = TextView(mActivity)
                textView.text = Data
                textView.textColor = mResources.getColor(R.color.primary_text)
                flParent?.addView(textView)

            }
            inputStream!!.close()

        } catch (e: Exception) {
            println(e.printStackTrace())
        }
    }



        private fun freeRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)

        return mi.availMem / 1048576L
    }

    private fun totalRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.totalMem / 1048576L
    }


    private fun readUsage(): Float {
        try {
            val reader = RandomAccessFile("/proc/stat", "r")
            var load = reader.readLine()

            var toks = load.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val idle1 = java.lang.Long.parseLong(toks[5])
            val cpu1 = (java.lang.Long.parseLong(toks[2]) + java.lang.Long.parseLong(toks[3]) + java.lang.Long.parseLong(toks[4])
                    + java.lang.Long.parseLong(toks[6]) + java.lang.Long.parseLong(toks[7]) + java.lang.Long.parseLong(toks[8]))

            try {
                Thread.sleep(360)
            } catch (e: Exception) {
            }

            reader.seek(0)
            load = reader.readLine()
            reader.close()

            toks = load.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val idle2 = java.lang.Long.parseLong(toks[5])
            val cpu2 = (java.lang.Long.parseLong(toks[2]) + java.lang.Long.parseLong(toks[3]) + java.lang.Long.parseLong(toks[4])
                    + java.lang.Long.parseLong(toks[6]) + java.lang.Long.parseLong(toks[7]) + java.lang.Long.parseLong(toks[8]))

            return (cpu2 - cpu1).toFloat() / (cpu2 + idle2 - (cpu1 + idle1))

        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return 0f
    }
}

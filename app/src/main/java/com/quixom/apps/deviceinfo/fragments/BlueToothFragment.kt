package com.quixom.apps.deviceinfo.fragments


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import com.quixom.apps.deviceinfo.R
import kotlinx.android.synthetic.main.fragment_blue_tooth.*
import kotlinx.android.synthetic.main.toolbar_ui.*


/**
 * A simple [Fragment] subclass.
 */
class BlueToothFragment : BaseFragment() {

    val REQUEST_ENABLE_BT = 1
    var mBluetoothAdapter: BluetoothAdapter? = null

    private val mbluetoothStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val action = intent?.action

            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        tv_bluetooth_state.text = mActivity.resources.getString(R.string.switch_off)
                        bluetoothAnimationView.visibility = View.GONE
                        bluetoothOnOff.isEnabled = true
                        bluetoothOnOff.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimary))
                    }
                    BluetoothAdapter.STATE_ON -> {
                        bluetoothAnimationView.visibility = View.VISIBLE
                        bluetoothOnOff.isEnabled = false
                        bluetoothOnOff.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.divider))
                    }
                }
            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_blue_tooth, container, false)
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.BluetoothTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_blue_tooth, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_blue_one)
            window.navigationBarColor = resources.getColor(R.color.dark_blue_one)

        }
        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()

        // Register broadcasts receiver for bluetooth state change
        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        mActivity.registerReceiver(mbluetoothStateReceiver, filter)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        getLocalBluetoothName()
        bluetoothOnOff.setOnClickListener(View.OnClickListener {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        })


    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity.unregisterReceiver(mbluetoothStateReceiver)
    }

    private fun initToolbar(): Unit {
        mActivity?.iv_menu?.visibility = View.VISIBLE
        mActivity?.iv_back?.visibility = View.GONE
        mActivity?.tv_title?.text = mResources.getString(R.string.bluetooth)
        mActivity?.iv_menu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bluetoothAnimationView.visibility = View.VISIBLE
            } else {
                bluetoothAnimationView.visibility = View.GONE
            }
        }

        if (mBluetoothAdapter?.isEnabled!!) {
            bluetoothOnOff.isEnabled = false
            bluetoothOnOff.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.divider))
        } else {
            bluetoothOnOff.isEnabled = true
            bluetoothOnOff.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.blueetooth_color))
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds", "WrongConstant")
    private fun getLocalBluetoothName() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            bluetoothOnOff.isEnabled = false
        }

        tv_bluetooth_name.text = mBluetoothAdapter?.name
        tv_bluetooth_address.text = mBluetoothAdapter?.address
        tv_bluetooth_scan_mode.text = mBluetoothAdapter?.scanMode.toString()

        if (mBluetoothAdapter?.isEnabled!!) {
            tv_bluetooth_state.text = mActivity.resources.getString(R.string.switch_on)
            bluetoothAnimationView.visibility = View.VISIBLE
            bluetoothOnOff.isEnabled = false
            bluetoothOnOff.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.divider))
        } else {
            bluetoothAnimationView.visibility = View.GONE
            tv_bluetooth_state.text = mActivity.resources.getString(R.string.switch_off)
            bluetoothOnOff.text = mActivity.resources.getString(R.string.turn_on_bluetooth)
            bluetoothOnOff.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.blueetooth_color))
        }

        if (mBluetoothAdapter!!.isDiscovering) {
        } else {
            tv_bluetooth_discovery.text = mActivity.resources.getString(R.string.switch_off)
        }
    }
}

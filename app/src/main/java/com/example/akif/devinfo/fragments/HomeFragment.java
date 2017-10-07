package com.example.akif.devinfo.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akif.devinfo.R;
import com.example.akif.devinfo.models.DeviceInfo;
import com.example.akif.devinfo.utilities.KeyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    Unbinder unbinder;
    int mode;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_manufacturer)
    TextView tvManufacturer;
    @BindView(R.id.tv_brand_name)
    TextView tvBrand;
    @BindView(R.id.tv_model_number)
    TextView tvModel;
    @BindView(R.id.tv_board)
    TextView tvBoard;
    @BindView(R.id.tv_hardware)
    TextView tvHardware;
    @BindView(R.id.tv_serial_no)
    TextView tvSerialNo;
    @BindView(R.id.tv_android_id)
    TextView tvAndroidId;
    @BindView(R.id.tv_screen_resolution)
    TextView tvScreenResolution;
    @BindView(R.id.tv_boot_loader)
    TextView tvBootLoader;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_host)
    TextView tvHost;

    public static HomeFragment getInstance(int mode) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KeyUtil.KEY_MODE, mode);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
        getBundleData();
        getDeviceInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isAdded()) {
            initToolbar();
        }
    }

    private void initToolbar() {
        ivMenu.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText(mResources.getString(R.string.device));
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.openDrawer();
            }
        });
    }

    private void getDeviceInfo() {
        tvManufacturer.setText("".concat(Build.MANUFACTURER));
        tvBrand.setText("".concat(Build.BRAND));
        tvModel.setText("".concat(Build.MODEL));
        tvBoard.setText("".concat(Build.BOARD));
        tvHardware.setText("".concat(Build.HARDWARE));

        tvSerialNo.setText("".concat(Build.SERIAL));

        @SuppressLint("HardwareIds") String androidID = (Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        tvAndroidId.setText("".concat(androidID));

        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        tvScreenResolution.setText("".concat(width + " * " + height + " " + "Pixels"));
        tvBootLoader.setText(Build.BOOTLOADER);
        tvHost.setText(Build.HOST);
        tvUser.setText(Build.USER);
    }

    /**
     * Get data from bundle
     */
    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(KeyUtil.KEY_MODE)) {
                mode = bundle.getInt(KeyUtil.KEY_MODE);
            }
        }
    }
}

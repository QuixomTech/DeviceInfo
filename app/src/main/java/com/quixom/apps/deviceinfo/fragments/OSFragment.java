package com.quixom.apps.deviceinfo.fragments;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quixom.apps.deviceinfo.R;
import com.quixom.apps.deviceinfo.utilities.Methods;

import java.lang.reflect.Array;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OSFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_os_ver_ic)
    ImageView ivOSVerIC;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_api_level)
    TextView tvApiLevel;
    @BindView(R.id.tv_build_id)
    TextView tvBuildId;
    @BindView(R.id.tv_build_time)
    TextView tvBuildTime;
    @BindView(R.id.tv_fingerprint)
    TextView tvFingerprint;
    @BindView(R.id.tv_sdk_name)
    TextView tvSdkName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_os, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
        getOSInfo();
    }

    private void initToolbar() {
        ivMenu.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText(mResources.getString(R.string.os));
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.openDrawer();
            }
        });
    }

    private void getOSInfo() {

        int CVersion = android.os.Build.VERSION.SDK_INT;
        tvApiLevel.setText("".concat(String.valueOf(Build.VERSION.SDK_INT)));
        tvVersion.setText("".concat(Build.VERSION.RELEASE));
        tvBuildId.setText("".concat(Build.ID));
        tvBuildTime.setText("".concat(Methods.getDate(Build.TIME)));
        tvFingerprint.setText("".concat(Build.FINGERPRINT));

        tvSdkName.setText(Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName());
        switch (CVersion) {
            case 11:
                tvVersionName.setText(mResources.getString(R.string.honeycomb).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("February 22, 2011"));
                break;

            case 12:
                tvVersionName.setText(mResources.getString(R.string.honeycomb).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("May 10, 2011"));
                break;

            case 13:
                tvVersionName.setText(mResources.getString(R.string.honeycomb).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("July 15, 2011"));
                break;

            case 14:
                tvVersionName.setText(mResources.getString(R.string.ics).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("October 18, 2011"));
                break;

            case 15:
                tvVersionName.setText(mResources.getString(R.string.ics).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("November 28, 2011"));
                break;

            case 16:
                tvVersionName.setText(mResources.getString(R.string.jellybean).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("July 9, 2012"));
                break;

            case 17:
                tvVersionName.setText(mResources.getString(R.string.jellybean).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("November 13, 2012"));
                break;

            case 18:
                tvVersionName.setText(mResources.getString(R.string.jellybean).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("July 24, 2013"));
                break;

            case 19:
                tvVersionName.setText(mResources.getString(R.string.kitkat).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("October 31, 2013"));
                break;

            case 21:
                tvVersionName.setText(mResources.getString(R.string.lollipop).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("November 12, 2014"));
                break;

            case 22:
                tvVersionName.setText(mResources.getString(R.string.lollipop).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("March 9, 2015"));
                break;

            case 23:
                tvVersionName.setText(mResources.getString(R.string.marshmallow).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("October 5, 2015"));
                break;

            case 24:
                tvVersionName.setText(mResources.getString(R.string.nougat).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("August 22, 2016"));
                break;

            case 25:
                tvVersionName.setText(mResources.getString(R.string.nougat).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("October 4, 2016"));
                break;
            case 26:
                tvVersionName.setText(mResources.getString(R.string.oreo).concat(" ".concat(String.valueOf(Build.VERSION.RELEASE))));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("August 21, 2017"));
                break;
            default:
                tvVersionName.setText(mResources.getString(R.string.unknown_version));
                tvReleaseDate.setText(mResources.getString(R.string.release_date).concat("-"));
                break;
        }


//        deviceInfoList.get(0).setBuildRelease(Build.VERSION.RELEASE);
//        deviceInfoList.get(0).setDisplay(Build.DISPLAY);
//        deviceInfoList.get(0).setFingerprint(Build.FINGERPRINT);
//        deviceInfoList.get(0).setBuildId(Build.ID);
//        deviceInfoList.get(0).setTime(String.valueOf(Build.TIME));
//        deviceInfoList.get(0).setType(Build.TYPE);
//        deviceInfoList.get(0).setUser(Build.USER);
//        deviceInfoList.get(0).setVersion(String.valueOf(Build.VERSION.SDK_INT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Assumes every value type has a reasonable toString()
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private String formatCameraCharacteristics(CameraCharacteristics info) {
        String infoText;
        if (info != null) {
            StringBuilder infoBuilder = new StringBuilder(
                    "Camera characteristics:\n\n");

            for (CameraCharacteristics.Key<?> key : info.getKeys()) {
                infoBuilder.append(String.format(Locale.US, "%s:  ",
                        key.getName()));

                Object val = info.get(key);
                if (val.getClass().isArray()) {
                    // Iterate an array-type value
                    // Assumes camera characteristics won't have arrays of arrays as values
                    int len = Array.getLength(val);
                    infoBuilder.append("[ ");
                    for (int i = 0; i < len; i++) {
                        infoBuilder.append(String.format(Locale.US, "%s%s",
                                Array.get(val, i), (i + 1 == len) ? ""
                                        : ", "));
                    }
                    infoBuilder.append(" ]\n\n");
                } else {
                    // Single value
                    infoBuilder.append(String.format(Locale.US, "%s\n\n",
                            val.toString()));
                }
            }
            infoText = infoBuilder.toString();
        } else {
            infoText = "No info";
        }
        return infoText;
    }

    private void getCameraDetails() {

    }
}

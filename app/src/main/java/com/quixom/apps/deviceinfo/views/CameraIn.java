package com.quixom.apps.deviceinfo.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.params.BlackLevelPattern;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.util.SizeF;

import com.quixom.apps.deviceinfo.MainActivity;
import com.quixom.apps.deviceinfo.fragments.BaseFragment;

/**
 * Created by akif on 10/17/17.
 */

public class CameraIn extends BaseFragment{
    private static final String BUNDLE_CAMERA_ID = "camera_id";

    public static CameraIn newInstance(int cameraId) {
        CameraIn fragment = new CameraIn();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_CAMERA_ID, cameraId);
        fragment.setArguments(args);
        return fragment;
    }

    public CameraIn() { }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void getCameraInfoNew(MainActivity mainActivity) {
        CameraManager cameraManager = (CameraManager) mainActivity.getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics("1");
            int[] b = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES);
            int[] c = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
            int[] d = characteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);

            for (int aB : b) {
                if (aB == CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_FAST) {
                    System.out.println("Aberration mode fast");
                } else if (aB == CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY) {
                    System.out.println("Aberration mode high quality");
                } else {
                    System.out.println("Aberration mode off");
                }
            }

            for (int aC: c) {
                if (aC == CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_OFF) {
                    System.out.println("Video stabilization Off");
                } else {
                    System.out.println("Video stabilization On");
                }
            }

            for (int ad: d) {
                if (ad == CameraMetadata.CONTROL_EFFECT_MODE_AQUA) {
                    System.out.println("AQUA");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_BLACKBOARD) {
                    System.out.println("BACKBOARD");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_MONO) {
                    System.out.println("MONO");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_NEGATIVE) {
                    System.out.println("NEGATIVE");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_POSTERIZE) {
                    System.out.println("POSTERIZE");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_SEPIA) {
                    System.out.println("SEPIA");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_SOLARIZE) {
                    System.out.println("SOLARIZE");
                } else if (ad == CameraMetadata.CONTROL_EFFECT_MODE_WHITEBOARD) {
                    System.out.println("WHITEBOARD");
                } else {
                    System.out.println("MODE OFF");
                }
            }

            float[] lens= characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
            if (lens != null) {
                for (float len: lens) {
                    if (len == CameraMetadata.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_APPROXIMATE) {
                        System.out.println("Calibration Approximate");
                    } else if (len == CameraMetadata.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_CALIBRATED) {
                        System.out.println("celebration calibrated");
                    } else {
                        System.out.println("celebration un-calibrated");
                    }
                }
            }

            SizeF size = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            BlackLevelPattern pattern = characteristics.get(CameraCharacteristics.SENSOR_BLACK_LEVEL_PATTERN);
            ColorSpaceTransform calibrationTransform1 = characteristics.get(CameraCharacteristics.SENSOR_CALIBRATION_TRANSFORM1);
            StreamConfigurationMap streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            System.out.println("size = " + size);
            System.out.println("pattern = " + pattern);
            System.out.println("calibrationTransform1 = " + calibrationTransform1);
            System.out.println("streamConfigurationMap = " + streamConfigurationMap);


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}

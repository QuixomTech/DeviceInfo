package com.quixom.apps.deviceinfo.utilities;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by akif on 4/10/2017.
 */
public class Camera2Utility {

    private Context m_Context = null;
    private boolean m_bEnableLog = false;
    public static final String szTag = "Camera2Utility";
    private CameraManager m_CameraManager = null;
    private ArrayList<DeviceCamera> m_CameraList = null;
    private StringBuilder m_szDeviceCameraSummary = null;

    public CameraManager getCameraManager() {
        return m_CameraManager;
    }

    public Camera2Utility(Context context, boolean bEnableLoging) {
        m_bEnableLog = bEnableLoging;

        m_Context = context;

        m_CameraList = new ArrayList<DeviceCamera>();

        m_szDeviceCameraSummary = new StringBuilder();

        m_szDeviceCameraSummary.setLength(0);
    }

    public void enableLoging(boolean bLog) {
        m_bEnableLog = bLog;
    }

    public void log(String szString) {
        if (m_bEnableLog) {
            Log.d(szTag, szString);
        }
    }

    public void addToDeviceCameraSummary(String szString) {
        m_szDeviceCameraSummary.append(szString);
    }

    public String getDeviceCameraSummary() {
        return m_szDeviceCameraSummary.toString();
    }

    public boolean init() {
        boolean bResult = m_Context != null;

        String[] cameraIds = null;

        if (bResult) {
            m_CameraManager = (CameraManager) m_Context.getSystemService(Context.CAMERA_SERVICE);

            bResult = (m_CameraManager != null);
        }

        if (bResult) {
            try {
                cameraIds = m_CameraManager.getCameraIdList();
            } catch (CameraAccessException e) {
                log("getCameraIdList failed");

                e.printStackTrace();

                bResult = false;
            }

        }

        if (bResult) {
            this.addToDeviceCameraSummary(String.format("Found %d Cameras\n\n", cameraIds.length));

            for (String id : cameraIds) {
                DeviceCamera camera = new DeviceCamera(id, this);

                m_CameraList.add(camera);
            }

        }

        return bResult;
    }

    public void unint() {
        for (DeviceCamera deviceCamera : m_CameraList) {
            deviceCamera.unInit();

            deviceCamera = null;
        }

        m_CameraList.clear();

        m_Context = null;

        m_bEnableLog = false;

        m_CameraManager = null;

    }
}

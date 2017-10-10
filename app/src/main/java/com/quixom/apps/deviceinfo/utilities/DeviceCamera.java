package com.quixom.apps.deviceinfo.utilities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Size;

import java.util.ArrayList;

/**
 * Created by akif on 4/10/2017.
 */
public class DeviceCamera
{

    private boolean m_bFlashSupported = false;
    private Size[] m_VideoPreviewSizes = null;
    private String m_szCameraID = "";
    private Camera2Utility m_Camera2Utility = null;
    private StreamConfigurationMap m_StreamConfigurationMap = null;
    private CameraCharacteristics m_CameraCharacteristics = null;
    private ArrayList<CameraImageFormat> m_CameraImageFormatArrayList = null;


    private class CameraImageFormat
    {
        private int m_iFormatNumber = 0;
        private String m_szFormatName = "";
        private Size[] m_SupportedImageSize = null;


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        CameraImageFormat(int iFormat, StreamConfigurationMap cStreamConfigurationMap)
        {
            m_szFormatName = new String(getFormatNameFromIndex(iFormat));

            m_iFormatNumber = iFormat;

            m_SupportedImageSize = cStreamConfigurationMap.getOutputSizes(iFormat);
        }

        public String getFormatName()
        {
            return m_szFormatName;
        }

        public int getTotalSizesSupported()
        {
            return m_SupportedImageSize.length;
        }

        public Size getSizeAt(int iIndex)
        {
            return m_SupportedImageSize[iIndex];
        }

        public int getFormatIndex()
        {
            return m_iFormatNumber;
        }

        private String getFormatNameFromIndex(int iIndex)
        {
            String szFormatName="";

            switch(iIndex)
            {
                case ImageFormat.NV16:
                {
                    szFormatName = "NV16";
                    break;
                }

                case ImageFormat.NV21:
                {
                    szFormatName = "NV21";
                    break;
                }

                case ImageFormat.YUY2:
                {
                    szFormatName = "YUY2";
                    break;
                }

                case ImageFormat.JPEG:
                {
                    szFormatName = "JPEG";
                    break;
                }

                case ImageFormat.YUV_420_888:
                {
                    szFormatName = "YUV_420_888";
                    break;
                }

                case ImageFormat.YUV_422_888:
                {
                    szFormatName = "YUV_422_888";
                    break;
                }

                case ImageFormat.YUV_444_888:
                {
                    szFormatName = "YUV_444_888";
                    break;
                }

                case ImageFormat.FLEX_RGB_888:
                {
                    szFormatName = "FLEX_RGB_888";
                    break;
                }

                case ImageFormat.FLEX_RGBA_8888:
                {
                    szFormatName = "FLEX_RGBA_8888";
                    break;
                }

                case ImageFormat.RAW_SENSOR:
                {
                    szFormatName = "RAW_SENSOR";
                    break;
                }

                case ImageFormat.RAW10:
                {
                    szFormatName = "RAW10";
                    break;
                }

                case ImageFormat.RAW12:
                {
                    szFormatName = "RAW12";
                    break;
                }

                case ImageFormat.DEPTH16:
                {
                    szFormatName = "DEPTH16";
                    break;
                }

                case ImageFormat.DEPTH_POINT_CLOUD:
                {
                    szFormatName = "DEPTH_POINT_CLOUD";
                    break;
                }

                case ImageFormat.UNKNOWN:
                {
                    szFormatName = "UNKNOWN";
                    break;
                }


                default:
                {
                    szFormatName = "UNKNOWN";
                    break;
                }

            }


            return szFormatName;
        }

        public void unit()
        {

        }

    }


    public DeviceCamera(String szCameraID, Camera2Utility camera2Utility)
    {
        m_szCameraID = szCameraID;

        m_Camera2Utility = camera2Utility;

        m_CameraImageFormatArrayList = new ArrayList<CameraImageFormat>();

        if( m_Camera2Utility!=null )
        {
            init();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected boolean init()
    {
        boolean bResult = m_Camera2Utility!=null;

        if(bResult)
        {
            try
            {
                m_CameraCharacteristics = m_Camera2Utility.getCameraManager().getCameraCharacteristics(m_szCameraID);
            }
            catch (CameraAccessException e)
            {
                m_Camera2Utility.log(String.format("getCameraCharacteristics Failed"));

                e.printStackTrace();

                bResult = false;
            }
        }

        if(bResult)
        {
            m_StreamConfigurationMap =  m_CameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            bResult = (m_StreamConfigurationMap != null);
        }


        if( bResult )
        {
            m_bFlashSupported = m_CameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        }

        if(bResult)
        {
            m_VideoPreviewSizes = m_StreamConfigurationMap.getOutputSizes(SurfaceTexture.class);
        }

        if(bResult)
        {
            int[] iSupportedFormats  = m_StreamConfigurationMap.getOutputFormats();

            for(int iFormat : iSupportedFormats)
            {
                CameraImageFormat cameraImageFormat = new CameraImageFormat(iFormat,m_StreamConfigurationMap);

                m_CameraImageFormatArrayList.add(cameraImageFormat);

            }
        }

        if(bResult)
        {
            dumpCameraInfo();
        }


        return bResult;
    }

    @SuppressLint("DefaultLocale")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void dumpCameraInfo()
    {
        m_Camera2Utility.log(String.format("--------------------------------"));
        m_Camera2Utility.log(String.format("Camera Info for Camera ID = %s" , m_szCameraID));

        m_Camera2Utility.log(String.format("Flash Supported = %s " , this.isFlashSupported() ? "Yes" : "No"));

        m_Camera2Utility.addToDeviceCameraSummary(String.format("Camera(%s)\n" , this.m_szCameraID));
        m_Camera2Utility.addToDeviceCameraSummary(String.format("Flash %s\n" , this.isFlashSupported() ? "Supported" : "Not Supprted"));

        for(Size size : m_VideoPreviewSizes )
        {
            m_Camera2Utility.log(String.format("Video Preview Supported = %dX%d " , size.getWidth() , size.getHeight()));
            m_Camera2Utility.addToDeviceCameraSummary(String.format("Video Preview Size (%dX%d)\n" , size.getWidth() , size.getHeight()));

        }

        for(CameraImageFormat cameraImageFormat :  m_CameraImageFormatArrayList)
        {
            m_Camera2Utility.log(String.format("Image Name = %S(%d) " , cameraImageFormat.getFormatName() , cameraImageFormat.getFormatIndex()));

            for(int i = 0; i < cameraImageFormat.getTotalSizesSupported(); i++)
            {
                m_Camera2Utility.log(String.format("Image Name = %S(%d) supported %d X %d" , cameraImageFormat.getFormatName() , cameraImageFormat.getFormatIndex(), cameraImageFormat.getSizeAt(i).getWidth(),cameraImageFormat.getSizeAt(i).getHeight()));

                m_Camera2Utility.addToDeviceCameraSummary(String.format("Still Image %S(%d X %d)\n" , cameraImageFormat.getFormatName() ,  cameraImageFormat.getSizeAt(i).getWidth(),cameraImageFormat.getSizeAt(i).getHeight()));
            }

        }
        {

        }

        m_Camera2Utility.addToDeviceCameraSummary(String.format("\n"));
        m_Camera2Utility.log(String.format("--------------------------------"));
    }

    public boolean isFlashSupported()
    {
        return m_bFlashSupported;
    }

    public void unInit()
    {
        for(CameraImageFormat cCameraImageFormat : m_CameraImageFormatArrayList )
        {
            cCameraImageFormat.unit();
        }

        m_CameraImageFormatArrayList.clear();

        m_CameraImageFormatArrayList = null;

        m_Camera2Utility = null;

        m_CameraCharacteristics = null;

        m_StreamConfigurationMap = null;

    }
}

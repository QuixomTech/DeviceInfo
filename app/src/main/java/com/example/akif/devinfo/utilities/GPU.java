package com.example.akif.devinfo.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.widget.FrameLayout;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by akif on 10/9/17.
 */

public class GPU {

        // region OpenGLGles10

        public static class OpenGLGles10Info extends OpenGLInfo {

            // general
            public String GL_RENDERER;
            public String GL_VERSION;
            public String GL_VENDOR;

            // texture related
            public int GL_MAX_TEXTURE_SIZE;
            public int GL_MAX_TEXTURE_UNITS;
            public int[] GL_MAX_VIEWPORT_DIMS;

            // fixed function pipeline constrains
            public int GL_MAX_MODELVIEW_STACK_DEPTH;
            public int GL_MAX_PROJECTION_STACK_DEPTH;
            public int GL_MAX_TEXTURE_STACK_DEPTH;
            public int GL_MAX_LIGHTS;
            public int GL_SUBPIXEL_BITS;
            public int GL_DEPTH_BITS;
            public int GL_STENCIL_BITS;
            public int GL_MAX_ELEMENTS_INDICES;
            public int GL_MAX_ELEMENTS_VERTICES;

            // extensions
            public String GL_EXTENSIONS;

            protected OpenGLGles10Info() {
                super(1);
            }

            @Override
            protected void loadOnCreate() {
                GL_RENDERER = glGetString(GLES10.GL_RENDERER);
                GL_VERSION = glGetString(GLES10.GL_VERSION);
                GL_VENDOR = glGetString(GLES10.GL_VENDOR);
                GL_MAX_TEXTURE_SIZE = glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE);
                GL_MAX_TEXTURE_UNITS = glGetIntegerv(GLES10.GL_MAX_TEXTURE_UNITS);
                GL_MAX_LIGHTS = glGetIntegerv(GLES10.GL_MAX_LIGHTS);
                GL_SUBPIXEL_BITS = glGetIntegerv(GLES10.GL_SUBPIXEL_BITS);
                GL_MAX_ELEMENTS_VERTICES = glGetIntegerv(GLES10.GL_MAX_ELEMENTS_VERTICES);
                GL_MAX_ELEMENTS_INDICES = glGetIntegerv(GLES10.GL_MAX_ELEMENTS_INDICES);
                GL_MAX_MODELVIEW_STACK_DEPTH = glGetIntegerv(GLES10.GL_MAX_MODELVIEW_STACK_DEPTH);
                GL_MAX_PROJECTION_STACK_DEPTH = glGetIntegerv(GLES10.GL_MAX_PROJECTION_STACK_DEPTH);
                GL_MAX_TEXTURE_STACK_DEPTH = glGetIntegerv(GLES10.GL_MAX_TEXTURE_STACK_DEPTH);
                GL_DEPTH_BITS = glGetIntegerv(GLES10.GL_DEPTH_BITS);
                GL_STENCIL_BITS = glGetIntegerv(GLES10.GL_STENCIL_BITS);
                GL_EXTENSIONS = glGetString(GLES10.GL_EXTENSIONS);
                GL_MAX_VIEWPORT_DIMS = glGetIntegerv(GLES10.GL_MAX_VIEWPORT_DIMS, 2);
            }

            @Override
            public String toString() {
                return "OpenGLGles10Info{" + '\n' +
                        "GL_RENDERER='" + GL_RENDERER + '\'' + '\n' +
                        ", GL_VERSION='" + GL_VERSION + '\'' + '\n' +
                        ", GL_VENDOR='" + GL_VENDOR + '\'' + '\n' +
                        ", GL_MAX_TEXTURE_SIZE=" + GL_MAX_TEXTURE_SIZE + '\n' +
                        ", GL_MAX_TEXTURE_UNITS=" + GL_MAX_TEXTURE_UNITS + '\n' +
                        ", GL_MAX_VIEWPORT_DIMS=" + Arrays.toString(GL_MAX_VIEWPORT_DIMS) + '\n' +
                        ", GL_MAX_MODELVIEW_STACK_DEPTH=" + GL_MAX_MODELVIEW_STACK_DEPTH + '\n' +
                        ", GL_MAX_PROJECTION_STACK_DEPTH=" + GL_MAX_PROJECTION_STACK_DEPTH + '\n' +
                        ", GL_MAX_TEXTURE_STACK_DEPTH=" + GL_MAX_TEXTURE_STACK_DEPTH +
                        ", GL_MAX_LIGHTS=" + GL_MAX_LIGHTS + '\n' +
                        ", GL_SUBPIXEL_BITS=" + GL_SUBPIXEL_BITS + '\n' +
                        ", GL_DEPTH_BITS=" + GL_DEPTH_BITS + '\n' +
                        ", GL_STENCIL_BITS=" + GL_STENCIL_BITS + '\n' +
                        ", GL_MAX_ELEMENTS_INDICES=" + GL_MAX_ELEMENTS_INDICES + '\n' +
                        ", GL_MAX_ELEMENTS_VERTICES=" + GL_MAX_ELEMENTS_VERTICES + '\n' +
                        ", GL_EXTENSIONS='" + GL_EXTENSIONS + '\'' + '\n' +
                        '}';
            }
        }

        // endregion

        // region OpenGLGles20

        public static class OpenGLGles20Info extends OpenGLInfo {

            // general
            public String GL_RENDERER;
            public String GL_VERSION;
            public String GL_VENDOR;
            public String GL_SHADING_LANGUAGE_VERSION;

            // texture related
            public int GL_MAX_TEXTURE_SIZE;
            public int GL_MAX_TEXTURE_UNITS;
            public int GL_MAX_TEXTURE_IMAGE_UNITS;
            public int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS;
            public int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
            public int GL_MAX_CUBE_MAP_TEXTURE_SIZE;
            public int[] GL_MAX_VIEWPORT_DIMS;
            public int GL_MAX_RENDERBUFFER_SIZE;
            public boolean Vertex_Texture_Fetch;

            // shader constrains
            public int GL_MAX_VERTEX_UNIFORM_VECTORS;
            public int GL_MAX_VERTEX_ATTRIBS;
            public int GL_MAX_VARYING_VECTORS;
            public int GL_MAX_FRAGMENT_UNIFORM_VECTORS;

            // extensions
            public String GL_EXTENSIONS;

            // precision [] { -range, range, precision }
            public int[] GL_VERTEX_SHADER_GL_LOW_INT;
            public int[] GL_VERTEX_SHADER_GL_MEDIUM_INT;
            public int[] GL_VERTEX_SHADER_GL_HIGH_INT;
            public int[] GL_VERTEX_SHADER_GL_LOW_FLOAT;
            public int[] GL_VERTEX_SHADER_GL_MEDIUM_FLOAT;
            public int[] GL_VERTEX_SHADER_GL_HIGH_FLOAT;

            public int[] GL_FRAGMENT_SHADER_GL_LOW_INT;
            public int[] GL_FRAGMENT_SHADER_GL_MEDIUM_INT;
            public int[] GL_FRAGMENT_SHADER_GL_HIGH_INT;
            public int[] GL_FRAGMENT_SHADER_GL_LOW_FLOAT;
            public int[] GL_FRAGMENT_SHADER_GL_MEDIUM_FLOAT;
            public int[] GL_FRAGMENT_SHADER_GL_HIGH_FLOAT;

            protected OpenGLGles20Info() {
                super(GPU.supportsOpenGLES2() ? 2 : 1);
            }

            @Override
            protected void loadOnCreate() {

                GL_RENDERER = glGetString(GLES10.GL_RENDERER);
                GL_VERSION = glGetString(GLES10.GL_VERSION);
                GL_VENDOR = glGetString(GLES10.GL_VENDOR);
                GL_SHADING_LANGUAGE_VERSION = glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION);

                GL_MAX_TEXTURE_SIZE = glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE);
                GL_MAX_TEXTURE_UNITS = glGetIntegerv(GLES10.GL_MAX_TEXTURE_UNITS);
                GL_MAX_VERTEX_ATTRIBS = glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS);
                GL_MAX_VERTEX_UNIFORM_VECTORS = glGetIntegerv(GLES20.GL_MAX_VERTEX_UNIFORM_VECTORS);
                GL_MAX_FRAGMENT_UNIFORM_VECTORS = glGetIntegerv(GLES20.GL_MAX_FRAGMENT_UNIFORM_VECTORS);
                GL_MAX_VARYING_VECTORS = glGetIntegerv(GLES20.GL_MAX_VARYING_VECTORS);
                Vertex_Texture_Fetch = isVTFSupported();
                GL_MAX_TEXTURE_IMAGE_UNITS = glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS);
                GL_MAX_VIEWPORT_DIMS = glGetIntegerv(GLES10.GL_MAX_VIEWPORT_DIMS, 2);
                GL_EXTENSIONS = glGetString(GLES10.GL_EXTENSIONS);
                GL_MAX_RENDERBUFFER_SIZE = glGetIntegerv(GLES20.GL_MAX_RENDERBUFFER_SIZE);
                GL_MAX_CUBE_MAP_TEXTURE_SIZE = glGetIntegerv(GLES20.GL_MAX_CUBE_MAP_TEXTURE_SIZE);
                GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = glGetIntegerv(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
                GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = glGetIntegerv(GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS);

                GL_VERTEX_SHADER_GL_LOW_INT = glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER, GLES20.GL_LOW_INT);
                GL_VERTEX_SHADER_GL_MEDIUM_INT = glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER, GLES20.GL_MEDIUM_INT);
                GL_VERTEX_SHADER_GL_HIGH_INT = glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER, GLES20.GL_HIGH_INT);

                GL_VERTEX_SHADER_GL_LOW_FLOAT = glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER, GLES20.GL_LOW_FLOAT);
                GL_VERTEX_SHADER_GL_MEDIUM_FLOAT = glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER, GLES20.GL_MEDIUM_FLOAT);
                GL_VERTEX_SHADER_GL_HIGH_FLOAT = glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER, GLES20.GL_HIGH_FLOAT);

                GL_FRAGMENT_SHADER_GL_LOW_INT = glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER, GLES20.GL_LOW_INT);
                GL_FRAGMENT_SHADER_GL_MEDIUM_INT = glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER, GLES20.GL_MEDIUM_INT);
                GL_FRAGMENT_SHADER_GL_HIGH_INT = glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER, GLES20.GL_HIGH_INT);

                GL_FRAGMENT_SHADER_GL_LOW_FLOAT = glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER, GLES20.GL_LOW_FLOAT);
                GL_FRAGMENT_SHADER_GL_MEDIUM_FLOAT = glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER, GLES20.GL_MEDIUM_FLOAT);
                GL_FRAGMENT_SHADER_GL_HIGH_FLOAT = glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER, GLES20.GL_HIGH_FLOAT);
            }

            @Override
            public String toString() {
                return "OpenGLGles20Info{" +
                        "GL_RENDERER='" + GL_RENDERER + '\'' +
                        ", GL_VERSION='" + GL_VERSION + '\'' +
                        ", GL_VENDOR='" + GL_VENDOR + '\'' +
                        ", GL_SHADING_LANGUAGE_VERSION='" + GL_SHADING_LANGUAGE_VERSION + '\'' +
                        ", GL_MAX_TEXTURE_SIZE=" + GL_MAX_TEXTURE_SIZE +
                        ", GL_MAX_TEXTURE_UNITS=" + GL_MAX_TEXTURE_UNITS +
                        ", GL_MAX_TEXTURE_IMAGE_UNITS=" + GL_MAX_TEXTURE_IMAGE_UNITS +
                        ", GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS=" + GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS +
                        ", GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS=" + GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS +
                        ", GL_MAX_CUBE_MAP_TEXTURE_SIZE=" + GL_MAX_CUBE_MAP_TEXTURE_SIZE +
                        ", GL_MAX_VIEWPORT_DIMS=" + Arrays.toString(GL_MAX_VIEWPORT_DIMS) +
                        ", GL_MAX_RENDERBUFFER_SIZE=" + GL_MAX_RENDERBUFFER_SIZE +
                        ", Vertex_Texture_Fetch=" + Vertex_Texture_Fetch +
                        ", GL_MAX_VERTEX_UNIFORM_VECTORS=" + GL_MAX_VERTEX_UNIFORM_VECTORS +
                        ", GL_MAX_VERTEX_ATTRIBS=" + GL_MAX_VERTEX_ATTRIBS +
                        ", GL_MAX_VARYING_VECTORS=" + GL_MAX_VARYING_VECTORS +
                        ", GL_MAX_FRAGMENT_UNIFORM_VECTORS=" + GL_MAX_FRAGMENT_UNIFORM_VECTORS +
                        ", GL_EXTENSIONS='" + GL_EXTENSIONS + '\'' +
                        ", GL_VERTEX_SHADER_GL_LOW_INT=" + Arrays.toString(GL_VERTEX_SHADER_GL_LOW_INT) +
                        ", GL_VERTEX_SHADER_GL_MEDIUM_INT=" + Arrays.toString(GL_VERTEX_SHADER_GL_MEDIUM_INT) +
                        ", GL_VERTEX_SHADER_GL_HIGH_INT=" + Arrays.toString(GL_VERTEX_SHADER_GL_HIGH_INT) +
                        ", GL_VERTEX_SHADER_GL_LOW_FLOAT=" + Arrays.toString(GL_VERTEX_SHADER_GL_LOW_FLOAT) +
                        ", GL_VERTEX_SHADER_GL_MEDIUM_FLOAT=" + Arrays.toString(GL_VERTEX_SHADER_GL_MEDIUM_FLOAT) +
                        ", GL_VERTEX_SHADER_GL_HIGH_FLOAT=" + Arrays.toString(GL_VERTEX_SHADER_GL_HIGH_FLOAT) +
                        ", GL_FRAGMENT_SHADER_GL_LOW_INT=" + Arrays.toString(GL_FRAGMENT_SHADER_GL_LOW_INT) +
                        ", GL_FRAGMENT_SHADER_GL_MEDIUM_INT=" + Arrays.toString(GL_FRAGMENT_SHADER_GL_MEDIUM_INT) +
                        ", GL_FRAGMENT_SHADER_GL_HIGH_INT=" + Arrays.toString(GL_FRAGMENT_SHADER_GL_HIGH_INT) +
                        ", GL_FRAGMENT_SHADER_GL_LOW_FLOAT=" + Arrays.toString(GL_FRAGMENT_SHADER_GL_LOW_FLOAT) +
                        ", GL_FRAGMENT_SHADER_GL_MEDIUM_FLOAT=" + Arrays.toString(GL_FRAGMENT_SHADER_GL_MEDIUM_FLOAT) +
                        ", GL_FRAGMENT_SHADER_GL_HIGH_FLOAT=" + Arrays.toString(GL_FRAGMENT_SHADER_GL_HIGH_FLOAT) +
                        '}';
            }
        }

        @SuppressLint("StaticFieldLeak")
        private static Activity context;
        private volatile static IntBuffer buffer = IntBuffer.allocate(1);
        private volatile static IntBuffer buffer2 = IntBuffer.allocate(2);
        private volatile static int[] arrayBuffer = new int[1];

        public GPU(final Activity context) {
            GPU.context = context;
        }

        public synchronized static String glGetString(int value) {
            return GLES10.glGetString(value);
        }

        public synchronized static int glGetIntegerv(int value) {
            buffer.clear();
            GLES10.glGetIntegerv(value, buffer);
            return buffer.get(0);
        }

        public synchronized static int[] glGetIntegerv(int value, int size) {
            final IntBuffer buffer = IntBuffer.allocate(size);
            GLES10.glGetIntegerv(value, buffer);
            return buffer.array();
        }

        public synchronized static int[] glGetShaderPrecisionFormat(int shaderType, int precisionType) {
            return new int[]{buffer2.get(0), buffer2.get(1), buffer.get(0)};
        }

        public synchronized static int eglGetConfigAttrib(int eglType, final EGL10 egl, final EGLDisplay display, final EGLConfig eglConfig) {
            egl.eglGetConfigAttrib(display, eglConfig, eglType, arrayBuffer);
            return arrayBuffer[0];
        }

        public static boolean isVTFSupported() {
            GLES10.glGetIntegerv(GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, arrayBuffer, 0);
            return arrayBuffer[0] != 0;
        }

        private static int getOpenGLVersion() {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            return configurationInfo.reqGlEsVersion;
        }

        private static boolean supportsOpenGLES2() {
            return getOpenGLVersion() >= 0x20000;
        }

        // endregion

        // region GPU

        public void loadOpenGLGles10Info(final OnCompleteCallback<OpenGLGles10Info> callback) {
            new InfoLoader<OpenGLGles10Info>(new OpenGLGles10Info()).loadInfo(callback);
        }

        public void loadOpenGLGles20Info(final OnCompleteCallback<OpenGLGles20Info> callback) {
            new InfoLoader<OpenGLGles20Info>(new OpenGLGles20Info()).loadInfo(callback);
        }

        // endregion

        // region interfaces

        public interface OnCompleteCallback<T> {
            void onComplete(final T result);
        }

        private static abstract class OpenGLInfo {

            final int eGLContextClientVersion;
            public ArrayList<Egl> eglconfigs;

            protected OpenGLInfo(final int eGLContextClientVersion) {
                this.eGLContextClientVersion = eGLContextClientVersion;
                eglconfigs = new ArrayList<Egl>();
            }

            /**
             * Basically run this in a successfully running GLSurfaceView.Renderer.onSurfaceCreated.
             * Guaranties valid OpenGL context.
             */
            protected abstract void loadOnCreate();
        }

        // endregion

        // region InfoLoader

        public static class InfoLoader<T extends OpenGLInfo> {

            private volatile GPURenderer<T> renderer;
            private T info;

            protected InfoLoader(final T info) {
                this.info = info;
            }

            private void loadInfo(final OnCompleteCallback<T> callback) {

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // new view
                        final GLSurfaceView glView = new GLSurfaceView(context);

                        // egl info
                        glView.setEGLConfigChooser(new EglChooser<T>(info));

                        // need to be on top to be rendered at least for one frame
                        glView.setZOrderOnTop(true);

                        // create new renderer; note: we only need the oncreate method for all the infos
                        renderer = new GPURenderer<T>(glView, info, callback);

                        // set opengl version
                        glView.setEGLContextClientVersion(info.eGLContextClientVersion);

                        // set renderer
                        glView.setRenderer(renderer);

                        // add opengl view to current active view
                        final FrameLayout layout = (FrameLayout) context.findViewById(android.R.id.content); // Note: needs to be layout of current active view
                        layout.addView(glView);
                    }
                });
            }
        }

        // endregion

        // region GPURenderer

        private static class GPURenderer<T extends OpenGLInfo> implements GLSurfaceView.Renderer {

            private GLSurfaceView glSurfaceView;
            private T result;
            private OnCompleteCallback<T> callback;

            protected GPURenderer(final GLSurfaceView glSurfaceView, final T result, final OnCompleteCallback<T> callback) {
                this.result = result;
                this.glSurfaceView = glSurfaceView;
                this.callback = callback;
            }

            @Override
            public void onSurfaceCreated(final GL10 gl, final EGLConfig eglConfig) {

                // loadOnCreate info
                result.loadOnCreate();

                // remove view
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        final FrameLayout layout = (FrameLayout) context.findViewById(android.R.id.content);
                        layout.removeView(glSurfaceView);

                        // call callback
                        if (callback != null) callback.onComplete(result);
                    }
                });
            }

            @Override
            public void onSurfaceChanged(final GL10 gl10, final int width, final int height) {
                // do nothing
            }

            @Override
            public void onDrawFrame(final GL10 gl) {
                // do nothing
            }
        }

        final private static class EglChooser<T extends OpenGLInfo> implements GLSurfaceView.EGLConfigChooser {

            public T info;

            private EglChooser(final T info) {
                this.info = info;
            }

            @Override
            public EGLConfig chooseConfig(final EGL10 egl, final EGLDisplay display) {

                //Querying number of configurations
                final int[] num_conf = new int[1];
                egl.eglGetConfigs(display, null, 0, num_conf); //if configuration array is null it still returns the number of configurations
                final int configurations = num_conf[0];

                //Querying actual configurations
                final EGLConfig[] conf = new EGLConfig[configurations];
                egl.eglGetConfigs(display, conf, configurations, num_conf);

                EGLConfig result = null;

                for (int i = 0; i < configurations; i++) {
                    result = better(result, conf[i], egl, display);

                    final Egl config = new Egl(egl, display, conf[i]);
                    if (config.EGL_RED_SIZE + config.EGL_BLUE_SIZE + config.EGL_GREEN_SIZE + config.EGL_ALPHA_SIZE >= 16)
                        info.eglconfigs.add(config);
                }

                return result;
            }

            /**
             * Returns the best of the two EGLConfig passed according to depth and colours
             *
             * @param a The first candidate
             * @param b The second candidate
             * @return The chosen candidate
             */
            private EGLConfig better(EGLConfig a, EGLConfig b, EGL10 egl, EGLDisplay display) {
                if (a == null) return b;

                EGLConfig result;

                int[] value = new int[1];

                egl.eglGetConfigAttrib(display, a, EGL10.EGL_DEPTH_SIZE, value);
                int depthA = value[0];

                egl.eglGetConfigAttrib(display, b, EGL10.EGL_DEPTH_SIZE, value);
                int depthB = value[0];

                if (depthA > depthB)
                    result = a;
                else if (depthA < depthB)
                    result = b;
                else //if depthA == depthB
                {
                    egl.eglGetConfigAttrib(display, a, EGL10.EGL_RED_SIZE, value);
                    int redA = value[0];

                    egl.eglGetConfigAttrib(display, b, EGL10.EGL_RED_SIZE, value);
                    int redB = value[0];

                    if (redA > redB)
                        result = a;
                    else if (redA < redB)
                        result = b;
                    else //if redA == redB
                    {
                        //Don't care
                        result = a;
                    }
                }

                return result;
            }
        }

        public final static class Egl {

            public final int EGL_NON_CONFORMANT_CONFIG;
            public final int EGL_SAMPLES;
            public final int EGL_STENCIL_SIZE;
            public final int EGL_DEPTH_SIZE;
            public final int EGL_ALPHA_SIZE;
            public final int EGL_BLUE_SIZE;
            public final int EGL_GREEN_SIZE;
            public final int EGL_RED_SIZE;

            public Egl(final EGL10 egl, final EGLDisplay display, final EGLConfig eglConfig) {
                EGL_RED_SIZE = eglGetConfigAttrib(EGL10.EGL_RED_SIZE, egl, display, eglConfig);
                EGL_BLUE_SIZE = eglGetConfigAttrib(EGL10.EGL_BLUE_SIZE, egl, display, eglConfig);
                EGL_GREEN_SIZE = eglGetConfigAttrib(EGL10.EGL_GREEN_SIZE, egl, display, eglConfig);
                EGL_ALPHA_SIZE = eglGetConfigAttrib(EGL10.EGL_ALPHA_SIZE, egl, display, eglConfig);
                EGL_DEPTH_SIZE = eglGetConfigAttrib(EGL10.EGL_DEPTH_SIZE, egl, display, eglConfig);
                EGL_STENCIL_SIZE = eglGetConfigAttrib(EGL10.EGL_STENCIL_SIZE, egl, display, eglConfig);
                EGL_SAMPLES = eglGetConfigAttrib(EGL10.EGL_SAMPLES, egl, display, eglConfig);
                EGL_NON_CONFORMANT_CONFIG = eglGetConfigAttrib(EGL10.EGL_NON_CONFORMANT_CONFIG, egl, display, eglConfig);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Egl egl = (Egl) o;

                if (EGL_ALPHA_SIZE != egl.EGL_ALPHA_SIZE) return false;
                if (EGL_BLUE_SIZE != egl.EGL_BLUE_SIZE) return false;
                if (EGL_DEPTH_SIZE != egl.EGL_DEPTH_SIZE) return false;
                if (EGL_GREEN_SIZE != egl.EGL_GREEN_SIZE) return false;
                if (EGL_NON_CONFORMANT_CONFIG != egl.EGL_NON_CONFORMANT_CONFIG) return false;
                if (EGL_RED_SIZE != egl.EGL_RED_SIZE) return false;
                if (EGL_SAMPLES != egl.EGL_SAMPLES) return false;
                if (EGL_STENCIL_SIZE != egl.EGL_STENCIL_SIZE) return false;

                return true;
            }

            @Override
            public int hashCode() {
                int result = EGL_NON_CONFORMANT_CONFIG;
                result = 31 * result + EGL_SAMPLES;
                result = 31 * result + EGL_STENCIL_SIZE;
                result = 31 * result + EGL_DEPTH_SIZE;
                result = 31 * result + EGL_ALPHA_SIZE;
                result = 31 * result + EGL_BLUE_SIZE;
                result = 31 * result + EGL_GREEN_SIZE;
                result = 31 * result + EGL_RED_SIZE;
                return result;
            }

            @Override
            public String toString() {

                // rgba (rgba) depth stencil samples non comfort
                return "RGB" + (EGL_ALPHA_SIZE > 0 ? "A" : "") +
                        " " + (EGL_RED_SIZE + EGL_GREEN_SIZE + EGL_BLUE_SIZE + EGL_ALPHA_SIZE) + " bit" +
                        " (" + EGL_RED_SIZE + "" + EGL_GREEN_SIZE + EGL_BLUE_SIZE + "" + (EGL_ALPHA_SIZE > 0 ? EGL_ALPHA_SIZE : "") + ")" +
                        (EGL_DEPTH_SIZE > 0 ? " Depth " + EGL_DEPTH_SIZE + "bit" : "") +
                        (EGL_STENCIL_SIZE > 0 ? " Stencil " + EGL_STENCIL_SIZE : "") +
                        (EGL_SAMPLES > 0 ? " Samples x" + EGL_SAMPLES : "") +
                        (EGL_NON_CONFORMANT_CONFIG > 0 ? " Non-Conformant" : "");
            }
        }

        // endregion
    }
// }

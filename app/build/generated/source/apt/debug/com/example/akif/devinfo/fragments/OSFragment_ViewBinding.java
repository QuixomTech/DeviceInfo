// Generated code from Butter Knife. Do not modify!
package com.example.akif.devinfo.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.akif.devinfo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OSFragment_ViewBinding implements Unbinder {
  private OSFragment target;

  @UiThread
  public OSFragment_ViewBinding(OSFragment target, View source) {
    this.target = target;

    target.ivMenu = Utils.findRequiredViewAsType(source, R.id.iv_menu, "field 'ivMenu'", ImageView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.ivOSVerIC = Utils.findRequiredViewAsType(source, R.id.iv_os_ver_ic, "field 'ivOSVerIC'", ImageView.class);
    target.tvVersionName = Utils.findRequiredViewAsType(source, R.id.tv_version_name, "field 'tvVersionName'", TextView.class);
    target.tvReleaseDate = Utils.findRequiredViewAsType(source, R.id.tv_release_date, "field 'tvReleaseDate'", TextView.class);
    target.tvVersion = Utils.findRequiredViewAsType(source, R.id.tv_version, "field 'tvVersion'", TextView.class);
    target.tvApiLevel = Utils.findRequiredViewAsType(source, R.id.tv_api_level, "field 'tvApiLevel'", TextView.class);
    target.tvBuildId = Utils.findRequiredViewAsType(source, R.id.tv_build_id, "field 'tvBuildId'", TextView.class);
    target.tvBuildTime = Utils.findRequiredViewAsType(source, R.id.tv_build_time, "field 'tvBuildTime'", TextView.class);
    target.tvFingerprint = Utils.findRequiredViewAsType(source, R.id.tv_fingerprint, "field 'tvFingerprint'", TextView.class);
    target.tvSdkName = Utils.findRequiredViewAsType(source, R.id.tv_sdk_name, "field 'tvSdkName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OSFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivMenu = null;
    target.ivBack = null;
    target.tvTitle = null;
    target.toolbar = null;
    target.ivOSVerIC = null;
    target.tvVersionName = null;
    target.tvReleaseDate = null;
    target.tvVersion = null;
    target.tvApiLevel = null;
    target.tvBuildId = null;
    target.tvBuildTime = null;
    target.tvFingerprint = null;
    target.tvSdkName = null;
  }
}

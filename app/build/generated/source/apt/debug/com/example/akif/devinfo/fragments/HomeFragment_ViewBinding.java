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

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  @UiThread
  public HomeFragment_ViewBinding(HomeFragment target, View source) {
    this.target = target;

    target.ivMenu = Utils.findRequiredViewAsType(source, R.id.iv_menu, "field 'ivMenu'", ImageView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tvManufacturer = Utils.findRequiredViewAsType(source, R.id.tv_manufacturer, "field 'tvManufacturer'", TextView.class);
    target.tvBrand = Utils.findRequiredViewAsType(source, R.id.tv_brand_name, "field 'tvBrand'", TextView.class);
    target.tvModel = Utils.findRequiredViewAsType(source, R.id.tv_model_number, "field 'tvModel'", TextView.class);
    target.tvBoard = Utils.findRequiredViewAsType(source, R.id.tv_board, "field 'tvBoard'", TextView.class);
    target.tvHardware = Utils.findRequiredViewAsType(source, R.id.tv_hardware, "field 'tvHardware'", TextView.class);
    target.tvSerialNo = Utils.findRequiredViewAsType(source, R.id.tv_serial_no, "field 'tvSerialNo'", TextView.class);
    target.tvAndroidId = Utils.findRequiredViewAsType(source, R.id.tv_android_id, "field 'tvAndroidId'", TextView.class);
    target.tvScreenResolution = Utils.findRequiredViewAsType(source, R.id.tv_screen_resolution, "field 'tvScreenResolution'", TextView.class);
    target.tvBootLoader = Utils.findRequiredViewAsType(source, R.id.tv_boot_loader, "field 'tvBootLoader'", TextView.class);
    target.tvUser = Utils.findRequiredViewAsType(source, R.id.tv_user, "field 'tvUser'", TextView.class);
    target.tvHost = Utils.findRequiredViewAsType(source, R.id.tv_host, "field 'tvHost'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivMenu = null;
    target.ivBack = null;
    target.tvTitle = null;
    target.toolbar = null;
    target.tvManufacturer = null;
    target.tvBrand = null;
    target.tvModel = null;
    target.tvBoard = null;
    target.tvHardware = null;
    target.tvSerialNo = null;
    target.tvAndroidId = null;
    target.tvScreenResolution = null;
    target.tvBootLoader = null;
    target.tvUser = null;
    target.tvHost = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.example.akif.devinfo.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.akif.devinfo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BatteryFragment_ViewBinding implements Unbinder {
  private BatteryFragment target;

  @UiThread
  public BatteryFragment_ViewBinding(BatteryFragment target, View source) {
    this.target = target;

    target.ivMenu = Utils.findRequiredViewAsType(source, R.id.iv_menu, "field 'ivMenu'", ImageView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.llTop = Utils.findRequiredViewAsType(source, R.id.ll_top, "field 'llTop'", LinearLayout.class);
    target.tvBatteryType = Utils.findRequiredViewAsType(source, R.id.tv_battery_type, "field 'tvBatteryType'", TextView.class);
    target.tvPowerSource = Utils.findRequiredViewAsType(source, R.id.tv_power_source, "field 'tvPowerSource'", TextView.class);
    target.tvBatteryTemperature = Utils.findRequiredViewAsType(source, R.id.tv_battery_temperature, "field 'tvBatteryTemperature'", TextView.class);
    target.tvBatteryVoltage = Utils.findRequiredViewAsType(source, R.id.tv_battery_voltage, "field 'tvBatteryVoltage'", TextView.class);
    target.tvBatteryScale = Utils.findRequiredViewAsType(source, R.id.tv_battery_scale, "field 'tvBatteryScale'", TextView.class);
    target.tvBatteryHealth = Utils.findRequiredViewAsType(source, R.id.tv_battery_health, "field 'tvBatteryHealth'", TextView.class);
    target.fabBatteryCharging = Utils.findRequiredViewAsType(source, R.id.fab_battery_charging, "field 'fabBatteryCharging'", FloatingActionButton.class);
    target.tvBatteryLevel = Utils.findRequiredViewAsType(source, R.id.tv_battery_level, "field 'tvBatteryLevel'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BatteryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivMenu = null;
    target.ivBack = null;
    target.tvTitle = null;
    target.toolbar = null;
    target.progressBar = null;
    target.llTop = null;
    target.tvBatteryType = null;
    target.tvPowerSource = null;
    target.tvBatteryTemperature = null;
    target.tvBatteryVoltage = null;
    target.tvBatteryScale = null;
    target.tvBatteryHealth = null;
    target.fabBatteryCharging = null;
    target.tvBatteryLevel = null;
  }
}

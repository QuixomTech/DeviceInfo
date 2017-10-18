package com.quixom.apps.deviceinfo.utilities

import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.quixom.apps.deviceinfo.MainActivity

/**
 * Created by akif on 10/13/17.
 */
class RateUsApp {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    companion object {
        fun rateUsApp(mActivity: MainActivity) {
            val uri = Uri.parse("market://details?id=com.quixom.deviceinfo")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                mActivity.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                mActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.quixom.deviceinfo")))
            }
        }
    }
}
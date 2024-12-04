package com.muhia.damaris.interview.weatherapp.utils

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muhia.damaris.interview.weatherapp.R
import com.muhia.damaris.interview.weatherapp.WeatherApplication
import com.muhia.damaris.interview.weatherapp.utils.Constants.getRootFunction
import com.scottyab.rootbeer.RootBeer

fun checkRooted(): Boolean {
    val checker: Int = getRootFunction
    return checker == 1
}

fun isDeviceSecured(): Boolean {
    val keyguardManager = WeatherApplication.applicationContext()?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager //api 16+
    return keyguardManager.isDeviceSecure   //Return whether the keyguard is secured by a PIN, pattern or password or a SIM card is currently locked.
}
fun isVM(): Boolean {
    val radioVersion = Build.getRadioVersion()
    return radioVersion == null || radioVersion.isEmpty() || radioVersion == "1.0.0.0"
}



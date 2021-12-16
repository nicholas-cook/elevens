package com.souvenotes.elevens.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdSize

fun Fragment.getAdSize(): AdSize {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager =
            requireContext().getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        if (windowManager != null) {
            val widthPixels = windowManager.currentWindowMetrics.bounds.width()
            val dpi = requireContext().resources.configuration.densityDpi.toDouble()
            val density = dpi / DisplayMetrics.DENSITY_DEFAULT

            val adWidth = (widthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                requireContext(),
                adWidth
            )
        }
        return AdSize.BANNER
    } else if (requireContext() is Activity) {
        val display = (requireContext() as Activity).windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val widthPixels = outMetrics.widthPixels
        val density = outMetrics.density

        val adWidth = (widthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(), adWidth)
    }
    return AdSize.BANNER
}
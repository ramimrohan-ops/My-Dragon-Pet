package com.ramim.homedragon

import android.content.Context
import android.graphics.RectF

/** Icon positions found by the accessibility service, shared with the overlay (same process). */
object IconRegistry {
    @Volatile var icons: List<RectF> = emptyList()      // screen pixels
    @Volatile var onHome: Boolean = true                // launcher is the foreground app
    @Volatile var serviceActive: Boolean = false        // accessibility service connected
    @Volatile var launcherPkg: String? = null
    var listener: (() -> Unit)? = null                  // always called on the main thread
    var swipeListener: (() -> Unit)? = null             // launcher page is scrolling sideways (main thread)
}

object Prefs {
    private fun sp(c: Context) = c.getSharedPreferences("dragon", Context.MODE_PRIVATE)

    /** Every slider moves in steps of 10%: older saved values snap to the nearest step. */
    private fun snap(v: Int, lo: Int, hi: Int) = (((v + 5) / 10) * 10).coerceIn(lo, hi)

    fun scalePct(c: Context) = snap(sp(c).getInt("scale", 100), 50, 150)          // dragon size, 50..150
    fun speedPct(c: Context) = snap(sp(c).getInt("speed", 100), 50, 150)          // dragon speed, 50..150
    fun qualityPct(c: Context) = snap(sp(c).getInt("quality", 100), 10, 100)      // frame rate, 10..100
    fun particlePct(c: Context) = snap(sp(c).getInt("particles", 100), 10, 100)   // fire, smoke and sparks, 10..100
    fun cols(c: Context) = sp(c).getInt("cols", 4)
    fun rows(c: Context) = sp(c).getInt("rows", 6)
    fun enabled(c: Context) = sp(c).getBoolean("enabled", false)

    fun setScalePct(c: Context, v: Int) = sp(c).edit().putInt("scale", v).apply()
    fun setSpeedPct(c: Context, v: Int) = sp(c).edit().putInt("speed", v).apply()
    fun setQualityPct(c: Context, v: Int) = sp(c).edit().putInt("quality", v).apply()
    fun setParticlePct(c: Context, v: Int) = sp(c).edit().putInt("particles", v).apply()
    fun setCols(c: Context, v: Int) = sp(c).edit().putInt("cols", v).apply()
    fun setRows(c: Context, v: Int) = sp(c).edit().putInt("rows", v).apply()
    fun setEnabled(c: Context, v: Boolean) = sp(c).edit().putBoolean("enabled", v).apply()
}

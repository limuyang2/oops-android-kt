package io.nichijou.oops.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import io.nichijou.oops.Oops
import io.nichijou.oops.OopsLiveProvider
import io.nichijou.oops.ext.ctx
import io.nichijou.oops.utils.EdgeGlowUtil


class OopsViewPager(context: Context, @Nullable attrs: AttributeSet) : ViewPager(context, attrs), OopsLiveProvider {

    init {
        registerOopsLive()
    }

    private var live: LiveData<Int>? = null
    override fun registerOopsLive() {
        val ctx = this.ctx()
        live = Oops.live(ctx, Oops.oops::colorAccent)
        live!!.observe(ctx, Observer {
            EdgeGlowUtil.setEdgeGlowColor(this, it)
        })
    }

    override fun unregisterOopsLive() {
        live?.removeObservers(this.ctx())
        live = null
    }

    override fun onDetachedFromWindow() {
        unregisterOopsLive()
        super.onDetachedFromWindow()
    }
}
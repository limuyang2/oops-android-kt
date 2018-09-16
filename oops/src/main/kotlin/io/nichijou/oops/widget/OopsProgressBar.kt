package io.nichijou.oops.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.nichijou.oops.Oops
import io.nichijou.oops.OopsLiveProvider
import io.nichijou.oops.ext.ctx
import io.nichijou.oops.ext.resId
import io.nichijou.oops.ext.tint

class OopsProgressBar : ProgressBar, OopsLiveProvider {

    private val attrs: AttributeSet?

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        this.attrs = attrs
        registerOopsLive()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.attrs = attrs
        registerOopsLive()
    }

    private var live: LiveData<Int>? = null

    override fun registerOopsLive() {
        val ctx = this.ctx()
        val backgroundResId = ctx.resId(attrs, android.R.attr.background)
        live = Oops.liveColor(ctx, backgroundResId, Oops.live(ctx, Oops.oops::colorAccent))
        live!!.observe(ctx, Observer(this::tint))
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
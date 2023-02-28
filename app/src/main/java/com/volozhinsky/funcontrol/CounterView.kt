package com.volozhinsky.funcontrol

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet
    ? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private var countTrace = CounterTrace.LOW
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 300.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 300.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = Color.BLACK
    }
    private var countUp: Boolean = false
    private var counter: Int = 0

    init {
        isClickable = true
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DialView,
            0, 0
        ).apply {
            try {
                countUp = getBoolean(R.styleable.CounterView_count_up, false)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = when (countTrace) {
            CounterTrace.LOW -> Color.GREEN
            CounterTrace.MEDIUM -> Color.YELLOW
            CounterTrace.HIGH -> Color.RED
        }
        val stateIndex = when (countTrace) {
            CounterTrace.LOW -> 0.5f
            CounterTrace.MEDIUM -> 0.7f
            CounterTrace.HIGH -> 1f
        }
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius * stateIndex, paint)
        canvas.drawText(
            counter.toString(),
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            paintText
        )
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        if (countUp) {
            counter += 1
        } else {
            counter -= 1
        }
        if (counter % 10 == 0 && counter <= 20 && counter >= -20 && counter !=0) {
            if (countUp) {
                countTrace = countTrace.next()
            } else {
                countTrace = countTrace.previous()
            }
        }
        invalidate()
        return true
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (Integer.min(width, height) / 2.0 * 0.8).toFloat()
    }

    fun inverseCountUp() {
        countUp = !countUp
    }

    private enum class CounterTrace() {
        LOW(),
        MEDIUM(),
        HIGH();

        fun next() = when (this) {
            LOW -> MEDIUM
            MEDIUM -> HIGH
            HIGH -> LOW
        }

        fun previous() = when (this) {
            LOW -> HIGH
            MEDIUM -> LOW
            HIGH -> MEDIUM
        }
    }
}
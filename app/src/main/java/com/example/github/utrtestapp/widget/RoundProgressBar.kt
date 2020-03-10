package com.example.github.utrtestapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.example.github.utrtestapp.R

class RoundProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint: Paint


    private val roundColor: Int

    private val roundProgressColor: Int


    private val textColor: Int

    private val textSize: Float

    private val roundWidth: Float


    private val max: Int

    private var progress: Int = 0

    var isSpinning = false

    private val spinHandler = object : Handler() {
        /**
         * This is the code that will increment the progress variable
         * and so spin the wheel
         */
        override fun handleMessage(msg: Message) {
            invalidate()
            if (isSpinning) {
                progress += 10
                if (progress > 360) {
                    progress = 0
                }
                this.sendEmptyMessageDelayed(0, 0)
            }
            //super.handleMessage(msg);
        }
    }

    init {

        paint = Paint()

        val mTypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundProgressBar
        )

        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_round_color, Color.RED)
        roundProgressColor =
            mTypedArray.getColor(R.styleable.RoundProgressBar_round_progressColor, Color.GREEN)
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_round_textColor, Color.GREEN)
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_round_textsize, 15f)
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_round_width, 5f)
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_round_max, 360)

        mTypedArray.recycle()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val centre = width / 2
        val radius = (centre - roundWidth / 2).toInt()
        paint.color = roundColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = roundWidth
        paint.isAntiAlias = true


        if (isSpinning) {

            paint.strokeWidth = roundWidth
            paint.color = roundProgressColor
            val oval = RectF(
                (centre - radius).toFloat(),
                (centre - radius).toFloat(),
                (centre + radius).toFloat(),
                (centre + radius).toFloat()
            )
            paint.style = Paint.Style.STROKE
            canvas.drawArc(oval, (progress - 90).toFloat(), 320f, false, paint)
        } else {

            paint.strokeWidth = roundWidth
            paint.color = roundProgressColor
            val oval = RectF(
                (centre - radius).toFloat(),
                (centre - radius).toFloat(),
                (centre + radius).toFloat(),
                (centre + radius).toFloat()
            )
            paint.style = Paint.Style.STROKE
            canvas.drawArc(oval, -90f, progress.toFloat(), false, paint)
        }


    }

    @Synchronized
    fun setProgress(progress: Int) {
        var progress = progress
        require(progress >= 0) { "progress not less than 0" }
        if (progress > max) {
            progress = max
        }
        if (progress <= max) {
            this.progress = progress
            postInvalidate()
        }

    }





}
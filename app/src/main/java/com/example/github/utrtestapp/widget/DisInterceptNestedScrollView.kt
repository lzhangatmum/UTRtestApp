package com.example.github.utrtestapp.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

//Used by subclasses to prevent parent classes from intercepting subclass events
class DisInterceptNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :NestedScrollView(context,attrs) {
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.getAction()) {
            MotionEvent.ACTION_MOVE -> requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> requestDisallowInterceptTouchEvent(
                false
            )
        }
        return super.onTouchEvent(ev)
    }
}
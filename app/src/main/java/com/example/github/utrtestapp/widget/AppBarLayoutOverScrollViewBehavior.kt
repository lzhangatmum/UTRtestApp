package com.example.github.utrtestapp.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import java.util.jar.Attributes


/**
 * Picture zoom back
 * Personal information layout top and botoom follow the picture displacement
 * toolbar background discoloration
 */
class AppBarLayoutOverScrollViewBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
    ) :AppBarLayout.Behavior(context,attrs){
    val TAG = "overScroll"
    val TAG_TOOLBAR = "toolbar"
    val TAG_MIDDLE = "middle"
    val TARGET_HEIGHT = 1500f
    var mTargetView: View? = null
    var mParentHeight: Int = 0
    var mTargetViewHeight: Int = 0
    var mTotalDy: Float = 0.toFloat()
    var mLastScale: Float = 0.toFloat()
    var mLastBottom: Int = 0
    var isAnimate: Boolean = false
    var mToolBar: Toolbar? = null
    var middleLayout: ViewGroup? = null//personal info
    var mMiddleHeight: Int = 0
    var isRecovering = false//zoom back
    private var onProgressChangeListener: OnProgressChangeListener? = null


    val MAX_REFRESH_LIMIT = 0.3f//Reach the drop-down threshold and start refreshing the animation



    override fun onLayoutChild(
        parent: CoordinatorLayout,
        abl: AppBarLayout,
        layoutDirection: Int
    ): Boolean {
        val handled = super.onLayoutChild(parent, abl, layoutDirection)
        if (mToolBar == null) {
            mToolBar = parent.findViewWithTag(TAG_TOOLBAR) as Toolbar
        }
        if (middleLayout == null) {
            middleLayout = parent.findViewWithTag(TAG_MIDDLE) as ViewGroup
        }
        if (mTargetView == null) {
            mTargetView = parent.findViewWithTag(TAG)
            if (mTargetView != null) {
                initial(abl)
            }
        }
        abl.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
                mToolBar!!.setAlpha(Math.abs(i).toFloat() / appBarLayout.totalScrollRange)
            }
        })
        return handled
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        isAnimate = true
        return if (target is DisInterceptNestedScrollView) true //this is middleLayout
        else super.onStartNestedScroll(
            parent,
            child,
            directTargetChild,
            target,
            nestedScrollAxes,
            type
        )
    }
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (!isRecovering) {
            if (mTargetView != null && (dy < 0 && child.bottom >= mParentHeight || dy > 0 && child.bottom > mParentHeight)) {
                scale(child, target, dy)
                return
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }


    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (velocityY > 100) {//当y速度>100,就秒弹回
            isAnimate = false
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        recovery(abl)
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }


    fun initial(abl: AppBarLayout) {
        abl.clipChildren = false
        mParentHeight = abl.height
        mTargetViewHeight = mTargetView!!.getHeight()
        mMiddleHeight = middleLayout!!.getHeight()
    }

    fun scale(
        abl: AppBarLayout,
        target: View,
        dy: Int
    ) {
        mTotalDy += (-dy).toFloat()
        mTotalDy = Math.min(mTotalDy, TARGET_HEIGHT)
        mLastScale = Math.max(1f, 1f + mTotalDy / TARGET_HEIGHT)
        ViewCompat.setScaleX(mTargetView, mLastScale)
        ViewCompat.setScaleY(mTargetView, mLastScale)
        mLastBottom = mParentHeight + (mTargetViewHeight / 2 * (mLastScale - 1)).toInt()
        abl.setBottom(mLastBottom)
        target.scrollY = 0

        middleLayout!!.setTop(mLastBottom - mMiddleHeight)
        middleLayout!!.setBottom(mLastBottom)

        if (onProgressChangeListener != null) {
            val progress = Math.min((mLastScale - 1) / MAX_REFRESH_LIMIT, 1f)
            onProgressChangeListener!!.onProgressChange(progress, false)
        }
    }

    fun  recovery(abl: AppBarLayout) {
        if (isRecovering) return
        if (mTotalDy > 0) {
            isRecovering = true
            mTotalDy = 0f
            if (isAnimate) {
                val anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200);
                anim.addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    ViewCompat.setScaleX(mTargetView, value)
                    ViewCompat.setScaleY(mTargetView, value)
                    abl.bottom =
                        (mLastBottom - (mLastBottom - mParentHeight) * animation.animatedFraction).toInt()
                    middleLayout!!.setTop(
                        (mLastBottom.toFloat() -
                                (mLastBottom - mParentHeight) * animation.animatedFraction - mMiddleHeight.toFloat()).toInt()
                    )

                    if (onProgressChangeListener != null) {
                        val progress = Math.min((value - 1) / MAX_REFRESH_LIMIT, 1f)
                        onProgressChangeListener!!.onProgressChange(progress, true)
                    }
                }
                anim.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        isRecovering = false
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
                anim.start()
            } else run {
                ViewCompat.setScaleX(mTargetView, 1f)
                ViewCompat.setScaleY(mTargetView, 1f)
                abl.bottom = mParentHeight
                middleLayout!!.setTop(mParentHeight - mMiddleHeight)
                //                middleLayout.setBottom(mParentHeight);
                isRecovering = false

                if (onProgressChangeListener != null)
                    onProgressChangeListener!!.onProgressChange(0f, true)
            }

        }
    }

     interface OnProgressChangeListener {
        fun onProgressChange(progress: Float, isRelease: Boolean)
    }

    fun setOnProgressChangeListener(onProgressChangeListener: OnProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener
    }

}
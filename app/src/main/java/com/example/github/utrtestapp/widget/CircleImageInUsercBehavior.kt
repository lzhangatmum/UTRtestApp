package com.example.github.utrtestapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

class CircleImageInUsercBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<CircleImageView>() {
    private val TAG_TOOLBAR = "toolbar"

    private var mStartAvatarY: Float = 0.toFloat()

    private var mStartAvatarX: Float = 0.toFloat()

    private var mAvatarMaxHeight: Int = 0

    private var mToolBarHeight: Int = 0

    private var mStartDependencyY: Float = 0.toFloat()



    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: CircleImageView,
        dependency: View
    ): Boolean {
        return dependency is DisInterceptNestedScrollView
    }


    //当dependency变化的时候调用
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: CircleImageView,
        dependency: View
    ): Boolean {
        //初始化一些基础参数
        init(parent, child, dependency)
        //计算比例
        if (child.y <= 0) return false
        var percent = (child.y - mToolBarHeight) / (mStartAvatarY - mToolBarHeight)

        if (percent < 0) {
            percent = 0f
        }
        if (this.percent == percent || percent > 1) return true
        this.percent = percent
        //设置头像的大小
        ViewCompat.setScaleX(child, percent)
        ViewCompat.setScaleY(child, percent)

        return false
    }

    /**
     * 初始化数据
     * @param parent
     * @param child
     * @param dependency
     */
    private fun init(parent: CoordinatorLayout, child: CircleImageView, dependency: View) {
        if (mStartAvatarY == 0f) {
            mStartAvatarY = child.y
        }
        if (mStartDependencyY == 0f) {
            mStartDependencyY = dependency.y
        }
        if (mStartAvatarX == 0f) {
            mStartAvatarX = child.x
        }

        if (mAvatarMaxHeight == 0) {
            mAvatarMaxHeight = child.height
        }
        if (mToolBarHeight == 0) {
            val toolbar = parent.findViewWithTag(TAG_TOOLBAR) as Toolbar
            mToolBarHeight = toolbar.getHeight()
        }
    }

    internal var percent = 0f
}
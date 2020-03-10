package com.example.github.utrtestapp.widget

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.github.utrtestapp.R
import com.example.github.utrtestapp.listener.CustomTabEntity
import com.example.github.utrtestapp.listener.OnTabSelectListener
import com.example.github.utrtestapp.utils.FragmentChangeManage

import java.util.ArrayList


class CommonTabLayout @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(mContext, attrs, defStyleAttr), ValueAnimator.AnimatorUpdateListener {
    private val mTabEntitys = ArrayList<CustomTabEntity>()
    private val mTabsContainer: LinearLayout
    private var mCurrentTab: Int = 0
    private var mLastTab: Int = 0
    private var mTabCount: Int = 0


    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTabPadding: Float = 0.toFloat()
    private var mTabSpaceEqual: Boolean = false
    private var mTabWidth: Float = 0.toFloat()


    /**
     * underline
     */
    private var mUnderlineColor: Int = 0
    private var mUnderlineHeight: Float = 0.toFloat()
    private var mUnderlineGravity: Int = 0

    /**
     * divider
     */
    private var mDividerColor: Int = 0
    private var mDividerWidth: Float = 0.toFloat()
    private var mDividerPadding: Float = 0.toFloat()

    /**
     * title
     */
    private var mTextsize: Float = 0.toFloat()
    private var mTextSelectColor: Int = 0
    private var mTextUnselectColor: Int = 0
    private var mTextBold: Boolean = false
    private var mTextAllCaps: Boolean = false


    /**
     * icon
     */
    private var mIconVisible: Boolean = false
    private var mIconGravity: Int = 0
    private var mIconWidth: Float = 0.toFloat()
    private var mIconHeight: Float = 0.toFloat()
    private var mIconMargin: Float = 0.toFloat()

    private var mHeight: Int = 0

    /**
     * anim
     */
    private val mValueAnimator: ValueAnimator

    private val mFragmentChangeManager: FragmentChangeManage? = null




    private var mListener: OnTabSelectListener? = null

    private val mCurrentP = IndicatorPoint()
    private val mLastP = IndicatorPoint()

    init {
        setWillNotDraw(false)//重写onDraw方法,需要调用这个方法来清除flag
        clipChildren = false
        clipToPadding = false
        mTabsContainer = LinearLayout(mContext)
        addView(mTabsContainer)

        obtainAttributes(mContext, attrs)

        //get layout_height
        val height =
            attrs!!.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height")

        //create ViewPager
        if (height == ViewGroup.LayoutParams.MATCH_PARENT.toString() + "") {
        } else if (height == ViewGroup.LayoutParams.WRAP_CONTENT.toString() + "") {
        } else {
            val systemAttrs = intArrayOf(android.R.attr.layout_height)
            val a = mContext.obtainStyledAttributes(attrs, systemAttrs)
            mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            a.recycle()
        }

        mValueAnimator = ValueAnimator.ofObject(PointEvaluator(), mLastP, mCurrentP)
        mValueAnimator.addUpdateListener(this)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTabLayout)


        mUnderlineColor =
            ta.getColor(R.styleable.CommonTabLayout_tl_underline_color, Color.parseColor("#ffffff"))
        mUnderlineHeight =
            ta.getDimension(R.styleable.CommonTabLayout_tl_underline_height, dp2px(0f).toFloat())
        mUnderlineGravity =
            ta.getInt(R.styleable.CommonTabLayout_tl_underline_gravity, Gravity.BOTTOM)

        mDividerColor =
            ta.getColor(R.styleable.CommonTabLayout_tl_divider_color, Color.parseColor("#ffffff"))
        mDividerWidth =
            ta.getDimension(R.styleable.CommonTabLayout_tl_divider_width, dp2px(0f).toFloat())
        mDividerPadding =
            ta.getDimension(R.styleable.CommonTabLayout_tl_divider_padding, dp2px(12f).toFloat())

        mTextsize = ta.getDimension(R.styleable.CommonTabLayout_tl_textsize, sp2px(13f).toFloat())
        mTextSelectColor =
            ta.getColor(R.styleable.CommonTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"))
        mTextUnselectColor = ta.getColor(
            R.styleable.CommonTabLayout_tl_textUnselectColor,
            Color.parseColor("#AAffffff")
        )
        mTextBold = ta.getBoolean(R.styleable.CommonTabLayout_tl_textBold, false)
        mTextAllCaps = ta.getBoolean(R.styleable.CommonTabLayout_tl_textAllCaps, false)


        mIconVisible = ta.getBoolean(R.styleable.CommonTabLayout_tl_iconVisible, true)
        mIconGravity = ta.getInt(R.styleable.CommonTabLayout_tl_iconGravity, Gravity.TOP)
        mIconWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_iconWidth, dp2px(0f).toFloat())
        mIconHeight =
            ta.getDimension(R.styleable.CommonTabLayout_tl_iconHeight, dp2px(0f).toFloat())
        mIconMargin =
            ta.getDimension(R.styleable.CommonTabLayout_tl_iconMargin, dp2px(2.5f).toFloat())

        mTabSpaceEqual = ta.getBoolean(R.styleable.CommonTabLayout_tl_tab_space_equal, true)
        mTabWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_tab_width, dp2px(-1f).toFloat())
        mTabPadding = ta.getDimension(
            R.styleable.CommonTabLayout_tl_tab_padding,
            (if (mTabSpaceEqual || mTabWidth > 0) dp2px(0f) else dp2px(10f)).toFloat()
        )

        ta.recycle()
    }

    fun setTabData(tabEntitys: ArrayList<CustomTabEntity>?) {
        check(!(tabEntitys == null || tabEntitys.size == 0)) { "TabEntitys can not be NULL or EMPTY !" }

        this.mTabEntitys.clear()
        this.mTabEntitys.addAll(tabEntitys)

        notifyDataSetChanged()
    }


    /**
     * 更新数据
     */
    fun notifyDataSetChanged() {
        mTabsContainer.removeAllViews()
        this.mTabCount = mTabEntitys.size
        var tabView: View
        for (i in 0 until mTabCount) {
            if (mIconGravity == Gravity.LEFT) {
                tabView = View.inflate(mContext, R.layout.layout_tab_left, null)
            } else if (mIconGravity == Gravity.RIGHT) {
                tabView = View.inflate(mContext, R.layout.layout_tab_right, null)
            } else if (mIconGravity == Gravity.BOTTOM) {
                tabView = View.inflate(mContext, R.layout.layout_tab_bottom, null)
            } else {
                tabView = View.inflate(mContext, R.layout.layout_tab_top, null)
            }

            tabView.tag = i
            addTab(i, tabView)
        }

        updateTabStyles()
    }

    /**
     * 创建并添加tab
     */
    private fun addTab(position: Int, tabView: View) {
        val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
        tv_tab_title.setText(mTabEntitys[position].getTabTitle())


        tabView.setOnClickListener { v ->
            val position = v.tag as Int
            if (mCurrentTab != position) {
                setCurrentTab(position)
                if (mListener != null) {
                    mListener!!.onTabSelect(position)
                }
            } else {
                if (mListener != null) {
                    mListener!!.onTabReselect(position)
                }
            }
        }

        /** 每一个Tab的布局参数  */
        var lp_tab = if (mTabSpaceEqual)
            LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, 1.0f)
        else
            LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        if (mTabWidth > 0) {
            lp_tab =
                LinearLayout.LayoutParams(mTabWidth.toInt(), FrameLayout.LayoutParams.MATCH_PARENT)
        }
        mTabsContainer.addView(tabView, position, lp_tab)
    }

    private fun updateTabStyles() {
        for (i in 0 until mTabCount) {
            val tabView = mTabsContainer.getChildAt(i)
            tabView.setPadding(mTabPadding.toInt(), 0, mTabPadding.toInt(), 0)
            val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(if (i == mCurrentTab) mTextSelectColor else mTextUnselectColor)
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextsize)
                if (mTextAllCaps) {
                    tv_tab_title.text = tv_tab_title.text.toString().toUpperCase()
                }

                if (mTextBold) {
                    tv_tab_title.paint.isFakeBoldText = mTextBold
                }
            }



            if (mIconVisible) {

                val lp = LinearLayout.LayoutParams(
                    if (mIconWidth <= 0) LinearLayout.LayoutParams.WRAP_CONTENT else mIconWidth.toInt(),
                    if (mIconHeight <= 0) LinearLayout.LayoutParams.WRAP_CONTENT else mIconHeight.toInt()
                )
                if (mIconGravity == Gravity.LEFT) {
                    lp.rightMargin = mIconMargin.toInt()
                } else if (mIconGravity == Gravity.RIGHT) {
                    lp.leftMargin = mIconMargin.toInt()
                } else if (mIconGravity == Gravity.BOTTOM) {
                    lp.topMargin = mIconMargin.toInt()
                } else {
                    lp.bottomMargin = mIconMargin.toInt()
                }

            }
        }
    }

    private fun updateTabSelection(position: Int) {
        for (i in 0 until mTabCount) {
            val tabView = mTabsContainer.getChildAt(i)
            val isSelect = i == position
            val tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
            tab_title.setTextColor(if (isSelect) mTextSelectColor else mTextUnselectColor)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isInEditMode || mTabCount <= 0) {
            return
        }

        val height = height
        val paddingLeft = paddingLeft
        // draw divider
        if (mDividerWidth > 0) {
            mDividerPaint.strokeWidth = mDividerWidth
            mDividerPaint.color = mDividerColor
            for (i in 0 until mTabCount - 1) {
                val tab = mTabsContainer.getChildAt(i)
                canvas.drawLine(
                    (paddingLeft + tab.right).toFloat(),
                    mDividerPadding,
                    (paddingLeft + tab.right).toFloat(),
                    height - mDividerPadding,
                    mDividerPaint
                )
            }
        }

        // draw underline
        if (mUnderlineHeight > 0) {
            mRectPaint.color = mUnderlineColor
            if (mUnderlineGravity == Gravity.BOTTOM) {
                canvas.drawRect(
                    paddingLeft.toFloat(),
                    height - mUnderlineHeight,
                    (mTabsContainer.width + paddingLeft).toFloat(),
                    height.toFloat(),
                    mRectPaint
                )
            } else {
                canvas.drawRect(
                    paddingLeft.toFloat(),
                    0f,
                    (mTabsContainer.width + paddingLeft).toFloat(),
                    mUnderlineHeight,
                    mRectPaint
                )
            }
        }

    }

    //setter and getter
    fun setCurrentTab(currentTab: Int) {
        mLastTab = this.mCurrentTab
        this.mCurrentTab = currentTab
        updateTabSelection(currentTab)
        if (mFragmentChangeManager != null) {
            mFragmentChangeManager!!.setFragments(currentTab)
        }

    }

    fun setOnTabSelectListener(listener: OnTabSelectListener) {
        this.mListener = listener
    }


    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putInt("mCurrentTab", mCurrentTab)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state as Bundle?
            mCurrentTab = bundle!!.getInt("mCurrentTab")
            state = bundle.getParcelable("instanceState")
            if (mCurrentTab != 0 && mTabsContainer.childCount > 0) {
                updateTabSelection(mCurrentTab)
            }
        }
        super.onRestoreInstanceState(state)
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {

    }

    internal inner class IndicatorPoint {
        var left: Float = 0.toFloat()
        var right: Float = 0.toFloat()
    }

    internal inner class PointEvaluator : TypeEvaluator<IndicatorPoint> {
        override fun evaluate(
            fraction: Float,
            startValue: IndicatorPoint,
            endValue: IndicatorPoint
        ): IndicatorPoint {
            val left = startValue.left + fraction * (endValue.left - startValue.left)
            val right = startValue.right + fraction * (endValue.right - startValue.right)
            val point = IndicatorPoint()
            point.left = left
            point.right = right
            return point
        }
    }


    protected fun dp2px(dp: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    protected fun sp2px(sp: Float): Int {
        val scale = this.mContext.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

}

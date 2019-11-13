package com.sanketguru.myworth.view.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import  com.google.android.material.card.MaterialCardView
import com.sanketguru.myworth.R


class BorderCard : MaterialCardView {
    constructor(context: Context) : super(context) {

        //  setup()
    }

    constructor(context: Context, attr: AttributeSet? = null) : super(context, attr) {

        setup(attr, 0)
    }

    constructor(context: Context, attr: AttributeSet? = null, defArrt: Int = 0) : super(context, attr, defArrt) {

        setup(attr, 0)
    }

    private fun setup(attr: AttributeSet? = null, deff: Int) {
        val a = context.obtainStyledAttributes(attr, R.styleable.BorderCard, deff, 0)

        val borderColor = a.getColor(R.styleable.BorderCard_borderCardColor, DEFAULT_BORDER_COLOR)

        val borderWidth = a.getDimension(R.styleable.BorderCard_borderCardWidth, DEFAULT_BORDER_WIDTH)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth * 2

        a.recycle()
    }

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // Some colors for the face background, eyes and mouth.
    private var stripColor = DEFAULT_BORDER_COLOR

    private val strokeWidth = DEFAULT_BORDER_WIDTH

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)
        drawBorder(canvas)
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//
//    }

    private fun drawBorder(canvas: Canvas) {
        // 1
//        paint.color = stripColor
//        paint.style = Paint.Style.STROKE
//        paint.strokeWidth= strokeWidth

        canvas.drawLine(0f, 0f, 0f, height + 1f, paint)


    }

    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.BLUE
        private const val DEFAULT_BORDER_WIDTH = 30.0f
    }

}
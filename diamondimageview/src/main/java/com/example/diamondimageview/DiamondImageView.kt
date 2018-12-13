package com.example.diamondimageview

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import java.lang.Exception

class DiamondImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatImageView(
    context,
    attrs,
    defStyleAttr
){
    companion object {
        const val DEFAULT_BORDER_WIDTH : Float = 4f
    }

    // Shape
    private var path : Path = Path()
    private var borderPath = Path()


    // modifiable properties

    var borderWidth = 0f
    var borderColor: Int

    private var image: Bitmap? = null
    private lateinit var mDrawable : Drawable
    private var paint : Paint = Paint()
    private var paintBorder : Paint = Paint()

    constructor(context: Context, attrs: AttributeSet) : this(context,attrs,0)
    constructor(context: Context) : this(context,null,0)


    private var isBordered: Boolean = false

    init {

        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.DiamondImageView,
            defStyleAttr,
            0
        )

        paint.isAntiAlias = true
        paintBorder.isAntiAlias = true
        paintBorder.style = Paint.Style.STROKE

        isBordered = attributes.getBoolean(R.styleable.DiamondImageView_diamondIV_bordered,false)

        val defaultBorderWidth = DEFAULT_BORDER_WIDTH * context.resources.displayMetrics.density
        borderWidth = attributes.getDimension(R.styleable.DiamondImageView_diamondIV_border_width,defaultBorderWidth)
        paintBorder.strokeWidth = borderWidth

        borderColor = attributes.getColor(R.styleable.DiamondImageView_diamondIV_border_color, Color.BLACK)
        paintBorder.color = borderColor

        attributes.recycle()

    }

    override fun setColorFilter(cf: ColorFilter?) {
        if(this.colorFilter == cf) return
        this.colorFilter = cf
        invalidate()
        super.setColorFilter(cf)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(image != null){
            updateShader()
        }
    }

    override fun getScaleType(): ScaleType {
        val currentScaleType = super.getScaleType()
        return if(currentScaleType == null || currentScaleType != ScaleType.CENTER_INSIDE)
            ScaleType.CENTER_CROP
        else currentScaleType
    }

    override fun setScaleType(scaleType: ScaleType?) {
        if(scaleType != ScaleType.CENTER_CROP && scaleType != ScaleType.CENTER_INSIDE){
            throw java.lang.IllegalArgumentException("Scale Type Not Supported")
        }else {
            super.setScaleType(scaleType)
        }
    }

    override fun onDraw(canvas: Canvas) {
        loadBitmap()

        val half = (width / 2).toFloat()
        path.moveTo(0f,half)
        path.lineTo(half,0f)
        path.lineTo(half*2,half)
        path.lineTo(half,half*2)
        path.close()
        canvas.drawPath(path,paint)
        if (isBordered) {
            val offset = paintBorder.strokeWidth / 2
            borderPath.moveTo(offset, half)
            borderPath.lineTo(half, offset)
            borderPath.lineTo(half * 2 - offset, half)
            borderPath.lineTo(half, -offset + half * 2)
            borderPath.close()
            canvas.drawPath(borderPath, paintBorder)
        }
    }

    private fun loadBitmap() {
//        if (mDrawable != null && mDrawable == drawable) return
        if(drawable == null) return
        mDrawable = drawable
        image = drawableToBitmap(drawable)!!
        updateShader()
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        try {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0,0,canvas.width,canvas.height)
            drawable.draw(canvas)
            return bitmap
        }catch (e : Exception){
            e.printStackTrace()
            return null
        }
    }

    private fun updateShader() {
        if(image == null) return
        val shaderImage = image!!
        val shader = BitmapShader(
            shaderImage,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )

        var scale = 0f
        var dx = 0f
        var dy = 0f
        when(scaleType){
            ScaleType.CENTER_CROP ->{
                if (shaderImage.width * height > width * shaderImage.height){
                    scale = height / shaderImage.height.toFloat()
                    dx = (width - shaderImage.width * scale) * 0.5f
                } else {
                    scale = width / shaderImage.width.toFloat()
                    dy = (height - shaderImage.height * scale) * 0.5f
                }
            }
            ScaleType.CENTER_INSIDE -> {
                if (shaderImage.width * height < width * shaderImage.height){
                    scale = height / shaderImage.height.toFloat()
                    dx = (width - shaderImage.width * scale) * 0.5f
                }else {
                    scale = width / shaderImage.width.toFloat()
                    dy = (height - shaderImage.height * scale) * 0.5f
                }
            }
            else -> {
            }
        }

        val matrix = Matrix()
        matrix.setScale(scale,scale)
        matrix.postTranslate(dx,dy)
        shader.setLocalMatrix(matrix)

        paint.shader = shader

        paint.colorFilter = colorFilter
    }

}
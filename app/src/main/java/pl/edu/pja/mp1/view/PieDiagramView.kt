package pl.edu.pja.mp1.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PieDiagramView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val rect by lazy { Rect(0, 0, width, height) }
    private val whitePaint = Paint().apply {
        color =Color.WHITE
    }
    private val piePaint = Paint().apply {
        color =Color.BLUE
        strokeWidth = 20F
    }
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 80F
        textAlign = Paint.Align.CENTER
    }
    private val oval by lazy { RectF(0F, 20F, width.toFloat(), width.toFloat()) }
    private var progressVal: Int = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(rect, whitePaint)

        if (progressVal > 0) {
            if (canvas != null) {
                drawPieDiagram(canvas)
            }
        } else {
            if (canvas != null) {
                drawText(canvas)
                //drawCircle(canvas)
            }
        }
    }

    private fun drawPieDiagram(canvas: Canvas) {
        val progressAngle: Float = ((progressVal/100.0)*360).toFloat()
        canvas.drawArc(oval, 270F, progressAngle, true, piePaint)
    }

    private fun drawText(canvas: Canvas) {
        canvas.drawText("Brak postępu.", width/2F, height/2F, textPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawArc(oval, 0F, 360F, true, piePaint)
    }

    fun setProgressValue(progress: Int){
        this.progressVal = progress
        invalidate()
    }
}
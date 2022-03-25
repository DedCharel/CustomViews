package com.example.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviews.databinding.PartButtonsBinding

enum class BottomButtonAction{
    POSITIVE,NEGATIVE
}

typealias OnBottomButtonsActionListener=(BottomButtonAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
): ConstraintLayout(context, attrs,defStyleAttr, defStyleRes) {

    private val binding: PartButtonsBinding

    private var listener: OnBottomButtonsActionListener? = null

    var isProgressMode:Boolean = false
        set(value) {
            field = value
            if (value){
                binding.positiveButton.visibility = INVISIBLE
                binding.negativeButton.visibility = INVISIBLE
                binding.progress.visibility = VISIBLE
            }else{
                binding.positiveButton.visibility = VISIBLE
                binding.negativeButton.visibility = VISIBLE
                binding.progress.visibility = GONE
            }
        }

    constructor(context: Context,attrs: AttributeSet?,defStyleAttr: Int):this(context,attrs,defStyleAttr,R.style.MyBottomButtonsStyle)
    //constructor(context: Context,attrs: AttributeSet?,defStyleAttr: Int):this(context,attrs,defStyleAttr,0)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,R.attr.bottomButtonStyle)
    //constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context): this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons,this,true)
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs,defStyleAttr,defStyleRes)
        initListeners()
    }

    private fun initializeAttributes(attrs: AttributeSet?,defStyleAttr: Int,defStyleRes: Int){
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.BottomButtonsView,defStyleAttr, defStyleRes)

        with(binding){
            val positiveButtonText = typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            setPositiveButtonText(positiveButtonText)


            val negativeButtonText = typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            setNegativeButtonText(negativeButtonText)

            val positiveButtonBgColor = typedArray.getColor(R.styleable.BottomButtonsView_bottomPositiveBackgroundColor, Color.BLUE)
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonBgColor)

            val negativeButtonBgColor = typedArray.getColor(R.styleable.BottomButtonsView_bottomNegativeBackgroundColor, Color.WHITE)
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonBgColor)

            this@BottomButtonsView.isProgressMode =  typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)

        }
        typedArray.recycle()
    }

   private fun initListeners(){
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }
       binding.negativeButton.setOnClickListener {
           this.listener?.invoke(BottomButtonAction.NEGATIVE)
       }
    }

    fun setListener(listener: OnBottomButtonsActionListener?){
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?){
        binding.positiveButton.text = text ?: "OK"
    }

    fun setNegativeButtonText(text: String?){
        binding.negativeButton.text = text ?: "Cancel"
    }
}
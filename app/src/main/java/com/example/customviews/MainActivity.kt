package com.example.customviews

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.os.Looper
import android.widget.Toast
import com.example.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val token = Any()
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.bottomButtons.setListener {

            when(it){
                BottomButtonAction.POSITIVE ->{
                    binding.bottomButtons.isProgressMode = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed({
                            binding.bottomButtons.isProgressMode = false
                            binding.bottomButtons.setPositiveButtonText("Update Positive")
                            Toast.makeText(this, "Positive button  pressed", Toast.LENGTH_SHORT).show()
                        }, token, 2000)
                    }

                }
                BottomButtonAction.NEGATIVE -> {
                    binding.bottomButtons.setNegativeButtonText("Update Negative")
                    Toast.makeText(this, "Negative button  pressed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(token)
    }
}
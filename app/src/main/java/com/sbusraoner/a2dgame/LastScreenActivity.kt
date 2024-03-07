package com.sbusraoner.a2dgame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sbusraoner.a2dgame.databinding.ActivityLastScreenBinding

class LastScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLastScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("score",0)
        binding.textViewTotalScore.text = score.toString()

        val sp = getSharedPreferences("result",Context.MODE_PRIVATE)
        val highestScore = sp.getInt("highestScore",0)

        if (score >= highestScore) {
            val editor = sp.edit()
            editor.putInt("highestScore",score)
            editor.commit()
            binding.textViewHighestScore.text = score.toString()

        } else {
            binding.textViewHighestScore.text = highestScore.toString()

        }

        binding.buttonPlayAgain.setOnClickListener {
            startActivity(Intent(this@LastScreenActivity,MainActivity::class.java))

        }
    }
}
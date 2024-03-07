package com.sbusraoner.a2dgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.sbusraoner.a2dgame.databinding.ActivityGameScreenBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.floor

@SuppressLint("ClickableViewAccessibility")
class GameScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameScreenBinding

    //Positions
    private var mainCharacterX = 0.0f
    private var mainCharacterY = 0.0f
    private var squareX = 0.0f
    private var squareY = 0.0f
    private var triangleX = 0.0f
    private var triangleY = 0.0f
    private var circleX = 0.0f
    private var circleY = 0.0f


    //Control
    private var touchControl = false
    private var startControl = false
    private val timer = Timer()

    //Dimensions
    private var screenWidth = 0
    private var screenHeight = 0
    private var mainCharacterWidth = 0
    private var mainCharacterHeight = 0

    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.blackSquare.x = -800.0f
        binding.blackSquare.y = -800.0f
        binding.yellowCircle.x = -800.0f
        binding.yellowCircle.y = -800.0f
        binding.redTriangle.x = -800.0f
        binding.redTriangle.y = -800.0f




        binding.cl.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                if (startControl) {
                    if (p1?.action == MotionEvent.ACTION_DOWN) {
                        Log.e("MotionEvent","Ekrana Dokundu")
                        touchControl = true
                    }
                    if (p1?.action == MotionEvent.ACTION_UP) {
                        Log.e("MotionEvent","ACTION_UP:Ekranı Bıraktı")
                        touchControl = false
                    }

                }
                else {

                    startControl = true
                    binding.textViewStartGame.visibility = View.INVISIBLE

                    mainCharacterX = binding.mainCharacter.x
                    mainCharacterY = binding.mainCharacter.y

                    mainCharacterWidth = binding.mainCharacter.width
                    mainCharacterHeight = binding.mainCharacter.height
                    screenWidth = binding.cl.width
                    screenHeight = binding.cl.height


                    timer.schedule(0,20){
                        Handler(Looper.getMainLooper()).post {
                            mainCharacterMove()
                            objectMove()
                            actionControl()
                        }
                    }

                }

                return true
            }
        })



    }


    fun actionControl() {
        val triangleCenterX = triangleX + binding.redTriangle.width/2.0f
        val triangleCenterY = triangleY + binding.redTriangle.height/2.0f

        if(0.0f <= triangleCenterX && triangleCenterX <= mainCharacterWidth
            && mainCharacterY <= triangleCenterY && triangleCenterY <= mainCharacterY+mainCharacterHeight){
            score += 50
            triangleX = -40.0f
        }

        val circleCenterX = circleX + binding.yellowCircle.width/2.0f
        val circleCenterY = circleY + binding.yellowCircle.height/2.0f

        if(0.0f <= circleCenterX && circleCenterX <= mainCharacterWidth
            && mainCharacterY <= circleCenterY && circleCenterY <= mainCharacterY+mainCharacterHeight){
            score += 20
            circleX = -40.0f
        }

        val squareCenterX = squareX + binding.blackSquare.width/2.0f
        val squareCenterY = squareY + binding.blackSquare.height/2.0f

        if(0.0f <= squareCenterX && squareCenterX <= mainCharacterWidth
            && mainCharacterY <= squareCenterY && squareCenterY <= mainCharacterY+mainCharacterHeight){

            squareX = -40.0f

            timer.cancel() //Stop the game
            val intent = Intent(this@GameScreenActivity,LastScreenActivity::class.java)
            intent.putExtra("score",score)
            startActivity(intent)
            finish()

        }

        binding.textViewScor.text = score.toString()


    }

    fun objectMove() {
        squareX -= screenWidth/44.0f
        triangleX -= screenWidth/36.0f
        circleX -= screenWidth/54.0f


        if (circleX <= 0.0f){
            circleX = screenWidth + 20.0f
            circleY = floor(Math.random()*screenHeight).toFloat()
        }

        binding.yellowCircle.x = circleX
        binding.yellowCircle.y = circleY

        if (squareX <= 0.0f){
            squareX = screenWidth + 20.0f
            squareY = floor(Math.random()*screenHeight).toFloat()
        }
        binding.blackSquare.x = squareX
        binding.blackSquare.y = squareY

        if (triangleX <= 0.0f){
            triangleX = screenWidth + 20.0f
            triangleY = floor(Math.random()*screenHeight).toFloat()
        }
        binding.redTriangle.x = triangleX
        binding.redTriangle.y = triangleY

    }

    fun mainCharacterMove(){

        val mainCharacterSpeed = screenHeight/80.0f

        if (touchControl) {
            mainCharacterY -= mainCharacterSpeed
        } else {
            mainCharacterY += mainCharacterSpeed
        }

        //Preventing the main character from going off-screen
        if (mainCharacterY <= 0.0f) {
            mainCharacterY = 0.0f
        }
        if (mainCharacterY >= screenHeight - mainCharacterHeight) {
            mainCharacterY = (screenHeight - mainCharacterHeight).toFloat()
        }
        binding.mainCharacter.y = mainCharacterY
    }
}
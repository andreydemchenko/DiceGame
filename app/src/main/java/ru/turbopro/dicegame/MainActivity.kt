package ru.turbopro.dicegame

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var isFirstThrow: Boolean = true

    private lateinit var dice1imageview: ImageView
    private lateinit var dice2imageview: ImageView
    private lateinit var sumtextview: TextView
    private lateinit var resulttextview: TextView
    private lateinit var targettextview: TextView
    private lateinit var rollbutton: Button
    private lateinit var playagainbutton: Button
    private lateinit var rulesbutton: Button
    private lateinit var rolldiceimageview: ImageView

    var dice1: Int = 0
    var dice2: Int = 0
    var sum: Int = 0
    var target = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dice1imageview = findViewById(R.id.dice1iv)
        dice2imageview = findViewById(R.id.dice2iv)
        sumtextview = findViewById(R.id.sumtv)
        resulttextview = findViewById(R.id.resulttv)
        targettextview = findViewById(R.id.targettv)
        rollbutton = findViewById(R.id.rollbtn)
        playagainbutton = findViewById(R.id.playagainbtn)
        rulesbutton = findViewById(R.id.rulesbtn)
        rolldiceimageview = findViewById(R.id.rolldiceiv)

        playagainbutton.visibility = View.INVISIBLE
        rulesbutton.visibility = View.INVISIBLE

    }

    fun rollDice(view: View) {
        rollingDice()
        gameManager()
        rolldiceimageview.imageAlpha = 0
    }

    @SuppressLint("SetTextI18n")
    fun rollingDice() {
        //dice1
        dice1 = (1..6).random()
        dice1imageview.setImageResource(diceSides(dice1))

        //dice2
        dice2 = (1..6).random()
        dice2imageview.setImageResource(diceSides(dice2))

        //score
        sum = (dice1 + dice2)
        sumtextview.text = getString(R.string.score) + sum.toString()
    }

    @SuppressLint("SetTextI18n")
    fun gameManager() {
        if(isFirstThrow){ //on First throw
            if(sum==7 || sum==11){
                youWon()
                resulttextview.text = getString(R.string.lucky_you_won)
            }
            else if (sum==2 || sum==3 || sum==12){
                youLoose()
                resulttextview.text = getString(R.string.crap_loose)
            }
            else {
                target = sum
                targettextview.text = getString(R.string.target) + target.toString()
                resulttextview.text = getString(R.string.roll_again)
            }
            isFirstThrow = false
        }

        else if (!isFirstThrow){ //After first throw
            if (sum==7){
                youLoose()
            }
            else if (sum==target) {
                youWon()
            }
        }
    }

    fun youWon(){
        resulttextview.text = getString(R.string.you_won)
        resulttextview.setTextColor(Color.BLUE)
        tvDesignForWinLoose()
    }
    fun youLoose(){
        resulttextview.text = getString(R.string.you_loose)
        resulttextview.setTextColor(Color.RED)
        tvDesignForWinLoose()
    }

    fun tvDesignForWinLoose(){
        resulttextview.textSize = 35.0f
        resulttextview.setBackgroundResource(R.color.red_orange_light)
        rollbutton.visibility = View.INVISIBLE
        playagainbutton.visibility = View.VISIBLE
        rulesbutton.visibility = View.VISIBLE
    }

    fun diceSides(dices: Int): Int {
        val diceImage = when(dices) {
            1 -> R.drawable.dice_side_1
            2 -> R.drawable.dice_side_2
            3 -> R.drawable.dice_side_3
            4 -> R.drawable.dice_side_4
            5 -> R.drawable.dice_side_5
            else -> R.drawable.dice_side_6
        }
        return diceImage
    }

    fun playAgain(view: View) {
        val intent = intent
        finish()
        startActivity(intent)
    }

    fun goToMainMenu(view: View) {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}
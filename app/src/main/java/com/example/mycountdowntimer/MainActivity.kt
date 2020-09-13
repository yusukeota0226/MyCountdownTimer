package com.example.mycountdowntimer

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private var soundResId = 0

    inner class MyCountDownTimer(millisInfuture: Long,
                                   countDownInterval: Long) :
            CountDownTimer(millisInfuture, countDownInterval){

        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            timerText.text = "%1d:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            timerText.text = "0:00"
            soundPool.play(soundResId, 1.0f, 100f, 0, 0, 1.0f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText.text = "3:00"
        val timer = MyCountDownTimer(3 * 60 * 1000, 100)
        playStop.setOnClickListener{
            when(timer.isRunning){
                true -> timer.apply{
                    isRunning = false
                    cancel()
                    playStop.setImageResource(
                        R.drawable.ic_baseline_play_arrow_24)
                }
                false -> timer.apply{
                    isRunning = true
                    start()
                    playStop.setImageResource(
                        R.drawable.ic_baseline_stop_24
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        soundPool  = SoundPool(2, AudioManager.STREAM_ALARM, 0)
        soundResId = soundPool.load(this, R.raw.bellsound, 1)
    }

    override fun onPause() {
        super.onPause()
        soundPool.release()
    }
}
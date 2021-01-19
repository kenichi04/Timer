package jp.techacademy.kenichi04.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mTimer: Timer? = null
    // タイマー用の時間
    private var mTimerSec = 0.0
    private var mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            if (mTimer == null) {
                mTimer = Timer()
                // タイマーの始動
                mTimer!!.schedule(object : TimerTask() {
                    override fun run() {
                        mTimerSec += 0.1
                        // UIの描画はメインスレッドのみ可能のため、postメソッドを使用してTimer用のサブスレッドから描画を依頼する
                        mHandler.post {
                            timer.text = String.format("%.1f", mTimerSec)
                        }
                    }
                }, 100, 100)
            }
        }

        pause_button.setOnClickListener {
            if (mTimer != null) {
                mTimer!!.cancel()
                mTimer = null
            }
        }

        reset_button.setOnClickListener {
            mTimerSec = 0.0
            timer.text = String.format("%.1f", mTimerSec)
        }

    }
}
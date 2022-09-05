package jp.cafe_boscobel.ushio.zaizen.purchasing

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_booking.*

// マスターデータを作成しFirebaseへ記録

class BookingActivity : AppCompatActivity() {
    private var mGenre: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // 渡ってきたジャンルの番号を保持する
        val extras = intent.extras
        mGenre = extras!!.getInt("genre")

        // UIの準備
        title = getString(R.string.booking_title)

//        sendButton.setOnClickListener(this)
//        imageView.setOnClickListener(this)

        }


}
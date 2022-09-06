package jp.cafe_boscobel.ushio.zaizen.purchasing

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_booking.*

// マスターデータを作成しFirebaseへ記録

class BookingActivity : AppCompatActivity(), View.OnClickListener,DatabaseReference.CompletionListener  {
    private var mGenre: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // 渡ってきたジャンルの番号を保持する
        val extras = intent.extras
        mGenre = extras!!.getInt("genre")

        // UIの準備
        title = getString(R.string.booking_title)

        inputfinishButton.setOnClickListener(this as View.OnClickListener)
//        sendButton.setOnClickListener(this)
//        imageView.setOnClickListener(this)

        }

    override fun onClick(v: View) {

        Log.d("uztest","onclick entered")

        val dataBaseReference = FirebaseDatabase.getInstance().reference
        val genreRef = dataBaseReference.child(ContentsPATH).child(mGenre.toString())

        val data = HashMap<String, String>()

        data["uid"] = FirebaseAuth.getInstance().currentUser!!.uid

        Log.d("uztest","data uid = ${data["uid"]}")
        val productName = input1EditText.text.toString()
        val productUnit = input2EditText.text.toString()
        val productCode = input3EditText.text.toString()
        val productComm = input4EditText.text.toString()

        data["name"] = productName
        data["unit"] = productUnit
        data["code"] = productCode
        data["comment"] = productComm

        Log.d("uztest", "data save to firebase")

        genreRef.push().setValue(data, this)

        Log.d("uztest","data save completed")
    }

    override fun onComplete(databaseError: DatabaseError?, databaseReference: DatabaseReference) {
        progressBar.visibility = View.GONE

        if (databaseError == null) {
            finish()
        } else {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.question_send_error_message), Snackbar.LENGTH_LONG).show()
        }
    }

}
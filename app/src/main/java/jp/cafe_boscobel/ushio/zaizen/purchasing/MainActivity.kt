package jp.cafe_boscobel.ushio.zaizen.purchasing

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBarDrawerToggle    // ← 追加
import androidx.core.view.GravityCompat    // ← 追加
import com.google.android.material.navigation.NavigationView    // ← 追加
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
// findViewById()を呼び出さずに該当Viewを取得するために必要となるインポート宣言
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*    // ← 追加
import java.util.*

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {    // ← 修正

    companion object {
        var mYear:Int = 0
        var mMonth:Int = 0
        var mDay:Int = 0}

    private var mGenre = 0    // ← 追加

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // idがtoolbarがインポート宣言により取得されているので
        // id名でActionBarのサポートを依頼
        setSupportActionBar(toolbar)

        showDataPicker()

        // fabにClickリスナーを登録
        fab.setOnClickListener { view ->
            // ジャンルを選択していない場合（mGenre == 0）はエラーを表示するだけ
            if (mGenre == 0) {
                Snackbar.make(view, getString(R.string.question_no_select_genre), Snackbar.LENGTH_LONG).show()
            } else {

            }

            // ログイン済みのユーザーを取得する
            val user = FirebaseAuth.getInstance().currentUser

            if (user == null) {
                // ログインしていなければログイン画面に遷移させる
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            } else {
                //ジャンルを渡して入力画面を起動する
                val intent = Intent(applicationContext, BookingActivity::class.java)
                intent.putExtra("genre", mGenre)
                startActivity(intent)
            }
        }

        // ～～ ここから
        // ナビゲーションドロワーの設定
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.app_name, R.string.app_name)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // ～～ ここまで
    }

    private fun showDataPicker(){
// Set date to operate

        val calendar = Calendar.getInstance()
        val tYear = calendar.get(Calendar.YEAR)
        val tMonth = calendar.get(Calendar.MONTH)
        val tDay = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth ->
                    mYear = year
                    mMonth = month
                    mDay = dayOfMonth
                },
                tYear ,
                tMonth,
                tDay)

        datePickerDialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    // ～～ ここから
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val colval:Int

        if (id == R.id.nav_hobby) {
            toolbar.title = getString(R.string.menu_hobby_label)
            toolbar.setTitleTextColor(Color.WHITE)
            mGenre = 1
        } else if (id == R.id.nav_life) {
            toolbar.title = getString(R.string.menu_life_label)
            toolbar.setTitleTextColor(Color.WHITE)
            mGenre = 2
        } else if (id == R.id.nav_health) {
            toolbar.title = getString(R.string.menu_health_label)
            toolbar.setTitleTextColor(Color.WHITE)
            mGenre = 3
        } else if (id == R.id.nav_compter) {
            toolbar.title = getString(R.string.menu_compter_label)
            toolbar.setTitleTextColor(Color.WHITE)
            mGenre = 4
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    // ～～ ここまで
}

package com.heixiu.errand.widget

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MainActivity
import com.heixiu.errand.R
import com.heixiu.errand.utils.SPUtil

class SplashActivity : AppCompatActivity() {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        val entity = SPUtil.getString("userid")
        object : Thread() {
            override fun run() {
                super.run()
                try {
                    if (entity .equals("") ||entity.equals("1")) {
                        finish()
                        SPUtil.saveString("userid","")
                        val intent = Intent(mContext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.anim_right_enter, R.anim.anim_left_exit)
                    } else {
                        finish()
                        val intent = Intent(mContext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.anim_right_enter, R.anim.anim_left_exit)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

    }
}

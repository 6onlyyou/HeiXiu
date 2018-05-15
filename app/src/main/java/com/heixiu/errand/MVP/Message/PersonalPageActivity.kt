package com.heixiu.errand.MVP.Message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import kotlinx.android.synthetic.main.activity_personal_page.*

class PersonalPageActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_personal_page)
    }

    override fun findViewById() {
        personal_compile.setOnClickListener {
            startActivity(EditProfileActivity::class.java)
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}

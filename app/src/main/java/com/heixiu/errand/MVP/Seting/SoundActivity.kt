package com.heixiu.errand.MVP.Seting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_sound.*

class SoundActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_sound)
    }

    override fun findViewById() {
        initTitle("音效与通知", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        val musicState = SPUtil.getBoolean("soundInform")
        sound_inform.setChecked(musicState)
        sound_inform.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b: Boolean) {
                SPUtil.saveboolean("soundInform", b)
            }
        })
        val shakeState  = SPUtil.getBoolean("soundInform")
        shake_inform.setChecked(shakeState)
        shake_inform.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, b: Boolean) {
                SPUtil.saveboolean("soundInform", b)
            }
        })
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

}

package com.heixiu.errand.MVP.Seting

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import com.bumptech.glide.Glide
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.dialog.DialogShowPic
import com.heixiu.errand.dialog.DialogShowPicP
import com.heixiu.errand.utils.SPUtil
import com.umeng.socialize.utils.DeviceConfig.context
import kotlinx.android.synthetic.main.activity_seting_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SetingMainActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_seting_main)
    }

    override fun findViewById() {
        initTitle("设置", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        setting_account.setOnClickListener {
            startActivity(AccountActivity::class.java)
        }
        setting_notification.setOnClickListener {
            startActivity(SoundActivity::class.java)
        }
        setting_outaccont.setOnClickListener {
            SPUtil.saveString("headurl", "")
            SPUtil.saveString("nickname", "")
            SPUtil.saveString("bindzfb", "")
            SPUtil.saveString("token","")
            SPUtil.saveString("userid","")
            SPUtil.saveString("rongyun_token","")
            MyApplication.getInstance().setAlisa()
            startActivity(LoginActivity::class.java)
            finishWithAlpha()
        }
        setting_kefu.setOnClickListener {
            DlgForBigPhto()
//            startActivity(SoundActivity::class.java)
        }
    }

    override fun setListener() {
    }

    override fun processLogic() {
    }

    var dialog6IvPic: ImageView? = null

    private fun DlgForBigPhto() {
        val pDialogMy = DialogShowPic(this)
        pDialogMy.SetStyle(R.style.myDialogTheme1)
        pDialogMy.InitDialog(object : DialogShowPicP {
            var dialog6LlBckgrnd: LinearLayout?  = null

            override fun SetDialogView(pDialog: Dialog, pDialogMy: DialogShowPic) {
                pDialog.setContentView(R.layout.show_pic)
                dialog6LlBckgrnd = pDialog.findViewById<View>(R.id.dialog6LlBckgrnd) as LinearLayout
                dialog6LlBckgrnd!!.setOnClickListener(pDialogMy)
                dialog6IvPic = pDialog.findViewById<View>(R.id.dialog6IvPic) as ImageView
                dialog6IvPic!!.setOnLongClickListener {
                    val builder = AlertDialog.Builder(this@SetingMainActivity)
                    builder.setItems(arrayOf("保存图片")) { dialog, which ->
                        dialog6IvPic!!.isDrawingCacheEnabled = true
                        val imageBitmap = dialog6IvPic!!.drawingCache
                        if (imageBitmap != null) {
                            saveImageToGallery(this@SetingMainActivity, imageBitmap)
                        }
                    }
                    builder.show()
                    true
                }
                dialog6IvPic!!.setOnClickListener { pDialogMy.Destroy() }
//                Glide.with(dialog6IvPic!!.context)
//                        .load(url)
//                        .crossFade()
//                        .into(dialog6IvPic)
                dialog6IvPic!!.setImageResource(R.mipmap.offic_ico)
            }

            override fun SetOnClickListener(v: View, pDialogMy1: DialogShowPic) {
                if (v === dialog6LlBckgrnd) {
                    pDialogMy1.Destroy()
                }
            }

            override fun SetOnKeyListener(keyCode: Int, event: KeyEvent) {}
        })
        pDialogMy.Show()
    }

    fun saveImageToGallery(context: Context, bmp: Bitmap?) {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory(), "Boohee")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver,
                    file.absolutePath, fileName, null)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        // 最后通知图库更新
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.absolutePath)))
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
    }
}

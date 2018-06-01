package com.heixiu.errand.MVP.Community

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.utils.GlideLoader
import com.jaiky.imagespickers.ImageConfig
import com.jaiky.imagespickers.ImageSelector
import com.jaiky.imagespickers.ImageSelectorActivity
import kotlinx.android.synthetic.main.activity_post_text.*
import java.util.ArrayList
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.os.Build.VERSION.SDK_INT
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast


class PostTextActivity : BaseActivity() {
    override fun loadViewLayout() {
        setContentView(R.layout.activity_post_text)
        initTitle("图片动态", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        mTitle.setTv_Right("发送",R.color.white, View.OnClickListener { finishWithAnim() })
    }

    val REQUEST_CODE = 123
    private val path = ArrayList<String>()
    private var imageConfig: ImageConfig? = null
    override fun findViewById() {
        text_addImage.setOnClickListener {
            startAlbum()

        }
    }



    override fun setListener() {

    }

    override fun processLogic() {
    }

    private fun startAlbum() {
        val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")

        //检查权限
        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //已经禁止提示了
                    val builder = AlertDialog.Builder(this)
                    builder.setCancelable(false)
                            .setMessage("应用需要存储权限来让您选择手机中的相片！")
                            .setNegativeButton("取消", object : DialogInterface.OnClickListener {
                               override fun onClick(dialog: DialogInterface, which: Int) {
//                                    Toast.makeText(this, "点击了取消按钮", Toast.LENGTH_LONG).show()
                                }
                            })
                            .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                                override    fun onClick(dialog: DialogInterface, which: Int) {
                                    ActivityCompat.requestPermissions(this@PostTextActivity, PERMISSIONS_STORAGE, REQUEST_CODE)
                                }
                            }).show()

                } else {
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_CODE)
                }
            } else {
                imageConfig = ImageConfig.Builder(GlideLoader())
                        .steepToolBarColor(resources.getColor(R.color.titleBlue))
                        .titleBgColor(resources.getColor(R.color.titleBlue))
                        .titleSubmitTextColor(resources.getColor(R.color.white))
                        .titleTextColor(resources.getColor(R.color.white))
                        // 开启多选   （默认为多选）
                        .mutiSelect()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(9)
                        //设置图片显示容器，参数：、（容器，每行显示数量，是否可删除）
                        .setContainer(llContainer, 4, true)
                        // 已选择的图片路径
                        .pathList(path)
                        // 拍照后存放的图片路径（默认 /temp/picture）
                        .filePath("/temp")
                        // 开启拍照功能 （默认关闭）
                        .showCamera()
                        .requestCode(REQUEST_CODE)
                        .build()
                ImageSelector.open(this, imageConfig)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)

//            tv1.setText("")
//            for (path in pathList) {
//                tv1.append(path)
//                tv1.append("\n")
//            }

            path.clear()
            path.addAll(pathList)
        }
    }
}

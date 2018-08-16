package com.heixiu.errand.MVP.Community

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.mob.tools.utils.ResHelper.getFileSize
import kotlinx.android.synthetic.main.activity_post_text.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PostVideoActivity : BaseActivity() {
    val REQUEST_CODE = 123
    private val path = ArrayList<String>()
    private val fileList = ArrayList<File>()
    private var lisf = ArrayList<RequestBody>()
    private var bodys = ArrayList<MultipartBody.Part>()
    override fun loadViewLayout() {
        setContentView(R.layout.activity_post_text)
        initTitle("视频动态", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        mTitle.setTv_Right("发表", R.color.white, View.OnClickListener {
            if (isFastDoubleClick()) {
                publish()
            }

        })
    }

    private var lastClickTime: Long = 0
    fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (0 < timeD && timeD < 2000) {
            return false
        }
        lastClickTime = time
        return true
    }

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
        var PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
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
                            override fun onClick(dialog: DialogInterface, which: Int) {
                                ActivityCompat.requestPermissions(this@PostVideoActivity, PERMISSIONS_STORAGE, REQUEST_CODE)
                            }
                        }).show()

            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_CODE)
            }
        }
        RxGalleryFinalApi
                .getInstance(this@PostVideoActivity)
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                .setVDRadioResultEvent(object : RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Throws(Exception::class)
                    override fun onEvent(imageRadioResultEvent: ImageRadioResultEvent) {
                        fileList.clear()
                        val file: File = File(imageRadioResultEvent.result.originalPath);
                        fileList.add(file)
                        var uris = Uri.parse(imageRadioResultEvent.result.originalPath)
                        mainIvVido.visibility = View.VISIBLE
                        mainIvVido.setVideoURI(uris)
                        mainIvVido.start()
                    }
                })
                .open()
    }

    fun publish() {
        if (text_content.text.toString().equals("")) {
            ToastUtils.showLong("请输入动态文字内容")
            return
        }

        ToastUtils.showLong("正在发布中,请稍等")
        val paramsMap: Map<String, RequestBody> = MyApplication.getp(fileList)
        ////addFormDataPart()第一个参数为表单名字，这是和后台约定好的
        val builder: MultipartBody.Builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
        //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
        for (i in fileList.indices) {
            var size = getFileSize(fileList.get(i))
            //这里上传的是多图
            builder.addFormDataPart("file", fileList.get(i).getName(), RequestBody.create(MediaType.parse("video/*"), fileList.get(i)));
        }
        val requestBody: RequestBody = builder.build();

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().publish(requestBody, SPUtil.getString("userid"), 1, text_content.text.toString(), "")).subscribe({
            ToastUtils.showLong("发布成功")

        }, {
            ToastUtils.showLong(it.message)
        })
        finishWithAnim()
    }


}
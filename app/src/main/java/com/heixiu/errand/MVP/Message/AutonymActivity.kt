package com.heixiu.errand.MVP.Message

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import cn.finalteam.rxgalleryfinal.RxGalleryFinal
import cn.finalteam.rxgalleryfinal.bean.MediaBean
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.R
import com.heixiu.errand.adapter.DynamicAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.dialog.DialogShowPic
import com.heixiu.errand.dialog.DialogShowPicP
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.GlideLoader
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_autonym.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import top.zibin.luban.CompressionPredicate
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

class AutonymActivity : BaseActivity() {
    val REQUEST_CODE = 123
    private val path = ArrayList<String>()
    private val fileList = ArrayList<File>()
    private var list: List<MediaBean>? = null
    var dynamicAdapter: DynamicAdapter? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_autonym)
    }

    override fun findViewById() {
        initTitle("身份认证", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })

    }

    override fun setListener() {
        autonym_submit.setOnClickListener {
            if (autonym_inname.text.equals("") || autonym_sfz.text.equals("") || fileList.size < 1) {
                ToastUtils.showLong("姓名和身份证号和身份证图片不能为空")
            } else {
                val builder: MultipartBody.Builder =  MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
                for ( i in fileList.indices ) {

                    //这里上传的是多图
                    builder.addFormDataPart("file", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), fileList.get(i)));
                }
                val requestBody: RequestBody = builder.build();
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().uploadIdCard(requestBody, SPUtil.getString("userid"), autonym_inname.text.toString(), autonym_sfz.text.toString())).subscribe({
                    ToastUtils.showLong("保存成功")
                    finishWithAlpha()
                }, {
                    ToastUtils.showLong(it.message)
                })
            }
        }
        autonym_uploading.setOnClickListener {
            startAlbum()
        }
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
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    ActivityCompat.requestPermissions(this@AutonymActivity, PERMISSIONS_STORAGE, REQUEST_CODE)
                                }
                            }).show()

                } else {
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_CODE)
                }
            } else {
                val rxGalleryFinal = RxGalleryFinal
                        .with(this)
                        .image()
                        .multiple()
                if (list != null && !list!!.isEmpty()) {
                    rxGalleryFinal
                            .selected(list!!)
                }
                rxGalleryFinal.maxSize(2)
                        .imageLoader(ImageLoaderType.FRESCO)
                        .subscribe(object : RxBusResultDisposable<ImageMultipleResultEvent>() {

                            @Throws(Exception::class)
                            override fun onEvent(imageMultipleResultEvent: ImageMultipleResultEvent) {
                                list = imageMultipleResultEvent.getResult()
                                fileList.clear()
                                for (i in 0..list!!.size-1) {
                                    var file: File = File(list!![i].originalPath);
                                    fileList.add(file)
                                }
                                llContainer.setLayoutManager(GridLayoutManager(this@AutonymActivity, 4));

                                dynamicAdapter = DynamicAdapter(this@AutonymActivity, list)
                                llContainer.adapter = dynamicAdapter
                                dynamicAdapter!!.setOnItemClickListener(object : DynamicAdapter.OnItemOnClickListener {
                                    override fun onItemOnClick(view: View?, pos: Int) {
                                        DlgForBigPhto(list!![pos].originalPath)
                                    }

                                    override fun onItemLongOnClick(view: View?, pos: Int) {
                                    }

                                })
                                dynamicAdapter?.notifyDataSetChanged()
                            }

                            override fun onComplete() {
                                super.onComplete()
                            }
                        })
                        .openGallery()
            }
        }
    }

    private fun DlgForBigPhto(url: String) {
        val pDialogMy = DialogShowPic(this)
        pDialogMy.SetStyle(R.style.myDialogTheme1)
        pDialogMy.InitDialog(object : DialogShowPicP {
            lateinit var dialog6LlBckgrnd: LinearLayout
            lateinit var dialog6IvPic: ImageView
            override fun SetDialogView(pDialog: Dialog, pDialogMy: DialogShowPic) {
                pDialog.setContentView(R.layout.show_pic)
                dialog6LlBckgrnd = pDialog.findViewById<View>(R.id.dialog6LlBckgrnd) as LinearLayout
                dialog6LlBckgrnd.setOnClickListener(pDialogMy)
                dialog6IvPic = pDialog.findViewById<View>(R.id.dialog6IvPic) as ImageView
                Glide.with(dialog6IvPic.context)
                        .load(url)
                        .crossFade()
                        .into(dialog6IvPic)
            }

            override fun SetOnClickListener(v: View, pDialogMy: DialogShowPic) {
                if (v === dialog6LlBckgrnd) {
                    pDialogMy.Destroy()
                }
            }

            override fun SetOnKeyListener(keyCode: Int, event: KeyEvent) {}
        })
        pDialogMy.Show()
    }
}

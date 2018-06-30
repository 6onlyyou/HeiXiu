package com.heixiu.errand.MVP.Community

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.GlideLoader
import com.heixiu.errand.utils.SPUtil
import com.jaiky.imagespickers.ImageConfig
import com.jaiky.imagespickers.ImageSelector
import com.jaiky.imagespickers.ImageSelectorActivity
import kotlinx.android.synthetic.main.activity_post_text.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import top.zibin.luban.CompressionPredicate
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


class PostTextActivity : BaseActivity() {
    val REQUEST_CODE = 123
    private val path = ArrayList<String>()
    private val fileList = ArrayList<File>()
    private var imageConfig: ImageConfig? = null
    private var lisf = ArrayList<RequestBody>()
    private var bodys = ArrayList<MultipartBody.Part>()
    override fun loadViewLayout() {
        setContentView(R.layout.activity_post_text)
        initTitle("图片动态", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener { finishWithAnim() })
        mTitle.setTv_Right("发表", R.color.white, View.OnClickListener {
            publish()
        })
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
                        .setContainer(llContainer, 4, false)
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

    fun publish() {
        val paramsMap: Map<String, RequestBody> = MyApplication.getp(fileList)


        val builder: MultipartBody.Builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
        //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
        if (fileList.size < 1) {
            builder.addFormDataPart("file", null, RequestBody.create(MediaType.parse("image/*"), ""));
        }
        for (i in fileList.indices) {
            //这里上传的是多图
            builder.addFormDataPart("file", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), fileList.get(i)));
        }
        val requestBody: RequestBody = builder.build();

        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().publish(requestBody, SPUtil.getString("userid"), 0, text_content.text.toString(), "")).subscribe({
            ToastUtils.showLong("发布成功")
            finishWithAnim()
        }, {
            ToastUtils.showLong(it.message)
        })
//        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit()
//                .publish(paramsMap, SPUtil.getString("userid"),0,text_content.text,""))
//                .subscribe(object : Consumer<String>() {
//                    @Throws(Exception::class)
//                    fun accept(s: String) {
//                        picAddress.add(s)
//                        if (picAddress.size() === getView()!!.getFiles().size()) {
//                            if (TextUtils.isEmpty(getView()!!.getVideoPath())) {
//                                addRecord(picAddress)
//                            } else {
//                                requestVideo(File(getView()!!.getVideoPath()))
//                            }
//                        }
//                    }
//                }, object : Consumer<Throwable>() {
//                    @Throws(Exception::class)
//                    fun accept(throwable: Throwable) {
//                        Toast.makeText(getView(), throwable.message, Toast.LENGTH_LONG).show()
//                        Log.e(FragmentActivity.TAG, "accept: " + throwable.message)
//                    }
//                })

    }
//    fun   upload(){
//        //构建body

    //
//        return getRetrofitInterface(URLConstant.URL_BASE).upload(requestBody);
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)
            path.clear()
            path.addAll(pathList)
            fileList.clear()
            Luban.with(this)
                    .load(pathList)
                    .ignoreBy(100)
                    .setTargetDir(getPath())
                    .filter(object : CompressionPredicate {
                        override fun apply(path: String?): Boolean {

                            return !(TextUtils.isEmpty(path) || path!!.toLowerCase().endsWith(".gif"));
                        }


                    })
                    .setCompressListener(object : OnCompressListener {
                        override fun onError(e: Throwable?) {
                            ToastUtils.showLong(e.toString())
                        }

                        override fun onStart() {

                        }

                        override fun onSuccess(file: File) {
                            fileList.add(file)
                        }

                    }).launch();
        }
    }

    fun getPath(): String {
        val path = Environment.getExternalStorageDirectory().toString() + "/Heixiu/image/";
        val file: File = File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}

package com.heixiu.errand.MVP.Message

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.bumptech.glide.Glide
import com.fushuaige.common.utils.GlideUtil
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Seting.AliPayBindActivity
import com.heixiu.errand.MVP.Seting.AuthenticationActivity
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.dialog.DialogShowPic
import com.heixiu.errand.dialog.DialogShowPicP
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.GlideLoader
import com.heixiu.errand.utils.SPUtil
import com.jaiky.imagespickers.ImageConfig
import com.jaiky.imagespickers.ImageSelector
import com.jaiky.imagespickers.ImageSelectorActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.feezu.liuli.timeselector.TimeSelector
import top.zibin.luban.CompressionPredicate
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : BaseActivity() , View.OnClickListener{
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
                //				WindowManager wm = getWindowManager();
                //				int nScreenWidth = wm.getDefaultDisplay().getWidth();
                //				int nWidth = nScreenWidth;
                //				int nHeight = nWidth;
                //				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(nWidth, nHeight);
                //				pDialogView.dialog6IvPic.setLayoutParams(params);
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
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.chaeak_haead->DlgForBigPhto(SPUtil.getString("headurl"))
            R.id.choose_Photo-> startAlbum()
        }
        dialog!!.dismiss();
    }


    private var inflate: View? = null
    private var choosePhoto: TextView? = null
    private var takePhoto: TextView? = null
    private var dialog: Dialog? = null
    val REQUEST_CODE = 123
    private val path = ArrayList<String>()
    private val fileList = ArrayList<File>()
    private var imageConfig: ImageConfig? = null
    override fun loadViewLayout() {
        setContentView(R.layout.activity_edit_profile)
    }

    override fun findViewById() {
        initTitle("编辑资料", R.color.colorPrimary, R.color.white)
        mTitle.setIv_left(R.mipmap.back_btn, View.OnClickListener {
            finishWithAlpha()
        })
        mTitle.setTv_Right("保存", R.color.white,View.OnClickListener{
            var requestBody: RequestBody? = null
            if(fileList.size<1){
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().editDataNoHead(SPUtil.getString("userid"),profile_name.text.toString(),profile_sex.text.toString(),profile_sign.text.toString(),profile_birdday.text.toString())).subscribe({
                    ToastUtils.showLong("保存成功")
                    finishWithAlpha()
                },{
                    ToastUtils.showLong(it.message)
                })
            }else {
                val builder: MultipartBody.Builder = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
                for (i in fileList.indices) {
                    //这里上传的是多图
                    builder.addFormDataPart("file", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), fileList.get(i)));
                }
                    requestBody = builder.build();
                RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().editData(requestBody,SPUtil.getString("userid"),profile_name.text.toString(),profile_sex.text.toString(),profile_sign.text.toString(),profile_birdday.text.toString())).subscribe({
                    ToastUtils.showLong("保存成功")
                    finishWithAlpha()
                },{
                    ToastUtils.showLong(it.message)
                })
            }


    })
    getUserMessage()
        profile_sex.setOnClickListener {
            change_sex()
        }
        profile_birdday.setOnClickListener {
            val d = Date()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val timeSelector = TimeSelector(this, TimeSelector.ResultHandler { time ->
                profile_birdday.text=time
            }, "1960-01-01 00:00", sdf.format(d))
            timeSelector.setIsLoop(false);//设置不循环,true循环
            timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
            timeSelector.show();
        }
        profile_sign.setOnClickListener {
            val inputServer = EditText(this)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("设置签名").setView(inputServer)
                    .setNegativeButton("取消", null)
            builder.setPositiveButton("确定") { dialog, which ->
                profile_sign.setText(inputServer.text.toString())
            }
            builder.show()
        }
        profile_attestation.setOnClickListener {
            if(profile_attestation.text.toString().equals("已绑定")){
                startActivity(AuthenticationActivity::class.java)
            }else {
                startActivity(AutonymActivity::class.java)
            }

        }
        profile_bind.setOnClickListener {
            if(profile_bind.text.toString().equals("已绑定")){
                startActivity(AliPayBindActivity::class.java)
            }else {
                startActivity(AliBindActivity::class.java)
            }
        }
    }
    fun getUserMessage(){
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().selectDataById(SPUtil.getString("userid"))).subscribe({
            profile_nickname.text = it.userInfo.nickName
            profile_signshow.text = it.userInfo.sign
            profile_name.setText(it.userInfo.nickName)
            profile_sex.setText(it.userInfo.sex)
            profile_birdday.setText(it.userInfo.birthday)
            profile_sign.setText(it.userInfo.sign)
            if(it.dbSubAccount.cardStatus.equals("0")){
                profile_attestation.setText("未绑定")
            }else{
                profile_attestation.setText("已绑定")
            }
            if(it.dbSubAccount.zfbStatus.equals("0")){
                profile_bind.setText("未绑定")
            }else{
                profile_bind.setText("已绑定")
            }

            GlideUtil.load(this,it.userInfo.userImg,profile_hard)

        },{
            ToastUtils.showLong(it.message)
        })
        profile_hard.setOnClickListener {
             dialog =  Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_head, null);
        //初始化控件
        choosePhoto =  inflate!!.findViewById(R.id.choose_Photo);
        takePhoto = inflate!!.findViewById(R.id.chaeak_haead);
        choosePhoto!!.setOnClickListener(this);
        takePhoto!!.setOnClickListener(this);
        //将布局设置给Dialog
        dialog!!.setContentView(inflate);
        //获取当前Activity所在的窗体
        var dialogWindow:Window = dialog!!.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
         var lp:WindowManager.LayoutParams = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog!!.show();//显示对话框


        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    fun backgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        window.attributes = lp
    }
    override fun setListener() {

    }

    override fun processLogic() {
    }


    fun change_sex() {
        val builder = AlertDialog.Builder(this) //定义一个AlertDialog
        val strarr = arrayOf("男", "女")
        builder.setItems(strarr, object : DialogInterface.OnClickListener {
          override  fun onClick(arg0: DialogInterface, arg1: Int) {
                var sex = "2"
                // 自动生成的方法存根
                if (arg1 == 0) {//男
                    profile_sex.setText("男")
                    sex = "1"
                } else {//女
                    profile_sex.setText("女")
                    sex = "2"
                }
            }
        })
        builder.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)
            Glide.with(this)
                    .load(pathList.get(0))
                    .crossFade()
                    .placeholder(R.mipmap.defaulthead)
                    .into(profile_hard);
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
                                    ActivityCompat.requestPermissions(this@EditProfileActivity, PERMISSIONS_STORAGE, REQUEST_CODE)
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
                        .singleSelect()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(1)
                        //设置图片显示容器，参数：、（容器，每行显示数量，是否可删除）
//                        .setContainer(profile_hard, 4, false)
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
    fun getPath(): String {
        val path = Environment.getExternalStorageDirectory().toString() + "/Heixiu/image/";
        val file: File = File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


}

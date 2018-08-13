package com.heixiu.errand.MVP.Community

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
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
import com.facebook.common.internal.Objects
import com.facebook.drawee.backends.pipeline.Fresco
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MyApplication.MyApplication
import com.heixiu.errand.R
import com.heixiu.errand.adapter.DynamicAdapter
import com.heixiu.errand.base.BaseActivity
import com.heixiu.errand.dialog.DialogShowPic
import com.heixiu.errand.dialog.DialogShowPicP
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import kotlinx.android.synthetic.main.activity_post_text.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class PostTextActivity : BaseActivity() {
    val IMAGE_PICKER = 0x001
    val CHANGE_NAME = 0x002
    val REQUEST_CODE = 123
    private val path = ArrayList<String>()
    private val fileList = ArrayList<File>()
    //    private var imageConfig: ImageConfig? = null
    private var lisf = ArrayList<RequestBody>()
    private var bodys = ArrayList<MultipartBody.Part>()
    override fun loadViewLayout() {
        setContentView(R.layout.activity_post_text)
        initTitle("图片动态", R.color.colorPrimary, R.color.white)
        Fresco.initialize(this);
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

    private var list: List<MediaBean>? = null
    //    private val imglist: MutableList<MediaBean>? = null
    var dynamicAdapter: DynamicAdapter? = null

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
                val rxGalleryFinal = RxGalleryFinal
                        .with(this)
                        .image()
                        .multiple()
                if (list != null && !list!!.isEmpty()) {
                    rxGalleryFinal
                            .selected(list!!)
                }
                rxGalleryFinal.maxSize(8)
                        .imageLoader(ImageLoaderType.FRESCO)
                        .subscribe(object : RxBusResultDisposable<ImageMultipleResultEvent>() {

                            @Throws(Exception::class)
                            override fun onEvent(imageMultipleResultEvent: ImageMultipleResultEvent) {
                                list = imageMultipleResultEvent.getResult()
                                if(fileList.size>9){
                                    ToastUtils.showLong("已超出最大上传图片数量")
                                    return
                                }
//                                fileList.clear()
                                for (i in 0..list!!.size-1) {
                                    var file: File = File(list!![i].originalPath);
                                    fileList.add(file)
//                                    Luban.with(this@PostTextActivity)
//                                            .load(list!![i].originalPath)
//                                            .ignoreBy(100)
//                                            .setTargetDir(getPath())
//                                            .filter(object : CompressionPredicate {
//                                                override fun apply(path: String?): Boolean {
//                                                    return !(TextUtils.isEmpty(path) || path!!.toLowerCase().endsWith(".gif"));
//                                                }
//                                            })
//                                            .setCompressListener(object : OnCompressListener {
//                                                override fun onError(e: Throwable?) {
//                                                    ToastUtils.showLong(e.toString())
//                                                }
//
//                                                override fun onStart() {
//                                                }
//                                                override fun onSuccess(file: File) {
//                                                    fileList.add(file)
//                                                }
//                                            }).launch();
                                }

//                                for (i in 0..list!!.size-1){
////                                    var path = list!![i].originalPath
//                                    imglist!!.add(path)
//                                }
                                llContainer.setLayoutManager(GridLayoutManager(this@PostTextActivity, 4));

                                dynamicAdapter = DynamicAdapter(this@PostTextActivity, list)
                                llContainer.adapter = dynamicAdapter
                                dynamicAdapter!!.setOnItemClickListener(object : DynamicAdapter.OnItemOnClickListener {
                                    override fun onItemOnClick(view: View?, pos: Int) {
                                        DlgForBigPhto(list!![pos].originalPath)
                                    }

                                    override fun onItemLongOnClick(view: View?, pos: Int) {
                                        dynamicAdapter!!.removeItem(pos)
                                        fileList.removeAt(pos)
                                        Toast.makeText(this@PostTextActivity, "已删除", Toast.LENGTH_SHORT).show() //To change body of created functions use File | Settings | File Templates.
                                    }

                                })
                                dynamicAdapter?.notifyDataSetChanged()
                                Toast.makeText(baseContext, "已选择" + imageMultipleResultEvent.getResult().size + "张图片", Toast.LENGTH_SHORT).show()
                            }

                            override fun onComplete() {
                                super.onComplete()
                                Toast.makeText(baseContext, "OVER", Toast.LENGTH_SHORT).show()
                            }
                        })
                        .openGallery()
//                imageConfig = ImageConfig.Builder(GlideLoader())
//                        .steepToolBarColor(resources.getColor(R.color.titleBlue))
//                        .titleBgColor(resources.getColor(R.color.titleBlue))
//                        .titleSubmitTextColor(resources.getColor(R.color.white))
//                        .titleTextColor(resources.getColor(R.color.white))
//                        // 开启多选   （默认为多选）
//                        .mutiSelect()
//                        // 多选时的最大数量   （默认 9 张）
//                        .mutiSelectMaxSize(9)
//                        //设置图片显示容器，参数：、（容器，每行显示数量，是否可删除）
//                        .setContainer(llContainer, 4, false)
//                        // 已选择的图片路径
//                        .pathList(path)
//                        // 拍照后存放的图片路径（默认 /temp/picture）
//                        .filePath("/temp")
//                        // 开启拍照功能 （默认关闭）
//                        .showCamera()
//                        .requestCode(REQUEST_CODE)
//                        .build()
//                ImageSelector.open(this, imageConfig)
            }
        }
    }

    //    var popupMenu:PopupMenu ? =null
//    fun showPopMenu( view:View,  pos:Int){
//           popupMenu =  PopupMenu(this,view);
//        popupMenu!!.getMenuInflater().inflate(R.menu.item_menu,popupMenu!!.getMenu());
//        popupMenu!!.setOnMenuItemClickListener( PopupMenu.OnMenuItemClickListener() {
////            fun  onMenuItemClick(MenuItem item):Boolean {
////
////                myAdapter.removeItem(pos);
////                return false;
////            }
//        });
////        popupMenu.setOnDismissListener( PopupMenu.OnDismissListener() {
////            @Override
////            public void onDismiss(PopupMenu menu) {
////                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
////            }
////        });
//        popupMenu!!.show();
//    }
//}
    fun publish() {
        val paramsMap: Map<String, RequestBody> = MyApplication.getp(fileList)
        if (text_content.text.toString().equals("")) {
            ToastUtils.showLong("请输入动态文字内容")
            return
        }
        ToastUtils.showLong("正在发布中,请稍等")
        val builder: MultipartBody.Builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
        //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
//        fileList.clear()


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
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == IMAGE_PICKER) {
//                val images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
//                path.clear()
//                for (i in 0..images.size-1) {
//                    path.add(images[i].path)
//                }
//                fileList.clear()
//                Luban.with(this)
//                        .load(path)
//                        .ignoreBy(100)
//                        .setTargetDir(getPath())
//                        .filter(object : CompressionPredicate {
//                            override fun apply(path: String?): Boolean {
//                                return !(TextUtils.isEmpty(path) || path!!.toLowerCase().endsWith(".gif"));
//                            }
//
//
//                        })
//                        .setCompressListener(object : OnCompressListener {
//                            override fun onError(e: Throwable?) {
//                                ToastUtils.showLong(e.toString())
//                            }
//
//                            override fun onStart() {
//                            }
//                            override fun onSuccess(file: File) {
//                                fileList.add(file)
//                            }
//                        }).launch();
////                GlideUtil.load(mContext, images[0].path, mAvatarIv)
////                mPresenter.updateAvatar(File(images[0].path))
//            }
//        }
////        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
////            val pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)
////            path.clear()
////            path.addAll(pathList)
////            fileList.clear()
////            Luban.with(this)
////                    .load(pathList)
////                    .ignoreBy(100)
////                    .setTargetDir(getPath())
////                    .filter(object : CompressionPredicate {
////                        override fun apply(path: String?): Boolean {
////                            return !(TextUtils.isEmpty(path) || path!!.toLowerCase().endsWith(".gif"));
////                        }
////
////
////                    })
////                    .setCompressListener(object : OnCompressListener {
////                        override fun onError(e: Throwable?) {
////                            ToastUtils.showLong(e.toString())
////                        }
////
////                        override fun onStart() {
////                        }
////                        override fun onSuccess(file: File) {
////                            fileList.add(file)
////                        }
////                    }).launch();
////        }
//    }

    fun getPath(): String {
        val path = Environment.getExternalStorageDirectory().toString() + "/Heixiu/image/";
        val file: File = File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
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
}

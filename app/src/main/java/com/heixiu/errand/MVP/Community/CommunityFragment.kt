package com.heixiu.errand.MVP.Community


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.MVP.Login.LoginActivity
import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommounityAdapter
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.bean.PubLishInfo
import com.heixiu.errand.net.RetrofitFactory
import com.heixiu.errand.net.RxUtils
import com.heixiu.errand.utils.SPUtil
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import com.xiaochao.lcrapiddeveloplibrary.Video.JCVideoPlayer
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.issue_dialog.view.*
import java.util.*
import android.net.wifi.p2p.WifiP2pDevice.CONNECTED
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkInfo
import android.widget.Toast
import com.baidu.mapsdkvi.VDeviceAPI.getNetworkInfo




/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    override fun onRefresh() {
        swipeLayout.setRefreshing(true)
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().showAllPublishInfo(SPUtil.getString("userid"))).subscribe({
            swipeLayout.setRefreshing(false)
            if (it.size > 0) {
                rv_list.setLayoutManager(LinearLayoutManager(context))
//        如果Item高度固定  增加该属性能够提高效率
                rv_list.setHasFixedSize(true)
//        设置适配器
                commounityAdapter = CommounityAdapter(it)
                //设置加载动画
                commounityAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                //设置是否自动加载以及加载个数
                //将适配器添加到RecyclerView
                rv_list.setAdapter(commounityAdapter)
                //设置自动加载监听
            }
        }, {
            swipeLayout.setRefreshing(false)
            ToastUtils.showLong(it.message)
        })
    }

    var page: Int = 0
    var mCameraDialog: Dialog? = null
    internal var commounityAdapter: CommounityAdapter? = null
    private val dynamicEntityList = ArrayList<PubLishInfo>()
    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_community, container, false)
    }
    /**
     * 网络已经连接，然后去判断是wifi连接还是GPRS连接
     * 设置一些自己的逻辑调用
     */
    private var manager: ConnectivityManager? = null
    private fun isNetworkAvailable() {
//        manager = (manager)getSystemService(Context.CONNECTIVITY_SERVICE)
        val gprs = manager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
        val wifi = manager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
        if (gprs === NetworkInfo.State.CONNECTED || gprs === NetworkInfo.State.CONNECTING) {
            Toast.makeText(activity, "您是数据网络连接请注意视频流量", Toast.LENGTH_SHORT).show()
        }
//        //判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
//        if (wifi === NetworkInfo.State.CONNECTED || wifi === NetworkInfo.State.CONNECTING) {
//            Toast.makeText(this, "你是wife连接", Toast.LENGTH_SHORT).show()
//        }

    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }
    override fun initView() {
        rv_list.setLayoutManager(LinearLayoutManager(activity))
        //如果Item高度固定  增加该属性能够提高效率
        rv_list.setHasFixedSize(true)
        //设置适配器
        val list = ArrayList<PubLishInfo>()
        commounityAdapter = CommounityAdapter(list)
        commounityAdapter!!.setOnRecyclerViewItemClickListener { view, position ->
            startActivity(VideoInfoActivity::class.java, dynamicEntityList.get(position))
        }
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(Color.parseColor("#FF4081"), Color.parseColor("#643ac1"))
    }

    override fun initListener() {

        Bt_hot.setOnClickListener {
            if (mCameraDialog != null) {
                mCameraDialog!!.dismiss()
            }
            Bt_hot.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            Bt_issue.setTextColor(ContextCompat.getColor(context!!, R.color.gray_text))
        }
        Bt_issue.setOnClickListener {
            if (SPUtil.getString("userid").equals("") || SPUtil.getString("userid").equals("1")) {
                startActivity(LoginActivity::class.java)
                return@setOnClickListener
            }
            Bt_hot.setTextColor(ContextCompat.getColor(context!!, R.color.gray_text))
            Bt_issue.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            setDialog()
        }
    }

    override fun initData() {
        RxUtils.wrapRestCall(RetrofitFactory.getRetrofit().showAllPublishInfo(SPUtil.getString("userid"))).subscribe({
            if (it.size > 0) {
                rv_list.setLayoutManager(LinearLayoutManager(context))
//        如果Item高度固定  增加该属性能够提高效率
                rv_list.setHasFixedSize(true)
//        设置适配器
                commounityAdapter = CommounityAdapter(it)
                //设置加载动画
                commounityAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                //设置是否自动加载以及加载个数
                //将适配器添加到RecyclerView
                rv_list.setAdapter(commounityAdapter)
                //设置自动加载监听
            }
        }, {
            ToastUtils.showLong(it.message)
        })
    }

    override fun onLoadMoreRequested() {


    }

    private fun setDialog() {
        mCameraDialog = Dialog(context, R.style.BottomDialogs)
        val root = LayoutInflater.from(context).inflate(
                R.layout.issue_dialog, null) as LinearLayout
        //初始化视图
        root.Tv_text.setOnClickListener(this)
        root.Tv_pickup.setOnClickListener(this)
        root.Tv_pic.setOnClickListener(this)
        root.Iv_cancel.setOnClickListener(this)
        mCameraDialog!!.setContentView(root)
        val dialogWindow = mCameraDialog!!.getWindow()
        dialogWindow.setGravity(Gravity.BOTTOM)
        //        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        val lp = dialogWindow.getAttributes() // 获取对话框当前的参数值
        lp.x = 0 // 新位置X坐标
        lp.y = 0 // 新位置Y坐标
        lp.width = resources.displayMetrics.widthPixels.toInt() // 宽度
        root.measure(0, 0)
        lp.height = root.getMeasuredHeight()

        lp.alpha = 9f // 透明度
        dialogWindow.setAttributes(lp)
        mCameraDialog!!.show()
    }

    override fun onClick(view: View?) {
        when (view!!.getId()) {
            R.id.Tv_text ->
                //弹出对话框
                startActivity(PostTextActivity::class.java)
//                ToastUtils.showLong("文字")
            R.id.Tv_pickup ->
                //选择照片按钮
                startActivity(PostVideoActivity::class.java)
            R.id.Tv_pic ->
                //拍照按钮
                ToastUtils.showLong("相册")
            R.id.Iv_cancel ->
                //取消按钮
                if (mCameraDialog != null) {
                    mCameraDialog!!.dismiss()
                    Bt_hot.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    Bt_issue.setTextColor(ContextCompat.getColor(context!!, R.color.gray_text))
                }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}// Required empty public constructor

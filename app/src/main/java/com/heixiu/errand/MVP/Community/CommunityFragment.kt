package com.heixiu.errand.MVP.Community


import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heixiu.errand.MVP.Community.entity.DynamicEntity
import com.heixiu.errand.R
import com.heixiu.errand.adapter.CommounityAdapter
import com.heixiu.errand.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_community.*
import java.util.ArrayList
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter
import android.app.Dialog
import android.view.Gravity
import android.widget.LinearLayout
import com.fushuaige.common.utils.ToastUtils
import kotlinx.android.synthetic.main.issue_dialog.view.*


/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : BaseFragment(), BaseQuickAdapter.RequestLoadMoreListener,View.OnClickListener  {



    var page: Int = 0
    var mCameraDialog: Dialog? = null
    internal var mRefreshLayout: SwipeRefreshLayout? = null
    internal var commounityAdapter: CommounityAdapter? = null
    private val dynamicEntityList = ArrayList<DynamicEntity>()
    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_community, container, false)

    }
    override fun initView() {
        rv_list.setLayoutManager(LinearLayoutManager(activity))
        //如果Item高度固定  增加该属性能够提高效率
        rv_list.setHasFixedSize(true)
        var dynamicEntity:DynamicEntity? = DynamicEntity()
        dynamicEntity!!.comment="232"
        dynamicEntity!!.headurl="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1216917597,333247716&fm=85&s=FDA58F54CD227724227918C00300E0BC"
        dynamicEntity!!.id=1
        dynamicEntity!!.introduction="好的好啊大家的计算机数据介绍"
        dynamicEntity!!.nickname="fushaige"
        dynamicEntity!!.pictureUrl="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1216917597,333247716&fm=85&s=FDA58F54CD227724227918C00300E0BC"
        dynamicEntity!!.praise="2322"
        dynamicEntity!!.viodeoUrl="http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4"
        dynamicEntity!!.title="标题"
        dynamicEntityList.add(dynamicEntity);
        dynamicEntity = DynamicEntity()
        dynamicEntity!!.comment="123"
        dynamicEntity!!.headurl="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1216917597,333247716&fm=85&s=FDA58F54CD227724227918C00300E0BC"
        dynamicEntity!!.id=1
        dynamicEntity!!.introduction="好的好4323342收到啊大家的计算机数据介绍"
        dynamicEntity!!.nickname="fushaige23234"
        dynamicEntity!!.pictureUrl="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1216917597,333247716&fm=85&s=FDA58F54CD227724227918C00300E0BC"
        dynamicEntity!!.praise="54322"
        dynamicEntity!!.viodeoUrl=""
        dynamicEntity!!.title="标题的撒大"
        dynamicEntityList.add(dynamicEntity);

        //设置适配器
        commounityAdapter = CommounityAdapter(R.layout.community_item, dynamicEntityList)
        //设置加载动画
        commounityAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        //设置是否自动加载以及加载个数
        commounityAdapter!!.openLoadMore(1, true)
        //将适配器添加到RecyclerView
        rv_list.setAdapter(commounityAdapter)
        //设置自动加载监听
        commounityAdapter!!.setOnLoadMoreListener(this)
        val list = ArrayList<DynamicEntity>()
        commounityAdapter = CommounityAdapter(list)
    }
    override fun initListener() {
        Bt_hot.setOnClickListener{
            if(mCameraDialog!=null) {
                mCameraDialog!!.dismiss()
            }
            Bt_hot.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            Bt_issue.setTextColor(ContextCompat.getColor(context!!, R.color.gray_text))
        }
        Bt_issue.setOnClickListener{
            Bt_hot.setTextColor(ContextCompat.getColor(context!!, R.color.gray_text))
            Bt_issue.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            setDialog()
        }
    }

    override fun initData() {
    }
    override fun onLoadMoreRequested() {
        var dynamicEntity:DynamicEntity? = DynamicEntity()
        dynamicEntity!!.comment="232"
        dynamicEntity!!.headurl="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1216917597,333247716&fm=85&s=FDA58F54CD227724227918C00300E0BC"
        dynamicEntity!!.id=1
        dynamicEntity!!.introduction="好的好啊大家的计算机数据介绍"
        dynamicEntity!!.nickname="fushaige"
        dynamicEntity!!.pictureUrl="http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4"
        dynamicEntity!!.praise="2322"
        dynamicEntity!!.viodeoUrl=""
        dynamicEntity!!.title="标题"
        dynamicEntityList.add(dynamicEntity);
        commounityAdapter!!.setNewData(dynamicEntityList);//新增数据
//        commounityAdapter!!.openLoadMore(2,true);
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
                ToastUtils.showLong("文字")
            R.id.Tv_pickup ->
                //选择照片按钮
                ToastUtils.showLong("摄像")
            R.id.Tv_pic ->
                //拍照按钮
                ToastUtils.showLong("相册")
            R.id.Iv_cancel ->
                //取消按钮
                if(mCameraDialog!=null) {
                    mCameraDialog!!.dismiss()
                    Bt_hot.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    Bt_issue.setTextColor(ContextCompat.getColor(context!!, R.color.gray_text))
                }
        }
    }
}// Required empty public constructor

package com.example.app.MVP.Community


import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.MVP.Community.entity.DynamicEntity
import com.example.app.R
import com.example.app.adapter.CommounityAdapter
import com.example.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_community.*
import java.util.ArrayList
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter




/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : BaseFragment(), BaseQuickAdapter.RequestLoadMoreListener {


    var page: Int = 0
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
        commounityAdapter!!.openLoadMore(2, true)
        //将适配器添加到RecyclerView
        rv_list.setAdapter(commounityAdapter)
        //设置自动加载监听
        commounityAdapter!!.setOnLoadMoreListener(this)
        val list = ArrayList<DynamicEntity>()
        commounityAdapter = CommounityAdapter(list)
    }
    override fun initListener() {

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
        commounityAdapter!!.openLoadMore(2,true);
    }

}// Required empty public constructor

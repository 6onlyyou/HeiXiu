package com.heixiu.errand.MVP.Contentt


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.geocode.*
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.baidu.mapapi.utils.DistanceUtil
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.fushuaige.common.utils.ToastUtils
import com.heixiu.errand.Event.PublishParamsChangeEvent
import com.heixiu.errand.MVP.common.TicketActivity
import com.heixiu.errand.R
import com.heixiu.errand.adapter.SpinnerAdapter
import com.heixiu.errand.base.BaseFragment
import com.heixiu.errand.base.Contants
import com.heixiu.errand.bean.CouponTicketBean
import com.heixiu.errand.bean.OrderInfo
import com.heixiu.errand.dialog.AddPriceDialog
import com.heixiu.errand.dialog.ChooseWeightDialog
import com.heixiu.errand.dialog.InputAddressDialog
import com.heixiu.errand.utils.CityUtils
import com.heixiu.errand.utils.RxBus
import kotlinx.android.synthetic.main.fragment_content.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : BaseFragment(), InputAddressDialog.OnAddressConfirm {

    override fun addressConfirm(address: String, type: String) {
        if (type.equals(Contants.RECEIVER)) {
            ContentFragment.receiveAddress = address
            receiveAddress.text = address
        }

        if (type.equals(Contants.SEND)) {
            sendAddress.text = address
            ContentFragment.sendAddress = address
        }
    }

    companion object {
        var receiveAddress: String = ""
        var sendAddress: String = ""
        var sendTime: String = ""
        var packageType: String = ""
        var packageWeight: String = ""
        var courierNum: String = ""
        var addPrice: String = "0"
        var receiverName = ""
        var receiverNum = ""
        var descriptions = ""
        var sendLat = 0.00
        var sendLon = 0.00
        var receiveLat = 0.00
        var receiveLon = 0.00
        var ticketBean: CouponTicketBean? = null
    }

    private val days = ArrayList<String>()
    private val hour = ArrayList<Int>()
    private val mintnues = ArrayList<Int>()

    override fun initView() {

    }

    fun dealParamsState() {
        if ((ContentFragment.addPrice.toInt()) > 0) {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_add_price_publish_over)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            add_price_tv.setCompoundDrawables(null, drawable, null, null)
        } else {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_add_price_publish)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            add_price_tv.setCompoundDrawables(null, drawable, null, null)
        }

        if (ContentFragment.ticketBean != null) {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_ticket_publish_over)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            discount_coupon_tv.setCompoundDrawables(null, drawable, null, null)
        } else {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_discount_coupon)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            discount_coupon_tv.setCompoundDrawables(null, drawable, null, null)
        }

        if (!TextUtils.isEmpty(ContentFragment.packageWeight)) {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_weight_publish_over)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            package_weight_tv.setCompoundDrawables(null, drawable, null, null)
        } else {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_weight_publish)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            package_weight_tv.setCompoundDrawables(null, drawable, null, null)
        }

        if (!TextUtils.isEmpty(ContentFragment.packageType) && !TextUtils.isEmpty(ContentFragment.receiverName)
                && !TextUtils.isEmpty(ContentFragment.receiverNum)) {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_publish_package_over)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            package_type_tv.setCompoundDrawables(null, drawable, null, null)
        } else {
            var drawable = context?.resources?.getDrawable(R.mipmap.ic_package_publish)
            drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            package_type_tv.setCompoundDrawables(null, drawable, null, null)
        }
    }

    private var suggest: MutableList<String>? = ArrayList()

    override fun createView(inflater: LayoutInflater?, container: ViewGroup?): View {
        days.add("今天")
        days.add("明天")

        (0..24 step 1).mapTo(hour) { it }
        (1..60 step 1).mapTo(mintnues) { it }

        addressData.add("杭州市")
        var cityList = CityUtils.getJson("city.json", context)
        for (cityBean in cityList) {
            addressData.add(cityBean.name + "市")
        }
        RxBus.getDefault().toObservable(PublishParamsChangeEvent::class.java).subscribe({
            dealParamsState()
        }, {

        })
        return inflater!!.inflate(R.layout.fragment_content, container, false)
    }

    lateinit var sugAdapter: ArrayAdapter<String>
    var sendAddressType: Int = 0
    var receiveAddressType: Int = 1
    var addressType = -1
    var choosePosition = -1
    private var addressData: MutableList<String> = ArrayList()
    var spinnerAdapter: SpinnerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputAddressEt.threshold = 1
        inputAddressEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    searchAddress()
                }
            }
        })

        sugAdapter = ArrayAdapter(context, R.layout.item_auto_text, R.id.address_name)
        inputAddressEt.setAdapter(sugAdapter)
        inputAddressEt.setOnItemClickListener { parent, view, position, id ->
            hideInput(context!!, inputAddressEt)
            getLocationPosition(suggest?.get(position)!!)
        }

        spinnerAdapter = SpinnerAdapter(context)
        spinnerAdapter?.setDatas(addressData)
        city.adapter = spinnerAdapter
        city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                ToastUtils.showShort("请选择")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                choosePosition = position
            }
        }

        //取货地址
        receiveAddress.setOnClickListener({
            addressType = receiveAddressType
            addressLayout.visibility = View.VISIBLE
        })
        //送货地址
        sendAddress.setOnClickListener({
            addressType = sendAddressType
            addressLayout.visibility = View.VISIBLE
        })
        cancel.setOnClickListener({
            addressLayout.visibility = View.GONE
        })
        //送货时间
        sendTime.setOnClickListener({
            val pvNoLinkOptions: OptionsPickerView<Any> = OptionsPickerBuilder(context, object : OnOptionsSelectListener {
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                    dealTime(options1, options2, options3)
                }
            }).build()
            pvNoLinkOptions.setNPicker(days as List<Any>?, hour as List<Any>?, mintnues as List<Any>?)
            pvNoLinkOptions.setSelectOptions(0, 1, 1)
            pvNoLinkOptions.show()
        })
        //物品类型
        package_type_tv.setOnClickListener({
            PackageTypeActivity.startSelf(context!!)
        })
        //物品重量
        package_weight_tv.setOnClickListener({
            ChooseWeightDialog(context!!).show()
        })
        //物品优惠券
        discount_coupon_tv.setOnClickListener({
            TicketActivity.startSelf(context!!)
        })
        //加价
        add_price_tv.setOnClickListener({
            AddPriceDialog(context!!).show()
        })

        submit.setOnClickListener({
            if (TextUtils.isEmpty(ContentFragment.receiveAddress) ||
                    TextUtils.isEmpty(ContentFragment.sendAddress) ||
                    TextUtils.isEmpty(ContentFragment.sendTime) ||
                    TextUtils.isEmpty(ContentFragment.packageType) ||
                    TextUtils.isEmpty(ContentFragment.packageWeight)
                    || TextUtils.isEmpty(ContentFragment.receiverName)
                    || TextUtils.isEmpty(ContentFragment.receiverNum)
                    ) {
                ToastUtils.showLong("信息不完整")
            } else {
                try {
                    var orderInfo = OrderInfo()
                    orderInfo.receiveAddress = ContentFragment.receiveAddress
                    orderInfo.sendAddress = ContentFragment.sendAddress
                    orderInfo.sendTime = ContentFragment.sendTime
                    orderInfo.name = ContentFragment.packageType
                    orderInfo.addPrice = ContentFragment.addPrice.toInt()
                    orderInfo.receiveName = ContentFragment.receiverName
                    orderInfo.receiveNum = ContentFragment.receiverNum
                    orderInfo.weight = ContentFragment.packageWeight.toInt()
                    orderInfo.description = ContentFragment.descriptions
                    orderInfo.courierNum = ContentFragment.courierNum
                    orderInfo.destinationsLatitude = ContentFragment.sendLat
                    orderInfo.destinationsLongitude = ContentFragment.sendLon
                    orderInfo.originsLatitude = ContentFragment.receiveLat
                    orderInfo.originsLongitude = ContentFragment.receiveLon

                    var distance = DistanceUtil.getDistance(
                            LatLng(orderInfo.destinationsLatitude, orderInfo.destinationsLongitude),
                            LatLng(orderInfo.originsLatitude, orderInfo.originsLongitude)) / 1000

                    if (distance > 8) {
                        ToastUtils.showShort("配送距离超过八公里，请重新选择位置")
                    } else {
                        ConfirmPublishOrderActivity.startSelf(context!!, orderInfo)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun hideInput(context: Context, view: View) {
        var inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun dealTime(options1: Int, options2: Int, options3: Int) {
        val c = Calendar.getInstance()//
        var mYear = c.get(Calendar.YEAR) // 获取当前年份
        var mMonth = c.get(Calendar.MONTH)// 获取当前月份
        var mDay = c.get(Calendar.DAY_OF_MONTH)// 获取当日期

        if (options1 == 0) {
            c.set(mYear, mMonth, mDay, hour[options2], mintnues[options3])
        }
        if (options1 == 1) {
            c.add(Calendar.DAY_OF_YEAR, 1)
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), hour[options2], mintnues[options3])
        }
        sendTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.timeInMillis)
        ContentFragment.sendTime = sendTime.text.toString()
    }

    override fun initListener() {

    }

    var mSuggestionSearch: SuggestionSearch? = null

    override fun initData() {
        mSuggestionSearch = SuggestionSearch.newInstance()
        mSuggestionSearch?.setOnGetSuggestionResultListener {
            if (it?.allSuggestions == null) {
                Log.i("result: ", "未找到相关结果\n")
            } else {
                //获取在线建议检索结果
                var resl: List<SuggestionResult.SuggestionInfo> = it.allSuggestions
                suggest?.clear()
                for (suggestionInfo in resl) {
                    Log.i("result: ", "city" + suggestionInfo.city + " dis " + suggestionInfo.district + "key " + suggestionInfo.key)
                    if (suggestionInfo.key != null && !TextUtils.isEmpty(suggestionInfo.city)) {
                        suggest?.add(suggestionInfo.key)
                    }
                }
                sugAdapter = ArrayAdapter(context, R.layout.item_auto_text, R.id.address_name, suggest)
                inputAddressEt.setAdapter(sugAdapter)
                sugAdapter.notifyDataSetChanged()
            }
        }
    }

    fun searchAddress() {
        if (choosePosition == -1) {
            ToastUtils.showShort("请选择城市")
            return
        }

        mSuggestionSearch?.requestSuggestion(SuggestionSearchOption().keyword(inputAddressEt.text.toString()).city(addressData[choosePosition]))
    }

    private fun getLocationPosition(address: String) {
        mSearch = GeoCoder.newInstance()
        mSearch?.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(result: GeoCodeResult?) {
                if (result != null && result.location != null) {
                    when (addressType) {
                        sendAddressType -> {
                            ContentFragment.sendAddress = address
                            ContentFragment.sendLon = result.location.longitude
                            ContentFragment.sendLat = result.location.latitude
                            sendAddress.text = address
                        }
                        receiveAddressType -> {
                            ContentFragment.receiveAddress = address
                            ContentFragment.receiveLon = result.location.longitude
                            ContentFragment.receiveLat = result.location.latitude
                            receiveAddress.text = address
                        }
                    }
                    inputAddressEt.setText("")
                    addressLayout.visibility = View.GONE
                }
            }

            override fun onGetReverseGeoCodeResult(reverse: ReverseGeoCodeResult?) {

            }
        })
        mSearch?.geocode(GeoCodeOption().city(
                addressData[choosePosition]).address(address))
    }


    internal var mSearch: GeoCoder? = null
}

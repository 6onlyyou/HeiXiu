package com.heixiu.errand.net;

import com.heixiu.errand.bean.CouponTicketBean;
import com.heixiu.errand.bean.MessageInfoBean;
import com.heixiu.errand.bean.MyAddressInfo;
import com.heixiu.errand.bean.MyFansBean;
import com.heixiu.errand.bean.MyPublishOrderBean;
import com.heixiu.errand.bean.OrderInfo;
import com.heixiu.errand.bean.PackageInformationBean;
import com.heixiu.errand.bean.PhoneToken;
import com.heixiu.errand.bean.PubLishInfo;
import com.heixiu.errand.bean.PublishInfoDetail;
import com.heixiu.errand.bean.QueryCertificationStatusBean;
import com.heixiu.errand.bean.QueryMyIncomeBean;
import com.heixiu.errand.bean.QueryPersonalBean;
import com.heixiu.errand.bean.ResponseBean;
import com.heixiu.errand.bean.SelectDataByIdBean;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by YuanGang on 2018/5/4.
 */

public interface ApiService {

    /**
     * 获取手机验证码
     *
     * @return
     */
    @POST("user/loginByPhone")
    Observable<ResponseBean<PhoneToken>> loginByPhone(@Query("userId") String phone, @Query("city") String city);

    /**
     * 帐号密码登录
     *
     * @return
     */
    @POST("user/loginByAccount")
    Observable<ResponseBean<PhoneToken>> loginByAccount(@Query("userName") String userName, @Query("password") String password, @Query("city") String city);

    /**
     * 用户注册
     *
     * @return
     */
    @POST("user/register")
    Observable<ResponseBean> register(@Query("phone") String phone, @Query("userName") String userName, @Query("password") String password);
    /**
     * 首页所有用户的动态展示
     * @return
     */
    @POST("showAllPublishInfo")
    Observable<ResponseBean<List<PubLishInfo>>> showAllPublishInfo(@Query("userId") String phone);

    /**
     * 查询一条动态详情
     *
     * @return
     */
    @POST("showOnePublishInfoDetail")
    Observable<ResponseBean<PublishInfoDetail>> showOnePublishInfoDetail(@Query("publishId") String publishId, @Query("userId") String userid);


    /**
     * 发布动态
     *
     * @return
     */
    @POST("publish")
    Observable<ResponseBean<String>> publish(@Url String url,
                                             @Part MultipartBody.Part avatar,
                                             @Query("userId") String userid,
                                             @Query("type") String type,
                                             @Query("content") String content,
                                             @Query("title") String title);

    /**
     * 点赞
     *
     * @param publishId
     * @return city
     */
    @POST("userAdmire")
    Observable<ResponseBean<String>> userAdmire(@Query("admireUserId") String admireUserId,
                                                @Query("publishId") String publishId,
                                                          @Query("userId") String userId
    );

    /**
     * 评论
     * userId	当前用户id(评论人)
     * content	评论内容
     * publishId	动态id
     * admireId	被评论用户id
     *
     * @return
     */
    @POST("createComment")
    Observable<ResponseBean<String>> createComment(@Query("userId") String userId,
                                                   @Query("admireId") String admireId,
                                                   @Query("content") String content,
                                                   @Query("publishId") String publishId
    );

    /**
     * 新建订单
     *
     * @param
     * @return
     */
    @POST("createOrder")
    Observable<ResponseBean<String>> createOrder(
            @Query("userId") String userId,
            @Query("sendAddress") String sendAddress,
            @Query("receiveAddress") String receiveAddress,
            @Query("name") String name,
            @Query("weight") int weight,
            @Query("addPrice") int addPrice,
            @Query("description") String description,
            @Query("receiveName") String receiveName,
            @Query("receiveNum") String receiveNum,
            @Query("courierNum") String courierNum,
            @Query("supportPrice") int supportPrice,
            @Query("payment") int payment,
            @Query("originsLatitude") String originsLatitude,
            @Query("originsLongitude") String originsLongitude,
            @Query("destinationsLatitude") String destinationsLatitude,
            @Query("destinationsLongitude") String destinationsLongitude
    );


    /**
     * 首页查询订单
     *
     * @return
     */
    @POST("queryCreatedOrderInfo")
    Observable<ResponseBean<List<OrderInfo>>> queryCreatedOrderInfo();

    /**
     * 查看订单详情
     *
     * @return
     */
    @POST("queryOneOrderInfo")
    Observable<ResponseBean<List<OrderInfo>>> queryOneOrderInfo(@Query("orderNum") String orderNum);

    /**
     * 修改订单状态
     * “0” : 刚创建
     * “1” : 已接单
     * “2” : 已取货
     * “3” : 已送达
     * “4” : 完成
     *
     * @return
     */
    @POST("changeOrderStatus")
    Observable<ResponseBean<String>> changeOrderStatus(@Query("orderNum") String orderNum
            , @Query("receiveId") String receiveId, @Query("orderStatus") String orderStatus);

    /**
     * 获取优惠券列表
     *
     * @return
     */
    @POST("coupon/list")
    Observable<ResponseBean<List<CouponTicketBean>>> couponList();

    /**
     * 我发布的
     *
     * @return
     */
    @POST("queryMyPublishOrderInfos")
    Observable<ResponseBean<List<OrderInfo>>> MyIssued(@Query("userId") String userId);

    /**
     * 我的任务
     *
     * @return
     */
    @POST("queryAllMyRecieveOrderInfos")
    Observable<ResponseBean<List<OrderInfo>>> queryAllMyRecieveOrderInfos(@Query("userId") String userId);

    /**
     * 个人中心
     *
     * @return
     */
    @POST("queryPersonal")
    Observable<ResponseBean<QueryPersonalBean>> queryPersonal(@Query("userId") String userId, @Query("city") String city);

    /**
     * 查看所有收货地址
     *
     * @return
     */
    @POST("queryListOfMyAddressInfos")
    Observable<ResponseBean<List<MyAddressInfo>>> queryListOfMyAddressInfos(@Query("userId") String userId);


    /**
     * 新增收货地址
     * userId	用户id
     * receiveName	收件人
     * receiveNum	收件人电话
     * area	区
     * street	街道
     * detail	详情
     * addressStatus	状态   0-常规地址
     * 1-默认地址
     */
    @POST("createAddress")
    Observable<ResponseBean<String>> createAddress(
            @Query("userId") String userId,
            @Query("receiveName") String receiveName,
            @Query("receiveNum") String receiveNum,
            @Query("area") String area,
            @Query("street") String street,
            @Query("detail") String detail,
            @Query("addressStatus") int addressStatus
    );

    /**
     * 修改收货地址
     * userId	用户id
     * receiveName	收件人
     * receiveNum	收件人电话
     * area	区
     * street	街道
     * detail	详情
     * addressStatus	状态   0-常规地址
     * 1-默认地址
     */
    @POST("updateAddress")
    Observable<ResponseBean<String>> updateAddress(
            @Query("userId") String userId,
            @Query("id") String id,
            @Query("receiveName") String receiveName,
            @Query("receiveNum") String receiveNum,
            @Query("area") String area,
            @Query("street") String street,
            @Query("detail") String detail,
            @Query("addressStatus") int addressStatus
    );

    /**
     * 修改收货地址
     * userId	用户id
     * receiveName	收件人
     * receiveNum	收件人电话
     * area	区
     * street	街道
     * detail	详情
     * addressStatus	状态   0-常规地址
     * 1-默认地址
     */
    @POST("updateDefaultAddress")
    Observable<ResponseBean<String>> updateDefaultAddress(
            @Query("id") String id
    );

    /**
     * 修改收货地址
     * userId	用户id
     * id	收货地址id
     */

    @POST("removeAddress")
    Observable<ResponseBean<String>> removeAddress(
            @Query("userId") String userId,
            @Query("id") String id
    );

    /**
     * 个人主页或者朋友主页
     * userId	用户id
     * friendId	朋友id
     */

    @POST("queryMyMessage")
    Observable<ResponseBean<MessageInfoBean>> queryMyMessage(
            @Query("userId") String userId,
            @Query("friendId") String friendId
    );

    /**
     * 发布动态
     * file
     * 上传的文件
     * userId	用户名(手机号)
     * type	文件类型
     * 0-图片,1-视频
     * content	内容
     * title	标题
     */

    @POST("publish")
    Observable<ResponseBean<String>> publish(@Body RequestBody file, @Query("userId") String userId,
                                             @Query("type") int type,
                                             @Query("content") String content,
                                             @Query("title") String title
    );

//    @Multipart
//    @POST("publish")
//    Observable<ResponseBean<String>> publish(@PartMap Map<String, RequestBody> params, @Query("userId") String userId,
//                                             @Query("type") int type,
//                                             @Query("content") String content,
//                                             @Query("title") String title
//    );

    /**
     * 个人资料
     * userId	用户id
     * friendId	朋友id
     */

    @POST("selectDataById")
    Observable<ResponseBean<SelectDataByIdBean>> selectDataById(
            @Query("userId") String userId
    );

    /**
     * 编辑资料
     * userId	用户id
     * nickName	昵称
     * sex	性别
     * sign	签名
     * birthday	生日
     */

    @POST("editData")
    Observable<ResponseBean<String>> editData(
            @Body RequestBody file,
            @Query("userId") String userId,
            @Query("nickName") String nickName,
            @Query("sex") String sex,
            @Query("sign") String sign,
            @Query("birthday") String birthday
    );

    /**
     * 1.1.31	绑定支付宝
     * userId	用户id
     * zfbId	支付宝账户
     * zfbPassword	支付宝姓名
     *
     *
     */

    @POST("bindZfb")
    Observable<ResponseBean<String>> bindZfb(
            @Query("userId") String userId,
            @Query("zfbId") String zfbId,
            @Query("zfbName") String zfbName
    );

    /**
     * 1.1.32	身份认证
     * userId	用户id
     * relaName	真实姓名
     * cardNumId	身份证号码
     * file	身份证图片
     */

    @POST("uploadIdCard")
    Observable<ResponseBean<String>> uploadIdCard(@Body RequestBody file, @Query("userId") String userId,
                                                  @Query("relaName") String relaName,
                                                  @Query("cardNumId") String cardNumId
    );

    /**
     * 1.1.35	添加关注
     * userId	用户id
     * followId	被关注id
     */

    @POST("addFollow")
    Observable<ResponseBean<String>> addFollow(
            @Query("userId") String userId,
            @Query("followId") String followId
    );

    /**
     * 我的粉丝
     * userId	用户id
     */

    @POST("fansList")
    Observable<ResponseBean<List<MyFansBean>>> fansList(
            @Query("userId") String userId
    );

    /**
     * 我的收益
     * userId	用户id
     */

    @POST("queryMyIncome")
    Observable<ResponseBean<QueryMyIncomeBean>> queryMyIncome(
            @Query("userId") String userId
    );

    /**
     * 提现
     * userId	用户id
     * cash	提现金额
     */

    @POST("withdrawCash")
    Observable<ResponseBean<String>> withdrawCash(
            @Query("userId") String userId,
            @Query("cash") Double cash
    );

    /**
     * 交易记录
     * userId	用户id
     */

    @POST("queryMyTransactionRecords")
    Observable<ResponseBean<MyPublishOrderBean>> queryMyTransactionRecords(
            @Query("userId") String userId
    );


    /**
     * 修改密码
     * userId	用户id
     * password	密码
     */

    @POST("passwordSetting")
    Observable<ResponseBean<String>> passwordSetting(
            @Query("userId") String userId,
            @Query("password") String password
    );

    /**
     * 修改手机号
     * phone	手机号码
     * id	主键id
     */

    @POST("updatePhoneNumber")
    Observable<ResponseBean<String>> updatePhoneNumber(
            @Query("id") String userId,
            @Query("phone") String password
    );

    /**
     * 账号与安全
     * phone	手机号码
     * id	主键id
     */

    @POST("queryCertificationStatus")
    Observable<ResponseBean<QueryCertificationStatusBean>> queryCertificationStatus(
            @Query("userId") String userId
    );

    @POST("queryLogisticalInfomation")
    Observable<PackageInformationBean> queryLogisticalInformation(
            @Query("expCode") String expCode,
            @Query("expNo") String expNo
    );


}


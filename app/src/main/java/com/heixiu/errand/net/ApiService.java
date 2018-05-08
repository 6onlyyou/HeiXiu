package com.heixiu.errand.net;

import com.heixiu.errand.bean.PhoneToken;
import com.heixiu.errand.bean.PubLishInfo;
import com.heixiu.errand.bean.PublishInfoDetail;
import com.heixiu.errand.bean.ResponseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
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
    Observable<ResponseBean<PhoneToken>> loginByPhone(@Query("userId") String phone,@Query("city") String city);

    /**
     * 帐号密码登录
     *
     * @return
     */
    @POST("user/loginByAccount")
    Observable<ResponseBean<PhoneToken>> loginByAccount(@Query("userName") String userName, @Query("password") String password,@Query("password") String city );

    /**
     * 用户注册
     *
     * @return
     */
    @POST("user/register")
    Observable<ResponseBean<String>> register(@Query("phone") String phone, @Query("userName") String userName, @Query("password") String password);

    /**
     * 首页所有用户的动态展示
     *
     * @return
     */
    @POST("/showAllPublishInfo")
    Observable<ResponseBean<List<PubLishInfo>>> showAllPublishInfo();

    /**
     * 查询一条动态详情
     *
     * @return
     */
    @POST("/showOnePublishInfoDetail")
    Observable<ResponseBean<PublishInfoDetail>> showOnePublishInfoDetail(@Query("publishId") String publishId);

    /**
     * 发布动态
     *
     * @return
     */
    @POST("/publish")
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
     * @return
city     */
    @POST("/userAdmire")
    Observable<ResponseBean<String>> userAdmire(@Query("admireUserId") String admireUserId,
                                                @Query("publishId") String publishId
    );

    /**
     * 评论
     *
     * @param publishId
     * @return
     */
    @POST("/createComment")
    Observable<ResponseBean<String>> createComment(@Query("admireUserId") String admireUserId,
                                                   @Query("publishId") String publishId
    );

    /**
     * 新建订单
     *
     * @param
     * @return
     */
    @POST("/createOrder")
    Observable<ResponseBean<String>> createOrder(
            @Query("userId") String userId,
            @Query("sendAddress") String sendAddress,
            @Query("receiveAddress") String receiveAddress,
            @Query("name") String name,
            @Query("weight") String weight,
            @Query("addPrice") String addPrice,
            @Query("description") String description,
            @Query("receiveName") String receiveName,
            @Query("receiveNum") String receiveNum,
            @Query("courierNum") String courierNum,
            @Query("supportPrice") String supportPrice,
            @Query("payment") String payment,
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
    Observable<ResponseBean<List<OrderInfo>>> changeOrderStatus(@Query("orderNum") String orderNum
            , @Query("orderStatus") String orderStatus);


}

package com.heixiu.errand.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by YuanGang on 2018/5/4.
 */

public class OrderInfo implements Serializable {


    private static final long serialVersionUID = 6510760223886357972L;
    /**
     * id : 5
     * orderNum : d5b2912d-d66f-4b0f-984b-1d22d4543c2a
     * userId : 12
     * receiveId : null
     * courierNum : 1385555
     * receiveNum : 1367744444
     * receiveName : 黎明
     * payment : 25
     * paymentType : 0
     * addPrice : 10
     * supportPrice : 2
     * orderStatus : 0
     * paymentTime : 2018-05-02 21:19:48.0
     * sendTime : 2018-05-02 21:19:48.0
     * weight : 1
     * name : 手机
     * description : 东西很重要
     * sendAddress : 上海南站
     * receiveAddress : 徐家汇
     * originsLatitude : 0
     * originsLongitude : 0
     * destinationsLatitude : 0
     * destinationsLongitude : 0
     * createTime : 1525267188000
     * updateTime : 1525267188000
     */
    private PublishUserInfoBean publishUserInfo;
    private RecieveUserInfoBean recieveUserInfo;
    private int id;
    private String orderNum;
    private String userId;
    private Object receiveId;
    private String courierNum;
    private String receiveNum;
    private String receiveName;
    private int payment;
    private String paymentType;
    private int addPrice;
    private int orderPay;
    private int supportPrice;
    private String orderStatus;
    private String paymentTime;
    private String sendTime;
    private int weight;
    private String name;
    private String description;
    private String sendMapAdress = "";
    private String sendAddress;
    private String recieveMapAdress = "";
    private String receiveAddress;
    private double originsLatitude;
    private double originsLongitude;
    private double destinationsLatitude;
    private double destinationsLongitude;
    private long createTime;
    private long updateTime;

    public int getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(int orderPay) {
        this.orderPay = orderPay;
    }

    private int start;
    private int length;
    private Object couponId;
    private Object userName;
    private Object getOrderName;
    private String nickName;
    private String userImg;
    private int recieveOrderCount;
    private int publishOrderCount;
    private Object beginTime;
    private Object endTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSendMapAdress() {
        return sendMapAdress;
    }

    public void setSendMapAdress(String sendMapAdress) {
        this.sendMapAdress = sendMapAdress;
    }

    public String getRecieveMapAdress() {
        return recieveMapAdress;
    }

    public void setRecieveMapAdress(String recieveMapAdress) {
        this.recieveMapAdress = recieveMapAdress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PublishUserInfoBean getPublishUserInfo() {
        return publishUserInfo;
    }

    public void setPublishUserInfo(PublishUserInfoBean publishUserInfo) {
        this.publishUserInfo = publishUserInfo;
    }

    public RecieveUserInfoBean getRecieveUserInfo() {
        return recieveUserInfo;
    }

    public void setRecieveUserInfo(RecieveUserInfoBean recieveUserInfo) {
        this.recieveUserInfo = recieveUserInfo;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Object getCouponId() {
        return couponId;
    }

    public void setCouponId(Object couponId) {
        this.couponId = couponId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getGetOrderName() {
        return getOrderName;
    }

    public void setGetOrderName(Object getOrderName) {
        this.getOrderName = getOrderName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public int getRecieveOrderCount() {
        return recieveOrderCount;
    }

    public void setRecieveOrderCount(int recieveOrderCount) {
        this.recieveOrderCount = recieveOrderCount;
    }

    public int getPublishOrderCount() {
        return publishOrderCount;
    }

    public void setPublishOrderCount(int publishOrderCount) {
        this.publishOrderCount = publishOrderCount;
    }

    public Object getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Object beginTime) {
        this.beginTime = beginTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Object getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Object receiveId) {
        this.receiveId = receiveId;
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }

    public String getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(String receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(int addPrice) {
        this.addPrice = addPrice;
    }

    public int getSupportPrice() {
        return supportPrice;
    }

    public void setSupportPrice(int supportPrice) {
        this.supportPrice = supportPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSendTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Long.valueOf(sendTime)).replace("-","/");
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public double getOriginsLatitude() {
        return originsLatitude;
    }

    public void setOriginsLatitude(double originsLatitude) {
        this.originsLatitude = originsLatitude;
    }

    public double getOriginsLongitude() {
        return originsLongitude;
    }

    public void setOriginsLongitude(double originsLongitude) {
        this.originsLongitude = originsLongitude;
    }

    public double getDestinationsLatitude() {
        return destinationsLatitude;
    }

    public void setDestinationsLatitude(double destinationsLatitude) {
        this.destinationsLatitude = destinationsLatitude;
    }

    public double getDestinationsLongitude() {
        return destinationsLongitude;
    }

    public void setDestinationsLongitude(double destinationsLongitude) {
        this.destinationsLongitude = destinationsLongitude;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public static class PublishUserInfoBean implements Serializable, Parcelable {
        public static final Creator<PublishUserInfoBean> CREATOR = new Creator<PublishUserInfoBean>() {
            @Override
            public PublishUserInfoBean createFromParcel(Parcel in) {
                return new PublishUserInfoBean(in);
            }

            @Override
            public PublishUserInfoBean[] newArray(int size) {
                return new PublishUserInfoBean[size];
            }
        };
        /**
         * id : 81
         * userId : 12
         * userName : 冰川
         * password : null
         * nickName :
         * sex : 女
         * sign :
         * birthday :
         * userImg :
         * loginType :
         * createTime : 1525877216000
         * updateTime : 1531038600000
         * city : null
         */

        private int id;
        private String userId;
        private String userName;
        private Object password;
        private String nickName;
        private String sex;
        private String sign;
        private String birthday;
        private String userImg;
        private String loginType;
        private long createTime;
        private long updateTime;
        private Object city;

        protected PublishUserInfoBean(Parcel in) {
            id = in.readInt();
            userId = in.readString();
            userName = in.readString();
            nickName = in.readString();
            sex = in.readString();
            sign = in.readString();
            birthday = in.readString();
            userImg = in.readString();
            loginType = in.readString();
            createTime = in.readLong();
            updateTime = in.readLong();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(userId);
            parcel.writeString(userName);
            parcel.writeString(nickName);
            parcel.writeString(sex);
            parcel.writeString(sign);
            parcel.writeString(birthday);
            parcel.writeString(userImg);
            parcel.writeString(loginType);
            parcel.writeLong(createTime);
            parcel.writeLong(updateTime);
        }
    }

    public static class RecieveUserInfoBean implements Serializable {
        /**
         * id : 167
         * userId : 13
         * userName : rfffff
         * password : null
         * nickName : 0
         * sex : 男
         * sign : adada
         * birthday :
         * userImg : http://app.heixiuapp.cn:8080/pic/userImg/1530023870809file.jpg
         * loginType :
         * createTime : 1528794766000
         * updateTime : 1531038605000
         * city : null
         */

        private int id;
        private String userId;
        private String userName;
        private Object password;
        private String nickName;
        private String sex;
        private String sign;
        private String birthday;
        private String userImg;
        private String loginType;
        private long createTime;
        private long updateTime;
        private Object city;
        private double recieveOriginsLongitude;
        private double recieveOriginsLatitude;

        public double getRecieveOriginsLongitude() {
            return recieveOriginsLongitude;
        }

        public void setRecieveOriginsLongitude(double recieveOriginsLongitude) {
            this.recieveOriginsLongitude = recieveOriginsLongitude;
        }

        public double getRecieveOriginsLatitude() {
            return recieveOriginsLatitude;
        }

        public void setRecieveOriginsLatitude(double recieveOriginsLatitude) {
            this.recieveOriginsLatitude = recieveOriginsLatitude;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }
    }
}

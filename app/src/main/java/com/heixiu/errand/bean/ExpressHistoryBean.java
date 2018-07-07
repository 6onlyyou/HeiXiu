package com.heixiu.errand.bean;

import java.util.List;

/**
 * Created by YuanGang on 2018/7/7.
 */

public class ExpressHistoryBean {

    private List<LogisticsRecordsBean> logisticsRecords;

    public List<LogisticsRecordsBean> getLogisticsRecords() {
        return logisticsRecords;
    }

    public void setLogisticsRecords(List<LogisticsRecordsBean> logisticsRecords) {
        this.logisticsRecords = logisticsRecords;
    }

    public static class LogisticsRecordsBean {
        /**
         * userId : 11
         * logisticsId : null
         * logisticsName : 百世快递
         * logisticsNum : 70442548119300
         * logisticsStatus : 3
         * createTime : null
         * updateTime : null
         */

        private String userId;
        private Object logisticsId;
        private String logisticsName;
        private String logisticsNum;
        private String logisticsStatus;
        private Object createTime;
        private Object updateTime;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getLogisticsId() {
            return logisticsId;
        }

        public void setLogisticsId(Object logisticsId) {
            this.logisticsId = logisticsId;
        }

        public String getLogisticsName() {
            return logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }

        public String getLogisticsNum() {
            return logisticsNum;
        }

        public void setLogisticsNum(String logisticsNum) {
            this.logisticsNum = logisticsNum;
        }

        public String getLogisticsStatus() {
            return logisticsStatus;
        }

        public void setLogisticsStatus(String logisticsStatus) {
            this.logisticsStatus = logisticsStatus;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}

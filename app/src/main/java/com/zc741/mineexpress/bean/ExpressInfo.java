package com.zc741.mineexpress.bean;

import java.util.List;

/**
 * Created by jiae on 2016/5/11.
 */
public class ExpressInfo {

    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"company":"顺丰","com":"sf","no":"575677355677","status":"1","list":[{"datetime":"2013-06-25  10:44:05","remark":"已收件","zone":"台州市"},{"datetime":"2013-06-25  11:05:21","remark":"快件在 台州  ,准备送往下一站 台州集散中心  ","zone":"台州市"},{"datetime":"2013-06-25  20:36:02","remark":"快件在 台州集散中心  ,准备送往下一站 台州集散中心 ","zone":"台州市"},{"datetime":"2013-06-25  21:17:36","remark":"快件在 台州集散中心 ,准备送往下一站 杭州集散中心  ","zone":"台州市"},{"datetime":"2013-06-26  12:20:00","remark":"快件在 杭州集散中心  ,准备送往下一站 西安集散中心 ","zone":"杭州市"},{"datetime":"2013-06-27  05:48:42","remark":"快件在 西安集散中心 ,准备送往下一站 西安  ","zone":"西安市/咸阳市"},{"datetime":"2013-06-27  08:03:03","remark":"正在派件..","zone":"西安市/咸阳市"},{"datetime":"2013-06-27  08:51:33","remark":"派件已签收","zone":"西安市/咸阳市"},{"datetime":"2013-06-27 08:51","remark":"签收人是：已签收 ","zone":"西安市/咸阳市"}]}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * company : 顺丰
     * com : sf
     * no : 575677355677
     * status : 1
     * list : [{"datetime":"2013-06-25  10:44:05","remark":"已收件","zone":"台州市"},{"datetime":"2013-06-25  11:05:21","remark":"快件在 台州  ,准备送往下一站 台州集散中心  ","zone":"台州市"},{"datetime":"2013-06-25  20:36:02","remark":"快件在 台州集散中心  ,准备送往下一站 台州集散中心 ","zone":"台州市"},{"datetime":"2013-06-25  21:17:36","remark":"快件在 台州集散中心 ,准备送往下一站 杭州集散中心  ","zone":"台州市"},{"datetime":"2013-06-26  12:20:00","remark":"快件在 杭州集散中心  ,准备送往下一站 西安集散中心 ","zone":"杭州市"},{"datetime":"2013-06-27  05:48:42","remark":"快件在 西安集散中心 ,准备送往下一站 西安  ","zone":"西安市/咸阳市"},{"datetime":"2013-06-27  08:03:03","remark":"正在派件..","zone":"西安市/咸阳市"},{"datetime":"2013-06-27  08:51:33","remark":"派件已签收","zone":"西安市/咸阳市"},{"datetime":"2013-06-27 08:51","remark":"签收人是：已签收 ","zone":"西安市/咸阳市"}]
     */

    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String company;
        private String com;
        private String no;
        private String status;
        /**
         * datetime : 2013-06-25  10:44:05
         * remark : 已收件
         * zone : 台州市
         */

        private List<ListBean> list;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String datetime;
            private String remark;
            private String zone;

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }
}

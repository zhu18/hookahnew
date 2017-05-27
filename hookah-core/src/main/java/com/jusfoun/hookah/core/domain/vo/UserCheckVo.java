package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;

/**
 * Created by ctp on 2017/5/27.
 */
public class UserCheckVo implements Serializable {

    public static class UserCheckResult implements Serializable {
        private  String code;
        private  String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}

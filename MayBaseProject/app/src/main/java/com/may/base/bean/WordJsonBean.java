package com.may.base.bean;

import java.util.List;

/**
 * Created by may on 2018/8/24.
 */

public class WordJsonBean {

    /**
     * resourcetype : zh_cn
     * version : 1.2.0.44
     * error_massage : {"CAPTCHA_MISSING":"缺失图形验证码","INVALID_ARGUMENTS":"输入不合规"}
     * localization : {"wallet":"资产","dae_wallet":"数字资产"}
     * OTC : {"OTC_price":"价格","OTC_amount":"数量","bank":[{"bank_name":"中国工商银行","id":"ICBC","pic":"1"},{"bank_name":"中国农业银行","id":"ABC","pic":"2"}]}
     */

    private String resourcetype;
    private String version;
    private ErrorMassageBean error_massage;
    private LocalizationBean localization;
    private OTCBean OTC;

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ErrorMassageBean getError_massage() {
        return error_massage;
    }

    public void setError_massage(ErrorMassageBean error_massage) {
        this.error_massage = error_massage;
    }

    public LocalizationBean getLocalization() {
        return localization;
    }

    public void setLocalization(LocalizationBean localization) {
        this.localization = localization;
    }

    public OTCBean getOTC() {
        return OTC;
    }

    public void setOTC(OTCBean OTC) {
        this.OTC = OTC;
    }

    public static class ErrorMassageBean {
        /**
         * CAPTCHA_MISSING : 缺失图形验证码
         * INVALID_ARGUMENTS : 输入不合规
         */

        private String CAPTCHA_MISSING;
        private String INVALID_ARGUMENTS;

        public String getCAPTCHA_MISSING() {
            return CAPTCHA_MISSING;
        }

        public void setCAPTCHA_MISSING(String CAPTCHA_MISSING) {
            this.CAPTCHA_MISSING = CAPTCHA_MISSING;
        }

        public String getINVALID_ARGUMENTS() {
            return INVALID_ARGUMENTS;
        }

        public void setINVALID_ARGUMENTS(String INVALID_ARGUMENTS) {
            this.INVALID_ARGUMENTS = INVALID_ARGUMENTS;
        }
    }

    public static class LocalizationBean {
        /**
         * wallet : 资产
         * dae_wallet : 数字资产
         */

        private String wallet;
        private String dae_wallet;

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getDae_wallet() {
            return dae_wallet;
        }

        public void setDae_wallet(String dae_wallet) {
            this.dae_wallet = dae_wallet;
        }
    }

    public static class OTCBean {
        /**
         * OTC_price : 价格
         * OTC_amount : 数量
         * bank : [{"bank_name":"中国工商银行","id":"ICBC","pic":"1"},{"bank_name":"中国农业银行","id":"ABC","pic":"2"}]
         */

        private String OTC_price;
        private String OTC_amount;
        private List<BankBean> bank;

        public String getOTC_price() {
            return OTC_price;
        }

        public void setOTC_price(String OTC_price) {
            this.OTC_price = OTC_price;
        }

        public String getOTC_amount() {
            return OTC_amount;
        }

        public void setOTC_amount(String OTC_amount) {
            this.OTC_amount = OTC_amount;
        }

        public List<BankBean> getBank() {
            return bank;
        }

        public void setBank(List<BankBean> bank) {
            this.bank = bank;
        }

        public static class BankBean {
            /**
             * bank_name : 中国工商银行
             * id : ICBC
             * pic : 1
             */

            private String bank_name;
            private String id;
            private String pic;

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}

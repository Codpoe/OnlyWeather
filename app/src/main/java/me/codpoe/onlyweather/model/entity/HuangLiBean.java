package me.codpoe.onlyweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Codpoe on 2016/6/1.
 */
public class HuangLiBean {

    /**
     * reason : successed
     * result : {"id":"2280","yangli":"2016-06-01","yinli":"丙申(猴)年四月廿六","wuxing":"大溪水 收执位","chongsha":"冲猴(戊申)煞北","baiji":"甲不开仓财物耗散 寅不祭祀神鬼不尝","jishen":"月空 母仓 敬安 五合 鸣犬","yi":"栽种 捕捉 畋猎 馀事勿取","xiongshen":"劫煞 月害 土府 八专 天牢","ji":"开市 动土 祭祀 斋醮 安葬 探病"}
     * error_code : 0
     */

    @SerializedName("reason")
    private String reason;
    /**
     * id : 2280
     * yangli : 2016-06-01
     * yinli : 丙申(猴)年四月廿六
     * wuxing : 大溪水 收执位
     * chongsha : 冲猴(戊申)煞北
     * baiji : 甲不开仓财物耗散 寅不祭祀神鬼不尝
     * jishen : 月空 母仓 敬安 五合 鸣犬
     * yi : 栽种 捕捉 畋猎 馀事勿取
     * xiongshen : 劫煞 月害 土府 八专 天牢
     * ji : 开市 动土 祭祀 斋醮 安葬 探病
     */

    @SerializedName("result")
    private ResultBean result;
    @SerializedName("error_code")
    private int errorCode;

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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static class ResultBean {
        @SerializedName("id")
        private String id;
        @SerializedName("yangli")
        private String yangli;
        @SerializedName("yinli")
        private String yinli;
        @SerializedName("wuxing")
        private String wuxing;
        @SerializedName("chongsha")
        private String chongsha;
        @SerializedName("baiji")
        private String baiji;
        @SerializedName("jishen")
        private String jishen;
        @SerializedName("yi")
        private String yi;
        @SerializedName("xiongshen")
        private String xiongshen;
        @SerializedName("ji")
        private String ji;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYangli() {
            return yangli;
        }

        public void setYangli(String yangli) {
            this.yangli = yangli;
        }

        public String getYinli() {
            return yinli;
        }

        public void setYinli(String yinli) {
            this.yinli = yinli;
        }

        public String getWuxing() {
            return wuxing;
        }

        public void setWuxing(String wuxing) {
            this.wuxing = wuxing;
        }

        public String getChongsha() {
            return chongsha;
        }

        public void setChongsha(String chongsha) {
            this.chongsha = chongsha;
        }

        public String getBaiji() {
            return baiji;
        }

        public void setBaiji(String baiji) {
            this.baiji = baiji;
        }

        public String getJishen() {
            return jishen;
        }

        public void setJishen(String jishen) {
            this.jishen = jishen;
        }

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        public String getXiongshen() {
            return xiongshen;
        }

        public void setXiongshen(String xiongshen) {
            this.xiongshen = xiongshen;
        }

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }
    }
}

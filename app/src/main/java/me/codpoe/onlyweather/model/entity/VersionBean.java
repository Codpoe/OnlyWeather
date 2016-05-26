package me.codpoe.onlyweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Codpoe on 2016/5/26.
 */
public class VersionBean {

    /**
     * name : fir.im
     * version : 1.0
     * changelog : 更新日志
     * versionShort : 1.0.5
     * build : 6
     * install_url : http://download.fir.im/v2/app/install/xxxxxxxxxxxxxxxx?download_token=xxxxxxxxxxxxxxxxxxxxxxxxxxxx
     * update_url : http://fir.im/fir
     * binary : {"fsize":6446245}
     */

    @SerializedName("name")
    private String name;
    @SerializedName("version")
    private String version;
    @SerializedName("changelog")
    private String changelog;
    @SerializedName("versionShort")
    private String versionShort;
    @SerializedName("build")
    private String build;
    @SerializedName("install_url")
    private String installUrl;
    @SerializedName("update_url")
    private String updateUrl;
    /**
     * fsize : 6446245
     */

    @SerializedName("binary")
    private BinaryBean binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        @SerializedName("fsize")
        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}

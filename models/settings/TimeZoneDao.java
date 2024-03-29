package com.pomohouse.launcher.models.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 1/24/2017 AD.
 */

public class TimeZoneDao {

    /**
     * timeZone : Asia/Bangkok
     */
    @SerializedName("timeZone")
    @Expose
    private String timeZone;
    @SerializedName("GMT")
    @Expose
    private String timeZoneGMT;
    @SerializedName("autoTimeZone")
    @Expose
    private String autoTimezone;

    public String getTimeZoneGMT() {
        return timeZoneGMT;
    }

    public void setTimeZoneGMT(String timeZoneGMT) {
        this.timeZoneGMT = timeZoneGMT;
    }

    public String getAutoTimezone() {
        return autoTimezone;
    }

    public void setAutoTimezone(String autoTimezone) {
        this.autoTimezone = autoTimezone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}

package com.zmm.wifiserverstart2.model;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/6/26
 * Time:下午2:25
 */

public class DeviceModel {

    /**
     * loginId : C89346FD5A90
     * s_id : C89346FD5A90
     * s_name : RKF-1
     * curSpeed : 44
     * curResistance : 2
     * curDirection : 1
     * calories : 15.861
     * activeMileage : .136
     * spasmTimes : 0
     */

    private String loginId;
    private String s_id;
    private String s_name;
    private String curSpeed;
    private String curResistance;
    private String curDirection;
    private String calories;
    private String activeMileage;
    private String spasmTimes;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(String curSpeed) {
        this.curSpeed = curSpeed;
    }

    public String getCurResistance() {
        return curResistance;
    }

    public void setCurResistance(String curResistance) {
        this.curResistance = curResistance;
    }

    public String getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(String curDirection) {
        this.curDirection = curDirection;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getActiveMileage() {
        return activeMileage;
    }

    public void setActiveMileage(String activeMileage) {
        this.activeMileage = activeMileage;
    }

    public String getSpasmTimes() {
        return spasmTimes;
    }

    public void setSpasmTimes(String spasmTimes) {
        this.spasmTimes = spasmTimes;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "loginId='" + loginId + '\'' +
                ", s_id='" + s_id + '\'' +
                ", s_name='" + s_name + '\'' +
                ", curSpeed='" + curSpeed + '\'' +
                ", curResistance='" + curResistance + '\'' +
                ", curDirection='" + curDirection + '\'' +
                ", calories='" + calories + '\'' +
                ", activeMileage='" + activeMileage + '\'' +
                ", spasmTimes='" + spasmTimes + '\'' +
                '}';
    }
}

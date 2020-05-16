package com.nju.ledonghealthhelper.model;

public class SportEvent {
    private String userName;
    private long pubTime;
    private String sportType;
    private String pubLocation;
    private String pubContent;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getPubLocation() {
        return pubLocation;
    }

    public void setPubLocation(String pubLocation) {
        this.pubLocation = pubLocation;
    }

    public String getPubContent() {
        return pubContent;
    }

    public void setPubContent(String pubContent) {
        this.pubContent = pubContent;
    }
}

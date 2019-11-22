package com.knickglobal.yeloopp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFollowRequestPojo extends CommonPojo {

    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public class Detail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("requested_user_id")
        @Expose
        private String requestedUserId;
        @SerializedName("conn_status")
        @Expose
        private String connStatus;
        @SerializedName("user_details")
        @Expose
        private UserDetails userDetails;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRequestedUserId() {
            return requestedUserId;
        }

        public void setRequestedUserId(String requestedUserId) {
            this.requestedUserId = requestedUserId;
        }

        public String getConnStatus() {
            return connStatus;
        }

        public void setConnStatus(String connStatus) {
            this.connStatus = connStatus;
        }

        public UserDetails getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

    }

    public class UserDetails {

        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("social_login_type")
        @Expose
        private String socialLoginType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getSocialLoginType() {
            return socialLoginType;
        }

        public void setSocialLoginType(String socialLoginType) {
            this.socialLoginType = socialLoginType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

    }

}
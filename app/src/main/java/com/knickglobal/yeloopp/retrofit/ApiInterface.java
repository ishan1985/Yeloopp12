package com.knickglobal.yeloopp.retrofit;

import com.knickglobal.yeloopp.models.CommonPojo;
import com.knickglobal.yeloopp.models.FollowUsersPojo;
import com.knickglobal.yeloopp.models.FriendsListPojo;
import com.knickglobal.yeloopp.models.GetFollowRequestPojo;
import com.knickglobal.yeloopp.models.LoginPojo;
import com.knickglobal.yeloopp.models.NearbyUsersPojo;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login_register.php")
    Call<LoginPojo> loginApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("check_social_id_and_social_login.php")
    Call<LoginPojo> socialLoginApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("near_by_users_list.php")
    Call<NearbyUsersPojo> nearbyUsersApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("follow_request.php")
    Call<FollowUsersPojo> followUsersApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("logout.php")
    Call<CommonPojo> logoutApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("get_requests_list.php")
    Call<GetFollowRequestPojo> getFollowRequestsApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("accept_follow_request.php")
    Call<CommonPojo> acceptRequestsApi(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("get_friends_list.php")
    Call<FriendsListPojo> getFriendsApi(@FieldMap HashMap<String, String> map);

    @Multipart
    @POST("create_post.php")
    Call<CommonPojo> postApi(@PartMap HashMap<String, RequestBody> defaultData,
                             @Part MultipartBody.Part text_bg_image,
                             @Part MultipartBody.Part[] post_images,
                             @Part MultipartBody.Part post_video);


//    @FormUrlEncoded
//    @POST("get_vendor.php")
//    Call<ProfilePojo> getProfile(@Field("id") String id);
//
//    @FormUrlEncoded
//    @POST("vendorProfile.php")
//    Call<EditProfilePojo> editProfile(@FieldMap HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("add_cat.php")
//    Call<TimeSlotModel> add_cat(@Field("name") String name,
//                                @Field("vendor_id") String vendor_id);
//
//    @FormUrlEncoded
//    @POST("getimg.php")
//    Call<CoverImagesModel> getCoverImages(@Field("vendor_id") String vendor_id);
//
//    @Multipart
//    @POST("addimg.php")
//    Call<CoverImagesModel> uploadImage(@Part("vendor_id") RequestBody vendor_id,
//                                       @Part MultipartBody.Part img);
//
//    @FormUrlEncoded
//    @POST("event_listing.php")
//    Call<CreateUpdateEventPojo> getEventList(@Field("id") String id,
//                                             @Field("status") String status);
//
//    @FormUrlEncoded
//    @POST("deleteEventAndChangeStatus.php")
//    Call<EventDeleteAndChangeStatusPojo> eventDeleteAndChangeStatus(@FieldMap HashMap<String, String> hashMap);
//
//    @Multipart
//    @POST("create_edit_event.php")
//    Call<CreateUpdateEventPojo> createUpdateEvent(@PartMap HashMap<String, RequestBody> hashMap,
//                                                  @Part MultipartBody.Part image);
//
//    @FormUrlEncoded
//    @POST("wallet_transactions.php")
//    Call<GetWalletTransactionsPojo> getWalletTransactions(@FieldMap HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("withdraw_amount.php")
//    Call<WithdrawMoneyPojo> withdrawMoney(@FieldMap HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("get_wallet_balance.php")
//    Call<GetWalletBalancePojo> getWalletBal(@Field("user_id") String id);
//
//    @FormUrlEncoded
//    @POST("vendor_appointment_requests.php")
//    Call<AppointmentsPojo> getAppointments(@FieldMap HashMap<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("timer_notify_job_payment.php")
//    Call<StartTimerPojo> startTimer(@FieldMap Map<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("get_timing_details.php")
//    Call<GetTimerDetailsPojo> getTimerDetails(@Field("appointmentId") String appointmentId);
//
//    @FormUrlEncoded
//    @POST("get_invoice_details.php")
//    Call<InvoicePojo> getInvoiceDetails(@Field("appointment_id") String appointment_id);
//
//    @FormUrlEncoded
//    @POST("get_event_members.php")
//    Call<GetEventMembersPojo> getMembersList(@Field("event_id") String event_id);
//
//    @FormUrlEncoded
//    @POST("insert_bank_card_details.php")
//    Call<BankDetailsPojo> bankDetails(@FieldMap Map<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("contact_us_vendor.php")
//    Call<ContactusPojo> contactus(@FieldMap Map<String, String> hashMap);
//
//    @FormUrlEncoded
//    @POST("check_vendor_saved_timing.php")
//    Call<CheckSavedTimeSlotPojo> checkSavedTime(@Field("vendorId")String id);

//    //Get Api
//    @GET("vehicleDetails")
//    Call<VehicleTypePojo> vehicleDetails();
//
//
//    //Post Api
//    @FormUrlEncoded
//    @POST("driverMatchVerificationToken")
//    Call<RegisterLoginPojo> driverMatchVerificationToken(@Field("id") String id,
//                                                         @Field("token") String token);
//
//
//    //Multipart Api
//
//    @Multipart
//    @POST("driverRegister")
//    Call<RegisterLoginPojo> userRegister(@Part("driverName") RequestBody name,
//                                         @Part("driverEmail") RequestBody email,
//                                         @Part("driverPhoneNumber") RequestBody phone,
//                                         @Part("driverPassword") RequestBody password,
//                                         @Part("device_type") RequestBody device_type,
//                                         @Part("reg_id") RequestBody reg_id,
//                                         @Part("latitude") RequestBody latitude,
//                                         @Part("longitude") RequestBody longitude,
//                                         @Part MultipartBody.Part driverImage);

//
//    //request body text
//
//    RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);
//
//
    //multipart image
//    File file = new File(filePath);
//    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);


}
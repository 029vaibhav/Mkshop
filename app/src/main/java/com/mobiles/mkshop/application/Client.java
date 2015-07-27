package com.mobiles.mkshop.application;

import android.util.Log;

import com.mobiles.mkshop.pojos.AttendanceDates;
import com.mobiles.mkshop.pojos.Leader;
import com.mobiles.mkshop.pojos.Location;
import com.mobiles.mkshop.pojos.LoginDetails;
import com.mobiles.mkshop.pojos.NewUser;
import com.mobiles.mkshop.pojos.Notification;
import com.mobiles.mkshop.pojos.PartsRequests;
import com.mobiles.mkshop.pojos.PriceCompartorService;
import com.mobiles.mkshop.pojos.Product;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.pojos.Sales;
import com.mobiles.mkshop.pojos.UserListAttendance;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;


/**
 * Created by vaibhav on 30/6/15.
 */
public enum Client {


    INSTANCE;


    public MobileService mobileService;


    private interface MobileService {

        @GET("/mk/webservice/login.php")
        void login(@Query("email") String username, @Query("pwd") String password, Callback<LoginDetails> callback);

        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/sales.php")
        void sales(@Body Sales sales, Callback<Response> response);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/service.php")
        void sendService(@Body RepairPojo service, Callback<String> response);


        @GET("/mk/webservice/salesreport.php")
        void getSalesReport(@Query("from") String from, @Query("to") String to, Callback<List<Sales>> salesCallback);

        @GET("/mk/webservice/salesreport.php")
        List<Sales> getSalesReport1(@Query("from") String from, @Query("to") String to);

        @GET("/mk/webservice/serviceList.php")
        void getServiceList(Callback<List<RepairPojo>> callback);

        @GET("/mk/webservice/partList.php")
        void getPartList(Callback<List<PartsRequests>> callback);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/partRequest.php")
        void sendPartRequest(@Body PartsRequests partsRequests, Callback<String> response);


        @GET("/mk/webservice/profile.php")
        void getProfile(@Query("username") String username, Callback<Response> response);

        @POST("/mk/webservice/profile.php")
        void updateProfile(@Query("username") String username, @QueryMap Map<String, String> options, Callback<String> response);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/register.php")
        void createUser(@Body NewUser newUser, Callback<String> response);


        @GET("/mk/webservice/AttenReport.php")
        void getUserList(Callback<List<UserListAttendance>> response);


        @GET("/mk/webservice/AttenReport.php")
        void getUserAttendance(@Query("username") String username, Callback<List<AttendanceDates>> response);


        @GET("/mk/webservice/leaderboard.php")
        void getLeaderBoard(@Query("from") String from, @Query("to") String to, Callback<List<Leader>> callback);


        @GET("/mk/webservice/servicereport.php")
        void getServiceReport(@Query("from") String from, @Query("to") String to, Callback<List<RepairPojo>> callback);


        @GET("/mk/webservice/report.php")
        void getpricecompator(@Query("from") String from, @Query("to") String to, @Query("category") String category, Callback<List<PriceCompartorService>> callback);


        @GET("/mk/webservice/product.php")
        void getproduct(Callback<List<Sales>> response);

        @GET("/mk/webservice/product.php")
        void getproductid(@Query("id") String id, Callback<List<Product>> response);

        @Multipart
        @POST("/mk/webservice/image.php")
        void upload(@Query("username") String username, @Part("myfile") TypedFile file, @Part("description") String description, Callback<String> cb);

        @GET("/mk/webservice/location.php")
        void getAllLocation(Callback<List<Location>> response);

        @POST("/mk/webservice/latlong.php")
        void setLocation(@Body Location location, Callback<String> callback);

        @GET("/mk/webservice/message.php")
        void getNotificationDetail(@Query("role") String role, Callback<List<Notification>> response);

        @POST("/mk/webservice/message.php")
        void sendNotification(@Body Notification notification, Callback<String> callback);

    }


    Client() {

//


        RestAdapter restAdapter = new RestAdapter.Builder()
                //  .setEndpoint("http://52.74.153.158:8080")
                .setEndpoint("http://192.168.1.102:80")
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.i("getMyParking", message);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mobileService = restAdapter.create(MobileService.class);
    }


    public void login(String username, String password, Callback<LoginDetails> responseCallback)

    {
        mobileService.login(username, password, responseCallback);
    }


    public void sales(Sales sales, Callback<Response> responseCallback)

    {
        mobileService.sales(sales, responseCallback);
    }


    public void getSalesReport(String from, String to, Callback<List<Sales>> salesCallback) {
        mobileService.getSalesReport(from, to, salesCallback);

    }


    public List<Sales> getSalesReport1(String from, String to) {
        return mobileService.getSalesReport1(from, to);

    }


    public void sendService(RepairPojo service, Callback<String> callback) {
        mobileService.sendService(service, callback);

    }

    public void getServiceList(Callback<List<RepairPojo>> callback) {
        mobileService.getServiceList(callback);

    }

    public void getPartList(Callback<List<PartsRequests>> callback) {
        mobileService.getPartList(callback);

    }

    public void sendPartRequest(PartsRequests partsRequests, Callback<String> callback) {
        mobileService.sendPartRequest(partsRequests, callback);

    }

    public void getProfile(String username, Callback<Response> response) {
        mobileService.getProfile(username, response);

    }

    public void updateProfile(String username, Map<String, String> map, Callback<String> callback) {
        mobileService.updateProfile(username, map, callback);
    }

    public void createUser(NewUser newUser, Callback<String> callback) {
        mobileService.createUser(newUser, callback);
    }

    public void getUserList(Callback<List<UserListAttendance>> callback) {
        mobileService.getUserList(callback);
    }

    public void getUserAttendance(String username, Callback<List<AttendanceDates>> response) {
        mobileService.getUserAttendance(username, response);
    }

    public void getLeaderBoard(String from, String to, Callback<List<Leader>> response) {
        mobileService.getLeaderBoard(from, to, response);
    }

    public void getpricecompator(String from, String to, String category, Callback<List<PriceCompartorService>> response) {
        mobileService.getpricecompator(from, to, category, response);
    }

    public void getServiceReport(String from, String to, Callback<List<RepairPojo>> response) {
        mobileService.getServiceReport(from, to, response);
    }

    public void getproduct(Callback<List<Sales>> response) {
        mobileService.getproduct(response);
    }

    public void getproductid(String id, Callback<List<Product>> response) {
        mobileService.getproductid(id, response);
    }

    public void uploadImage(String username, TypedFile file, String description, Callback<String> callback) {
        mobileService.upload(username, file, description, callback);
    }


    public void getAllLocation(Callback<List<Location>> response) {
        mobileService.getAllLocation(response);
    }

    public void setLocation(Location location, Callback<String> callback) {
        mobileService.setLocation(location, callback);
    }

    public void getNotificationDetail(String role, Callback<List<Notification>> response) {
        mobileService.getNotificationDetail(role, response);

    }

    public void sendNotification(@Body Notification notification, Callback<String> callback) {
        mobileService.sendNotification(notification, callback);
    }
}

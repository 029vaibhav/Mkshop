package com.mobiles.mkshop.application;

import android.util.Log;

import com.mobiles.mkshop.pojos.AttendanceDates;
import com.mobiles.mkshop.pojos.ExpenseEntity;
import com.mobiles.mkshop.pojos.IncentiveEntity;
import com.mobiles.mkshop.pojos.Leader;
import com.mobiles.mkshop.pojos.Location;
import com.mobiles.mkshop.pojos.LoginDetails;
import com.mobiles.mkshop.pojos.NewUser;
import com.mobiles.mkshop.pojos.Notification;
import com.mobiles.mkshop.pojos.PartsRequests;
import com.mobiles.mkshop.pojos.PriceCompartorService;
import com.mobiles.mkshop.pojos.Product;
import com.mobiles.mkshop.pojos.ProductExpense;
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
import retrofit.http.Header;
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
        void login(@Query("email") String username, @Query("pwd") String password, Callback<String> callback);

        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/sales.php")
        void sales(@Header("AUTH") String auth, @Body Sales sales, Callback<String> response);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/service.php")
        void sendService(@Header("AUTH") String auth, @Body RepairPojo service, Callback<String> response);


        @GET("/mk/webservice/salesreport.php")
        void getSalesReport(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, Callback<List<Sales>> salesCallback);

        @GET("/mk/webservice/salesreport.php")
        List<Sales> getSalesReport1(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to);

        @GET("/mk/webservice/serviceList.php")
        void getServiceList(@Header("AUTH") String auth, Callback<List<RepairPojo>> callback);

        @GET("/mk/webservice/partList.php")
        void getPartList(@Header("AUTH") String auth, Callback<List<PartsRequests>> callback);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/partRequest.php")
        void sendPartRequest(@Header("AUTH") String auth, @Body PartsRequests partsRequests, Callback<String> response);


        @GET("/mk/webservice/profile.php")
        void getProfile(@Header("AUTH") String auth, @Query("username") String username, Callback<Response> response);

        @GET("/mk/webservice/profile.php")
        void updateProfile(@Header("AUTH") String auth, @Query("username") String username, @QueryMap Map<String, String> options, Callback<String> response);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/register.php")
        void createUser(@Header("AUTH") String auth, @Body NewUser newUser, Callback<String> response);


        @GET("/mk/webservice/AttenReport.php")
        void getUserList(@Header("AUTH") String auth, Callback<List<UserListAttendance>> response);


        @GET("/mk/webservice/AttenReport.php")
        void getUserAttendance(@Header("AUTH") String auth, @Query("username") String username, Callback<List<AttendanceDates>> response);


        @GET("/mk/webservice/leaderBoard.php")
        void getLeaderBoard(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, Callback<List<Leader>> callback);


        @GET("/mk/webservice/serviceReport.php")
        void getServiceReport(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, Callback<List<RepairPojo>> callback);


        @GET("/mk/webservice/Report.php")
        void getpricecompator(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, @Query("category") String category, Callback<List<PriceCompartorService>> callback);


        @GET("/mk/webservice/product.php")
        void getproduct(@Header("AUTH") String auth, Callback<List<Sales>> response);

        @GET("/mk/webservice/product.php")
        void getproductid(@Header("AUTH") String auth, @Query("id") String id, Callback<List<Product>> response);

        @Multipart
        @POST("/mk/webservice/image.php")
        void upload(@Header("AUTH") String auth, @Query("username") String username, @Part("myfile") TypedFile file, @Part("description") String description, Callback<String> cb);

        @GET("/mk/webservice/location.php")
        void getAllLocation(@Header("AUTH") String auth, Callback<List<Location>> response);

        @POST("/mk/webservice/latlong.php")
        void setLocation(@Header("AUTH") String auth, @Body Location location, Callback<String> callback);

        @GET("/mk/webservice/message.php")
        void getNotificationDetail(@Header("AUTH") String auth, @Query("role") String role, Callback<List<Notification>> response);

        @POST("/mk/webservice/message.php")
        void sendNotification(@Header("AUTH") String auth, @Body Notification notification, Callback<String> callback);


        @GET("/mk/webservice/login.php")
        void getLoginData(@Header("AUTH") String auth, @Query("username") String username, Callback<LoginDetails> callback);

        @GET("/mk/webservice/attendance.php")
        void markAttendance(@Header("AUTH") String auth, @Query("username") String username, Callback<String> callback);

        @GET("/mk/webservice/logout.php")
        void logout(@Query("username") String username, Callback<String> callback);

        @POST("/mk/webservice/incentiveMessage.php")
        void createIncentive(@Header("AUTH") String auth, @Body IncentiveEntity incentiveEntity, Callback<String> callback);

        @GET("/mk/webservice/allincentive.php")
        void getIncentiveList(@Header("AUTH") String auth, Callback<List<IncentiveEntity>> incentiveEntityCallback);


        @GET("/mk/webservice/incentivegetsales.php")
        void getIncentiveUserList(@Header("AUTH") String auth, @Query("id") String id, Callback<List<Sales>> callback);

        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/expenseinsert.php")
        void payUserIncentive(@Header("AUTH") String auth, @Body ExpenseEntity expenseEntity, Callback<String> callback);


        @GET("/mk/webservice/expenseReport.php")
        void getExpenseReport(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, Callback<List<ExpenseEntity>> salesCallback);

        @GET("/mk/webservice/incentivegetsales.php")
        void deleteIncentiveMessage(@Header("AUTH") String auth, @Query("id") int id, @Query("op") String delete, Callback<String> callback);

        @GET("/mk/webservice/leaderboardsaleslist.php")
        void getUserSales(@Header("AUTH") String auth, @Query("to") String s, @Query("from") String s1, @Query("username") String username, @Query("department") String department, Callback<List<Sales>> callback);

        @GET("/mk/webservice/deleteuser.php")
        void deleteUser(@Header("AUTH") String auth, @Query("username") String username, Callback<String> callback);

        @GET("/mk/webservice/noti/gcmserver/register.php")
        void registerGcm(@Header("AUTH") String auth, @Query("username") String username, @Query("regId") String regId, Callback<String> callback);


        @POST("/mk/webservice/vandor.php")
        void productPurchase(@Header("AUTH") String auth, @Body ProductExpense productExpense, Callback<String> cb);

        @GET("/mk/webservice/vandor.php")
        void getPurchasedProduct(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, Callback<List<ProductExpense>> callback);


    }


    Client() {


////
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JodaModule());

//        JacksonConverter jacksonConverter = new JacksonConverter(new ObjectMapper());

        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint("http://192.168.1.102:80")
                .setEndpoint("http://mkmobileshop.in:80")
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.i("mkshop", message);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mobileService = restAdapter.create(MobileService.class);
    }


    public void login(String username, String password, Callback<String> responseCallback)

    {
        mobileService.login(username, password, responseCallback);
    }


    public void sales(String auth, Sales sales, Callback<String> responseCallback)

    {
        mobileService.sales(auth, sales, responseCallback);
    }


    public void getSalesReport(String auth, String from, String to, Callback<List<Sales>> salesCallback) {
        mobileService.getSalesReport(auth, from, to, salesCallback);

    }


    public List<Sales> getSalesReport1(String auth, String from, String to) {
        return mobileService.getSalesReport1(auth, from, to);

    }


    public void sendService(String auth, RepairPojo service, Callback<String> callback) {
        mobileService.sendService(auth, service, callback);

    }

    public void getServiceList(String auth, Callback<List<RepairPojo>> callback) {
        mobileService.getServiceList(auth, callback);

    }

    public void getPartList(String auth, Callback<List<PartsRequests>> callback) {
        mobileService.getPartList(auth, callback);

    }

    public void sendPartRequest(String auth, PartsRequests partsRequests, Callback<String> callback) {
        mobileService.sendPartRequest(auth, partsRequests, callback);

    }

    public void getProfile(String auth, String username, Callback<Response> response) {
        mobileService.getProfile(auth, username, response);

    }

    public void updateProfile(String auth, String username, Map<String, String> map, Callback<String> callback) {
        mobileService.updateProfile(auth, username, map, callback);
    }

    public void createUser(String auth, NewUser newUser, Callback<String> callback) {
        mobileService.createUser(auth, newUser, callback);
    }

    public void getUserList(String auth, Callback<List<UserListAttendance>> callback) {
        mobileService.getUserList(auth, callback);
    }

    public void getUserAttendance(String auth, String username, Callback<List<AttendanceDates>> response) {
        mobileService.getUserAttendance(auth, username, response);
    }

    public void getLeaderBoard(String auth, String from, String to, Callback<List<Leader>> response) {
        mobileService.getLeaderBoard(auth, from, to, response);
    }

    public void getpricecompator(String auth, String from, String to, String category, Callback<List<PriceCompartorService>> response) {
        mobileService.getpricecompator(auth, from, to, category, response);
    }

    public void getServiceReport(String auth, String from, String to, Callback<List<RepairPojo>> response) {
        mobileService.getServiceReport(auth, from, to, response);
    }

    public void getproduct(String auth, Callback<List<Sales>> response) {
        mobileService.getproduct(auth, response);
    }

    public void getproductid(String auth, String id, Callback<List<Product>> response) {
        mobileService.getproductid(auth, id, response);
    }

    public void uploadImage(String auth, String username, TypedFile file, String description, Callback<String> callback) {
        mobileService.upload(auth, username, file, description, callback);
    }


    public void getAllLocation(String auth, Callback<List<Location>> response) {
        mobileService.getAllLocation(auth, response);
    }

    public void setLocation(String auth, Location location, Callback<String> callback) {
        mobileService.setLocation(auth, location, callback);
    }

    public void getNotificationDetail(String auth, String role, Callback<List<Notification>> response) {
        mobileService.getNotificationDetail(auth, role, response);

    }

    public void sendNotification(String auth, Notification notification, Callback<String> callback) {
        mobileService.sendNotification(auth, notification, callback);
    }

    public void getLoginData(String auth, String username, Callback<LoginDetails> callback) {
        mobileService.getLoginData(auth, username, callback);
    }

    public void markAttendance(String auth, String username, Callback<String> callback) {
        mobileService.markAttendance(auth, username, callback);

    }

    public void logout(String username, Callback<String> callback) {
        mobileService.logout(username, callback);
    }

    public void createIncentive(String auth, IncentiveEntity incentiveEntity, Callback<String> stringCallback) {
        mobileService.createIncentive(auth, incentiveEntity, stringCallback);
    }

    public void getIncentiveList(String auth, Callback<List<IncentiveEntity>> incentiveEntityCallback) {
        mobileService.getIncentiveList(auth, incentiveEntityCallback);
    }

    public void getIncentiveUserList(String auth, String id, Callback<List<Sales>> callback) {
        mobileService.getIncentiveUserList(auth, id, callback);
    }

    public void payUserIncentive(String auth, ExpenseEntity expenseEntity, Callback<String> callback) {
        mobileService.payUserIncentive(auth, expenseEntity, callback);

    }


    public void getExpenseReport(String auth, String from, String to, Callback<List<ExpenseEntity>> salesCallback) {
        mobileService.getExpenseReport(auth, from, to, salesCallback);

    }

    public void deleteIncentiveMessage(String auth, int id, String message, Callback<String> callback) {

        mobileService.deleteIncentiveMessage(auth, id, message, callback);
    }


    public void getUserSales(String auth, String s, String s1, String username, String departemnt, Callback<List<Sales>> callback) {
        mobileService.getUserSales(auth, s, s1, username, departemnt, callback);
    }

    public void deleteUser(String auth, String username, Callback<String> callback) {
        mobileService.deleteUser(auth, username, callback);
    }

    public void registerGcm(String auth, String username, String regId, Callback<String> stringCallback) {
        mobileService.registerGcm(auth, username, regId, stringCallback);
    }


    public void productPurchase(String auth, ProductExpense productExpense, Callback<String> cb) {
        mobileService.productPurchase(auth, productExpense, cb);
    }

    public void getPurchasedProduct(String auth, String from, String to, Callback<List<ProductExpense>> cb) {
        mobileService.getPurchasedProduct(auth, from, to, cb);
    }


}

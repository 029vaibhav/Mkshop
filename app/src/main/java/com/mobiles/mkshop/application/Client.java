package com.mobiles.mkshop.application;

import com.mobiles.mkshop.pojos.models.AttendanceDates;
import com.mobiles.mkshop.pojos.models.Auth;
import com.mobiles.mkshop.pojos.models.DealerInfo;
import com.mobiles.mkshop.pojos.models.EmployeeExpense;
import com.mobiles.mkshop.pojos.models.ExpenseManager;
import com.mobiles.mkshop.pojos.models.IncentiveEntity;
import com.mobiles.mkshop.pojos.models.Leader;
import com.mobiles.mkshop.pojos.models.Location;
import com.mobiles.mkshop.pojos.models.LoginDetails;
import com.mobiles.mkshop.pojos.models.Message;
import com.mobiles.mkshop.pojos.models.NewUser;
import com.mobiles.mkshop.pojos.models.Payment;
import com.mobiles.mkshop.pojos.models.Product;
import com.mobiles.mkshop.pojos.models.Purchase;
import com.mobiles.mkshop.pojos.models.Sales;
import com.mobiles.mkshop.pojos.models.ServiceCenterEntity;
import com.mobiles.mkshop.pojos.models.SparePart;
import com.mobiles.mkshop.pojos.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by vaibhav on 30/6/15.
 */
public enum Client {


    INSTANCE;


    public MobileService mobileService;


    private interface MobileService {

        @GET("/mk/webservice/login")
        Call<Auth> login(@Query("username") String username, @Query("password") String password);

        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/sales")
        Call<Void> sales(@Header("AUTH") String auth, @Body Sales sales);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/technical")
        Call<Void> sendService(@Header("AUTH") String auth, @Body ServiceCenterEntity service);

        @Headers("Content-Type: application/json")
        @PUT("/mk/webservice/technical")
        Call<Void> updateService(@Header("AUTH") String auth, @Body ServiceCenterEntity service);

        @GET("/mk/webservice/technical/getall")
        Call<List<ServiceCenterEntity>> getServiceList(@Header("AUTH") String auth);

        @GET("/mk/webservice/sales/report")
        Call<List<Sales>> getSalesReport(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to);


        @GET("/mk/webservice/sparepart/modified/{date}")
        Call<List<SparePart>> getPartList(@Header("AUTH") String auth, @Path("date") String date);


        @POST("/mk/webservice/sparepart")
        Call<SparePart> createSparePart(@Header("AUTH") String auth, @Body SparePart sparePart);

        @PUT("/mk/webservice/sparepart")
        Call<Void> updateSparePart(@Header("AUTH") String auth, @Body SparePart sparePart);


        @GET("/mk/webservice/user/username/{username}")
        Call<User> getProfile(@Header("AUTH") String auth, @Path("username") String username);

        @PATCH("/mk/webservice/user/username/{username}/userinfo")
        Call<User> updateProfile(@Header("AUTH") String auth, @Path("username") String username, @Body Map<String, String> option);


        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/user")
        Call<Void> createUser(@Header("AUTH") String auth, @Body NewUser newUser);


        @GET("/mk/webservice/user/username/getall")
        Call<List<User>> getUserList(@Header("AUTH") String auth);


        @GET("/mk/webservice/AttenReport.php")
        Call<List<AttendanceDates>> getUserAttendance(@Header("AUTH") String auth, @Query("username") String username);


        @GET("/mk/webservice/leaderboard")
        Call<List<Leader>> getLeaderBoard(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to);


        @GET("/mk/webservice/technical/report")
        Call<List<ServiceCenterEntity>> getServiceReport(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to);


        @GET("/mk/webservice/sales/report/brand")
        Call<List<Sales>> getpricecompator(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to, @Query("category") String category);

        @GET("/mk/webservice/technical/report/brand")
        Call<List<Sales>> getPriceComparatorTechnical(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to);


        @GET("/mk/webservice/product/getall")
        Call<List<Product>> getproduct(@Header("AUTH") String auth);

        @POST("/mk/webservice/product")
        Call<Product> createProduct(@Header("AUTH") String auth, @Body Product product);

        @POST("/mk/webservice/product/accessory")
        Call<Product> createAccessory(@Header("AUTH") String auth, @Body Product product);

        @GET("/mk/webservice/product/id/{id}")
        Call<List<Product>> getproductid(@Header("AUTH") String auth, @Path("id") Long id);


        @GET("/mk/webservice/location.php")
        Call<List<Location>> getAllLocation(@Header("AUTH") String auth);

        @POST("/mk/webservice/latlong.php")
        Call<String> setLocation(@Header("AUTH") String auth, @Body Location location);

        @GET("/mk/webservice/message/role/{role}")
        Call<List<Message>> getNotificationDetail(@Header("AUTH") String auth, @Path("role") String role);

        @POST("/mk/webservice/message")
        Call<Void> sendNotification(@Header("AUTH") String auth, @Body Message message);


        @GET("/mk/webservice/login/details")
        Call<LoginDetails> getLoginData(@Header("AUTH") String auth, @Query("username") String username);

        @GET("/mk/webservice/attendance.php")
        Call<String> markAttendance(@Header("AUTH") String auth, @Query("username") String username);

        @GET("/mk/webservice/logout")
        Call<Void> logout(@Query("username") String username);

        @POST("/mk/webservice/incentivemessage")
        Call<Void> createIncentive(@Header("AUTH") String auth, @Body IncentiveEntity incentiveEntity);

        @GET("/mk/webservice/incentivemessage/getall")
        Call<List<IncentiveEntity>> getIncentiveList(@Header("AUTH") String auth);


        @GET("/mk/webservice/incentivemessage/leaders/{id}")
        Call<List<Sales>> getIncentiveUserList(@Header("AUTH") String auth, @Path("id") String id);

        @Headers("Content-Type: application/json")
        @POST("/mk/webservice/employeeexpense")
        Call<EmployeeExpense> payUserIncentive(@Header("AUTH") String auth, @Body EmployeeExpense employeeExpense);


        @GET("/mk/webservice/employeeexpense/date")
        Call<List<EmployeeExpense>> getExpenseReport(@Header("AUTH") String auth, @Query("from") String from, @Query("to") String to);

        @DELETE("/mk/webservice/incentivemessage/{id}")
        Call<IncentiveEntity> deleteIncentiveMessage(@Header("AUTH") String auth, @Path("id") int id);

        @GET("/mk/webservice/sales/user")
        Call<List<Sales>> getUserSales(@Header("AUTH") String auth, @Query("to") String s, @Query("from") String s1, @Query("username") String username);

        @GET("/mk/webservice/technical/user")
        Call<List<ServiceCenterEntity>> getUserService(@Header("AUTH") String auth, @Query("to") String s, @Query("from") String s1, @Query("username") String username);

        @DELETE("/mk/webservice/user/username/{username}")
        Call<User> deleteUser(@Header("AUTH") String auth, @Path("username") String username);

        @GET("/mk/webservice/user/username/{username}/{gcmID}")
        Call<User> registerGcm(@Header("AUTH") String auth, @Path("username") String username, @Path("gcmID") String regId);

        @POST("/mk/webservice/purchase")
        Call<Purchase> productPurchase(@Header("AUTH") String auth, @Body Purchase expenseManager);

        @GET("/mk/webservice/purchase/report")
        Call<List<ExpenseManager>> getPurchasedProduct(@Header("AUTH") String auth, @Query("from") String from);

        @POST("/mk/webservice/payment")
        Call<Payment> duePayment(@Header("AUTH") String auth, @Body Payment payment);

        @DELETE("/mk/webservice/payment/{id}")
        Call<Payment> deletePayment(@Header("AUTH") String auth, @Path("id") Long serverID);

        @DELETE("/mk/webservice/purchase/{id}")
        Call<Purchase> deletePurchase(@Header("AUTH") String auth, @Path("id") Long id);

        @POST("/mk/webservice/dealerinfo")
        Call<DealerInfo> registerDealer(@Header("AUTH") String auth, @Body DealerInfo dealerInfo);
    }


    Client() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                boolean unAuthorized = false;
                Request request = chain.request();
                okhttp3.Response response = null;
                try {
                    response = chain.proceed(request);
                    unAuthorized = (response.code() > 400);
                    if (unAuthorized) {
                        throw new IOException(response.body().string());
                    }
                } catch (IOException e) {
                    if (unAuthorized)
                        throw e;
                    throw new IOException("please check you internet connection");

                }

                return response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.0.9:9098")
                .baseUrl("http://Default-Environment.egdsgf36pr.ap-southeast-1.elasticbeanstalk.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mobileService = retrofit.create(MobileService.class);
    }


    public Call<Auth> login(String username, String password) {
        Call<Auth> login = mobileService.login(username, password);
        return login;
    }


    public Call<Void> sales(String auth, Sales sales) {
        Call<Void> sales1 = mobileService.sales(auth, sales);
        return sales1;
    }


    public Call<List<Sales>> getSalesReport(String auth, String from, String to) {
        Call<List<Sales>> salesReport = mobileService.getSalesReport(auth, from, to);
        return salesReport;

    }


//    public List<Sales> getSalesReport1(String auth, String from, String to) {
//        return mobileService.getSalesReport1(auth, from, to);
//
//    }


    public Call<Void> sendService(String auth, ServiceCenterEntity service) {
        Call<Void> stringCall = mobileService.sendService(auth, service);
        return stringCall;

    }

    public Call<Void> updateService(String auth, ServiceCenterEntity service) {
        Call<Void> stringCall = mobileService.updateService(auth, service);
        return stringCall;

    }

    public Call<List<ServiceCenterEntity>> getServiceList(String auth) {
        Call<List<ServiceCenterEntity>> serviceList = mobileService.getServiceList(auth);
        return serviceList;

    }

    public Call<List<SparePart>> getPartList(String auth, String date) {
        Call<List<SparePart>> partList = mobileService.getPartList(auth, date);
        return partList;

    }

    public Call<SparePart> createSparePart(String auth, SparePart sparePart) {
        return mobileService.createSparePart(auth, sparePart);

    }

    public Call<Void> updateSparePart(String auth, SparePart sparePart) {
        return mobileService.updateSparePart(auth, sparePart);

    }

    public Call<User> getProfile(String auth, String username) {
        return mobileService.getProfile(auth, username);

    }

    public Call<User> updateProfile(String auth, String username, Map<String, String> map) {
        return mobileService.updateProfile(auth, username, map);
    }

    public Call<Void> createUser(String auth, NewUser newUser) {
        return mobileService.createUser(auth, newUser);
    }

    public Call<List<User>> getUserList(String auth) {
        return mobileService.getUserList(auth);
    }

    public Call<List<AttendanceDates>> getUserAttendance(String auth, String username) {
        return mobileService.getUserAttendance(auth, username);
    }

    public Call<List<Leader>> getLeaderBoard(String auth, String from, String to) {
        return mobileService.getLeaderBoard(auth, from, to);
    }

    public Call<List<Sales>> getpricecompator(String auth, String from, String to, String category) {
        return mobileService.getpricecompator(auth, from, to, category);
    }

    public Call<List<Sales>> getPriceComparatorTech(String auth, String from, String to) {
        return mobileService.getPriceComparatorTechnical(auth, from, to);
    }

    public Call<List<ServiceCenterEntity>> getServiceReport(String auth, String from, String to) {
        return mobileService.getServiceReport(auth, from, to);
    }

    public Call<List<Product>> getproduct(String auth) {
        return mobileService.getproduct(auth);
    }

    public Call<List<Product>> getproductid(String auth, Long id) {
        return mobileService.getproductid(auth, id);
    }

    public Call<List<Location>> getAllLocation(String auth) {
        return mobileService.getAllLocation(auth);
    }

    public Call<String> setLocation(String auth, Location location) {
        return mobileService.setLocation(auth, location);
    }

    public Call<List<Message>> getNotificationDetail(String auth, String role) {
        return mobileService.getNotificationDetail(auth, role);

    }

    public Call<Void> sendNotification(String auth, Message message) {
        return mobileService.sendNotification(auth, message);
    }

    public Call<LoginDetails> getLoginData(String auth, String username) {
        return mobileService.getLoginData(auth, username);
    }

    public Call<String> markAttendance(String auth, String username) {
        return mobileService.markAttendance(auth, username);
    }

    public Call<Void> logout(String username) {
        return mobileService.logout(username);
    }

    public Call<Void> createIncentive(String auth, IncentiveEntity incentiveEntity) {
        return mobileService.createIncentive(auth, incentiveEntity);
    }

    public Call<List<IncentiveEntity>> getIncentiveList(String auth) {
        return mobileService.getIncentiveList(auth);
    }

    public Call<List<Sales>> getIncentiveUserList(String auth, String id) {
        return mobileService.getIncentiveUserList(auth, id);
    }

    public Call<EmployeeExpense> payUserIncentive(String auth, EmployeeExpense employeeExpense) {
        return mobileService.payUserIncentive(auth, employeeExpense);
    }


    public Call<List<EmployeeExpense>> getExpenseReport(String auth, String from, String to) {
        return mobileService.getExpenseReport(auth, from, to);
    }

    public Call<IncentiveEntity> deleteIncentiveMessage(String auth, int id) {
        return mobileService.deleteIncentiveMessage(auth, id);
    }


    public Call<List<Sales>> getUserSales(String auth, String s, String s1, String username) {
        return mobileService.getUserSales(auth, s, s1, username);
    }

    public Call<List<ServiceCenterEntity>> getUserService(String auth, String s, String s1, String username) {
        return mobileService.getUserService(auth, s, s1, username);
    }

    public Call<User> deleteUser(String auth, String username) {
        return mobileService.deleteUser(auth, username);
    }

    public Call<User> registerGcm(String auth, String username, String regId) {
        return mobileService.registerGcm(auth, username, regId);
    }


    public Call<Purchase> productPurchase(String auth, Purchase expenseManager) {
        return mobileService.productPurchase(auth, expenseManager);
    }

    public Call<List<ExpenseManager>> getPurchasedProduct(String auth, String from) {
        return mobileService.getPurchasedProduct(auth, from);
    }

    public Call<Payment> duePayment(String auth, Payment payment) {
        return mobileService.duePayment(auth, payment);
    }

    public Call<Payment> deletePayment(String auth, Long id) {
        return mobileService.deletePayment(auth, id);
    }

    public Call<Purchase> deletePurchase(String auth, Long id) {
        return mobileService.deletePurchase(auth, id);
    }

    public Call<DealerInfo> registerDealerInfo(String auth, DealerInfo dealerInfo) {
        return mobileService.registerDealer(auth, dealerInfo);
    }

    public Call<Product> createProduct(String auth, Product product) {
        return mobileService.createProduct(auth, product);
    }

    public Call<Product> createAccessory(String auth, Product product) {
        return mobileService.createAccessory(auth, product);
    }


}

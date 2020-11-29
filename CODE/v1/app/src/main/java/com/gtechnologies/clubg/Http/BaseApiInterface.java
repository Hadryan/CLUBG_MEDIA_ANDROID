package com.gtechnologies.clubg.Http;

import com.gtechnologies.clubg.Model.Content;
import com.gtechnologies.clubg.Model.ContentResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Developed by Fojle Rabbi Saikat on 1/9/2017.
 * Owned by Bitmakers Ltd.
 * Contact fojle.rabbi@bitmakers-bd.com
 */
public interface BaseApiInterface {

//    @FormUrlEncoded
//    @POST("oauth/token?grant_type=password&client_secret=6af731d7-5a78-412c-975a-e83e7baa1c0f")
//    Call<ResponseBody> getToken(@Field("email") String username, @Field("password") String password);

    @GET("product/image")
    Call<ContentResponse> getImage(@Header("Authorization") String key, @Query("page") int pageNumber, @Query("size") int size);

    @GET("product/audio")
    Call<ContentResponse> getAudio(@Header("Authorization") String key, @Query("page") int pageNumber, @Query("size") int size);

    @GET("product/video")
    Call<ContentResponse> getVideo(@Header("Authorization") String key, @Query("page") int pageNumber, @Query("size") int size);

    @GET("product/news")
    Call<ContentResponse> getNews(@Header("Authorization") String key, @Query("page") int pageNumber, @Query("size") int size);

//    @GET("categories/{id}/programs")
//    Call<ResponseBody> getProgramByCategory(@Header("Authorization") String token, @Path("id") int id);
//
//    @GET("get_category_vendors.php?")
//    Call<ResponseBody> getCategoryWiseVendor(@Query("page") int page, @Query("category") int categoryID);
//
//    @GET("get_vendors_items.php?")
//    Call<ResponseBody> getVendorItem(@Query("page") int page, @Query("vendor") int vendorID);
//
//    @GET("get_vendor_package.php?")
//    Call<ResponseBody> getVendorPackage(@Query("page") int page, @Query("vendor") int vendorID);
//
//    @GET("group")
//    Call<ResponseBody> getAllGroup(@Header("Authorization") String token);
//
//    @GET("group/{id}/category")
//    Call<ResponseBody> getCategoryByGroup(@Header("Authorization") String token, @Path("id") int groupId);
//
//    @GET("categories/{id}/children")
//    Call<ResponseBody> getChildByCategory(@Header("Authorization") String token, @Path("id") int categoryId);
//
//    @GET("latest/programs/vdo/{page}")
//    Call<ResponseBody> getLatestVideoPrograms(@Header("Authorization") String token, @Path("page") int page);
//
//    @GET("latest/programs/pic/{page}")
//    Call<ResponseBody> getLatestPicturePrograms(@Header("Authorization") String token, @Path("page") int page);

}

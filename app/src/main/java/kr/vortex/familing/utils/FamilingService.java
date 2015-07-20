package kr.vortex.familing.utils;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by kotohana-laptop on 15. 7. 20.
 package kr.edcan.billim.utils;

 import java.util.List;

 import retrofit.Callback;
 import retrofit.http.Field;
 import retrofit.http.FormUrlEncoded;
 import retrofit.http.Multipart;
 import retrofit.http.POST;
 import retrofit.http.Part;
 import retrofit.mime.TypedFile;

 /**
 * Created by kotohana5706 on 15. 7. 12.
 */
public interface FamilingService {
    @FormUrlEncoded
    @POST("/api/auth/login")
    public void login(@Field("username") String username, @Field("password") String password, Callback<User> callback);

    @FormUrlEncoded
    @POST("/api/auth/register")
    public void register(@Field("username") String username, @Field("password") String password, @Field("name") String name, Callback<User> callback);

    @GET("/api/auth/logout")
    public void logout(Callback callback);

    @FormUrlEncoded
    @POST("/api/user/self/gcm")
    public void setGcm(@Field("gcm") String gcm, Callback<User> callback);

    @GET("/api/user/self/delete")
    public void userDelete(Callback<User> callback);

    @GET("/api/user/self/info")
    public void userSelfInfo(Callback<User> callback);

    @FormUrlEncoded
    @POST("/api/group/info")
    public void groupInfo(@Field("code") String code, Callback<Group> callback);

    @GET("/api/group/self/info")
    public void groupSelfInfo(Callback<Group> callback);

    @FormUrlEncoded
    @POST("/api/group/self/join")
    public void groupJoin(@Field("code") String code, Callback<Group> callback);

    @FormUrlEncoded
    @POST("/api/group/self/create")
    public void groupCreate(@Field("name") String name, Callback<Group> callback);

    @Multipart
    @POST("/api/article/self/create")
    public void postArticle(@Part("type") int type, @Part("name") String name,
                            @Part("description") String description, @Part("tagged") List<Integer> tagged,
                            @Part("voteEntries") List<String> voteEntries, @Part("canAdd") boolean a, @Part("photo") TypedFile photo,  Callback<Group> callback);

    @GET("/api/article/listByUsers")
    public void groupListByUsers(Callback<List<BaseUser>> callback);
}
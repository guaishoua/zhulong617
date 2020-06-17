package com.hdj.frame;


import com.google.gson.JsonObject;
import com.hdj.data.AdvertBean;
import com.hdj.data.BaseInfo;
import com.hdj.data.CourseBean;
import com.hdj.data.DataGroupBean;
import com.hdj.data.GroupDetailEntity;
import com.hdj.data.HomeBannerBean;
import com.hdj.data.HomeliveBean;
import com.hdj.data.LoginInfo;
import com.hdj.data.MainAdEntity;
import com.hdj.data.PersonHeader;
import com.hdj.data.RecentlyBean;
import com.hdj.data.SpecialtyChooseEntity;
import com.hdj.data.VipBannerBean;
import com.hdj.data.VipListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    String url = "https://a.zhulong.com/";

    @GET("ad/getAd")
    Observable<AdvertBean> getAdvertData(@QueryMap Map<String, Object> params);


    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList();

    @GET("loginByMobileCode")
    Observable<BaseInfo<String>> getLoginVerify(@Query("mobile") String mobile);

    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByVerify(@QueryMap Map<String, Object> params);

    @POST("getUserHeaderForMobile")
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@FieldMap Map<String, Object> params);


    @POST("openapi/lesson/getCarouselphoto")
    @FormUrlEncoded
    Observable<HomeBannerBean> getHomeData(@FieldMap Map<String, Object> params);

    @POST("lesson/getIndexCommend")
    @FormUrlEncoded
    Observable<BaseInfo<List<HomeliveBean>>> getLiveData(@FieldMap Map<String, Object> params);


    @POST("lesson/getLessonListForApi")
    @FormUrlEncoded
    Observable<BaseInfo<CourseBean>> getCourseData(@FieldMap Map<String, Object> params);

    @GET("lesson/get_new_vip")
    Observable<BaseInfo<VipBannerBean>> getVipBannerData(@QueryMap Map<String, Object> params);


    @POST("lesson/getVipSmallLessonList")
    @FormUrlEncoded
    Observable<BaseInfo<VipListBean>> getVipListData(@FieldMap Map<String, Object> params);


    @POST("group/getThreadEssence")
    @FormUrlEncoded
    Observable<BaseInfo<List<RecentlyBean>>> getRecentlyData(@FieldMap Map<String, Object> params);


    @POST("group/getGroupList")
    @FormUrlEncoded
    Observable<BaseInfo<List<DataGroupBean>>> getGroupData(@FieldMap Map<String, Object> params);


    @POST
    @FormUrlEncoded
    Observable<BaseInfo> removeFocus(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> focus(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkVerifyCode(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkPhoneIsUsed(@Url String url, @Field("mobile") Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> sendRegisterVerify(@Url String url, @Field("mobile") Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkName(@Url String url, @Field("username")Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> registerCompleteWithSubject(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByAccount(@Url String url, @FieldMap Map<String,Object> params);

    @GET
    Observable<JsonObject> getWechatToken(@Url String url, @QueryMap Map<String,Object> parmas);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByWechat(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> bindThirdAccount(@Url String url, @FieldMap Map<String,Object> params);

    @GET
    Observable<BaseInfo<GroupDetailEntity>> getGroupDetail(@Url String url, @Query("gid") Object object);

    @GET
    Observable<BaseInfo<GroupDetailEntity>> getGroupDetailFooterData(@Url String url, @QueryMap Map<String,Object> parmas);
}


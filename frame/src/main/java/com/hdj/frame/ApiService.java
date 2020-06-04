package com.hdj.frame;



import com.hdj.data.AdvertBean;
import com.hdj.data.BaseInfo;
import com.hdj.data.LoginInfo;
import com.hdj.data.MainAdEntity;
import com.hdj.data.PersonHeader;
import com.hdj.data.SpecialtyChooseEntity;

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
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@FieldMap Map<String,Object> params);
}

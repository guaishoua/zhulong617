package com.hdj.frame;



import com.hdj.data.AdvertBean;
import com.hdj.data.BaseInfo;
import com.hdj.data.SpecialtyChooseEntity;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {
    String url = "https://a.zhulong.com/";

    @POST("openapi/ad/getAd")
    @FormUrlEncoded
    Observable<AdvertBean> getAdvertData(@Field("positions_id")String positions_id, @Field("is_show")String is_show);


    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList();
}

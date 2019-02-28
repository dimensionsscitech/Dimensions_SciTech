package com.wd.common.netWork;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :  RXjava的被观察者
 */
public interface ObservedApis {

    @GET
    Observable<ResponseBody> get(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map, @HeaderMap Map<String, String> headerMap);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> parmas);

    @POST
    @Multipart
    Observable<ResponseBody> postImage(@Url String url, @Part MultipartBody.Part parts);

}

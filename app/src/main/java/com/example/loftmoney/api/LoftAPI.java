package com.example.loftmoney.api;

import io.reactivex.Completable;
import io.reactivex.Single;
import com.example.loftmoney.items.MoneyResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoftAPI {

    @GET(".items")
    Single <MoneyResponse> getItems(@Query("type")String type, @Query("auth-token") String authToken);

    @POST("./items/add")
    @FormUrlEncoded
    Completable putItems(@Field("price")String price, @Field("name")String name, @Field("type")String type);


}

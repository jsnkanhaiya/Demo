package com.example.demo.network

import com.example.demo.data.model.ApodResponse
import com.example.demo.local.Entities.ApodEntity
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.*

interface ApiService {

    @GET
    fun getAOPDData(
        @Url url: String,
        @Query("date") strDate: String,
        @Query("hd") ishd: Boolean,
        @Query("api_key") strTokenKey: String
    ):Observable<ApodResponse>

    /*@GET
    fun getAOPDData(
        @Url url: String

    ):Observable<ApodResponse>*/
  /*  @POST
    fun postSocialToken(
        @Header("entity") entity: String,
        @Url url: String,
        @Query("token") token: String,
        @Query("clientId") client: String
    ): Observable<TokenResponse>

    @FormUrlEncoded
    @POST
    fun getGoogleAccessTokenFromApi(
        @Url url: String,
        @FieldMap fields: Map<String, String>
    ): Observable<GoogleSignInAccessTokenResponse>

    @POST
    fun subscribeToNotifications(
        @Header("Cookie") cookie: String,
        @Url url: String,
        @Body subscriptionRequest: SubscriptionRequest
    ): Observable<GenericApiResponse>

    @POST
    fun changeDeviceToken(
        @Header("Cookie") cookie: String,
        @Url url: String,
        @Body subscriptionRequest: SubscriptionRequest
    ): Observable<GenericApiResponse>

    @GET
    fun getSubscriptionList(
        @Header("Cookie") cookie: String,
        @Url url: String,
        @Query("deviceId") deviceId: String
    ): Observable<JSONObject>*/


}


//        @Field("id_token") id_token: String

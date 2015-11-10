package ch.rasc.retrofit;

import retrofit.Call;
import retrofit.http.GET;

interface HttpBinService {
    @GET("/ip")
    Call<Ip> getIp();
  }
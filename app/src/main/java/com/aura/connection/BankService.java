package com.aura.connection;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BankService {


    @POST ("login/{id}{password}")
    Call<Credentials> getLogin(@Path("id,password") String id, String password);

    public static final Retrofit retrofit = new  Retrofit.Builder()
            .baseUrl("http://192.168.1.84")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}

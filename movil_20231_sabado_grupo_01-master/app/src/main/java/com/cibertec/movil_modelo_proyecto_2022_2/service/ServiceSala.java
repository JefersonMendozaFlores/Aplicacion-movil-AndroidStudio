package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceSala {

    @GET("sala")
    public Call<List<Sala>> listAll();

    @POST("sala")
    Call<Sala> register(@Body Sala arg);

    @PUT("sala")
    Call<Sala> update(@Body Sala arg);

}

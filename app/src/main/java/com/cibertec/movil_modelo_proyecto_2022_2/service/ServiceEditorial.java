package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceEditorial {
    @POST("editorial")
    public Call<Editorial> registerEditorial(@Body Editorial obj);

    @GET("editorial")
    public abstract Call<List<Editorial>> listaEditorial();

    @POST("editorial")
    public abstract Call<Editorial> actualizarEditorial(@Body Editorial obj);

}

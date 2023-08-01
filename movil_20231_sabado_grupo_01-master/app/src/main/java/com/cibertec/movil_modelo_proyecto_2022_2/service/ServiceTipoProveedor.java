package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.TipoProveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceTipoProveedor {

    @GET("util/listaTipoProveedor")
    public Call<List<TipoProveedor>> listaTodos();

}

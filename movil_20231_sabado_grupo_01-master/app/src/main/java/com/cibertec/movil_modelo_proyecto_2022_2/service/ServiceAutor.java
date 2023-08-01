package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceAutor {

    // PARA REGISTRAR
    @POST("autor")
    public  abstract Call<Autor> insertaAutor(@Body Autor objAutor);

    // PARA ACTUALIZAR
    @PUT("autor")
    public  abstract Call<Autor> actualizarAutor(@Body Autor objAutor);




    // PARA MIS COMBOS
    @GET("util/listaGrado")
    public abstract Call<List<Grado>> listaGrado();

    @GET("util/listaPais")
    public abstract Call<List<Pais>> listaPaises();

    // PARA MI LISTADO
    @GET("autor")
    public abstract Call<List<Autor>> listaAutores();
}

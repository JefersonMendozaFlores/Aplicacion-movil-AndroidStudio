package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceAlumno {

    @POST("alumno")
    public abstract Call<Alumno> insertaAlumno (@Body Alumno objAlumno);

    @PUT("alumno")
    public abstract Call<Alumno> actualizaAlumno (@Body Alumno objAlumno);


    @GET("util/listaPais")
    public abstract   Call<List<Pais>> listaPais();
    @GET("util/listaModalidad")
    public abstract  Call<List<Modalidad>> listaModalidad();

    @GET("alumno")
    public abstract Call<List<Alumno>> listaAlumnos();


}

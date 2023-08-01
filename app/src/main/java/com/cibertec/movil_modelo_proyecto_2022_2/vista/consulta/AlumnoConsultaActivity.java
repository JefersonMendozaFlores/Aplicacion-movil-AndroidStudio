package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AlumnoAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoConsultaActivity extends NewAppCompatActivity {

    //boton
    Button btnListar;

    //listview
    GridView gridAlumnos;
    ArrayList<Alumno> data=new ArrayList<Alumno>();
    AlumnoAdapter adaptador;

    //service
    ServiceAlumno serviceAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_consulta);

        btnListar =findViewById(R.id.btnListar);

        gridAlumnos=findViewById(R.id.gridAlumnos);
        adaptador = new AlumnoAdapter(this, R.layout.activity_alumno_item, data);
        gridAlumnos.setAdapter(adaptador);


        gridAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Alumno objAlumno = data.get(position);

                Intent intent = new Intent(AlumnoConsultaActivity.this, AlumnoConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO",objAlumno);
                startActivity(intent);

            }
        });

        serviceAlumno= ConnectionRest.getConnection().create(ServiceAlumno.class);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaAlumnos();
            }
        });
    }

    public void listaAlumnos(){
        Call<List<Alumno>> call = serviceAlumno.listaAlumnos();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(response.isSuccessful()){
                    List<Alumno> lista =response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }



}
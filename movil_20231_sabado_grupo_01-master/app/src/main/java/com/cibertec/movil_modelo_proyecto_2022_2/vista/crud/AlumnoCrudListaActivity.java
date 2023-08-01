package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AlumnoCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlumnoCrudListaActivity extends NewAppCompatActivity {


    Button btnCrudListar, btnCrudRegistra;

    GridView gridCrudAlumno;

    ArrayList<Alumno> data = new ArrayList<Alumno>();
    AlumnoCrudAdapter adaptador;

    ServiceAlumno serviceAlumno;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_lista);

        btnCrudListar=findViewById(R.id.btnCrudListar);
        btnCrudRegistra=findViewById(R.id.btnCrudRegistra);
        //
        gridCrudAlumno=findViewById(R.id.gridCrudAlumno);
        adaptador = new AlumnoCrudAdapter(this,R.layout.activity_alumno_crud_item,data);
        gridCrudAlumno.setAdapter(adaptador);
        //
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        //BOTON REGISTRA
        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        AlumnoCrudListaActivity.this,
                        AlumnoCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA ALUMNO");
                intent.putExtra("var_tipo", "REGISTRA");
               // intent.putExtra("var_objeto", objalumno);
                startActivity(intent);
            }
        });

        //BOTON LISTAR
        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        gridCrudAlumno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Alumno objAlumno = data.get(position);

                Intent intent = new Intent(
                        AlumnoCrudListaActivity.this,
                        AlumnoCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA ALUMNO");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objAlumno);
                startActivity(intent);

            }
        });

        lista();
    }

    public void lista(){

        Call<List<Alumno>> call = serviceAlumno.listaAlumnos();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                List<Alumno> lista =response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }

}
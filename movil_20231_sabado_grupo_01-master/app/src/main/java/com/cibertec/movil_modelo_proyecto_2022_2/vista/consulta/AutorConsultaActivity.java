package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AutorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorConsultaActivity extends NewAppCompatActivity {

    // BOTON
    Button btnLista;

    // GRIDVIEW
    GridView gridAutores;
    ArrayList<Autor> data = new ArrayList<Autor>();
    AutorAdapter adaptador;

    // SERVICIO
    ServiceAutor serviceAutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_consulta);

        btnLista = findViewById(R.id.btnLista);

        gridAutores = findViewById(R.id.gridAutores);
        adaptador = new AutorAdapter(this, R.layout.activity_autor_item, data);
        gridAutores.setAdapter(adaptador);

        gridAutores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Autor objAutor = data.get(position);

                Intent intent = new Intent(AutorConsultaActivity.this, AutorConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO",objAutor);
                startActivity(intent);

            }
        });

        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAutopres();
            }
        });

    }

    public void listaAutopres(){
        Call<List<Autor>> call = serviceAutor.listaAutores();
        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()){
                    List<Autor> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {

            }
        });

    }




}
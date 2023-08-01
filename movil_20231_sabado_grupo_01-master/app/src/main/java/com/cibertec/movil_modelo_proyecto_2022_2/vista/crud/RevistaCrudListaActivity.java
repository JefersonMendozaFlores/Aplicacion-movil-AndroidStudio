package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.RevistaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RevistaCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar;
    Button btnCrudRegistrar;

    GridView gridCrudRevista;
    ArrayList<Revista> data = new ArrayList<>();
    RevistaAdapter adapter;

    ServiceRevista service;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);

        btnCrudListar = findViewById(R.id.btnCrudRevistaListar);
        btnCrudRegistrar = findViewById(R.id.btnCrudRevistaRegistra);

        gridCrudRevista = findViewById(R.id.gridCrudRevista);
        adapter = new RevistaAdapter(this, R.layout.activity_revista_crud_item, data);
        gridCrudRevista.setAdapter(adapter);

        service = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listar();
            }
        });

        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RevistaCrudListaActivity.this, RevistaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });

        gridCrudRevista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Revista revista = data.get(position);

                Intent intent = new Intent(RevistaCrudListaActivity.this, RevistaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", revista);
                startActivity(intent);
            }
        });
    }

    public void listar() {
        Call<List<Revista>> call = service.listarTodo();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                List<Revista> lista = response.body();
                data.clear();
                data.addAll(lista);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

            }
        });
    }

}
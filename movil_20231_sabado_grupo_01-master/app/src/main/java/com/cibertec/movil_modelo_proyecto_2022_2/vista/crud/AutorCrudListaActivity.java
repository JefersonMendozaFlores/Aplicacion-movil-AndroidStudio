package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AutorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AutorCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutorCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar, btnCrudRegistra;
    // GridView
    GridView gridCrudAutor;
    ArrayList<Autor> data = new ArrayList<Autor>();
    AutorCrudAdapter adaptador;
    // Servicio
    ServiceAutor serviceAutor;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_lista);

        btnCrudListar = findViewById(R.id.btnCrudListar);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistra);
        //
        gridCrudAutor = findViewById(R.id.gridCrudAutor);
        adaptador = new AutorCrudAdapter(this,R.layout.activity_autor_crud_item,data);
        gridCrudAutor.setAdapter(adaptador);
        //
        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);

        // BOTON REGISTRAR
        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        AutorCrudListaActivity.this,
                        AutorCrudFormularioActivity.class);
                //intent.putExtra("var_titulo", "REGISTRA AUTOR");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });

        // BOTON LISTAR
        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        // GridCrudAutor
        gridCrudAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Autor objautor = data.get(position);

                Intent intent = new Intent(
                        AutorCrudListaActivity.this,
                        AutorCrudFormularioActivity.class);
                //intent.putExtra("var_titulo", "ACTUALIZA CLIENTE");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objautor);
                startActivity(intent);

            }
        });

        lista();

    }

    // METODO LISTAR
    public void lista(){
        Call<List<Autor>> call = serviceAutor.listaAutores();
        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                List<Autor> lista =response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {

            }
        });
    }



}
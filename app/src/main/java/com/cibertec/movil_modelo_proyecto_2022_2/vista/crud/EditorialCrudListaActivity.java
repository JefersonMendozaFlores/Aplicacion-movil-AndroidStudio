package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {


    Button btnCrudListarEditorial, btnCrudRegistraEditorial;

    GridView gridCrudEditorial;

    ArrayList<Editorial> dataE = new ArrayList<Editorial>();

    EditorialCrudAdapter adaptador;

    ServiceEditorial serviceEditorial;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);


        btnCrudListarEditorial = findViewById(R.id.btnCrudListarEditorial);
        btnCrudRegistraEditorial = findViewById(R.id.btnCrudRegistraEditorial);

        gridCrudEditorial = findViewById(R.id.gridCrudEditorial);
        adaptador = new EditorialCrudAdapter(this,R.layout.activity_editorial_crud_lista,dataE);
        gridCrudEditorial.setAdapter(adaptador);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);




        btnCrudRegistraEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });




        btnCrudListarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        gridCrudEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Editorial obj = dataE.get(position);

                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", obj);
                startActivity(intent);

            }
        });

    }



    public void lista(){
        Call<List<Editorial>> call = serviceEditorial.listaEditorial();
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                List<Editorial> lista =response.body();
                dataE.clear();
                dataE.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {

            }
        });

    }





}
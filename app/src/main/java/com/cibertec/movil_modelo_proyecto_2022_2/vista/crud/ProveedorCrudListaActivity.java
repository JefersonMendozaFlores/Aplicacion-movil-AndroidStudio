package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudListaActivity extends NewAppCompatActivity {


    Button btnCrudListar,btnCrudRegistrar;


    //del grid
    GridView gridCrudProveedor;

    ArrayList<Proveedor> data= new ArrayList<Proveedor>();
    ProveedorCrudAdapter adaptadorCrud;

    //service

    ServiceProveedor serviceProveedor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_lista);

        btnCrudListar = findViewById(R.id.btnCrudListarProveedor);
        btnCrudRegistrar = findViewById(R.id.btnCrudRegistraProveedor);
        gridCrudProveedor = findViewById(R.id.gridCrudProveedor);
        adaptadorCrud = new ProveedorCrudAdapter(this,R.layout.activity_proveedor_crud_item,data);
        gridCrudProveedor.setAdapter(adaptadorCrud);

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProveedorCrudListaActivity.this, ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA PROVEEDOR");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);




            }
        });

        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lista();

            }
        });

        gridCrudProveedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Proveedor objProveedor = data.get(position);


                Intent intent = new Intent(
                        ProveedorCrudListaActivity.this,
                        ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA PROVEEDOR");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objProveedor);
                startActivity(intent);
            }
        });


    }

    // metodos

    public void lista(){
        //declaramos
        Call<List<Proveedor>> call = serviceProveedor.listaProveedor();

        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {

                if(response.isSuccessful()){
                    List<Proveedor> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptadorCrud.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {

            }
        });
    }







}
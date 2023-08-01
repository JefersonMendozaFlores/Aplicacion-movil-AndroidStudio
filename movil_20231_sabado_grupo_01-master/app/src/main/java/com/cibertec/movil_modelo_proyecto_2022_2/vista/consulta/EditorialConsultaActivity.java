package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorialConsultaActivity extends NewAppCompatActivity {

    Button btnListaEditorial;

    GridView gridEditorial;

    ArrayList<Editorial> data = new ArrayList<>();

    EditorialAdapter adaptador;

    ServiceEditorial serviceEditorial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editorial_consulta);

        btnListaEditorial = findViewById(R.id.btnListaEditorial);
        gridEditorial = findViewById(R.id.gridEditorial);

        adaptador = new EditorialAdapter(this, R.layout.activity_editorial_item, data);
        gridEditorial.setAdapter(adaptador);

        gridEditorial.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adaptaderView, View view, int position, long id){
                Editorial objEditorial = data.get(position);

                Intent intent = new Intent(EditorialConsultaActivity.this, EditorialConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO", objEditorial);
                startActivity(intent);
            }
        });

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnListaEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEditorial();

            }
        });
    }


    public void listaEditorial(){
        Call<List<Editorial>> call = serviceEditorial.listaEditorial();
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                //mensajeAlert("Vas a consultar"+ response.code() + "-"+ response.message());
                //mensajeAlert("Vas a consultar on response");
                if (response.isSuccessful()){
                    List<Editorial> listaEditorial = response.body();
                    data.clear();
                    data.addAll(listaEditorial);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {
                //mensajeAlert("Error en el consulta");
            }
        });
        //Call<List<Editorial>> call = serviceEditorial.listaEditorial();
    }



    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDiallog = new AlertDialog.Builder(this);
        alertDiallog.setMessage(msg);
        alertDiallog.setCancelable(true);
        alertDiallog.show();
    }
}
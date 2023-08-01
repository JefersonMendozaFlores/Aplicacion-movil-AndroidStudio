package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.SalaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaConsultaActivity extends NewAppCompatActivity implements SalaAdapter.RoomListener {

    List<Sala> rooms = new ArrayList<>();

    RecyclerView rcvRooms;
    SalaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta);


        rcvRooms = (RecyclerView) findViewById(R.id.rcvRooms);
        rcvRooms.setHasFixedSize(true);
        rcvRooms.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SalaAdapter(rooms);
        adapter.setListener(this);
        rcvRooms.setAdapter(adapter);

        loadRooms();
    }

    private void loadRooms() {
        ConnectionRest.getConnection().create(ServiceSala.class).listAll().enqueue(
                new Callback<List<Sala>>() {
                    @Override
                    public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                        if (response.isSuccessful()) {
                            rooms.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Sala>> call, Throwable t) {
                        Log.d("Rest", "Error al listar salas: " + t.getMessage());
                    }
                }
        );
    }


    @Override
    public void onRoomClick(Sala room) {
        Intent intent = new Intent(this, SalaConsultaDetalleActivity.class);
        intent.putExtra("ROOM", new Gson().toJson(room));
        startActivity(intent);
    }

}
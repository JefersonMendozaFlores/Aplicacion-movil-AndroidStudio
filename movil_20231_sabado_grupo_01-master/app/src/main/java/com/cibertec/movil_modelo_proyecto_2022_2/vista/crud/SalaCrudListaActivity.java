package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.SalaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.RoomComparator;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaCrudListaActivity extends NewAppCompatActivity implements SalaAdapter.RoomListener {

    List<Sala> rooms = new ArrayList<>();

    RecyclerView rcvRooms;
    SalaAdapter adapter;
    ImageButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_lista);

        rcvRooms = findViewById(R.id.rcvRooms);
        rcvRooms.setHasFixedSize(true);
        rcvRooms.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SalaAdapter(rooms);
        adapter.setListener(this);
        rcvRooms.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            startRegisterOrUpdateActivity(null);
        });

        loadRooms();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            loadRooms();
        }
    }

    private void loadRooms() {
        ConnectionRest.getConnection().create(ServiceSala.class).listAll().enqueue(
                new Callback<List<Sala>>() {
                    @Override
                    public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                        if (response.isSuccessful()) {
                            List<Sala> dbRooms = response.body();
                            Collections.sort(dbRooms, new RoomComparator());
                            rooms.clear();
                            rooms.addAll(dbRooms);
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
    public boolean isOnlyView() {
        return false;
    }

    @Override
    public void onUpdateClick(Sala room) {
        startRegisterOrUpdateActivity(room);
    }

    @Override
    public void onRoomClick(Sala room) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.parse(room.getFechaRegistro(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            long days = ChronoUnit.DAYS.between(localDateTime, LocalDateTime.now());
            new AlertDialog.Builder(this)
                    .setTitle(room.getNumero())
                    .setMessage("Sala registrada " + (days == 0 ? "hoy" : "hace " + days + " días."))
                    .show();
        }
    }

    private void startRegisterOrUpdateActivity(Sala room) {
        Intent intent = new Intent(this, SalaCrudFormularioActivity.class);
        if (room != null) {
            intent.putExtra("ROOM", new Gson().toJson(room));
        }
        startActivityForResult(intent, 1);
    }

}
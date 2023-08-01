package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import static com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala.NUMBER_FORMAT;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sede;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSala;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceSede;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaRegistraActivity extends NewAppCompatActivity implements View.OnClickListener {

    ArrayAdapter<String> modalityAdapter;
    ArrayAdapter<String> campusAdapter;

    Spinner spModality;
    Spinner spCampus;
    EditText edtNumber;
    EditText edtFloor;
    EditText edtStudents;
    EditText edtResources;
    EditText edtRegisterDate;
    Button btnRegister;

    List<Modalidad> modalities = new ArrayList<>();
    List<String> modalityEntries = new ArrayList<>();
    List<Sede> campuses = new ArrayList<>();
    List<String> campusEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sala_registra);

        modalityAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalityEntries);
        spModality = findViewById(R.id.spModality);
        spModality.setAdapter(modalityAdapter);

        campusAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, campusEntries);
        spCampus = findViewById(R.id.spCampus);
        spCampus.setAdapter(campusAdapter);

        edtNumber = findViewById(R.id.edtNumber);
        edtFloor = findViewById(R.id.edtFloor);
        edtStudents = findViewById(R.id.edtStudents);
        edtResources = findViewById(R.id.edtResources);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        loadSpinners();
    }

    @Override
    public void onClick(View view) {
        ServiceSala service = ConnectionRest.getConnection().create(ServiceSala.class);

        String number = Objects.isNull(edtNumber.getText()) ? "" : edtNumber.getText().toString().trim();
        Integer floor = Objects.isNull(edtFloor.getText()) || edtFloor.getText().toString().trim().isEmpty()
                ? -1 : Integer.parseInt(edtFloor.getText().toString().trim());
        Integer students = Objects.isNull(edtStudents.getText()) || edtStudents.getText().toString().trim().isEmpty() ?
                -1 : Integer.parseInt(edtStudents.getText().toString().trim());
        String resources = Objects.isNull(edtResources.getText()) ? "" : edtResources.getText().toString().trim();
        String modality = Objects.isNull(spModality.getSelectedItem()) ? "" : (String) spModality.getSelectedItem();
        String campus = Objects.isNull(spCampus.getSelectedItem()) ? "" : (String) spCampus.getSelectedItem();

        if (number.isEmpty() || floor == -1 || students == -1 || resources.isEmpty() || modality.isEmpty() || campus.isEmpty()) {
            showDialog(false, "Debe ingresar todos los valores para poder hacer el registro.");
            return;
        }

        if (!Pattern.matches(NUMBER_FORMAT, number)) {
            showDialog(false, "El número no cumple con el formato establecido. Ej.: A001 ó Z999.");
            return;
        }

        Sala sala = new Sala();
        sala.setNumero(number);
        sala.setPiso(floor);
        sala.setNumAlumnos(students);
        sala.setRecursos(resources);
        sala.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
        sala.setEstado(1);

        for (Modalidad modalityItem : modalities) {
            if (modalityItem.getDescripcion().equals(modality)) {
                sala.setModalidad(modalityItem);
            }
        }

        for (Sede campusItem : campuses) {
            if (campusItem.getNombre().equals(campus)) {
                sala.setSede(campusItem);
            }
        }

        service.register(sala).enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()) {
                    Sala newSala = response.body();
                    showDialog(true, "Nueva sala registrada con id: " + newSala.getIdSala());
                } else {
                    showDialog(false, "Error al registrar sala: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                ToastUtil.showToast(getBaseContext(), "Error al registrar sala: " + t.getMessage());
            }
        });
    }

    private void loadSpinners() {
        ConnectionRest.getConnection().create(ServiceModalidad.class).listaTodos().enqueue(
                new Callback<List<Modalidad>>() {
                    @Override
                    public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                        if (response.isSuccessful()) {
                            modalities = response.body();
                            for (Modalidad modality : modalities) {
                                modalityEntries.add(modality.getDescripcion());
                            }
                            modalityAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                        Log.d("Rest", "Error al listar modalidades: " + t.getMessage());
                    }
                }
        );
        ConnectionRest.getConnection().create(ServiceSede.class).listaTodos().enqueue(
                new Callback<List<Sede>>() {
                    @Override
                    public void onResponse(Call<List<Sede>> call, Response<List<Sede>> response) {
                        if (response.isSuccessful()) {
                            campuses = response.body();
                            for (Sede campus : campuses) {
                                campusEntries.add(campus.getNombre());
                            }
                            campusAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Sede>> call, Throwable t) {
                        Log.d("Rest", "Error al lista sedes: " + t.getMessage());
                    }
                }
        );
    }

    private void showDialog(boolean isSuccess, String message) {
        new AlertDialog.Builder(this)
                .setTitle(isSuccess ? "Success" : "Error")
                .setMessage(message)
                .show();
    }
}
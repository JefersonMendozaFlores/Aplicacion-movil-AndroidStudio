package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.MainActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaRegistraActivity extends NewAppCompatActivity {

    Spinner spnModalidad;
    ArrayAdapter<String> modalidadAdapter;
    ArrayList<String> modalidades = new ArrayList<String>();

    Spinner spnPais;
    ArrayAdapter<String> paisAdapter;
    ArrayList<String> paises = new ArrayList<String>();

    EditText txtNombre;
    EditText txtFrecuencia;
    EditText txtFechaCreacion;
    Button btnRegistrar;

    ServiceModalidad serviceModalidad;
    ServicePais servicePais;
    ServiceRevista serviceRevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_registra);

        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        modalidadAdapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidades);
        spnModalidad = findViewById(R.id.spnModalidad);
        spnModalidad.setAdapter(modalidadAdapter);

        paisAdapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(paisAdapter);

        cargarModalidades();
        cargarPaises();

        txtNombre = findViewById(R.id.txtNombre);
        txtFrecuencia = findViewById(R.id.txtFrecuencia);
        txtFechaCreacion = findViewById(R.id.txtFechaCreacion);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        Locale.setDefault( new Locale("es_ES"));

        txtFechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(RevistaRegistraActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH,month);
                            myCalendar.set(Calendar.DAY_OF_MONTH,day);

                            txtFechaCreacion.setText(dateFormat.format(myCalendar.getTime()));
                        }
                    },
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                String frecuencia = txtFrecuencia.getText().toString();
                String fechaCreacion = txtFechaCreacion.getText().toString();
                String modalidad = spnModalidad.getSelectedItem().toString().split(":")[0];
                String pais = spnPais.getSelectedItem().toString().split(":")[0];

                if (!nombre.matches(ValidacionUtil.TEXTO)) {
                    txtNombre.setError("El nombre es de 2 a 20 caracteres");
                    return;
                }

                if (!frecuencia.matches(ValidacionUtil.TEXTO)) {
                    txtFrecuencia.setError("La frecuencia es de 2 a 20 caracteres");
                    return;
                }

                if (!fechaCreacion.matches(ValidacionUtil.FECHA)) {
                    mensajeToast("La fecha de creación es YYYY-MM-dd");
                    txtFechaCreacion.setError("La fecha de creación es YYYY-MM-dd");
                    return;
                }

                Modalidad m = new Modalidad();
                m.setIdModalidad(Integer.parseInt(modalidad));

                Pais p = new Pais();
                p.setIdPais(Integer.parseInt(pais));

                Revista nueva = new Revista();
                nueva.setNombre(nombre);
                nueva.setFrecuencia(frecuencia);
                nueva.setFechaCreacion(fechaCreacion);
                nueva.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                nueva.setEstado(1);
                nueva.setModalidad(m);
                nueva.setPais(p);

                registrarRevista(nueva);
            }
        });
    }

    private void registrarRevista(Revista nueva) {
        Call<Revista> call = serviceRevista.registrar(nueva);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()) {
                    Revista e = response.body();
                    String mensaje = "Se registró la Revista \nCÓDIGO: " + e.getIdRevista()
                            + "\n";
                    mensajeAlert(mensaje);

                } else {
                    mensajeAlert("Error al acceder al servicio Rest >>> " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {

            }
        });
    }

    private void cargarModalidades() {
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()) {
                    List<Modalidad> lstModalidad = response.body();
                    for (Modalidad m : lstModalidad) {
                        modalidades.add(m.getIdModalidad() + ":" + m.getDescripcion());
                    }

                    modalidadAdapter.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    private void cargarPaises() {
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> lstPais = response.body();
                    for (Pais p : lstPais) {
                        paises.add(p.getIdPais()+ ":" + p.getNombre());
                    }

                    paisAdapter.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}
package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegistrar;
    Button btnCrudRegresar;

    EditText txtNombre;
    EditText txtFrecuencia;
    EditText txtFechaCreacion;

    Spinner spnCrudModalidad;
    ArrayAdapter<String> adapterModalidad;
    ArrayList<String> lstModalidad = new ArrayList<>();

    Spinner spnCrudPais;
    ArrayAdapter<String> adapterPais;
    ArrayList<String> lstPais = new ArrayList<>();

    ServiceModalidad serviceModalidad;
    ServicePais servicePais;
    ServiceRevista serviceRevista;

    String tipo;

    Revista revistaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_formulario);

        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnCrudRegistrar = findViewById(R.id.btnCrudRevistaFormularioRegistrar);

        txtNombre = findViewById(R.id.txtCrudRevistaFormularioNombre);
        txtFrecuencia = findViewById(R.id.txtCrudRevistaFormularioFrecuencia);
        txtFechaCreacion = findViewById(R.id.txtCrudRevistaFormularioFechaCreacion);
        spnCrudModalidad = findViewById(R.id.spnCrudRevistaFormularioModalidad);
        spnCrudPais = findViewById(R.id.spnCrudRevistaFormularioPais);

        adapterModalidad = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstModalidad);
        spnCrudModalidad.setAdapter(adapterModalidad);

        adapterPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstPais);
        spnCrudPais.setAdapter(adapterPais);

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");

        listarModalidades();
        listarPaises();

        if (tipo.equals("ACTUALIZA")) {
            revistaSeleccionada = (Revista) extras.get("var_objeto");
            txtNombre.setText(revistaSeleccionada.getNombre());
            txtFrecuencia.setText(revistaSeleccionada.getFrecuencia());
            txtFechaCreacion.setText(revistaSeleccionada.getFechaCreacion());
        }

        btnCrudRegistrar.setText(tipo);

        btnCrudRegresar = findViewById(R.id.btnCrudRevistaFormularioRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RevistaCrudFormularioActivity.this,
                        RevistaCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                String frecuencia = txtFrecuencia.getText().toString();
                String fechaCreacion = txtFechaCreacion.getText().toString();

                if (!nombre.matches(ValidacionUtil.TEXTO)) {
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                } else if (!frecuencia.matches(ValidacionUtil.TEXTO)) {
                    mensajeAlert("La frecuencia es de 2 a 20 caracteres");
                } else if (!fechaCreacion.matches(ValidacionUtil.FECHA)) {
                    mensajeAlert("La Fecha de Creación tiene formato YYYY-MM-dd");
                } else {
                    String textoModalidad = spnCrudModalidad.getSelectedItem().toString();
                    String idModalidad = textoModalidad.split(":")[0];
                    Modalidad objModalidad = new Modalidad();
                    objModalidad.setIdModalidad(Integer.parseInt(idModalidad));

                    String textoPais = spnCrudPais.getSelectedItem().toString();
                    String idPais = textoPais.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    Revista revista = new Revista();
                    revista.setNombre(nombre);
                    revista.setFrecuencia(frecuencia);
                    revista.setFechaCreacion(fechaCreacion);
                    revista.setModalidad(objModalidad);
                    revista.setPais(objPais);
                    revista.setEstado(1);
                    revista.setFechaRegistro((FunctionUtil.getFechaActualStringDateTime()));

                    if (tipo.equals("REGISTRA")) {
                        registar(revista);
                    } else if (tipo.equals("ACTUALIZA")) {
                        Revista objAux = (Revista) extras.get("var_objeto");
                        revista.setIdRevista(objAux.getIdRevista());
                        actualizar(revista);
                    }
                }
            }
        });
    }

    public void listarModalidades() {
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()) {
                    List<Modalidad> data = response.body();
                    for (Modalidad obj : data) {
                        lstModalidad.add(obj.getIdModalidad() + ":" + obj.getDescripcion());
                    }

                    adapterModalidad.notifyDataSetChanged();

                    if (tipo.equals("ACTUALIZA")) {
                        Modalidad objModalidad = revistaSeleccionada.getModalidad();
                        String aux = objModalidad.getIdModalidad() + ":" + objModalidad.getDescripcion();
                        int pos = -1;

                        for (int i = 0; i < lstModalidad.size(); i++) {
                            if (lstModalidad.get(i).equals(aux)) {
                                pos = i;
                                break;
                            }
                        }

                        spnCrudModalidad.setSelection(pos);
                    }
                } else {
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void listarPaises() {
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> data = response.body();
                    for (Pais obj : data) {
                        lstPais.add(obj.getIdPais() + ":" + obj.getNombre());
                    }

                    adapterPais.notifyDataSetChanged();

                    if (tipo.equals("ACTUALIZA")) {
                        Pais objPais = revistaSeleccionada.getPais();
                        String aux = objPais.getIdPais() + ":" + objPais.getNombre();
                        int pos = -1;

                        for (int i = 0; i < lstPais.size(); i++) {
                            if (lstPais.get(i).equals(aux)) {
                                pos = i;
                                break;
                            }
                        }

                        spnCrudPais.setSelection(pos);
                    }
                } else {
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void registar(Revista revista) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(revista);

        Call<Revista> call = serviceRevista.registrar(revista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if(response.isSuccessful()){
                    Revista objSalida = response.body();
                    String msg="Se registró la Revista con éxito\n";
                    msg+="CÓDIGO : " + objSalida.getIdRevista() + "\n";
                    msg+="NOMBRE : " + objSalida.getNombre() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void actualizar(Revista revista) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(revista);

        Call<Revista> call = serviceRevista.actualizar(revista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if(response.isSuccessful()){
                    Revista objSalida = response.body();
                    String msg="Se actualizó la Revista con éxito\n";
                    msg+="CÓDIGO : " + objSalida.getIdRevista() + "\n";
                    msg+="NOMBRE : " + objSalida.getNombre() ;
                    mensajeAlert(msg);
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

}
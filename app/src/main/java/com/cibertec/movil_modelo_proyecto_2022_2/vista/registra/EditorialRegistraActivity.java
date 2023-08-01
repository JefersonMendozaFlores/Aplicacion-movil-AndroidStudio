package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
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


public class EditorialRegistraActivity extends NewAppCompatActivity {

    EditText idEditorial;
    EditText txtRazonSocial;
    EditText txtDireccion;
    EditText txtRuc;
    EditText txtfechaCreacion;
    EditText fechaRegistro;
    EditText txtEstado;

    Spinner spnPaises;
    Spinner spnCategorias;

    ArrayAdapter<String> adapterPais;
    ArrayList<String> nombrePaises = new ArrayList<String>();

    ArrayAdapter<String> adapterCategorias;
    ArrayList<String> desCategorias = new ArrayList<String>();
    Button btnRegistrar;

    ServicePais servicePais;
    ServiceCategoria serviceCategoria;
    ServiceEditorial serviceEditorial;

    List<Editorial> ltsEditorial;
    List<Pais> ltsPaises;
    List<Categoria> ltsCategorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_registra);

        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtRuc = findViewById(R.id.txtRuc);
        txtfechaCreacion = findViewById(R.id.fechaCreacion);

        spnPaises = findViewById(R.id.spnPaises);
        adapterPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nombrePaises);
        spnPaises.setAdapter(adapterPais);

        spnCategorias = findViewById(R.id.spnCategorias);
        adapterCategorias = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, desCategorias);
        spnCategorias.setAdapter(adapterCategorias);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        listaPaises();
        listaCategorias();


        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String razonSocial = txtRazonSocial.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String ruc = txtRuc.getText().toString();
                String fechaCreacion = txtfechaCreacion.getText().toString();
                String pais = spnPaises.getSelectedItem().toString();
                String idPais = pais.split(":")[0];
                String categoria = spnCategorias.getSelectedItem().toString();
                String idCategoria = categoria.split(":")[0];

                //VALIDAR
                boolean isError = false;


                if(!txtRazonSocial.getText().toString().matches(ValidacionUtil.TEXTO)) {
                    txtRazonSocial.setError("- Este campo debe ser completado con la razón social");
                    isError=true;
                }

                if(!txtDireccion.getText().toString().matches(ValidacionUtil.DIRECCION)) {
                    txtDireccion.setError("- Completar con dirección válida");
                    isError=true;
                }

                if(!txtRuc.getText().toString().matches(ValidacionUtil.RUC)) {
                    txtRuc.setError("- Este campo debe ser completado con RUC válido de 11 dígitos");
                    isError=true;
                }

                if(txtfechaCreacion.getText().toString().isEmpty()){
                    txtfechaCreacion.setError("- Debe completar la fecha de creación  con este formato yyyy-MM-dd");
                    isError=true;
                }

                if (isError) {
                    return;
                }

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                Categoria objCategoria = new Categoria();
                objCategoria.setIdCategoria(Integer.parseInt(idCategoria));


                Editorial obj = new Editorial();
                obj.setRazonSocial(razonSocial);
                obj.setDireccion(direccion);
                obj.setRuc(ruc);
                obj.setFechaCreacion(fechaCreacion);
                obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                obj.setEstado(1);
                obj.setPais(objPais);
                obj.setCategoria(objCategoria);


                registraEditorial(obj);
            }
        });
    }

      public void registraEditorial(Editorial obj){
          Call<Editorial> call = serviceEditorial.registerEditorial(obj);

          //mensajeAlert("request body: " + call.request().body() + "| url: " + call.request().url());

          call.enqueue(new Callback<Editorial>() {
              @Override
              public void onResponse(Call<Editorial> call, Response<Editorial> response) {

                  //mensajeAlert("Vas a registrar"+ response.code() + "-"+ response.message());
                  if (response.isSuccessful()){
                      Editorial objSalida = response.body();
                      mensajeAlert("Registro exitoso >><ID>>>"+ objSalida.getIdEditorial() + "\n>>>>>Razón Social>>>>>"+objSalida.getRazonSocial());
                      limpiar();
                  } else {
                      mensajeAlert(response.toString());
                  }

              }

              @Override
              public void onFailure(Call<Editorial> call, Throwable t) {
                  mensajeAlert("Error en el registro"+ t.getMessage());
              }
          });

        }



    private void listaPaises() {

        Call<List<Pais>> call =  servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                //mensajeToast("Acceso exitosos de país?" + response.isSuccessful());
                if (response.isSuccessful()) {
                    ltsPaises = response.body();
                    for (Pais obj : ltsPaises) {
                        nombrePaises.add(obj.getIdPais() + ":" +obj.getNombre());
                    }
                    adapterPais.notifyDataSetChanged();
                } else {
                    mensajeToast("Error de acceso" + response.message());
                }

            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("error al acceder al Servicio Rest "+ t.getMessage());
            }

        });
    }


    public void listaCategorias(){
        Call<List<Categoria>> call =  serviceCategoria.listaCategoriaDeEditorial();
        call.enqueue(new Callback<List<Categoria>>(){
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                //mensajeToast("Acceso a Categorias exitoso" + response.isSuccessful());
                if (response.isSuccessful()) {
                    ltsCategorias =  response.body();
                    for(Categoria obj: ltsCategorias){
                        desCategorias.add(obj.getIdCategoria() + ":"+ obj.getDescripcion());
                    }
                    adapterCategorias.notifyDataSetChanged();
                }else{
                    mensajeToast(">>"+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast("error al obtener categorias" + t.getCause().toString());

            }
        });
    }

    public void limpiar(){

        txtRazonSocial.setText("");
        txtDireccion.setText("");
        txtRuc.setText("");
        txtfechaCreacion.setText("");
        spnPaises.setSelection(0);
        spnCategorias.setSelection(0);

    }




                public void mensajeAlert(String msg){
                    AlertDialog.Builder alertDiallog = new AlertDialog.Builder(this);
                    alertDiallog.setMessage(msg);
                    alertDiallog.setCancelable(true);
                    alertDiallog.show();
                }

                void mensajeToast(String mensaje){
                    Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
                    toast1.show();
                }


    }

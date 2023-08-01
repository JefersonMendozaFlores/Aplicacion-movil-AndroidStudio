package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.AutoCompleteTextView;

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {

    EditText idEditorial;
    EditText txtRazonSocial;
    EditText txtDireccion;
    EditText txtRuc;
    EditText txtfechaCreacion;
    EditText fechaRegistro;
    EditText txtEstado;
    Spinner spnPaises;

    //UI reference of textView
    //AutoCompleteTextView spnPaises;

    Spinner spnCategorias;
    //AutoCompleteTextView spnCategorias;

    ArrayAdapter<String> adapterPais;
    ArrayList<String> nombrePaises = new ArrayList<String>();

    ArrayAdapter<String> adapterCategorias;
    ArrayList<String> desCategorias = new ArrayList<String>();
    Button btnCrudRegresar,btnCrudRegistrar;

    GridView gridCrudEditorial;

    ArrayList<Editorial> dataE = new ArrayList<>();

    EditorialCrudAdapter adaptador;

    ServicePais servicePais;
    ServiceCategoria serviceCategoria;
    ServiceEditorial serviceEditorial;

    List<Pais> ltsPaises;
    List<Categoria> ltsCategorias;
    Editorial objEditorialSelect;

    String tipo;

    TextInputLayout lblRazon, lblDireccion, lblRuc, lblPais, lblFechaCreacion, lblCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);

        lblRazon = findViewById(R.id.lblRazon);
        lblDireccion = findViewById(R.id.lblDireccion);
        lblRuc = findViewById(R.id.lblRuc);
        //lblPais = findViewById(R.id.lblPais);
        lblFechaCreacion = findViewById(R.id.lblFechaCreacion);
        //lblCategoria = findViewById(R.id.lblCategoria);



        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);

        btnCrudRegistrar= findViewById(R.id.btnCrudRegistrar);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtRuc = findViewById(R.id.txtRuc);
        txtfechaCreacion = findViewById(R.id.fechaCreacion);
        configureDatePicker();

        spnPaises = findViewById(R.id.spnPaises);
        adapterPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nombrePaises);
        spnPaises.setAdapter(adapterPais);

        spnCategorias = findViewById(R.id.spnCategorias);
        adapterCategorias = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, desCategorias);
        spnCategorias.setAdapter(adapterCategorias);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);


        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");

        listaPaises();
        listaCategorias();

        if (tipo.equals("ACTUALIZA")){
            objEditorialSelect = (Editorial) extras.get("var_objeto");
            txtRazonSocial.setText(objEditorialSelect.getRazonSocial());
            txtDireccion.setText(objEditorialSelect.getDireccion());
            txtRuc.setText(objEditorialSelect.getRuc());
            txtfechaCreacion.setText(objEditorialSelect.getFechaCreacion());
        }

        btnCrudRegistrar.setText(tipo);

        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditorialCrudFormularioActivity.this,EditorialCrudListaActivity.class);
                startActivity(intent);

            }
        });


        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                borrarErrores();
                String razonSocial = txtRazonSocial.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String ruc = txtRuc.getText().toString();
                String fechaCreacion = txtfechaCreacion.getText().toString();

                String pais = spnPaises.getSelectedItem().toString();
                //String pais = spnPaises.getText().toString();

                String idPais = pais.split(":")[0];
                String categoria = spnCategorias.getSelectedItem().toString();
                //String categoria = spnCategorias.getText().toString();
                String idCategoria = categoria.split(":")[0];

                //VALIDAR
                boolean isError = false;
                if(!txtRazonSocial.getText().toString().matches(ValidacionUtil.TEXTO)) {
                    //txtRazonSocial.setError("- Este campo debe ser completado con la razón social");
                    lblRazon.setError("- Este campo debe ser completado con la razón social");
                    isError=true;
                }

                if(!txtDireccion.getText().toString().matches(ValidacionUtil.DIRECCION)) {
                    lblDireccion.setError("- Completar con dirección válida");
                    isError=true;
                }

                if(!txtRuc.getText().toString().matches(ValidacionUtil.RUC)) {
                    lblRuc.setError("- Este campo debe ser completado con RUC válido de 11 dígitos");
                    isError=true;
                }

                /*if(txtfechaCreacion.getText().toString().isEmpty()){
                    txtfechaCreacion.setError("- Debe completar la fecha de creación  con este formato YYYY-MM-DD");
                    isError=true;
                }*/
                if(!fechaCreacion.matches(ValidacionUtil.FECHA)){
                    lblFechaCreacion.setError("- Debe completar la fecha de creación  con este formato YYYY-MM-DD");
                    //mensajeAlert("- Debe completar la fecha de creación  con este formato YYYY-MM-DD");
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

                //json
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(obj);
                //mensajeAlert(json);

                //ACTUALIZAR
                if (tipo.equals("REGISTRA")) {
                    registraEditorial(obj);
                } else if (tipo.equals("ACTUALIZA")) {
                    Editorial objAux = (Editorial) extras.get("var_objeto");
                    obj.setIdEditorial(objAux.getIdEditorial());
                    actualizarEditorial(obj);
                }

            }
        });
    }

    //REGISTRA EDITORIAL (METODO)
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


    public void actualizarEditorial(Editorial obj){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        // mensajeAlert(json);
        Call<Editorial> call = serviceEditorial.actualizarEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    String msg="Se actualizó el Editorial con exito\n";
                    msg+="ID : "+ objSalida.getIdEditorial() +"\n";
                    msg+="NOMBRE : "+ objSalida.getRazonSocial() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
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

                    if (tipo.equals("ACTUALIZA")){
                        Pais objPais = objEditorialSelect.getPais();
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1;
                        for(int i=0; i< nombrePaises.size(); i++){
                            if (nombrePaises.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnPaises.setSelection(pos);
                    }

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

                        if (tipo.equals("ACTUALIZA")){
                            Categoria objCategoria = objEditorialSelect.getCategoria();
                            String aux2 = objCategoria.getIdCategoria()+":"+objCategoria.getDescripcion();
                            int pos = -1;
                            for(int i=0; i< desCategorias.size(); i++){
                                if (desCategorias.get(i).equals(aux2)){
                                    pos = i;
                                    break;
                                }
                            }
                            spnCategorias.setSelection(pos);
                        }

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

        //spnPaises.setText("");
        //spnCategorias.setText("");

        borrarErrores();
        //lblPais.setError(null);
        //lblCategoria.setError(null);


    }

    private void borrarErrores(){
        lblRazon.setError(null);
        lblDireccion.setError(null);
        lblRuc.setError(null);
        lblFechaCreacion.setError(null);
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


        private void configureDatePicker(){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Selecciona fecha")
                    .setTextInputFormat(format)
                    .build();

            txtfechaCreacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDatePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick(Object selection) {

                            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                            calendar.setTimeInMillis((Long)selection);

                            String formattedDate  = format.format(calendar.getTime());
                            txtfechaCreacion.setText(formattedDate);
                        }
                    });
                }
            });


        }

    }




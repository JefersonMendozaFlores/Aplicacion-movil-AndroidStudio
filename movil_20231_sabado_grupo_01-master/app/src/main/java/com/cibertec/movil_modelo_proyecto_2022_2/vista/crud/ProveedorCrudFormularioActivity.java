package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.TipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceTipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.registra.ProveedorRegistraActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudFormularioActivity extends NewAppCompatActivity {


    Button btnCrudRegresar, btnCrudRegistra;
    TextView txtTitulo;



    ////////
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();



    Spinner spnTipProveedor;
    ArrayAdapter<String> adaptadorTipoProve;
    ArrayList<String> tipoProveedor = new ArrayList<String>();




    ServicePais servicePais;
    ServiceTipoProveedor serviceTipoProveedor;
    ServiceProveedor serviceProveedor;




    EditText txtRazonSocial, txtRuc, txtDireccion, txtTelFijo, txtTelMov, txtContacto, txtFechaRegistro;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_formulario);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);
        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);




        //para el adaptador

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnCrudProvePais);
        spnPais.setAdapter(adaptadorPais);



        adaptadorTipoProve = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoProveedor);
        spnTipProveedor = findViewById(R.id.spnCrudProveTipoProve);
        spnTipProveedor.setAdapter(adaptadorTipoProve);

        cargaListaPais();
        cargaListaTipoProveedor();






        btnCrudRegistra = findViewById(R.id.btnCrudFormRegProv);
        txtTitulo = findViewById(R.id.idTitcrudProeedor);




        txtRazonSocial = findViewById(R.id.txtCrudProveRazonSocial);
        txtRuc = findViewById(R.id.txtCrudProveRuc);
        txtDireccion = findViewById(R.id.txtCrudProveDirec);
        txtTelFijo = findViewById(R.id.txtCrudProveTelf);
        txtTelMov = findViewById(R.id.txtCrudProveCel);
        txtContacto = findViewById(R.id.txtCrudProveContacto);
        txtFechaRegistro = findViewById(R.id.txtCrudProveFechaRegistro);


        Locale.setDefault( new Locale("es_ES"));


        txtFechaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String horaActual = sdf.format(new Date());

                new DatePickerDialog(
                        ProveedorCrudFormularioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);


                                txtFechaRegistro.setText(dateFormat.format(myCalendar.getTime())+" "+horaActual);
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }


        });




        // para determinar si actualiza o registra



        Bundle extras =  getIntent().getExtras();
        String tipo = (String) extras.get("var_tipo");
        String titulo =(String) extras.get("var_titulo");





        if (tipo.equals("ACTUALIZA")){
            Proveedor objProveedor = (Proveedor) extras.get("var_objeto");
            txtRazonSocial.setText(objProveedor.getRazonsocial());
            txtRuc.setText(objProveedor.getRuc());
            txtDireccion.setText(objProveedor.getDireccion());
            txtTelFijo.setText(objProveedor.getTelefono());
            txtTelMov.setText(objProveedor.getCelular());
            txtContacto.setText(objProveedor.getContacto());
            txtFechaRegistro.setText(objProveedor.getFechaRegistro());

        }


        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);







        btnCrudRegresar= findViewById(R.id.btnCrudFormRegresarProv);

        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProveedorCrudFormularioActivity.this, ProveedorCrudListaActivity.class);
                startActivity(intent);
            }
        });


        /////////desde aqui

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //recibimos los datos osea lllenvamos el combo papa



                String rs = txtRazonSocial.getText().toString();
                String ruc = txtRuc.getText().toString();
                String dire = txtDireccion.getText().toString();
                String tfij = txtTelFijo.getText().toString();
                String tmov = txtTelMov.getText().toString();
                String conta = txtContacto.getText().toString();
                String fregi = txtFechaRegistro.getText().toString();

                if (!rs.matches(ValidacionUtil.TEXTO)){
                    //mensajeToast("La razón social es de 2 a 20 caracteres");
                    txtRazonSocial.setError("La razón social es de 2 a 20 caracteres");
                }else if (!ruc.matches(ValidacionUtil.RUC)){
                    //mensajeToast("El RUC debe tener 11 dígitos");
                    txtRuc.setError("El RUC es 11 dígitos");
                }else if (!dire.matches(ValidacionUtil.DIRECCION)) {
                    //mensajeToast("La dirección  debe ser mayor a 5 caracteres");
                    txtDireccion.setError("La dirección  es de 3 a 30 caracteres");
                }else if (!tmov.matches(ValidacionUtil.TELEFONO)){
                    txtTelMov.setError("El numero Móvil debe ser de 9 digitos");
                }else if (!tfij.matches(ValidacionUtil.TELEFONOFIJO)){
                    txtTelFijo.setError("El numero Fijo debe tener 7 digitos despues de codigo local");



                }else {

                    String textoPais = spnPais.getSelectedItem().toString();
                    String idPais = textoPais.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));


                    String textoTipoProveedor = spnTipProveedor.getSelectedItem().toString();
                    String idTipoProveedor = textoTipoProveedor.split(":")[0];
                    TipoProveedor objTipoProveedor = new TipoProveedor();
                    objTipoProveedor.setIdTipoProveedor(Integer.parseInt(idTipoProveedor));

                    Proveedor objProveedor = new Proveedor();
                    objProveedor.setIdProveedor(0);
                    objProveedor.setRazonsocial(rs);
                    objProveedor.setRuc(ruc);
                    objProveedor.setDireccion(dire);
                    objProveedor.setTelefono(tfij);
                    objProveedor.setCelular(tmov);
                    objProveedor.setContacto(conta);
                    objProveedor.setEstado(1);
                    objProveedor.setFechaRegistro(fregi);
                    objProveedor.setPais(objPais);
                    objProveedor.setTipoProveedor(objTipoProveedor);





                    if (tipo.equals("REGISTRA")){
                        insertarProveedor(objProveedor);

                    }else if (tipo.equals("ACTUALIZA")){
                        Proveedor objAux = (Proveedor) extras.get("var_objeto");
                        objProveedor.setIdProveedor(objAux.getIdProveedor());
                        actualizaProveedor(objProveedor);
                    }
                }






            }
        });

    }



    //metodos


    public void insertarProveedor(Proveedor objProveedor){

        Call<Proveedor> call = serviceProveedor.insertaProveedor(objProveedor);


        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {



                if(response.isSuccessful()){
                    Proveedor objProveRegistrados = response.body();
                    mensajeAlert("Registro Exitoso : "+"\n"+"id :" + objProveRegistrados.getIdProveedor()
                            +"\n"+"RAzón Social :"  + objProveRegistrados.getRazonsocial()+"\n"+"RUC : "+ objProveRegistrados.getRuc());
                }else{
                    mensajeToast("Error al acceder al servicio rest 1" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeToast("Erro al Acceder al servico rest 2" + t.getMessage());

            }
        });

    }


    public void actualizaProveedor(Proveedor objProveedor){

        Call<Proveedor> call = serviceProveedor.actualizaProveedor(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if(response.isSuccessful()){
                    Proveedor objSalida = response.body();
                    String msg="Se actualizó el Proveedor con exito\n";
                    msg+="ID : "+ objSalida.getIdProveedor() +"\n";
                    msg+="NOMBRE : "+ objSalida.getRazonsocial() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }













    public void cargaListaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lstPaises = response.body();
                    for(Pais obj : lstPaises){
                        paises.add(obj.getIdPais()+":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al servicio REst");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al servico rest <<" + t.getMessage());

            }
        });
    }


    public void cargaListaTipoProveedor(){
        Call<List<TipoProveedor>> call= serviceTipoProveedor.listaTodos();
        call.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if(response.isSuccessful()){
                    List<TipoProveedor> lstTipoProve= response.body();
                    for(TipoProveedor obj : lstTipoProve){
                        tipoProveedor.add(obj.getIdTipoProveedor()+":"+ obj.getDescripcion());
                    }
                    adaptadorTipoProve.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al servico REst");
                }
            }

            @Override
            public void onFailure(Call<List<TipoProveedor>> call, Throwable t) {
                mensajeToast("errror al aceceder al servicio rest <<<<"+ t.getMessage());

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
package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;



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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRegistraActivity extends NewAppCompatActivity {

    //REGISTRO

    Button btnRegistrar;

    EditText txtNombre;
    EditText txtApellidos;
    EditText txtTelefono;
    EditText txtDni;
    EditText txtCorreo;
    EditText txtDireccion;
    EditText txtFechaNacimiento;

    //HILADORES

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombresPaises=new ArrayList<String>();
    List<Pais> lstPaises;

    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> lstNombresModalidades=new ArrayList<String>();
    List<Modalidad> lstModalidades;

    ServiceAlumno service;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_registra);

        service = ConnectionRest.getConnection().create(ServiceAlumno.class);

        //hiladores
        adaptadorPais =new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPaises);
        spnPais=findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptadorPais);
        listaPaises();

        adaptadorModalidad = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresModalidades);
        spnModalidad=findViewById(R.id.spnModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);
        listaModalidades();


        //registro

        txtNombre=findViewById(R.id.txtNombre);
        txtApellidos=findViewById(R.id.txtApellidos);
        txtTelefono=findViewById(R.id.txtTelefono);
        txtDni=findViewById(R.id.txtDni);
        txtCorreo=findViewById(R.id.txtCorreo);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtFechaNacimiento=findViewById(R.id.txtFechaNacimiento);

        //BOTON

        btnRegistrar =findViewById(R.id.btnRegistrar);

        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                new DatePickerDialog(
                        AlumnoRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {
                                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                                txtFechaNacimiento.setText(dateFormat.format(myCalendar.getTime()));
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

            String nom = txtNombre.getText().toString();
            String ape = txtApellidos.getText().toString();
            String tel = txtTelefono.getText().toString();
            String dni = txtDni.getText().toString();
            String email = txtCorreo.getText().toString();
            String direc =txtDireccion.getText().toString();
            String fec = txtFechaNacimiento.getText().toString();

            if(!nom.matches(ValidacionUtil.TEXTO)) {
                txtNombre.setError("El campo nombre es de 2 a 20 caracteres");
            }else if(!ape.matches(ValidacionUtil.TEXTO)){
                txtApellidos.setError("El campo apellido es de 2 a 20 caracteres");
            }else if(!tel.matches(ValidacionUtil.TELEFONO)){
                txtTelefono.setError("El campo telefono debe contar con 9 digitos");
            }else if(!dni.matches(ValidacionUtil.DNI)){
                txtDni.setError("El campo dni debe contar con 8 digitos");
            }else if(!email.matches(ValidacionUtil.CORREO_GMAIL)){
                txtCorreo.setError("El campo E-Mail debe contar con @gmail.com");
            }else if(!direc.matches(ValidacionUtil.DIRECCION)){
                txtDireccion.setError("El campo direccion debe contar con caracteres");
            }else if(!fec.matches(ValidacionUtil.FECHA)){
                txtFechaNacimiento.setError("El campo fecha debe ser llenado");
            }else{

                //Pais
                String textoPais = spnPais.getSelectedItem().toString();
                String idPais = textoPais.split(":")[0];

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                //Modalidad
                String textoModalidad = spnModalidad.getSelectedItem().toString();
                String idModalidad = textoModalidad.split(":")[0];

                Modalidad objModalidad = new Modalidad();
                objModalidad.setIdModalidad(Integer.parseInt(idModalidad));


                Alumno objAlumno = new Alumno();
                objAlumno.setNombres(nom);
                objAlumno.setApellidos(ape);
                objAlumno.setTelefono(tel);
                objAlumno.setDni(dni);
                objAlumno.setCorreo(email);
                objAlumno.setDireccion(direc);
                objAlumno.setFechaNacimiento(fec);
                objAlumno.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objAlumno.setEstado(1);
                objAlumno.setPais(objPais);
                objAlumno.setModalidad(objModalidad);


                insertaAlumno(objAlumno);

                }
            }
        });
    }

    public void insertaAlumno(Alumno objAlumno){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAlumno);
       // mensajeAlert(json);
        Call<Alumno> call = service.insertaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno objSalida = response.body();
                    String msg="Se registro Alumno con exito\n";
                    msg+="ID : "+ objSalida.getIdAlumno() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }
    public void listaPaises(){
        Call<List<Pais>> call= service.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                mensajeToast(">>"+response.isSuccessful());
                if(response.isSuccessful()){
                lstPaises=response.body();
                for(Pais obj:lstPaises){
                    lstNombresPaises.add(obj.getIdPais()+":"+obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void listaModalidades(){
        Call<List<Modalidad>> call= service.listaModalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                mensajeToast(">>"+response.isSuccessful());
                if(response.isSuccessful()){
                    lstModalidades=response.body();
                    for(Modalidad obj:lstModalidades){
                        lstNombresModalidades.add(obj.getIdModalidad()+":"+obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {

            }
        });

    }


    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder( this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();

    }
    void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

}
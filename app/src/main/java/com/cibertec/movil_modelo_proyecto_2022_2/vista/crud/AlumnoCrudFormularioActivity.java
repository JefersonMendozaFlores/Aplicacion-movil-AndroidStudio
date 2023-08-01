package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
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

public class AlumnoCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresar, btnCrudRegistrar;

    TextView txtTitulo;

    EditText txtCrudNombre,txtCrudApellido,txtCrudTelefono,txtCrudDni,txtCrudCorreo,
            txtCrudDirección,txtCrudFechaNacimiento;

    Spinner spnCrudAlumnoModalidad,spnCrudAlumnoPais;


    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> lstNombreModalidad = new ArrayList<String>();

    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombrePais = new ArrayList<String>();


    ServiceAlumno serviceAlumno;

    ServicePais servicePais;
    ServiceModalidad serviceModalidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_formulario);

        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);

        btnCrudRegistrar=findViewById(R.id.btnCrudRegistrar);
        txtTitulo=findViewById(R.id.idCrudTituloAlumno);

        txtCrudNombre=findViewById(R.id.txtCrudNombre);
        txtCrudApellido=findViewById(R.id.txtCrudApellido);
        txtCrudTelefono=findViewById(R.id.txtCrudTelefono);
        txtCrudDni=findViewById(R.id.txtCrudDni);
        txtCrudCorreo=findViewById(R.id.txtCrudCorreo);
        txtCrudDirección=findViewById(R.id.txtCrudDirección);
        txtCrudFechaNacimiento=findViewById(R.id.txtCrudFechaNacimiento);

        spnCrudAlumnoPais=findViewById(R.id.spnCrudAlumnoPais);
        spnCrudAlumnoModalidad=findViewById(R.id.spnCrudAlumnoModalidad);

        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombrePais);
        spnCrudAlumnoPais.setAdapter(adaptadorPais);

        adaptadorModalidad = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombreModalidad);
        spnCrudAlumnoModalidad.setAdapter(adaptadorModalidad);


        Bundle extras = getIntent().getExtras();
        String tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");



        cargaPais();
        cargaModalidad();



        if(tipo.equals("ACTUALIZA")){
            Alumno objAlumno=(Alumno) extras.get("var_objeto");
            txtCrudNombre.setText(objAlumno.getNombres());
            txtCrudApellido.setText(objAlumno.getApellidos());
            txtCrudTelefono.setText(objAlumno.getTelefono());
            txtCrudDni.setText(objAlumno.getDni());
            txtCrudCorreo.setText(objAlumno.getCorreo());
            txtCrudDirección.setText(objAlumno.getDireccion());
            txtCrudFechaNacimiento.setText(objAlumno.getFechaNacimiento());

           /* Pais objPais = objAlumno.getPais();
            String aux2= objPais.getIdPais()+":"+objPais.getNombre();
            int pos=-1;
            for(int i=0; i<lstNombrePais.size();i++){
                if(lstNombrePais.get(i).equals(aux2)){
                    pos=i;
                }
            }
            spnCrudAlumnoPais.setSelection(pos);

            Modalidad objModalidad = objAlumno.getModalidad();
            String aux3= objModalidad.getIdModalidad()+":"+objModalidad.getDescripcion();
            int posi=-1;
            for(int i=0; i<lstNombreModalidad.size();i++){
                if(lstNombreModalidad.get(i).equals(aux3)){
                    posi=i;
                }
            }
            spnCrudAlumnoModalidad.setSelection(posi); */
        }


        btnCrudRegistrar.setText(tipo);
        txtTitulo.setText(titulo);
        btnCrudRegresar=findViewById(R.id.btnCrudRegresar);

        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        AlumnoCrudFormularioActivity.this,
                        AlumnoCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = txtCrudNombre.getText().toString();
                String apellido = txtCrudApellido.getText().toString();
                String telefono= txtCrudTelefono.getText().toString();
                String dni = txtCrudDni.getText().toString();
                String correo = txtCrudCorreo.getText().toString();
                String direccion = txtCrudDirección.getText().toString();
                String fecNac = txtCrudFechaNacimiento.getText().toString();

                if (!nombre.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                }else if (!apellido.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El apellido es de 2 a 20 caracteres");
                }else if (!telefono.matches(ValidacionUtil.TELEFONO)){
                    mensajeAlert("Ingrese 9 digitos");
                }else if (!dni.matches(ValidacionUtil.DNI)){
                    mensajeAlert("Ingrese 8 digitos");
                }else if (!correo.matches(ValidacionUtil.CORREO_GMAIL)){
                    mensajeAlert("El campo cuenta con @gamil.com");
                }else if (!direccion.matches(ValidacionUtil.DIRECCION)){
                    mensajeAlert("Ingrese dirección");
                }else if (!fecNac.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha de nacimiento es YYYY-MM-dd");

                }else {
                        //
                    String textoPais = spnCrudAlumnoPais.getSelectedItem().toString();
                    String idPais = textoPais.split(":")[0];

                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));
                    //
                    String textoModalidad= spnCrudAlumnoModalidad.getSelectedItem().toString();
                    String idModalidad = textoModalidad.split(":")[0];

                    Modalidad objModalidad = new Modalidad();
                    objModalidad.setIdModalidad(Integer.parseInt(idModalidad));

                    Alumno objAlumno = new Alumno();
                    objAlumno.setNombres(nombre);
                    objAlumno.setApellidos(apellido);
                    objAlumno.setTelefono(telefono);
                    objAlumno.setDni(dni);
                    objAlumno.setCorreo(telefono);
                    objAlumno.setDireccion(direccion);
                    objAlumno.setFechaNacimiento(FunctionUtil.getFechaActualStringDateTime());
                    objAlumno.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());     // automatico
                    objAlumno.setEstado(1);  // activo y innactivo                               // automatico
                    objAlumno.setPais(objPais);
                    objAlumno.setModalidad(objModalidad);

                    if (tipo.equals("REGISTRA")) {
                        insertaAlumno(objAlumno);
                    } else if (tipo.equals("ACTUALIZA")) {
                        Alumno objAux = (Alumno) extras.get("var_objeto");
                        objAlumno.setIdAlumno(objAux.getIdAlumno());
                        actualizaAlumno(objAlumno);
                    }

                }
            }
        });


    }

    public void insertaAlumno(Alumno objAlumno){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAlumno);
        // mensajeAlert(json);
        Call<Alumno> call = serviceAlumno.insertaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno objSalida = response.body();
                    String msg="Se registró el alumno con exito\n";
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

    public void actualizaAlumno(Alumno objAlumno){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAlumno);
        // mensajeAlert(json);
        Call<Alumno> call = serviceAlumno.actualizaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno objSalida = response.body();
                    String msg="Se actualizó el alumno con exito\n";
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

    public void cargaPais() {
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lst = response.body();
                    for(Pais obj:lst){
                        lstNombrePais.add(obj.getIdPais()+":"+obj.getNombre());
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
    public void   cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if(response.isSuccessful()){
                    List<Modalidad> lst = response.body();
                    for(Modalidad obj:lst){
                        lstNombreModalidad.add(obj.getIdModalidad()+":"+obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();


                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

}
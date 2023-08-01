package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
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

public class AutorCrudFormularioActivity extends NewAppCompatActivity {

    // botones
    Button btnCrudRegistrar, btnCrudRegresar;

    // Campos
    // EditText
    EditText txtCrudNombre,txtCrudApellido,txtCrudCorreo,txtCrudFechaNacimiento,txtCrudTelefono;

    // Spinner
    Spinner spnCrudAutorGrado,spnCrudAutorPais;

    // Adaptador - Grado
    ArrayAdapter<String> adaptadorGrados;
    ArrayList<String> lstNombresGra = new ArrayList<String>();

    // Adaptador - Pais
    ArrayAdapter<String> adaptadorPaises;
    ArrayList<String> lstNombresPaises = new ArrayList<String>();

    // Servicio
    ServiceAutor serviceAutor;

    // El tipo define si es REGISTRA o ACTUALIZA
    String tipo;

    // Se recibe el cliente seleccionado
    Autor objAutorSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_formulario);

        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);

        btnCrudRegistrar = findViewById(R.id.btnCrudRegistrar);

        txtCrudNombre = findViewById(R.id.txtCrudNombre);
        txtCrudApellido = findViewById(R.id.txtCrudApellido);
        txtCrudCorreo = findViewById(R.id.txtCrudCorreo);
        txtCrudFechaNacimiento = findViewById(R.id.txtCrudFechaNacimiento);
        txtCrudTelefono = findViewById(R.id.txtCrudTelefono);
        spnCrudAutorGrado = findViewById(R.id.spnCrudAutorGrado);
        spnCrudAutorPais = findViewById(R.id.spnCrudAutorPais);

        // Spinner Autor Grado
        adaptadorGrados = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresGra);
        spnCrudAutorGrado.setAdapter(adaptadorGrados);

        // Spinner Autor Pais
        adaptadorPaises = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPaises);
        spnCrudAutorPais.setAdapter(adaptadorPaises);


        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");

        cargaGrados();
        cargaPaises();

        if (tipo.equals("ACTUALIZA")){
            objAutorSeleccionado = (Autor) extras.get("var_objeto");
            txtCrudNombre.setText(objAutorSeleccionado.getNombres());
            txtCrudApellido.setText(objAutorSeleccionado.getApellidos());
            txtCrudCorreo.setText(objAutorSeleccionado.getCorreo());
            txtCrudFechaNacimiento.setText(objAutorSeleccionado.getFechaNacimiento());
            txtCrudTelefono.setText(objAutorSeleccionado.getTelefono());
            /*
            if (objClienteSeleccionado.getEstado() == 0){
                spnEstado.setSelection(1);
            }else{
                spnEstado.setSelection(0);
            }
            */
        }

        btnCrudRegistrar.setText(tipo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AutorCrudFormularioActivity.this,
                        AutorCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                String nom = txtCrudNombre.getText().toString();
                String ape = txtCrudApellido.getText().toString();
                String correo = txtCrudCorreo.getText().toString();
                String fecNac = txtCrudFechaNacimiento.getText().toString();
                String telef = txtCrudTelefono.getText().toString();
                // VALIDACIONES
                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                }else if (!ape.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El apellido es de 2 a 20 caracteres");
                }else if (!correo.matches(ValidacionUtil.CORREO_GMAIL)){
                    mensajeAlert("El correo debe terminar en @gmail.com. Ejemplo: correo@gmail.com");
                }else if (!fecNac.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha de nacimiento. Ejemplo: 2004-01-14");
                }else if (!telef.matches(ValidacionUtil.TELEFONO)){
                    mensajeAlert("La teléfono es de 9");
                }else {
                    //
                    String textoGrado = spnCrudAutorGrado.getSelectedItem().toString();
                    String idGrado = textoGrado.split(":")[0];

                    Grado objGrado = new Grado();
                    objGrado.setIdGrado(Integer.parseInt(idGrado));
                    //
                    String textoPais = spnCrudAutorPais.getSelectedItem().toString();
                    String idPais = textoPais.split(":")[0];

                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));
                    //
                    Autor objNewAutor = new Autor();
                    objNewAutor.setNombres(nom);
                    objNewAutor.setApellidos(ape);
                    objNewAutor.setCorreo(correo);
                    objNewAutor.setFechaNacimiento(fecNac);
                    objNewAutor.setTelefono(telef);
                    objNewAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());     // automatico
                    objNewAutor.setEstado(1);  // activo y innactivo                               // automatico
                    objNewAutor.setGrado(objGrado);
                    objNewAutor.setPais(objPais);
                    //
                    if (tipo.equals("REGISTRA")) {
                        insertaAutor(objNewAutor);
                    } else if (tipo.equals("ACTUALIZA")) {
                        Autor objAux = (Autor) extras.get("var_objeto");
                        objNewAutor.setIdAutor(objAux.getIdAutor());
                        actualizarAutor(objNewAutor);
                    }

                }

            }

        });


    }

    // METODO REGISTRAR
    public void insertaAutor(Autor objAutor){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAutor);
        // mensajeAlert(json);
        Call<Autor> call = serviceAutor.insertaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if(response.isSuccessful()){
                    Autor objSalida = response.body();
                    String msg="Se registró el Autor con éxito\n";
                    msg+="ID : "+ objSalida.getIdAutor() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    // METODO ACTUALIZAR
    public void actualizarAutor(Autor objAutor){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAutor);
        // mensajeAlert(json);
        Call<Autor> call = serviceAutor.actualizarAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if(response.isSuccessful()){
                    Autor objSalida = response.body();
                    String msg="Se actualizó el Autor con éxito\n";
                    msg+="ID : "+ objSalida.getIdAutor() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    // CARGAR PAISES EN EL SPINNER - COMBO
    public void cargaPaises() {
        Call<List<Pais>> call = serviceAutor.listaPaises();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lst = response.body();
                    for(Pais obj:lst){
                        lstNombresPaises.add(obj.getIdPais()+":"+obj.getNombre());
                    }
                    adaptadorPaises.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Pais objPais = objAutorSeleccionado.getPais();
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1;
                        for(int i=0; i< lstNombresPaises.size(); i++){
                            if (lstNombresPaises.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnCrudAutorPais.setSelection(pos);
                    }

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



    // CARGAR GRADOS EN EL SPINNER - COMBO
    public void cargaGrados(){
        Call<List<Grado>> call = serviceAutor.listaGrado();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if(response.isSuccessful()){
                    List<Grado> lst = response.body();
                    for(Grado obj:lst){
                        lstNombresGra.add(obj.getIdGrado()+":"+obj.getDescripcion());
                    }
                    adaptadorGrados.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Grado objGrado = objAutorSeleccionado.getGrado();
                        String aux2 = objGrado.getIdGrado()+":"+objGrado.getDescripcion();
                        int pos = -1;
                        for(int i=0; i< lstNombresGra.size(); i++){
                            if (lstNombresGra.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnCrudAutorGrado.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
}
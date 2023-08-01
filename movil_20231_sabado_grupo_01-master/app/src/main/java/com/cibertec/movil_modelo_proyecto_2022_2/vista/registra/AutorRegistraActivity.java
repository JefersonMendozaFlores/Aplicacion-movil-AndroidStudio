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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ToastUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorRegistraActivity extends NewAppCompatActivity {

    // REGISTRAR

    Button btnRegistrar;
    EditText txtNombre;
    EditText txtApellido;
    EditText txtCorreo;
    EditText txtFechaNacimiento;
    EditText txtTelefono;

    // SPINNERS

    Spinner SpnGrado;
    ArrayAdapter<String> adaptadorGrados;
    ArrayList<String> lstNombresGra = new ArrayList<String>();
    List<Grado> lstGrados;

    Spinner SpnPais;
    ArrayAdapter<String> adaptadorPaises;
    ArrayList<String> lstNombresPaises = new ArrayList<String>();
    List<Pais> lstPaises;



    ServiceAutor service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_registra);

        service = ConnectionRest.getConnection().create(ServiceAutor.class);

        // SPINNERS

        adaptadorGrados = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresGra);
        SpnGrado = findViewById(R.id.SpnGrado);
        SpnGrado.setAdapter(adaptadorGrados);
        listaGrado();

        adaptadorPaises = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPaises);
        SpnPais = findViewById(R.id.SpnPais);
        SpnPais.setAdapter(adaptadorPaises);
        listaPaises();

        // REGISTRO
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtTelefono = findViewById(R.id.txtTelefono);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String ape = txtApellido.getText().toString();
                String correo = txtCorreo.getText().toString();
                String fecNac = txtFechaNacimiento.getText().toString();
                String telef = txtTelefono.getText().toString();

                // VALIDACIONES
                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El nombre es de 2 a 20 caracteres");
                }else if (!ape.matches(ValidacionUtil.TEXTO)){
                    mensajeAlert("El apellido es de 2 a 20 caracteres");
                }else if (!correo.matches(ValidacionUtil.CORREO_GMAIL)){
                    mensajeAlert("El correo debe terminar en @gmail.com");
                }else if (!fecNac.matches(ValidacionUtil.FECHA)){
                    mensajeAlert("La fecha de nacimiento es YYYY-MM-dd");
                }else if (!telef.matches(ValidacionUtil.TELEFONO)){
                    mensajeAlert("La teléfono es de 9");
                }else {

                    String textoGrado = SpnGrado.getSelectedItem().toString();
                    String idGrado = textoGrado.split(":")[0];

                    Grado objGrado = new Grado();
                    objGrado.setIdGrado(Integer.parseInt(idGrado));

                    // -----------------------------------------------------------------------------------------------------------------------------------------------------

                    String textoPais = SpnPais.getSelectedItem().toString();
                    String idPais = textoPais.split(":")[0];

                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    Autor objAutor = new Autor();
                    objAutor.setNombres(nom);
                    objAutor.setApellidos(ape);
                    objAutor.setCorreo(correo);
                    objAutor.setFechaNacimiento(fecNac);
                    objAutor.setTelefono(telef);
                    objAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());     // automatico
                    objAutor.setEstado(1);                                                      // automatico
                    objAutor.setGrado(objGrado);
                    objAutor.setPais(objPais);

                    insertarAutor(objAutor);

                }

            }
        });

    }

    // REGISTRAR

    public void  insertarAutor(Autor objAutor){
        //mensajeAlert(objAutor.toString());
        Call<Autor> call = service.insertaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida = response.body();
                    mensajeAlert(" Registro exitoso >>> ID >> " + objSalida.getIdAutor());
                }
                else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    // LISTAR GRADO
    public void listaGrado(){
        Call<List<Grado>> call = service.listaGrado();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()) {
                        lstGrados = response.body();
                        for (Grado obj: lstGrados){
                            lstNombresGra.add(obj.getIdGrado() + ":" + obj.getDescripcion());
                        }
                        adaptadorGrados.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }



    // LISTAR PAISES
    public  void listaPaises(){
        Call<List<Pais>> call = service.listaPaises();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    lstPaises = response.body();
                    for (Pais obj: lstPaises){
                        lstNombresPaises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adaptadorPaises.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }



    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }


}
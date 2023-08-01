package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class AutorConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtIdAutor, txtNombre, txtApellido, txtCorreo, txtFechaNacimiento, txtTelefono, txtFechaRegistro, txtEstado, txtDescripcionGrado, txtPais, txtIso, txtIdGrado, txtIdPais;
    Button btnDetalleRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_consulta_detalle);

        txtIdAutor = findViewById(R.id.txtIdAutor);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtFechaRegistro = findViewById(R.id.txtFechaRegistro);
        txtEstado = findViewById(R.id.txtEstado);
        txtIdGrado = findViewById(R.id.txtIdGrado);
        txtDescripcionGrado = findViewById(R.id.txtDescripcionGrado);
        txtIdPais = findViewById(R.id.txtIdPais);
        txtPais = findViewById(R.id.txtPais);
        txtIso = findViewById(R.id.txtIso);
        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Autor objAutor = (Autor) extras.get("VAR_OBJETO");

        txtIdAutor.setText("ID Autor: "+Integer.toString(objAutor.getIdAutor()));
        txtNombre.setText("Nombres: "+objAutor.getNombres());
        txtApellido.setText("Apellidos: "+objAutor.getApellidos());
        txtCorreo.setText("Correo: "+objAutor.getCorreo());
        txtFechaNacimiento.setText("Fecha de Nacimiento: "+objAutor.getFechaNacimiento());
        txtTelefono.setText("Teléfono: "+objAutor.getTelefono());
        txtFechaRegistro.setText("Fecha y Hora de Registro: "+objAutor.getFechaRegistro());
        txtEstado.setText("Estado: "+objAutor.getEstado());
        // id grado - posible
        txtIdGrado.setText("ID Grado: "+Integer.toString(objAutor.getGrado().getIdGrado()));
        txtDescripcionGrado.setText("Grado: "+objAutor.getGrado().getDescripcion());
        // id pais - posible
        txtIdPais.setText("ID País: "+Integer.toString(objAutor.getPais().getIdPais()));
        txtIso.setText("Iso: " + objAutor.getPais().getIso());
        txtPais.setText("País: "+objAutor.getPais().getNombre());

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutorConsultaDetalleActivity.this, AutorConsultaActivity.class);
                startActivity(intent);

            }
        });

    }
}
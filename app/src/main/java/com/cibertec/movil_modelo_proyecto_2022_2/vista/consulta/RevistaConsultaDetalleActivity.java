package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class RevistaConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtId;
    TextView txtNombre;
    TextView txtFrecuencia;
    TextView txtFechaCreacion;
    TextView txtFechaRegistro;
    TextView txtEstado;
    TextView txtModalidad;
    TextView txtPais;

    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_consulta_detalle);

        txtId = findViewById(R.id.txtIdRevista);
        txtNombre = findViewById(R.id.txtNombreRevista);
        txtFrecuencia = findViewById(R.id.txtFrecuenciaRevista);
        txtFechaCreacion = findViewById(R.id.txtFechaCreacionRevista);
        txtFechaRegistro = findViewById(R.id.txtFechaRegistroRevista);
        txtEstado = findViewById(R.id.txtEstadoRevista);
        txtModalidad = findViewById(R.id.txtModalidadRevista);
        txtPais = findViewById(R.id.txtPaisRevista);
        btnRegresar = findViewById(R.id.btnRegresar);

        Bundle extras = getIntent().getExtras();
        Revista objRevista = (Revista) extras.get("VAR_OBJETO");

        txtId.setText("CÓDIGO: " + Integer.toString(objRevista.getIdRevista()));
        txtNombre.setText("NOMBRE: " + objRevista.getNombre());
        txtFrecuencia.setText("FRECUENCIA: "+ objRevista.getFrecuencia());
        txtFechaCreacion.setText("FECHA DE CREACIÓN: " + objRevista.getFechaCreacion());
        txtFechaRegistro.setText("FECHA DE REGISTRO: " + objRevista.getFechaRegistro());
        txtEstado.setText("ESTADO: " + objRevista.getEstado());
        txtModalidad.setText("MODALIDAD: " + objRevista.getModalidad().getDescripcion());
        txtPais.setText("PAÍS: " + objRevista.getPais().getNombre());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RevistaConsultaDetalleActivity.this, RevistaConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}
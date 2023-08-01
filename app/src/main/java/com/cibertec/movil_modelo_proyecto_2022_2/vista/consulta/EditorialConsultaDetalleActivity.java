package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class EditorialConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtRazonSocial,txtDireccion, txtRuc, txtFechaCreacion, txtPais, txtCategoria;
    Button btnDetalleRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_consulta_detalle);

        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtRuc = findViewById(R.id.txtRuc);
        txtFechaCreacion = findViewById(R.id.txtFechaCreacion);
        txtPais = findViewById(R.id.txtPais);
        txtCategoria = findViewById(R.id.txtCategoria);
        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Editorial objEditorial = (Editorial) extras.get("VAR_OBJETO");

        txtRazonSocial.setText("Razón Social: "+objEditorial.getRazonSocial());
        txtDireccion.setText("Dirección: "+objEditorial.getDireccion());
        txtRuc.setText("RUC: "+objEditorial.getRuc().toString());
        txtFechaCreacion.setText("Fecha de Creación: "+objEditorial.getFechaCreacion().toString());
        txtPais.setText("País: "+objEditorial.getPais().getNombre());
        txtCategoria.setText("Categoría: "+objEditorial.getCategoria().getDescripcion());

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(EditorialConsultaDetalleActivity.this, EditorialConsultaActivity.class);
                startActivity(intent);
            }

        });

    }
}
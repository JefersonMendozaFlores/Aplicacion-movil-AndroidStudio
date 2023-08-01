package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.google.gson.Gson;

public class SalaConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtNumber;
    TextView txtFloor;
    TextView txtStudents;
    TextView txtResources;
    TextView txtCreateDate;
    TextView txtStatus;
    TextView txtModality;
    TextView txtSite;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta_detalle);
        
        this.txtNumber = (TextView) findViewById(R.id.txtNumberDetail);
        this.txtFloor = (TextView) findViewById(R.id.txtFloorDetail);
        this.txtStudents = (TextView) findViewById(R.id.txtStudentsDetail);
        this.txtResources = (TextView) findViewById(R.id.txtResourcesDetail);
        this.txtCreateDate = (TextView) findViewById(R.id.txtCreateDateDetail);
        this.txtStatus = (TextView) findViewById(R.id.txtStatusDetail);
        this.txtModality = (TextView) findViewById(R.id.txtModalityDetail);
        this.txtSite = (TextView) findViewById(R.id.txtSiteDetail);
        this.btnBack = (Button) findViewById(R.id.btnBack);

        Sala room = new Gson().fromJson(getIntent().getStringExtra("ROOM"), Sala.class);
        txtNumber.setText("Número: " + room.getNumero());
        txtFloor.setText("Piso: " + room.getPiso());
        txtStudents.setText("Nro. Alumnos: " + room.getNumAlumnos());
        txtResources.setText("Recursos: " + room.getRecursos());
        txtCreateDate.setText("Fecha de Registro: " + room.getFechaRegistro());
        txtStatus.setText("Estado: " + room.getEstado());
        txtModality.setText("Modalidad: " + room.getModalidad().getDescripcion());
        txtSite.setText("Sede: " + room.getSede().getNombre());

        btnBack.setOnClickListener(view -> onBackPressed());
    }
}
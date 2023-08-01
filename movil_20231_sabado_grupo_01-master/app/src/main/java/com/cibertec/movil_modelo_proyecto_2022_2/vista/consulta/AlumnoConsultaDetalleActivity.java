package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class AlumnoConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtIdAlumno,  TxtNombreAlumno, txtApellidoAlumno, txtTelefono,txtDni,
                txtCorreo, txtDireccion, txtFechaNacimiento, txtFechaRegistro, txtEstado,
                txtIdPais, txtIso, txtNombrePais, txtIdModalidad,txtDescripcion;

    Button btnDetalleAlumno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_consulta_detalle);

        txtIdAlumno=findViewById(R.id.txtIdAlumno);
        TxtNombreAlumno=findViewById(R.id.txtNombreAlumno);
        txtApellidoAlumno=findViewById(R.id.txtApellidoAlumno);
        txtTelefono=findViewById(R.id.txtTelefono);
        txtDni=findViewById(R.id.txtDni);
        txtCorreo=findViewById(R.id.txtCorreo);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtFechaNacimiento=findViewById(R.id.txtFechaNacimiento);
        txtFechaRegistro=findViewById(R.id.txtFechaRegistro);
        txtEstado=findViewById(R.id.txtEstado);
        txtIdPais=findViewById(R.id.txtIdPais);
        txtIso=findViewById(R.id.txtIso);
        txtNombrePais=findViewById(R.id.txtNombrePais);
        txtIdModalidad=findViewById(R.id.txtIdModalidad);
        txtDescripcion=findViewById(R.id.txtDescripción);

        btnDetalleAlumno=findViewById(R.id.btnDetalleAlumno);

        Bundle extras=getIntent().getExtras();
        Alumno objAlumno=(Alumno) extras.get("VAR_OBJETO");

        txtIdAlumno.setText("Código alumno: "+Integer.toString(objAlumno.getIdAlumno()));
        TxtNombreAlumno.setText("Nombre alumno: "+objAlumno.getNombres());
        txtApellidoAlumno.setText("Apellido alumno: "+objAlumno.getApellidos());
        txtTelefono.setText("Teléfono: "+objAlumno.getTelefono());
        txtDni.setText("DNI: "+objAlumno.getDni());
        txtCorreo.setText("Correo: "+objAlumno.getCorreo());
        txtDireccion.setText("Dirección: "+objAlumno.getDireccion());
        txtFechaNacimiento.setText("Fecha de nacimiento: "+objAlumno.getFechaNacimiento());
        txtFechaRegistro.setText("Fecha de registro: "+objAlumno.getFechaRegistro());
        txtEstado.setText("Estado: "+objAlumno.getEstado());

        txtIdPais.setText("Código pais: "+Integer.toString(objAlumno.getPais().getIdPais()));
        txtIso.setText("Iso: "+objAlumno.getPais().getIso());
        txtNombrePais.setText("Nombre del pais: "+objAlumno.getPais().getNombre());

        txtIdModalidad.setText("Código Modalidad: "+Integer.toString(objAlumno.getModalidad().getIdModalidad()));
        txtDescripcion.setText("Descripción: "+objAlumno.getModalidad().getDescripcion());

        btnDetalleAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AlumnoConsultaDetalleActivity.this, AlumnoConsultaActivity.class);
                startActivity(intent);
            }
        });



    }
}
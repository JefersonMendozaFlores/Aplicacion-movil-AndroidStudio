package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;


import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.activity_alumno_item, parent, false);

        TextView txtIdAlumno =row.findViewById(R.id.txtIdAlumno);
        TextView txtNombreAlumno =row.findViewById(R.id.txtNombreAlumno);
        TextView txtApellidoAlumno =row.findViewById(R.id.txtApellidoAlumno);
        TextView txtTelefonoAlumno =row.findViewById(R.id.txtTelefonoAlumno);
        TextView txtCorreo =row.findViewById(R.id.txtCorreo);
        TextView txtDireccion =row.findViewById(R.id.txtDireccion);

        Alumno objAlumno = lista.get(position);
        txtIdAlumno.setText(String.valueOf(objAlumno.getIdAlumno()));
        txtNombreAlumno.setText(objAlumno.getNombres());
        txtApellidoAlumno.setText(objAlumno.getApellidos());
        txtTelefonoAlumno.setText(objAlumno.getTelefono());
        txtCorreo.setText(objAlumno.getCorreo());
        txtDireccion.setText(objAlumno.getDireccion());

        return row;
    }
}

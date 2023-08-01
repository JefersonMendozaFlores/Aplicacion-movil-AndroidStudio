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

public class AlumnoCrudAdapter extends ArrayAdapter<Alumno> {

    private Context context;
    private List<Alumno> lista;

    public AlumnoCrudAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista){
    super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_crud_item, parent, false);


        TextView txtCrudAlumnoItemId =row.findViewById(R.id.txtCrudAlumnoItemId);
        TextView txtCrudAlumnoItemNombre = row.findViewById(R.id.txtCrudAlumnoItemNombre);
        TextView txtCrudAlumnoItemApellido = row.findViewById(R.id.txtCrudAlumnoItemApellido);
        TextView txtCrudAlumnoItemTelefono = row.findViewById(R.id.txtCrudAlumnoItemTelefono);

        TextView txtCrudAlumnoItemDni = row.findViewById(R.id.txtCrudAlumnoItemDni);
        TextView txtCrudAlumnoItemCorreo = row.findViewById(R.id.txtCrudAlumnoItemCorreo);
        TextView txtCrudAlumnoItemDireccion = row.findViewById(R.id.txtCrudAlumnoItemDireccion);
        TextView txtCrudAlumnoItemFecNac = row.findViewById(R.id.txtCrudAlumnoItemFecNac);
        TextView txtCrudAlumnoItemFegRe = row.findViewById(R.id.txtCrudAlumnoItemFegRe);
        TextView txtCrudAlumnoItemEstado = row.findViewById(R.id.txtCrudAlumnoItemEstado);
        TextView txtCrudAlumnoItemPais = row.findViewById(R.id.txtCrudAlumnoItemPais);
        TextView txtCrudAlumnoItemModalidad = row.findViewById(R.id.txtCrudAlumnoItemModalidad);

        Alumno objAlumno =lista.get(position);

        txtCrudAlumnoItemId.setText(String.valueOf(objAlumno.getIdAlumno()));
        txtCrudAlumnoItemNombre.setText(objAlumno.getNombres());
        txtCrudAlumnoItemApellido.setText(objAlumno.getApellidos());
        txtCrudAlumnoItemTelefono.setText(objAlumno.getTelefono());
        txtCrudAlumnoItemDni.setText(objAlumno.getDni());
        txtCrudAlumnoItemCorreo.setText(objAlumno.getCorreo());
        txtCrudAlumnoItemDireccion.setText(objAlumno.getDireccion());
        txtCrudAlumnoItemFecNac.setText(objAlumno.getFechaNacimiento());
        txtCrudAlumnoItemFegRe.setText(objAlumno.getFechaRegistro());
        txtCrudAlumnoItemEstado.setText(String.valueOf(objAlumno.getEstado()));
        txtCrudAlumnoItemPais.setText(objAlumno.getPais().getNombre());
        txtCrudAlumnoItemModalidad.setText(objAlumno.getModalidad().getDescripcion());

        return row;


    }
}

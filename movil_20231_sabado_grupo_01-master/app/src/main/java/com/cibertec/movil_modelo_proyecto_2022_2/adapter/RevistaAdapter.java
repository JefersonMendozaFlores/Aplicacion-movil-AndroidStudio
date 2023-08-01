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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;

import java.util.List;

public class RevistaAdapter extends ArrayAdapter<Revista>  {

    private Context context;
    private List<Revista> lista;

    public RevistaAdapter(@NonNull Context context, int resource, @NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_revista_consulta_item, parent, false);

        TextView txtId = row.findViewById(R.id.txtIdRevista);
        TextView txtNombre = row.findViewById(R.id.txtNombreRevista);
        TextView txtFrecuencia = row.findViewById(R.id.txtFrecuenciaRevista);

        Revista obj = lista.get(position);
        txtId.setText("Código: " + String.valueOf(obj.getIdRevista()));
        txtNombre.setText("Nombre: " + String.valueOf(obj.getNombre()));
        txtFrecuencia.setText("Frecuencia: " + String.valueOf(obj.getFrecuencia()));

        return row;
    }
}

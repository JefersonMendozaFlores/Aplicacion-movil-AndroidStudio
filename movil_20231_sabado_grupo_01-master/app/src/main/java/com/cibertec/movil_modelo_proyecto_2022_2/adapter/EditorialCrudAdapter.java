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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;

import java.util.List;

public class EditorialCrudAdapter extends ArrayAdapter<Editorial> {
    private Context context;
    private List<Editorial> lista;

    public EditorialCrudAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_crud_item, parent, false);

        TextView txtRazonSocial = row.findViewById(R.id.txtRazonSocial);
        TextView txtDireccion = row.findViewById(R.id.txtDireccion);
        TextView txtRUC = row.findViewById(R.id.txtRuc);
        TextView txtFechaCreacion = row.findViewById(R.id.txtFechaCreacion);
        TextView txtPais = row.findViewById(R.id.txtPais);
        TextView txtCategoria = row.findViewById(R.id.txtCategoria);


        Editorial objEditorial = lista.get(position);
        txtRazonSocial.setText(objEditorial.getRazonSocial());
        txtDireccion.setText(objEditorial.getDireccion());
        txtRUC.setText(objEditorial.getRuc());
        txtFechaCreacion.setText(objEditorial.getFechaCreacion());
        txtPais.setText(objEditorial.getPais().getNombre());
        txtCategoria.setText(objEditorial.getCategoria().getDescripcion());

        return row;
    }
}

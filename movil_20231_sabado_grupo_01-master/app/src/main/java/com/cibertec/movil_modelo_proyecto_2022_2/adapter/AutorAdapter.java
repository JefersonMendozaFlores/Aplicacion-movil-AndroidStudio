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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;

import org.w3c.dom.Text;

import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_autor_item, parent, false);

        // Para el detalle autor
        TextView txtId = row.findViewById(R.id.txtIdAutor);
        TextView txtNombre = row.findViewById(R.id.txtNombre);
        TextView txtApellido= row.findViewById(R.id.txtApellido);
        TextView txtCorreo = row.findViewById(R.id.txtCorreo);
        TextView txtTelefono = row.findViewById(R.id.txtTelefono);

        Autor objAutor = lista.get(position);
        txtId.setText(String.valueOf(objAutor.getIdAutor()));
        txtNombre.setText(objAutor.getNombres());
        txtApellido.setText(objAutor.getApellidos());
        txtCorreo.setText(objAutor.getCorreo());
        txtTelefono.setText(objAutor.getTelefono());

        return row;
    }

}

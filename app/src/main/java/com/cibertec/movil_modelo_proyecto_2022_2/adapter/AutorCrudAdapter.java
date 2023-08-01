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

import java.util.List;

public class AutorCrudAdapter extends ArrayAdapter<Autor>{
    private Context context;
    private List<Autor> lista;

    public AutorCrudAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_autor_crud_item, parent, false);

        // Para el listado autor de MANTENIMIENTO
        TextView txtCrudAutorItemId =row.findViewById(R.id.txtCrudAutorItemId);
        TextView txtCrudAutorItemNombre = row.findViewById(R.id.txtCrudAutorItemNombre);
        TextView txtCrudAutorItemApellido = row.findViewById(R.id.txtCrudAutorItemApellido);
        TextView txtCrudAutorItemCorreo = row.findViewById(R.id.txtCrudAutorItemCorreo);
        //
        TextView txtCrudAutorItemFecNac = row.findViewById(R.id.txtCrudAutorItemFecNac);
        TextView txtCrudAutorItemTelefono = row.findViewById(R.id.txtCrudAutorItemTelefono);
        TextView txtCrudAutorItemFecReg = row.findViewById(R.id.txtCrudAutorItemFecReg);
        TextView txtCrudAutorItemEstado = row.findViewById(R.id.txtCrudAutorItemEstado);
        TextView txtCrudAutorItemGrado = row.findViewById(R.id.txtCrudAutorItemGrado);
        TextView txtCrudAutorItemPais = row.findViewById(R.id.txtCrudAutorItemPais);

        Autor objAutor = lista.get(position);
        txtCrudAutorItemId.setText(String.valueOf(objAutor.getIdAutor()));
        txtCrudAutorItemNombre.setText(objAutor.getNombres());
        txtCrudAutorItemApellido.setText(objAutor.getApellidos());
        txtCrudAutorItemCorreo.setText(objAutor.getCorreo());
        //
        txtCrudAutorItemFecNac.setText(objAutor.getFechaNacimiento());
        txtCrudAutorItemTelefono.setText(objAutor.getTelefono());
        txtCrudAutorItemFecReg.setText(objAutor.getFechaRegistro());
        txtCrudAutorItemEstado.setText(String.valueOf(objAutor.getEstado()));
        txtCrudAutorItemGrado.setText(objAutor.getGrado().getDescripcion());
        txtCrudAutorItemPais.setText(objAutor.getPais().getNombre());

        return row;
    }

}

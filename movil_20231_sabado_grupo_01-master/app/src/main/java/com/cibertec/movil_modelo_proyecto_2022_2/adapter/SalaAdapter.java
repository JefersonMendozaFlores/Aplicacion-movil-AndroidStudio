package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.ViewHolder> {

    private RoomListener listener;
    private final List<Sala> rooms;

    public SalaAdapter(List<Sala> rooms) {
        this.rooms = rooms;
    }

    public void setListener(RoomListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SalaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_sala_crud_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaAdapter.ViewHolder holder, int position) {
        final Sala room = rooms.get(position);
        holder.txtNumber.setText(room.getNumero());
        holder.txtFloor.setText("Piso: " + room.getPiso());
        holder.txtStudents.setText("Nro. Alumnos: " + room.getNumAlumnos());
        holder.txtResources.setText("Recursos: " + room.getRecursos());
        holder.txtCreateDate.setText("Fecha de Registro: " + room.getFechaRegistro());
        holder.txtStatus.setText("Estado: " + room.getEstado());
        holder.txtModality.setText("Modalidad: " + room.getModalidad().getDescripcion());
        holder.txtSite.setText("Sede: " + room.getSede().getNombre());

        if (!listener.isOnlyView()) {
            holder.btnUpdate.setVisibility(View.VISIBLE);
            holder.btnUpdate.setOnClickListener(view -> listener.onUpdateClick(room));
        }

        holder.itemView.setOnClickListener(view -> listener.onRoomClick(room));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNumber;
        public TextView txtFloor;
        public TextView txtStudents;
        public TextView txtResources;
        public TextView txtCreateDate;
        public TextView txtStatus;
        public TextView txtModality;
        public TextView txtSite;
        public MaterialButton btnUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtNumber = itemView.findViewById(R.id.txtNumberItem);
            this.txtFloor = itemView.findViewById(R.id.txtFloorItem);
            this.txtStudents = itemView.findViewById(R.id.txtStudentsItem);
            this.txtResources = itemView.findViewById(R.id.txtResourcesItem);
            this.txtCreateDate = itemView.findViewById(R.id.txtCreateDateItem);
            this.txtStatus = itemView.findViewById(R.id.txtStatusItem);
            this.txtModality = itemView.findViewById(R.id.txtModalityItem);
            this.txtSite = itemView.findViewById(R.id.txtSiteItem);
            this.btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }

    }

    public interface RoomListener {

        default boolean isOnlyView() {
            return true;
        }

        default void onRoomClick(Sala room) {}

        default void onUpdateClick(Sala room) {}
    }

}

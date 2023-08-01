package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.util.Comparator;

public class RoomComparator implements Comparator<Sala> {

    @Override
    public int compare(Sala room1, Sala room2) {
        return Integer.compare(room2.getIdSala(), room1.getIdSala());
    }

}

package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

public class Sala implements Serializable {

    public static final String NUMBER_FORMAT = "([A-Z][0-9][0-9][0-9])";

    private int idSala;
    private String numero;
    private int piso;
    private int numAlumnos;
    private String recursos;
    private String fechaRegistro;
    private int estado;
    private Modalidad modalidad;
    private Sede sede;

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getNumAlumnos() {
        return numAlumnos;
    }

    public void setNumAlumnos(int numAlumnos) {
        this.numAlumnos = numAlumnos;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "idSala=" + idSala +
                ", numero='" + numero + '\'' +
                ", piso=" + piso +
                ", numAlumnos=" + numAlumnos +
                ", fechaRegistro=" + fechaRegistro +
                ", recursos='" + recursos + '\'' +
                ", modalidad=" + modalidad +
                ", sede=" + sede +
                '}';
    }
}

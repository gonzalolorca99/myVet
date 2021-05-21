package com.dam.myvet;

public class Cita {
    private String asunto;
    private String cliente;
    private String mascota;
    private String fecha;

    public Cita(String asunto, String cliente, String mascota, String fecha) {
        this.asunto = asunto;
        this.cliente = cliente;
        this.mascota = mascota;
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

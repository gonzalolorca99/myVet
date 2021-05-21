package com.dam.myvet;

public class Mascota {
    private String nombre;
    private String raza;
    private String edad;
    private String dueño;
    private String historial;
    private String citasPendientes;

    public Mascota(String nombre, String raza, String edad, String dueño, String historial, String citasPendientes) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.dueño = dueño;
        this.historial = historial;
        this.citasPendientes = citasPendientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getCitasPendientes() {
        return citasPendientes;
    }

    public void setCitasPendientes(String citasPendientes) {
        this.citasPendientes = citasPendientes;
    }
}

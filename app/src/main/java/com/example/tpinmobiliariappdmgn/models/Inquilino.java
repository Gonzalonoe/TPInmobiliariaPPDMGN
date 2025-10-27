package com.example.tpinmobiliariappdmgn.models;

public class Inquilino {

    private int idInquilino;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;

    public Inquilino() {
    }

    public Inquilino(String apellido, String dni, String email, int idInquilino, String nombre, String telefono) {
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.idInquilino = idInquilino;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

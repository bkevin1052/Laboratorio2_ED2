package com.laboratoriodos.kevin.laboratorio2_ed2.clases;

public class Archivo {

    private String nombre;
    private String ruta;
    private double razonCompresion;
    private double factorCompresion;
    private double porcentajeReduccion;

    public Archivo(String nombre, String ruta, double razonCompresion, double factorCompresion, double porcentajeReduccion) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.razonCompresion = razonCompresion;
        this.factorCompresion = factorCompresion;
        this.porcentajeReduccion = porcentajeReduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public double getRazonCompresion() {
        return razonCompresion;
    }

    public void setRazonCompresion(double razonCompresion) {
        this.razonCompresion = razonCompresion;
    }

    public double getFactorCompresion() {
        return factorCompresion;
    }

    public void setFactorCompresion(double factorCompresion) {
        this.factorCompresion = factorCompresion;
    }

    public double getPorcentajeReduccion() {
        return porcentajeReduccion;
    }

    public void setPorcentajeReduccion(double porcentajeReduccion) {
        this.porcentajeReduccion = porcentajeReduccion;
    }
}

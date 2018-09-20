package com.laboratoriodos.kevin.laboratorio2_ed2.clases;

import com.laboratoriodos.kevin.laboratorio2_ed2.huffman.Arbol;

public class Archivo {

    private String nombre;
    private String ruta;
    private double razonCompresion;
    private double factorCompresion;
    private double porcentajeReduccion;
    private String algoritmoDeCompresion;
    private int imagen;
    private Arbol arbol;

    public Archivo(String nombre, String ruta, double razonCompresion, double factorCompresion, double porcentajeReduccion,String algoritmoDeCompresion,int imagen,Arbol arbol) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.razonCompresion = razonCompresion;
        this.factorCompresion = factorCompresion;
        this.porcentajeReduccion = porcentajeReduccion;
        this.algoritmoDeCompresion = algoritmoDeCompresion;
        this.imagen = imagen;
        this.arbol = arbol;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
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

    public String getAlgoritmoDeCompresion() {
        return algoritmoDeCompresion;
    }

    public void setAlgoritmoDeCompresion(String algoritmoDeCompresion) {
        this.algoritmoDeCompresion = algoritmoDeCompresion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}

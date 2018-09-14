package com.laboratoriodos.kevin.laboratorio2_ed2.huffman;

public abstract class Arbol implements Comparable<Arbol> {

    public final int frecuencia;

    public Arbol(int frecuencia) {
        this.frecuencia = frecuencia;
    }


    public int compareTo(Arbol arbol) {
        return frecuencia - arbol.frecuencia;
    }

}

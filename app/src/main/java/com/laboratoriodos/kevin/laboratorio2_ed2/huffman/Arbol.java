package com.laboratoriodos.kevin.laboratorio2_ed2.huffman;

abstract class Arbol implements Comparable<Arbol> {

    public final int frecuencia;

    public Arbol(int frecuencia) {
        this.frecuencia = frecuencia;
    }

}

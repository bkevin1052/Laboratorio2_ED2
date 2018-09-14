package com.laboratoriodos.kevin.laboratorio2_ed2.huffman;

public class Nodo extends  Arbol{

    public final Arbol izquierda, derecha;

    public Nodo(Arbol izquierda, Arbol derecha) {
        super(izquierda.frecuencia + derecha.frecuencia);
        this.izquierda = izquierda;
        this.derecha = derecha;
    }
}

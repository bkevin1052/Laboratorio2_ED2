package com.laboratoriodos.kevin.laboratorio2_ed2.huffman;

public class Hoja extends  Arbol{

    public final char valor;

    public Hoja(int frecuencia, char valor) {
        super(frecuencia);
        this.valor = valor;
    }


}

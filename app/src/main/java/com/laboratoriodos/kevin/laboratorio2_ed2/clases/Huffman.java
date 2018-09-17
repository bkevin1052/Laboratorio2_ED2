package com.laboratoriodos.kevin.laboratorio2_ed2.clases;

import com.laboratoriodos.kevin.laboratorio2_ed2.huffman.Arbol;
import com.laboratoriodos.kevin.laboratorio2_ed2.huffman.Hoja;
import com.laboratoriodos.kevin.laboratorio2_ed2.huffman.Nodo;

import java.util.PriorityQueue;

public class Huffman {

    private String cadena;


    public Huffman(String cadena){
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Arbol arbolHuffman(int[] caracteresContador){

        //Cola de prioridad para que almacene el mayor numero en la raiz, respecto a la cantidad de caracteres
        PriorityQueue<Arbol> arboles = new PriorityQueue<>();

        for(int i =0; i < caracteresContador.length;i++){
            if(caracteresContador[i]>0){
                arboles.offer(new Hoja(caracteresContador[i],(char)i));
            }
        }

        while((arboles.size()>1)){
            Arbol a = arboles.poll();
            Arbol b = arboles.poll();
            arboles.offer(new Nodo(a, b));
        }
        return arboles.poll();
    }


    public String cifrar(Arbol arbol, String cadena){

        String textoCifrado = "";//null
        for(char c : cadena.toCharArray()){
            textoCifrado+=(obtenerBinario(arbol, new StringBuffer(),c));
        }
        return textoCifrado;
    }

    public static String obtenerBinario(Arbol arbol, StringBuffer prefijo, char c) {

        if (arbol instanceof Hoja) {
            Hoja hoja = (Hoja)arbol;

            if (hoja.valor == c ){
                return prefijo.toString();
            }

        } else if (arbol instanceof Nodo) {
            Nodo nodo = (Nodo)arbol;
            prefijo.append('0');
            String izquierda = obtenerBinario(nodo.izquierda, prefijo, c);
            prefijo.deleteCharAt(prefijo.length()-1);
            prefijo.append('1');
            String derecha = obtenerBinario(nodo.derecha, prefijo,c);
            prefijo.deleteCharAt(prefijo.length()-1);

            if (izquierda==null){
                return derecha;
            } else {
                return izquierda;
            }
        }
        return null;
    }
}

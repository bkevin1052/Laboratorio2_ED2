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


    public String decodificar(Arbol arbol, String decodificar) {

        String decodificarTexto="";
        Nodo nodo = (Nodo)arbol;
        for (char code : decodificar.toCharArray()){
            if (code == '0'){
                if (nodo.izquierda instanceof Hoja) {
                    decodificarTexto += ((Hoja)nodo.izquierda).valor; // Retorna el valor del lado izquierdo del arbol
                    nodo = (Nodo)arbol; // Retorna a la raiz

                }else{
                    nodo = (Nodo)nodo.izquierda; // Continua recorriendo el lado izquierdo
                }
            }else if (code == '1'){
                if (nodo.derecha instanceof Hoja) {
                    decodificarTexto += ((Hoja)nodo.derecha).valor; // Retorna el valor del lado derecho del arbo
                    nodo = (Nodo)arbol; //  Retorna a la raiz
                }else{
                    nodo = (Nodo)nodo.derecha; // Continua recorriendo el lado derecho
                }
            }
        }
        return decodificarTexto.toString(); // Retorna texto decodificado
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

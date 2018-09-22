package com.laboratoriodos.kevin.laboratorio2_ed2.LZW;

import java.util.HashMap;
import java.util.Map;

public class lzw {


    private String cadena; // cadena a comprimir y descomprimir
    private final int DICT_TAMANIO = 256; //cantidad de caracteres maxima

    public lzw(String cadena){
        this.cadena = cadena;
    }


    /**
     * codificarUTF8
     * @param cadena
     * @return resultado
     */
    public String comprimir(String cadena){
        cadena = codificarUTF8(cadena);
        int index = DICT_TAMANIO;
        //Instancia del dicciario de letras
        Map<String,Integer> letrasDiccionario = new HashMap<String,Integer>();
        for (int i = 0; i < DICT_TAMANIO; i++) {
            letrasDiccionario.put("" + (char) i, i);
        }
        String w = "";
        StringBuilder resultado = new StringBuilder();
        for (char c : cadena.toCharArray()) {
            String wc = w + c;
            if (letrasDiccionario.containsKey(wc))
                w = wc;
            else {
                int i = letrasDiccionario.get(w);
                resultado.append((char) i);
                // Add wc to the dictionary.
                letrasDiccionario.put(wc, index++);
                w = "" + c;
            }
        }
        if (!w.equals("")) {
            int i = letrasDiccionario.get(w);
            resultado.append((char) i);
        }
        return resultado.toString();
    }

    /**
     * codificarUTF8
     * @param cadena
     * @return resultado
     */
    public String codificarUTF8(String cadena) {
        StringBuilder resultado = new StringBuilder();
        for (char c : cadena.toCharArray()) {
            int i = (int) c;
            if (i < 128) {
                resultado.append((char) i);
            } else if (i > 127 && i < 2048) {
                int j = (i >> 6) | 192;
                resultado.append((char) j);
                j = (i & 63) | 128;
                resultado.append((char) j);
            } else {
                int j = (i >> 12) | 224;
                resultado.append((char) j);
                j = ((i >> 6) & 63) | 128;
                resultado.append((char) j);
                j = (c & 63) | 128;
                resultado.append((char) j);
            }
        }
        return resultado.toString();
    }
}

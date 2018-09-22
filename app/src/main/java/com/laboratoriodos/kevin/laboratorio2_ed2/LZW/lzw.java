package com.laboratoriodos.kevin.laboratorio2_ed2.LZW;

import java.util.HashMap;
import java.util.Map;

public class lzw {


    private String cadena; // cadena a comprimir y descomprimir
    private final int DICT_TAMANIO = 256; //cantidad de caracteres maxima

    public lzw(String cadena){
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    /**
     * codificarUTF8
     * @param cadena
     * @return resultado
     */
    public String comprimir(String cadena){
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
}

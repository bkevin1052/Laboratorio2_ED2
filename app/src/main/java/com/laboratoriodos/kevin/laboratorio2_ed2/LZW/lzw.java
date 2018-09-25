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
     * @param cadena
     * @return resultado
     */
    public String comprimir(String cadena){
        int index = DICT_TAMANIO;
        //Instancia del dicciario de letras
        Map<String,Integer> letrasDiccionario = new HashMap<>();
        for (int i = 0; i < DICT_TAMANIO; i++) {
            letrasDiccionario.put(""+(char) i, i);
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

    /**
     * @param cadena
     * @return resultado
     */
    public String descomprimir(String cadena) {
        int index = DICT_TAMANIO;
        Map<Integer,String> letrasDiccionario = new HashMap<>();
        for (int i = 0; i < DICT_TAMANIO; i++)
            letrasDiccionario.put(i, "" + (char)i);

        char[] chars = cadena.toCharArray();
        String w = "" + chars[0];
        StringBuffer resultado = new StringBuffer(w);
        for (int j = 1; j < chars.length; j++) {
            int k = (int) chars[j];
            String entrada;
            if (letrasDiccionario.containsKey(k)) {
                entrada = letrasDiccionario.get(k);
            }
            else if (k == index) {
                entrada = w + w.charAt(0);
            }
            else {
                throw new IllegalArgumentException("ERROR: " + k);
            }
            resultado.append(entrada);
            letrasDiccionario.put(index++, w + entrada.charAt(0));
            w = entrada;
        }
        return resultado.toString();
    }

}

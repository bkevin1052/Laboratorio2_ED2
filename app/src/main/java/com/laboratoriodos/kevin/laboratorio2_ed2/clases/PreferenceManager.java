package com.laboratoriodos.kevin.laboratorio2_ed2.clases;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Sharedpref file name
    private static final String SHARED_PREFS_FILE = "Preference";
    private static final String NOMBRE = "nombre";
    private static final String RUTA = "ruta";
    private static final String RAZONCOMPRESION = "razonCompresion";
    private static final String FACTORCOMPRESION = "factoCompresion";
    private static final String PORCENTAJEREDUCCION = "porcentajeCompresion";
    private static final String ALGORITMOCOMPRESION = "algoritmoCompresion";
    private static final String IMAGEN = "imagen";


    public PreferenceManager(Context context) {
        this._context = context;
    }

    private SharedPreferences getSettings(){
        return _context.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public String getNombre(){
        return getSettings().getString(NOMBRE, null);
    }

    public void setNombre(String nombre){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(NOMBRE, nombre );
        editor.commit();
    }

    public String getRuta(){
        return getSettings().getString(RUTA, null);
    }

    public void setRuta(String ruta){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(RUTA, ruta );
        editor.commit();
    }

    public String getRazonCompresion(){
        return getSettings().getString(RAZONCOMPRESION, null);
    }

    public void setRazonCompresion(String razonCompresion){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(RAZONCOMPRESION, razonCompresion );
        editor.commit();
    }

    public String getFactorCompresion(){
        return getSettings().getString(FACTORCOMPRESION, null);
    }

    public void setFactorCompresion(String factorCompresion){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(FACTORCOMPRESION, factorCompresion );
        editor.commit();
    }

    public String getPorcentajeReduccion(){
        return getSettings().getString(PORCENTAJEREDUCCION, null);
    }

    public void setPorcentajeReduccion(String porcentajeReduccion){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(PORCENTAJEREDUCCION, porcentajeReduccion );
        editor.commit();
    }


    public String getAlgoritmoCompresion(){
        return getSettings().getString(ALGORITMOCOMPRESION, null);
    }

    public void setAlgoritmoCompresion(String algoritmoCompresion){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(ALGORITMOCOMPRESION, algoritmoCompresion );
        editor.commit();
    }


    public String getImagen(){
        return getSettings().getString(IMAGEN, null);
    }

    public void setImagen(String imagen){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(IMAGEN,imagen);
        editor.commit();
    }





}

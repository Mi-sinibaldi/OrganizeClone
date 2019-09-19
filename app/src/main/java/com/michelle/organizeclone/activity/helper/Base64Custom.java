package com.michelle.organizeclone.activity.helper;

import android.util.Base64;

public class Base64Custom {

    //classe usada para fazer a codificação e a decodificação do e-mail

    public static String codificarBase64(String texto) {

        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodigicarBase64(String textoCodificado) {

        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}

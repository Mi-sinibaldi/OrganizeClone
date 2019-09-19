package com.michelle.organizeclone.activity.helper;

import java.text.SimpleDateFormat;

public class DateUtil {

    //data customizado
    public static String dataAtual() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataSring = simpleDateFormat.format(date);
        return dataSring;
    }

    public static String mesAnoDataEscolhida(String data) {
        String retornoData[] = data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];

        String mesAno = mes + ano;
        return mesAno;
    }
}

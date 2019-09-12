package com.michelle.organizeclone.activity.helper;

import java.text.SimpleDateFormat;

public class DateUtil {

    //data customizado
    public static String dataAtual() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataSring = simpleDateFormat.format(date);
        return dataSring;
    }
}

package com.cibertec.movil_modelo_proyecto_2022_2.util;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtil {

    private ToastUtil() {
    }

    public static void showToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }

}

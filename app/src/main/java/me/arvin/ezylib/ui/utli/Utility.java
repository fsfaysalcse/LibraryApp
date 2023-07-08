package me.arvin.ezylib.ui.utli;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utility {
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

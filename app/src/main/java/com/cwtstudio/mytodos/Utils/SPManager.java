package com.cwtstudio.mytodos.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.cwtstudio.mytodos.Adapters.TodoAdapter;
import com.cwtstudio.mytodos.Models.Todo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SPManager {
    private static final String PREF_NAME = "MY_TODOS";
    private static final String LIST_KEY = "TODOS_LIST";

    public static void saveTodoList(Context context, List<Todo> todolist) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String jsonList = gson.toJson(todolist);
        editor.putString(LIST_KEY, jsonList);
        editor.apply();


    }

    public static List<Todo> getTodoList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonList = prefs.getString(LIST_KEY, null);
        if (jsonList != null) {
            Type type = new TypeToken<List<Todo>>() {
            }.getType();
            return gson.fromJson(jsonList, type);

        } else {
            return new ArrayList<>();
        }
    }

    public static void Clear(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(context, "Cleared list!", Toast.LENGTH_SHORT).show();
    }


}

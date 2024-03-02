package com.example.diploma.presentation;

import android.content.SharedPreferences;

import com.example.diploma.domain.models.CategoryModel;
import com.example.diploma.domain.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class Global {
    public static String BASE_URL="";
    public static Boolean is_logined = false;
    public static int userId = 0;
    public static UserModel userMe;
}

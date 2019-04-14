package com.kgcorner.vachan.util;

/*
Description : <Write is class Description>
Author: kumar
Created on : 14/4/19
*/

public interface Strings {
    static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() < 1;
    }
}
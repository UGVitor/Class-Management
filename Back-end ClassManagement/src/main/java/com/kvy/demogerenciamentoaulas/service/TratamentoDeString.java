package com.kvy.demogerenciamentoaulas.service;

public class TratamentoDeString {
    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Converte toda a string para minúsculas
        str = str.toLowerCase();

        // Divide a string em palavras
        String[] words = str.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        // Capitaliza a primeira letra de cada palavra
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(word.substring(0, 1).toUpperCase()) // Primeira letra maiúscula
                        .append(word.substring(1))                 // Restante da palavra
                        .append(" ");                              // Adiciona espaço
            }
        }

        // Remove o espaço extra no final e retorna
        return capitalized.toString().trim();
    }

    public static String convertToUpperCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Transforma toda a string em maiúsculas
        return str.toUpperCase();
    }


}

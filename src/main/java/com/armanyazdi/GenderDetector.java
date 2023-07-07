package com.armanyazdi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenderDetector {
    private static final HashMap<String, String> dataset = new HashMap<>();

    private static void readCSV() {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/names.csv"))) {
            String line;
            while ((line = reader.readLine()) != null)
                records.add(Arrays.asList(line.split(",")));

            for (List<String> record : records)
                dataset.put(record.get(0), record.get(1));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String clearName(String name) {
        Pattern pattern = Pattern.compile("[\\p{P}\\p{Mn}\\p{S}\\p{javaDigit}]");
        Matcher matcher = pattern.matcher(name);
        matcher.reset();

        return matcher.replaceAll("").trim()
                .replace("\u200c", " ")
                .replace("آ", "ا")
                .replace("ي", "ی")
                .replace("ك", "ک")
                .replace("ـ", "");
    }

    public static String getGender(String name) {
        String firstName = null;
        String clearedName = clearName(name);
        List<String> fullName = new ArrayList<>(List.of(clearedName.split(" ")));
        List<String> prefixes = Arrays.asList("سید", "سیده", "استاد", "دکتر", "مهندس", "سرکار");

        while (prefixes.contains(fullName.get(0))) fullName.remove(0);
        readCSV();

        outerloop:
        for (int i = 0; i < fullName.size(); fullName.remove(fullName.size() - 1))
            for (String key : dataset.keySet())
                if (key.equals(String.join(" ", fullName))) {
                    firstName = String.join(" ", fullName);
                    break outerloop;
                }

        if (dataset.containsKey(firstName))
            switch (dataset.get(firstName)) {
                case "M" -> {return "MALE";}
                case "F" -> {return "FEMALE";}
            }

        return "UNKNOWN";
    }
}

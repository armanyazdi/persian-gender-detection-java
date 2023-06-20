package com.armanyazdi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenderDetector {
    private static final List<List<String>> allRecords = new ArrayList<>();
    private static final List<String> allFirstNames = new ArrayList<>();
    private static final List<String> allGenders = new ArrayList<>();

    private static void readCSV(String file) {
        allRecords.clear();
        allFirstNames.clear();
        allGenders.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/".concat(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                allRecords.add(Arrays.asList(values));
            }

            reader.close();

            for (List<String> record : allRecords) {
                allFirstNames.add(record.get(0));
                allGenders.add(record.get(1));
            }
        } catch (IOException e) {
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
        readCSV("names.csv");

        outerloop:
        for (int i = 0; i < fullName.size(); fullName.remove(fullName.size() - 1)) {
            for (String s : allFirstNames) {
                if (s.equals(String.join(" ", fullName))) {
                    firstName = String.join(" ", fullName);
                    break outerloop;
                }
            }
        }

        if (allFirstNames.contains(firstName))
            switch (allGenders.get(allFirstNames.indexOf(firstName))) {
                case "M" -> {return "MALE";}
                case "F" -> {return "FEMALE";}
            }

        return "UNKNOWN";
    }
}

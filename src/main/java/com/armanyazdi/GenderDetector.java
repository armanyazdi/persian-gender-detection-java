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
                .replace("آ", "ا")
                .replace("ي", "ی")
                .replace("ك", "ک")
                .replace("ـ", "");
    }

    public static String getGender(String name) {
        boolean doExists = false;
        String firstName =  null;
        String clearedName = clearName(name);
        List<String> fullName = new ArrayList<>(List.of(clearedName.split(" ")));
        List<String> prefixes = Arrays.asList("سید", "سیده", "استاد", "دکتر", "مهندس", "سرکار");

        readCSV("names.csv");
        while (prefixes.contains(fullName.get(0))) fullName.remove(0);

        for (String s : allFirstNames) {
            if (fullName.size() == 1) {
                if (s.equals(fullName.get(0))) {
                    firstName = fullName.get(0);
                    doExists = true;
                }
            }
            else if (fullName.size() == 2) {
                if (s.equals(String.join(" ", fullName.get(0), fullName.get(1)))) {
                    firstName = String.join(" ", fullName.get(0), fullName.get(1));
                    doExists = true;
                }
                else if (s.equals(fullName.get(0))) {
                    firstName = fullName.get(0);
                    doExists = true;
                }
            }
            else {
                if (s.equals(String.join(" ", fullName.get(0), fullName.get(1), fullName.get(2)))) {
                    firstName = String.join(" ", fullName.get(0), fullName.get(1), fullName.get(2));
                    doExists = true;
                }
                else if (s.equals(String.join(" ", fullName.get(0), fullName.get(1)))) {
                    firstName = String.join(" ", fullName.get(0), fullName.get(1));
                    doExists = true;
                }
                else if (s.equals(fullName.get(0))) {
                    firstName = fullName.get(0);
                    doExists = true;
                }
            }
        }

        if (doExists)
            switch (allGenders.get(allFirstNames.indexOf(firstName))) {
                case "M" -> {return "MALE";}
                case "F" -> {return "FEMALE";}
            }

        return "UNKNOWN";
    }
}

package com.armanyazdi;

public class Main {
    public static void main(String[] args) {
        // Detect Gender:
        System.out.println(GenderDetector.getGender("  عــــلی  "));
        System.out.println(GenderDetector.getGender("محــ🌚ــمد"));
        System.out.println(GenderDetector.getGender("بیــ🥲ــتا"));
        System.out.println(GenderDetector.getGender("۱۲۳مهناز۴۵۶"));
        System.out.println(GenderDetector.getGender("مهدي-1980"));
        System.out.println(GenderDetector.getGender("بابك آذر مهر"));
        System.out.println(GenderDetector.getGender("بی بی سلطان"));
        System.out.println(GenderDetector.getGender("شیدا علیزاده"));
        System.out.println(GenderDetector.getGender("ممد رضا"));
        System.out.println(GenderDetector.getGender("پانته‌آ عَبّاسی"));
    }
}
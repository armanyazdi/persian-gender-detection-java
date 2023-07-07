# Persian Gender Detection

A Java library for detecting gender by Persian and Arabic names (with more than 23K names).

## Installation

- [ ] Upload artifact to Maven Central Repository

## Usage

Let's take a look at what an example test case would look like using `persian-gender-detection`.

### Detect Gender:

```java
GenderDetector.getGender("  عــــلی  ");    // MALE
GenderDetector.getGender("محــ🌚ــمد");     // MALE
GenderDetector.getGender("بیــ🥲ــتا");     // FEMALE
GenderDetector.getGender("۱۲۳مهناز۴۵۶");    // FEMALE
GenderDetector.getGender("مهدي-1980");      // MALE
GenderDetector.getGender("بابك آذر مهر");   // MALE
GenderDetector.getGender("بی بی سلطان");    // FEMALE
GenderDetector.getGender("شیدا علیزاده");   // FEMALE
GenderDetector.getGender("ممد رضا");        // MALE
GenderDetector.getGender("پانته‌آ عَبّاسی");   // FEMALE
GenderDetector.getGender("دکتر ندا حسینی"); // FEMALE
GenderDetector.getGender("سید امیر موسوی"); // MALE
GenderDetector.getGender("جناب آقای X");    // MALE
GenderDetector.getGender("سرکار خانم Y");   // FEMALE
```

## Issues

Feel free to submit issues and enhancement requests.

## Contributing

Feel free to contribute names database with your kindly pull requests.

## TODO

- [ ] Add Finglish support

## License

`persian-gender-detection` is available under the [MIT license](https://github.com/armanyazdi/persian-gender-detection/blob/master/LICENSE).
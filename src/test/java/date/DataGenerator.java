package date;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateData(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        var cities = new String[] {"Астрахань", "Кострома","Краснодар","Москва",
                "Петропавловск-Камчатский","Ростов-на-Дону","Салехард","Самара",
                "Санкт-Петербург","Саранск","Саратов","Севастополь","Ставрополь",
                "Сыктывкар","Чебоксары","Элиста","Южно-Сахалинск","Ярославль"};
        return cities [new Random().nextInt(cities.length)];
    }
    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }
    public static String generatePhone(String lokale) {
        var faker = new Faker(new Locale(lokale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {

        }

        public static userInfo  generateUser(String locale) {
            return new userInfo(generateCity(), generatePhone(locale), generateName(locale)) ;
        }
    }

    @Value
    public static class userInfo {
        String city;
        String name;
        String phone;
    }
}

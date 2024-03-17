package ru.netology;

import date.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {


    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-sha-usage");
        options.addArguments("--no-sandbox ");
        options.addArguments("--headless ");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @Test
    @DisplayName("Card with delivery")
    void meetingPlanTest() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        int  appointmentDate = 3;
        String  firstMeetingDate = DataGenerator.generateData(appointmentDate);
        int  newAppointmentDate = 4;
        String  daysToAddForSecondMeeting = DataGenerator.generateData(newAppointmentDate);
        $("[date-test-id='city'] input").setValue(validUser.getCity());
        $("[date-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[date-test-id='date'] input").setValue(firstMeetingDate);
        $("[date-test-id='name'] input").setValue(validUser.getName());
        $("[date-test-id='phone'] input").setValue(validUser.getPhone());
        $("[date-test-id='agreement']").click();
        $("button").click();
        $(byText("Успешно запланированна ")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']. notification")
                .shouldHave(exactText("Встреча успешно запланированна на " + appointmentDate ))
                .shouldBe(visible);
        $("[date-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[date-test-id='date'] input").setValue(firstMeetingDate);
        $("button").click();
        $("[date-test-id='replan-notification'].notification_content")
                .shouldHave(exactText("У вас уже запланированна встреча на другую дату.Перепланировать? "))
                .shouldBe(visible);
        $("[date-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification']. notification")
                .shouldHave(exactText("Встреча успешно запланированна на " + daysToAddForSecondMeeting ))
                .shouldBe(visible);
    }
}

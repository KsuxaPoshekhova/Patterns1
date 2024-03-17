package ru.netology;

import data.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999") ;
    }

    @Test
    @DisplayName("Card with delivery")
    void meetingPlanTest() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        int  appointmentDate = 3;
        String  firstMeetingDate = DataGenerator.generateData(appointmentDate);
        int  newAppointmentDate = 7;
        String  daysToAddForSecondMeeting = DataGenerator.generateData(newAppointmentDate);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно запланированна ")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']. notification")
                .shouldHave(exactText("Встреча успешно запланированна на " + appointmentDate ))
                .shouldBe(visible);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'].notification_content")
                .shouldHave(exactText("У вас уже запланированна встреча на другую дату.Перепланировать? "))
                .shouldBe(visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification']. notification")
                .shouldHave(exactText("Встреча успешно запланированна на " + daysToAddForSecondMeeting ))
                .shouldBe(visible);
    }
}

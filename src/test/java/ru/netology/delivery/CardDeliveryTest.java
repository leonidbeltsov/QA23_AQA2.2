package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @Test
    public void shouldDeliveredCard() {

        Configuration.headless = true;

        LocalDate date = LocalDate.now();
        date = date.plusDays(7);
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd" + "." + "MM" + "." + "yyyy");
        String dataFormatString = date.format(dataFormat);

        open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Москва");
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").sendKeys(dataFormatString);
        $("[data-test-id='name'] [class='input__control']").setValue("Иванов Иван");
        $("[data-test-id='phone'] [class='input__control']").setValue("+79998887766");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='notification'] [class='notification__title']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно забронирована на" + " " + dataFormatString));
    }
}
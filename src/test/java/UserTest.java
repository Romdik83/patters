import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class UserTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void orderingABankCard() {
        Selenide.open("http://localhost:9999");
        $$("[placeholder='Город']").find(Condition.visible).setValue("Воронеж");
        $$("[placeholder='Дата встречи']").find(Condition.visible).doubleClick().sendKeys(Keys.BACK_SPACE);
        $$("[placeholder='Дата встречи']").find(Condition.visible).setValue(generateDate(3));
        $$("[name='name']").find(Condition.visible).setValue("Новикова Оксана");
        $$("[name='phone']").find(Condition.visible).setValue("+79150000000");
        $$("[data-test-id='agreement']").find(Condition.visible).click();
        $$("button").find(text("Запланировать")).click();
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $(Selectors.withText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно запланирована на " + generateDate(3)));
    }
}

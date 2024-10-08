package hello.typeconverter.converter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text = " + text + ", locale = " + locale);
        // 1,000 -> 1000 format
        return NumberFormat.getInstance(locale).parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object = " + object + ", locale = " + locale);
        return NumberFormat.getInstance(locale).format(object);
    }
}

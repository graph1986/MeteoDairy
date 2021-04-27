package com.example.meteodairy.tools;

import android.provider.DocumentsContract;

import androidx.room.Insert;

import com.example.meteodairy.models.DayMeteo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


public class DaysParseHelper {
    public List<DayMeteo> parseDiary(String s, int city, int year, int month) {
        List<DayMeteo> dayMeteoList = new ArrayList<>();
            Document document = Jsoup.parse(s);
            Element table = document.getElementById("data_block").getElementsByTag("table").first();
            if (table != null) {
                Elements days = table.getElementsByTag("tbody").get(1).getElementsByTag("tr");
                for (int i = 0; i < days.size(); i++) {
                    int numberDay = Integer.parseInt(days.get(i).getElementsByTag("td").get(0).text());
                    String temperature = days.get(i).getElementsByTag("td").get(1).text();
                    String urlEffect = "";
                    String urlCloud = "";
                    if (days.get(i).getElementsByTag("td").get(3).childrenSize() != 0) {
                        urlCloud = days.get(i).getElementsByTag("td").get(3).getElementsByTag("img").get(0).attr("src");
                    }
                    if (days.get(i).getElementsByTag("td").get(4).childrenSize() != 0) {
                        urlEffect = days.get(i).getElementsByTag("td").get(4).getElementsByTag("img").get(0).attr("src");
                    }
                    DayMeteo dayMeteo = new DayMeteo(city, numberDay, temperature, urlCloud, urlEffect, year, month);
                    dayMeteoList.add(dayMeteo);
                }
                return dayMeteoList;
            } else {
                return dayMeteoList;
            }
        }


    public List<DayMeteo> parseWeather(String s, int city, int year, int month) {
        List<DayMeteo> dayMeteoList = new ArrayList<>();
            Document document = Jsoup.parse(s);
            Element date = document.getElementsByClass("widget__row widget__row_date").first();
            Elements days = date.getElementsByClass("widget__item");
            Element effect = document.getElementsByClass("widget__row widget__row_table widget__row_icon").first();
            Elements effects = effect.getElementsByClass("widget__item");
            Element templineWTemperature = document.getElementsByClass("templine w_temperature").first();
            Element values = templineWTemperature.getElementsByClass("values").first();
            Elements value = values.getElementsByClass("value");
            for (int i = 0; i < 6; i++) {
                String temperature = value.get(i).getElementsByClass("unit unit_temperature_c").get(0).text();
                int numberDay = Integer.parseInt(days.get(i).getElementsByTag("span").get(0).text().replaceAll("\\D+", ""));
                if (i > 0) {
                    if (numberDay < Integer.parseInt(days.get(i - 1).getElementsByTag("span").get(0).text().replaceAll("\\D+", ""))) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MONTH, +1);
                        year=calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH) + 1;
                    }
                }
                String urlEffect = effects.get(i).getElementsByTag("span").get(0).attr("data-text");
                if (urlEffect.contains("гроза")) {
                    urlEffect = "//st4.gismeteo.ru/static/diary/img/storm.png";
                } else if (urlEffect.contains("дождь")) {
                    urlEffect = "//st8.gismeteo.ru/static/diary/img/rain.png";
                } else if (urlEffect.contains("снег")) {
                    urlEffect = "//st8.gismeteo.ru/static/diary/img/snow.png";
                } else {
                    urlEffect = "";
                }
                String urlCloud = effects.get(i).getElementsByTag("span").get(0).attr("data-text");
                if (urlCloud.contains("Малооблачно")) {
                    urlCloud = "//st4.gismeteo.ru/static/diary/img/sunc.png";
                } else if (urlCloud.contains("Ясно")) {
                    urlCloud = "//st7.gismeteo.ru/static/diary/img/sun.png";
                } else if (urlCloud.contains("Переменная облачность")) {
                    urlCloud = "//st6.gismeteo.ru/static/diary/img/suncl.png";
                } else if (urlCloud.contains("Пасмурно")) {
                    urlCloud = "//st7.gismeteo.ru/static/diary/img/dull.png";
                }
                DayMeteo dayMeteo = new DayMeteo(city, numberDay, temperature, urlCloud, urlEffect, year, month);
                dayMeteoList.add(dayMeteo);
            }
            return dayMeteoList;
        }
}

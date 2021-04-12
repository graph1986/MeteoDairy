package com.example.meteodairy.tools;

import android.provider.DocumentsContract;

import com.example.meteodairy.models.DayMeteo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class DaysParseHelper {
    public List<DayMeteo> parse(String s, String year, String month) {
        List<DayMeteo> dayMeteoList = new ArrayList<>();
        Document document = Jsoup.parse(s);
        Element table = document.getElementById("data_block").getElementsByTag("table").first();
        if (table != null) {
            Elements days = table.getElementsByTag("tbody").get(1).getElementsByTag("tr");
            for (int i = 0; i < days.size(); i++) {
                String numberDay = days.get(i).getElementsByTag("td").get(0).text();
                String temperature = days.get(i).getElementsByTag("td").get(1).text();
                String urlEffect = "";
                String urlCloud = days.get(i).getElementsByTag("td").get(3).getElementsByTag("img").get(0).attr("src");
                if (days.get(i).getElementsByTag("td").get(4).childrenSize() != 0) {
                    urlEffect = days.get(i).getElementsByTag("td").get(4).getElementsByTag("img").get(0).attr("src");
                }
                DayMeteo dayMeteo = new DayMeteo(numberDay, temperature, urlCloud, urlEffect, year, month);
                dayMeteoList.add(dayMeteo);
            }
            return dayMeteoList;
        } else {
            return dayMeteoList;
        }
    }
}

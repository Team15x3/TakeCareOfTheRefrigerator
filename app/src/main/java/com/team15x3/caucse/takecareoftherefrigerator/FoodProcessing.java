package com.team15x3.caucse.takecareoftherefrigerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

public class FoodProcessing {

    private ArrayList<Food> mFoodList;

    public FoodProcessing(ArrayList<Food> foodList) {
        mFoodList = foodList;
    }

    public ArrayList<Food> getFoodListNearExpirationDate() {
        ArrayList<Food> food_list = new ArrayList<Food>();
        SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

        Calendar calendar = Calendar.getInstance();

        try {
            String str = Integer.toString(calendar.get(Calendar.YEAR)*10000 + (calendar.get(Calendar.MONTH) + 1)*100 + calendar.get(Calendar.DAY_OF_MONTH));
            Date curDate = simple_date_format.parse(str);

            Iterator<Food> iterator = mFoodList.iterator();
            while (iterator.hasNext()) {
                Food food = iterator.next();
                Date expiration_date = simple_date_format.parse(food.getSellByDate());

                long calDate = expiration_date.getTime() - curDate.getTime();
                long calDateDays = calDate / (24 * 60 * 60 * 1000);

                if ((int)calDateDays == food.getD_Day()) {
                    food_list.add(food);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return food_list;
    }

    public static String getUsebyDateFromSellbyDate(String sell_by_date) {
        Date today = new Date();
        String use_by_date = null;

        SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

        Calendar sell_calendar = new GregorianCalendar(Locale.KOREA);
        Calendar today_calendar = new GregorianCalendar(Locale.KOREA);

        try {
            int diff_date = 0;
            Date sell_date = simple_date_format.parse(sell_by_date);

            sell_calendar.setTime(sell_date);
            today_calendar.setTime(today);

            int sell_year = sell_calendar.get(Calendar.YEAR);
            int sell_month = sell_calendar.get(Calendar.MONTH);
            int sell_day = sell_calendar.get(Calendar.DAY_OF_YEAR);

            int today_year = today_calendar.get(Calendar.YEAR);
            int today_month = today_calendar.get(Calendar.MONTH);
            int today_day = today_calendar.get(Calendar.DAY_OF_YEAR);

            diff_date = (((sell_year - today_year) * 365 + (sell_month - today_month) * 30 + (sell_day - today_day)) * (2 / 3));

            sell_calendar.set(Calendar.DAY_OF_YEAR, diff_date);

            use_by_date = simple_date_format.format(sell_calendar.getTime());

            // sell_date - today * 2/3 == x

            // sell + x == use

            // Date to String

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return use_by_date;
    }
}

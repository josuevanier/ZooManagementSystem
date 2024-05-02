package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Days {
   public EnumSet<DayOfWeek> openDays;
   private LocalTime openTime;
   private LocalTime closedTime;
   private Map<LocalDate, LocalTime[]> specialSchedule;

   public Days( EnumSet<DayOfWeek> openDays,LocalTime openTime, LocalTime closedTime) {
      this.openDays = openDays;
      this.specialSchedule = new HashMap<>();
      this.openTime = openTime;
      this.closedTime = closedTime;
   }

   public void setSpecialSchedule(LocalDate date, LocalTime opening, LocalTime closing) {
      specialSchedule.put(date, new LocalTime[]{opening, closing});
   }

   public void setClosed(LocalDate date) {
      specialSchedule.put(date, null);
   }

   public static String seeOpenHours(Days days){
      String format = String.format("The Zoo is Open " + days.getOpenTime() + " and closes at " + days.getClosedTime());
      return format;
   }

   public LocalTime getOpenTime() {
      return openTime;
   }

   public LocalTime getClosedTime() {
      return closedTime;
   }

   public boolean isOpen(LocalDateTime dateTime) {
      LocalTime[] times = specialSchedule.get(dateTime.toLocalDate());
      if (times == null && !openDays.contains(dateTime.getDayOfWeek())) {
         return false;
      } else if (times != null) {
         if (times[0] == null && times[1] == null) {
            return false;
         }

         return !dateTime.toLocalTime().isBefore(times[0]) && !dateTime.toLocalTime().isAfter(times[1]);
      }

      return !dateTime.toLocalTime().isBefore(openTime) && !dateTime.toLocalTime().isAfter(closedTime);
   }
   public static void bookingZooTicket(Days days, LocalTime localTime){
      if(days.getOpenTime().isBefore(localTime) && days.closedTime.isAfter(localTime)){
         System.out.println("Nice");
      }else System.out.println("no");

   }

   @Override
   public String toString() {
      return "Days{" +
              "openDays=" + openDays +
              ", openTime=" + openTime +
              ", closedTime=" + closedTime +
              ", specialSchedule=" + specialSchedule +
              '}';
   }
   public static void bookingZooTicket(Days days, LocalDateTime dateTime) {
      LocalDate date = dateTime.toLocalDate();
      LocalTime time = dateTime.toLocalTime();


      if (!days.isOpen(dateTime)) {
         System.out.println("The zoo is closed at the specified date and time.");
         return;
      }

      if (days.specialSchedule.containsKey(date)) {
         LocalTime[] specialTimes = days.specialSchedule.get(date);
         if (specialTimes != null) { // Special schedule exists

            if (time.isBefore(specialTimes[0]) || time.isAfter(specialTimes[1])) {
               System.out.println("The zoo is closed at the specified date and time according to special schedule.");
               return;
            }
         } else {
            System.out.println("The zoo is closed at the specified date according to special schedule.");
            return;
         }
      }
      System.out.println("The ticket is booked for " + date + " at " + time);
   }

   public static void main(String[] args) {
      LocalTime localTime =  LocalTime.of(22,30,0);
      LocalTime localTime1 = LocalTime.of(15,20,5);

      EnumSet<DayOfWeek> dayOfWeeks;
      dayOfWeeks = EnumSet.noneOf(DayOfWeek.class);
      dayOfWeeks.add(DayOfWeek.Monday);

      Days days = new Days(dayOfWeeks,LocalTime.now(),localTime);
      System.out.println(days);


      Days.bookingZooTicket(days,localTime1);
   }
}


package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Days {
   public static EnumSet<DayOfWeek> openDays;
   private LocalTime openTime;
   private LocalTime closedTime;
   private Map<LocalDate, LocalTime[]> specialSchedule;
 private static   Scanner sc = new Scanner(System.in);

   public Days( EnumSet<DayOfWeek> openDays,LocalTime openTime, LocalTime closedTime) {
      this.openDays = openDays;
      this.specialSchedule = new HashMap<>();
      this.openTime = openTime;
      this.closedTime = closedTime;
   }


    public void setSpecialSchedule(LocalDate date, LocalTime opening, LocalTime closing, boolean closed) {
        if (closed) {
            specialSchedule.put(date, null);
        } else {
            specialSchedule.put(date, new LocalTime[]{opening, closing});
        }
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
       java.time.DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
       System.out.println("Day of the week " +dayOfWeek);
       ArrayList<DayOfWeek> toCheck = new ArrayList<>();
       toCheck.addAll(openDays);

       boolean isRegularOpenDay = false;
       for(DayOfWeek day : toCheck){
         String current = day + "";
         String dayOf = dayOfWeek + "";
         if(current.equals(dayOf)){
            isRegularOpenDay = true;
            break;
         }
       }

       System.out.println("is regular day "  + isRegularOpenDay);

       // Check if the day is a regular open day
       if (isRegularOpenDay) {
           // If the day is regular open, check if it falls within regular opening hours
           return !dateTime.toLocalTime().isBefore(openTime) && !dateTime.toLocalTime().isAfter(closedTime);
       } else if (times != null) {
           // Check if the day has special opening hours
           return !dateTime.toLocalTime().isBefore(times[0]) && !dateTime.toLocalTime().isAfter(times[1]);
       } else {
           // If neither regular nor special opening hours are defined, the zoo is closed
           return false;
       }
   }
   public static void bookingZooTicket(Days days, LocalTime localTime){
      if(days.getOpenTime().isBefore(localTime) && days.closedTime.isAfter(localTime)){
         System.out.println("Nice");
      }else System.out.println("no");

   }

   @Override
   public String toString() {
       if (openDays.isEmpty() || openTime == null || closedTime == null) {
           return "Zoo is closed";
       }

       StringBuilder sb = new StringBuilder();
       sb.append("Open days: ").append(openDays);
       sb.append("\nOpening time: ").append(openTime);
       sb.append("\nClosing time: ").append(closedTime);

       if (specialSchedule != null && !specialSchedule.isEmpty()) {
           sb.append("\nSpecial Schedule:");
           for (Map.Entry<LocalDate, LocalTime[]> entry : specialSchedule.entrySet()) {
               LocalDate date = entry.getKey();
               LocalTime[] times = entry.getValue();
               sb.append("\n- Date: ").append(date);
               if (times != null) {
                   sb.append(", Opening Time: ").append(times[0]);
                   sb.append(", Closing Time: ").append(times[1]);
               } else {
                   sb.append(", Closed");
               }
           }
       }
       return sb.toString();
   }


   public EnumSet<DayOfWeek> getOpenDays() {
      return openDays;
   }

   public Map<LocalDate, LocalTime[]> getSpecialSchedule() {
      return specialSchedule;
   }


      public static void setOpenedOrClosed(EnumSet<DayOfWeek> openedOrClosed) {

         System.out.println("Enter the day to set (e.g., MONDAY):");
         String input = sc.next().toUpperCase(); // Convert input to uppercase for case-insensitivity
         try {
            DayOfWeek day = DayOfWeek.valueOf(input); // Parse input to DayOfWeek enum
            System.out.println("Enter the status for " + day + " (OPEN/CLOSED):");
            String status = sc.next().toUpperCase(); // Convert input to uppercase for case-insensitivity
            switch (status) {
               case "OPEN":
                  openedOrClosed.add(day);
                  System.out.println(day + " set to OPEN.");
                  break;
               case "CLOSED":
                  openedOrClosed.remove(day);
                  System.out.println(day + " set to CLOSED.");
                  break;
               default:
                  System.out.println("Invalid status. Please enter OPEN or CLOSED.");
            }
         } catch (IllegalArgumentException e) {
            System.out.println("Invalid day. Please enter a valid day of the week (e.g., MONDAY).");
         }
   }

   public void setOpenTime(LocalTime openTime) {
      this.openTime = openTime;
   }

   public void setClosedTime(LocalTime closedTime) {
      this.closedTime = closedTime;
   }

}


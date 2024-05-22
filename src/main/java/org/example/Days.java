package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Contains the info of Days in the zoo mangamenet system
 */
public class Days {
   public static EnumSet<DayOfWeek> openDays;
   private LocalTime openTime;
   private LocalTime closedTime;
   private Map<LocalDate, LocalTime[]> specialSchedule;
 private static   Scanner sc = new Scanner(System.in);

    /**
     * Constructor for days
     * @param openDays
     * @param openTime
     * @param closedTime
     */
   public Days( EnumSet<DayOfWeek> openDays,LocalTime openTime, LocalTime closedTime) {
      this.openDays = openDays;
      this.specialSchedule = new HashMap<>();
      this.openTime = openTime;
      this.closedTime = closedTime;
   }

    /**
     * Set special scheduel
     * @param date Local date
     * @param opening Opening time
     * @param closing closing time
     * @param closed and boolean status if its open are closes
     */
    public void setSpecialSchedule(LocalDate date, LocalTime opening, LocalTime closing, boolean closed) {
        if (closed) {
            specialSchedule.put(date, null);
        } else {
            specialSchedule.put(date, new LocalTime[]{opening, closing});
        }
    }


    /**
     *Get the oepn time
      * @return Local time object
     */
    public LocalTime getOpenTime() {
      return openTime;
   }

    /**
     * Chceck if the zoo is open during the current days
     * @param dateTime date time object
     * @return boolean value if its open true else false
     */

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

    /**
     * To String method to print the days values
     * @return
     */
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


    /**
     * Get special scheduels
     * @return A map special scheduel with local date and time [] as an array because it invololves opening time and closed time
     */
   public Map<LocalDate, LocalTime[]> getSpecialSchedule() {
      return specialSchedule;
   }

    /**
     * Set opene or closed base of the current enum of the weak
     * @param openedOrClosed
     */
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

    /**
     * Set open time
     * @param openTime take a local time object
     */
   public void setOpenTime(LocalTime openTime) {
      this.openTime = openTime;
   }

    /**
     * Set closed time
     * @param closedTime take a local time object
     */
   public void setClosedTime(LocalTime closedTime) {
      this.closedTime = closedTime;
   }
}


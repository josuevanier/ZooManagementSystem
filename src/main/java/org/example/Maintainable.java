package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface Maintainable {
    // Method to update opening hours
    void updateOpeningHours(Map<DayOfWeek, LocalTime[]> newOpeningHours);

    // Method for adding or updating animal information
    void addOrUpdateAnimal(String species, String enclosureId);

    // Method for adding or updating enclosure information
    void addOrUpdateEnclosure(String enclosureName);

    // Method for managing staff members
    void manageStaff(Staff staff);

    // Method for updating ticket prices
    void updateTicketPrices(Map<String, Double> ticketPrices);

    // Method for handling special events or schedules
    void handleSpecialEvents(Map<LocalDate, String> specialEvents);

    // Method for performing routine maintenance tasks
    void performMaintenanceTasks();
}

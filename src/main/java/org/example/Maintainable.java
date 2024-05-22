package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 * Interface that contains the method of Maintainable that staffs and admin implimented
 */
public interface Maintainable {
    // Method to update opening hours
    void updateOpeningHours(Days days);

    // Method for updating ticket prices
    void updateTicketPrices(Tickets zooTickets);
    // Method for performing routine maintenance tasks
    void performMaintenanceTasks();
    void UpdateClosedTimeHours(Days days);
}

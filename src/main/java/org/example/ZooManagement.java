package org.example;

import java.io.File;
import java.sql.SQLOutput;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains the info of a Zoo management
 * @author Joseph Josue fORESTAL
 */
public class ZooManagement {
    private static boolean[] isAdmIn = {false};
    private static boolean[] isStaff = {false};
    public static LocalTime DEFAULT_OPENING_TIME = LocalTime.of(10, 0); // 10:00 AM
    public static LocalTime DEFAULT_CLOSING_TIME = LocalTime.of(22, 0); // 10:00 PM
    static EnumSet<DayOfWeek> openedOrClosed = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    static Days zooDays = new Days(openedOrClosed, DEFAULT_OPENING_TIME, DEFAULT_CLOSING_TIME);


    static List<String> tasks = new ArrayList<>();
    static TreeSet<Users> users = new TreeSet<>(new Comparator<Users>() {
        @Override
        public int compare(Users u1, Users u2) {
            // Example: Compare primarily by lastName, then firstName if lastNames are the same
            int last = u1.getLastName().compareTo(u2.getLastName());
            if (last != 0) return last;
            return u1.getFirstName().compareTo(u2.getFirstName());
        }
    });
    static TreeSet<? extends Staff> employees;
    static Tickets currentTickets;

    static List<Enclosure> enclosures = new ArrayList<>();


    static Scanner sc = new Scanner(System.in);
    static String zooName;

    {
        System.out.println("Enter The name of the Zoo !");
        zooName = sc.next();
    }

    /**
     * Get the current zooName
     *
     * @return a Sting the zoo Name
     */
    public static String getZooName() {
        return zooName;
    }

    /**
     * The menu of each user type
     *
     * @param args (not use)
     * @throws Exception (Trows the exceptions)
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to " + getZooName());

        boolean exit = false;
        while (!exit) {
            System.out.println("Select user type:");
            System.out.println("1. Admin");
            System.out.println("2. Staff");
            System.out.println("3. Visitor");
            System.out.println("4. Exit");
            boolean isNum = false;
            int userType = 0;
            while (!isNum) {
                System.out.println("Enter your choice:");
                if (scanner.hasNextInt()) {
                    userType = scanner.nextInt();
                    isNum = true;
                } else {
                    System.out.println("Choice must be an integer.");
                    scanner.next(); // Clear the invalid input
                }
            }


            switch (userType) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    if (employees == null) {
                        System.out.println("Employees  is empty");
                    } else isLoggedIn(employees);
                    break;

                case 3:
                    visitorMenu();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
        scanner.close();
    }

    /**
     * Staff must log in through this
     * @param employees employees tree where each employee is added.
     */
    public static void isLoggedIn(TreeSet<? extends Staff> employees) {
        int MAX_ATTEMPTS = 3;
        int attempts = 0;
        String id = "";
        while (!isStaff[0] && attempts < MAX_ATTEMPTS) {
            System.out.print("Enter Staff ID: ");
            String inputId = sc.nextLine().trim();
            id = inputId;
            for (Staff employee : employees) {
                if (inputId.equals(employee.staffGetId())) {
                    isStaff[0] = true;
                    System.out.println("Login successful!");
                    break;
                } else {
                    attempts++;
                    System.out.println("Invalid Staff ID. Attempts left: " + (MAX_ATTEMPTS - attempts));
                }
            }
        }

        if (isStaff[0]) {

            while (isStaff[0]) {
                StaffMenu(employees, id);
            }
            isStaff[0] = false;

        } else {
            System.out.println("Maximum login attempts reached. Exiting...");
        }
    }

    /**
     * Staff menu with its methods
     * @param employees  employee tree to check each employee to access the employee method
     */
    public static void StaffMenu(TreeSet<? extends  Staff> employees, String id) {
        boolean currentStaffIn = false;
        while (!currentStaffIn) {
            System.out.println("Staff menu ");
            System.out.println("1. See tasks");
            System.out.println("2. Complete tasks");
            System.out.println("3. Quit");
            System.out.println("4. Update ticket prices");
            System.out.println("5. Update Opening hours");
            System.out.println("6.Update closed time hours");
            System.out.println("7.Sort enclosures");
            System.out.println("8.Sort animal within enclosure");

            System.out.println("Enter your choice: ");
            int choice;
            while (true) {
                System.out.print("Enter an integer: ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    sc.next(); // Consume invalid input
                }
            }
            switch (choice) {
                case 1:
                    for (Staff employee : employees) {
                        if (employee.staffGetId().equals(id)) {
                            ((NormalEmployee) employee).performMaintenanceTasks();
                            ((NormalEmployee) employee).Transaction("Task seen");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter the specific task you have completed: ");
                    boolean findTask = false;
                    String complete = sc.next().trim();
                    for (String task : tasks) {
                        if (complete.equalsIgnoreCase(task)) {
                            for (Staff employee : employees) {
                                if (employee.staffGetId().equals(id)) {
                                    ((NormalEmployee) employee).Transaction("Task " + task + " allegedly completed");
                                }
                            }
                            tasks.remove(task);
                            System.out.println("Task " + task + "completed.");
                            findTask = true;
                            break;
                        }
                    }
                    if (!findTask) {
                        System.out.println("Task has not been found.");
                        break;
                    }
                    break;
                case 3:
                    currentStaffIn = true;
                    break;
                case 4:
                    for (Staff employee : employees) {
                        ((NormalEmployee) employee).updateTicketPrices(currentTickets);
                        break;
                    }
                    break;
                case 5:
                    for (Staff employee : employees) {
                        ((NormalEmployee) employee).updateOpeningHours(zooDays);
                        break;
                    }
                    break;
                case 6:
                    for (Staff employee : employees) {
                        ((NormalEmployee) employee).UpdateClosedTimeHours(zooDays);
                        break;
                    }
                case 7:
                    sortEnclosures();
                    break;
                case 8:
                    sortAnimalsWithinEnclosures();
                    break;
            }
        }
    }

    /**
     * Sort the enclosures with comaparator
     */
    public static void sortEnclosures() {
        Collections.sort(enclosures, Comparator.comparing(Enclosure::getName));
        System.out.println(enclosures);
        System.out.println("Enclosures sorted successfully.");
    }

    /**
     * Sort the animals within that the current enclosure
     */
    public static void sortAnimalsWithinEnclosures() {
        for (Enclosure enclosure : enclosures) {
            enclosure.sortAnimals();
        }
        System.out.println("Animals within enclosures sorted successfully.");
    }


    /**
     * The admin menu
     * @param admin Admin object (single pattern)
     * @param zooDays the current zooDays of Days object to keep track of time
     * @throws Exception throws the exceptions away just in case, but they are stopped by ifs statements
     */
    public static void adminMenu(Admin admin, Days zooDays) throws Exception {
        System.out.println("Admin Menu");
        System.out.println("1. Add enclosure");
        System.out.println("2. Add animal from file");
        System.out.println("3. Add an Animal ");
        System.out.println("4. Set staffs from file(only) :");
        System.out.println("5. View User Transaction History");
        System.out.println("6. Perform Maintenance Tasks");
        System.out.println("7. UpdateOpen Time");
        System.out.println("8. Update Closed Time");
        System.out.println("9. Update Tickets price");
        System.out.println("10. Set special schedule");
        System.out.println("11. Set days open or closed");
        System.out.println("12. view enclosures");
        System.out.println("13. Sort users");
        System.out.println("14. Sort staff");
        System.out.println("15. Set pricing");
        System.out.println("16.Remove  enclosure");
        System.out.println("17. Quit");
        int choice;
        while (true) {
            System.out.print("Enter an integer: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();  // Consume the newline left-over
                break;  // Exit the loop if input is an integer
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next();  // Consume the invalid input
            }
        }

        switch (choice) {
            case 1:
                System.out.println("Enclosure must have a name of a species: ");
                String enclosureName = sc.next().toUpperCase();
                Enclosure enclosure = new Enclosure(enclosureName);
                if (!enclosures.contains(enclosure)) {
                    enclosures.add(enclosure);
                    break;
                } else System.out.println("Enclosure " + enclosureName + " already exists");
                break;
            case 2:
                String fileName = "/Users/josue/Animal.readFromFile";
                System.out.println("Is file name given (Y/N)");
                String answer = sc.next();
                if (answer.equalsIgnoreCase("N")) {

                    System.out.println("Ener file name");
                    fileName = sc.next();
                    List<Animal> animalsFromFile = Animal.readFromFile(fileName);
                    for (Animal animal : animalsFromFile) {
                        addAnimalToEnclosure(animal);
                    }
                    break;
                }else{
                    List<Animal> animalsFromFile = Animal.readFromFile(fileName);
                    for (Animal animal : animalsFromFile) {
                        addAnimalToEnclosure(animal);
                    }
                    break;
                }
            case 3:
                System.out.println("Enter species name(example Lion or Tiger)");
                String animalName = sc.next();
                System.out.println("Enter enclosure ID");
                String enclosureId = sc.next();
                Animal animal = new Animal(animalName);
                for (Enclosure enclosure1 : enclosures) {
                    if (enclosure1.getId().equalsIgnoreCase(enclosureId)) {
                        enclosure1.addAnimal(animal);
                        break;
                    } else {
                        int count = 0;
                        System.out.println("Invalid " + count);
                        ++count;
                        break;
                    }
                }
                break;
            case 4:
                System.out.println("Is file name for employee already given(Y/N)");
                String ans = sc.next();
                String fileEmployee = "/Users/josue/EmployeeReadFile";
                if(ans.equalsIgnoreCase("Y")){
                    employees = NormalEmployee.readStaffFromFile(fileEmployee);
                    break;
                }else {
                    System.out.println("Enter file as path: ");
                    fileEmployee  = sc.next();
                    employees = NormalEmployee.readStaffFromFile(fileEmployee);
                    break;
                }
            case 5:
                System.out.println("Enter the user name: ");
                String name = sc.next();
                admin.viewUserTransactionHistory(users, name);
                break;
            case 6:
                admin.performMaintenanceTasks();
                break;
            case 7:
                admin.updateOpeningHours(zooDays);
                break;
            case 8:
                admin.UpdateClosedTimeHours(zooDays);
                break;
            case 9:
                admin.updateTicketPrices(currentTickets);
                break;
            case 10:
                AdminSetSpecial();
                break;
            case 11:
                Days.setOpenedOrClosed(openedOrClosed);
                break;
            case 12:
                for (Enclosure enclosure1 : enclosures) {
                    enclosure1.displayAnimals(enclosures);
                    break;
                }
                break;
            case 13:
                sortUsersMenu();
                break;
            case 14:
                sortStaffMenu();
                break;
            case 15:
                System.out.println("Tickets");
               Tickets.setPricing(sc);
               break;
            case 16:
                System.out.println("Enter enclosure name:");
                String names = sc.next();
                for(Enclosure enclosure1 : enclosures){
                    if(enclosure1.getName().equalsIgnoreCase(names)){
                        enclosures.remove(enclosure1);
                        break;
                    }
                }
            case 17:
                System.out.println("Bye " + admin.getUserName());
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /**
     * Set the admin special schedule by asking user's inputs
     */
    public static void AdminSetSpecial() {
        Scanner scanner = new Scanner(System.in);
        int year, month, day;
        do {
            System.out.println("Enter the year:");
            year = scanner.nextInt();
        } while (year < 0 || year > 9999); // Assuming valid year range

        do {
            System.out.println("Enter the month (1-12):");
            month = scanner.nextInt();
        } while (month < 1 || month > 12); // Validating month range

        int maxDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
        do {
            System.out.println("Enter the day (1-" + maxDayOfMonth + "):");
            day = scanner.nextInt();
        } while (day < 1 || day > maxDayOfMonth); // Validating day range
        LocalTime openingTime, closingTime;
        do {
            System.out.println("Enter the opening time (HH:mm:ss):");
            String openingTimeStr = scanner.next();
            try {
                openingTime = LocalTime.parse(openingTimeStr);
            } catch (DateTimeParseException e) {
                openingTime = null;
            }
        } while (openingTime == null);

        do {
            System.out.println("Enter the closing time (HH:mm:ss):");
            String closingTimeStr = scanner.next();
            try {
                closingTime = LocalTime.parse(closingTimeStr);
            } catch (DateTimeParseException e) {
                closingTime = null;
            }
        } while (closingTime == null);

        LocalDate date = LocalDate.of(year, month, day);
        zooDays.setSpecialSchedule(date, openingTime, closingTime, false); // Assuming the d
    }

    /**
     * Add animal to the current set of enclosures
     * @param animal an animal object
     */
    public static void addAnimalToEnclosure(Animal animal) {
        String species = animal.getSpecies();
        boolean added = false;
        for (Enclosure enclosure : enclosures) {
            if (enclosure.getName().equalsIgnoreCase(species)) {
                enclosure.addAnimal(animal);
                added = true;
                break;
            }
        }
        if (!added) {
            System.out.println("Enclosure for species " + species + " not found.");
        }
    }

    /**
     * Visitor menu for normal users
     */
    private static void visitorMenu() {
        System.out.println("Enter firstName: ");
        String firstName = sc.next();
        System.out.println("Enter last name: ");

        String lastName = sc.next();
        int age;
        while (true) {
            System.out.print("Enter your age: ");
            if (sc.hasNextInt()) { // Check if the next token is an integer
                age = sc.nextInt(); // Read the integer value
                if (age > 16) {
                    break; // If age is greater than 16, break out of the loop
                } else {
                    System.out.println("You must be older than 16.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // Consume the invalid token to avoid an infinite loop
            }

        }
        boolean isExit = false;
        System.out.println("The user transaction must be done now ! ");
        Users users1 = new Users(firstName, lastName, age, zooDays);
        users.add(users1);
        while (!isExit) {
            System.out.println("Visitor Menu");
            System.out.println("1.View zooDays");
            System.out.println("2. View Enclosure");
            System.out.println("3. Back to Main Menu");
            System.out.println("4. Book ticket ");
            System.out.println("5. Cancel ticket transaction ");
            System.out.println("5. See transactions");
            System.out.println("5. See prices ");
            System.out.println("6. Exit only when you are done ! ");
            System.out.print("Enter your choice: ");
            int choice;
            while (true) {
                System.out.print("Enter an integer: ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();  // Consume the newline left-over
                    break;  // Exit the loop if input is an integer
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    sc.next();  // Consume the invalid input
                }
            }
            switch (choice) {
                case 1:
                    System.out.println(zooDays.toString());
                    break;
                case 2:
                    for (Enclosure enclosure : enclosures) {
                        System.out.println(enclosure.getName());
                    }
                    break;

                case 3:
                    for (Enclosure enclosure1 : enclosures) {
                        System.out.println("Enclosure " + enclosure1.getName() + "ID : " + enclosure1.getId());
                        System.out.println();
                         enclosure1.displayAnimals(enclosures);
                    }
                    break;
                case 4:
                    users1.bookTickets();
                    break;
                case 5:
                    System.out.println("Ticket ID provided from email: ");
                    String msg = sc.next();
                    if(msg.matches("Zoo-[0-9]+")) users1.cancelTicket(msg);
                    else System.out.println("Wrong id");
                    break;
                case 23:
                    System.out.println("See transactions: ");
                    if(users1.getTransactions() != null){
                        users1.getTransactions();
                        break;
                    }
                    else{System.out.println("No transactions have been made.");
                    break;
                    }

                case 7:
                    System.out.println("Child price  "  + Tickets.getChildPrice() + "$");
                    System.out.println("Adults price " + Tickets.getAdultPrice() + "$");
                    System.out.println("Elders/Seniors price " + Tickets.getSeniorPrice() + "$");
                    break;
                case 10:
                    isExit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    /**
     * Check if admin logged in and if it has valid inputs
     * @param admin Admin object as info refference to check if the inputs are valid
     * @return a boolean value true or false based of the log in inputs
     */
    private static boolean adminLogin(Admin admin) {
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            String enteredUsername = prompt("Enter username: ");
            String enteredPassword = prompt("Enter password: ");

            if (enteredUsername.equals(admin.getUserName()) && enteredPassword.equals(admin.getPassWord())) {
                return true; // Successful login
            } else {
                System.out.println("Invalid username or password. Please try again.");
                attempts++;
            }
        }

        return false; // Failed login after max attempts
    }

    /**
     * Prom  message for admin
     * @param message String message
     * @return (return) the input
     */
    private static String prompt(String message) {
        System.out.println(message);
        return sc.next();
    }

    /**
     * Display the admin menu and also check if the admin already sign in.
     * @throws Exception In case but exception are deal with if statements
     */
    private static void adminMenu() throws Exception{
        if (!isAdmIn[0]) { // Check if admin is not already signed in
            Admin adminInfo = Admin.getInstance();
            if (adminLogin(adminInfo)) {
                System.out.println("Welcome, " + adminInfo.getUserName() + "!");
                System.out.println("Admin menu options:");
                adminMenu(adminInfo, zooDays);
                isAdmIn[0] = true; // Set admin signed in flag to true
            }
        } else {
            Admin adminInfo = Admin.getInstance();
            System.out.println("Welcome back, " + adminInfo.getUserName() + "!");
            boolean isNotExitAdmin = false;
            while(!isNotExitAdmin) {
                System.out.println("Admin menu options:");
                adminMenu(adminInfo, zooDays);

                System.out.println( adminInfo.getUserName() + "wanna quit ? :)(Y/N) ");
                String ans = sc.next();
                if(ans.equalsIgnoreCase("Y") || ans.equalsIgnoreCase("Yes")){
                    isNotExitAdmin  = true;
                }
            }
            isAdmIn[0] = false;
        }
    }

    /**
     * The possible sorting  of users  for the admin based: names and age
      */
    private static void sortUsersMenu() {
        System.out.println("Sort Users By:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Age");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                TreeSet<Users> sortedUsersByFirstName = new TreeSet<>(Comparator.comparing(Users::getFirstName));
                sortedUsersByFirstName.addAll(users);
                System.out.println("Users sorted successfully.");
                sortedUsersByFirstName.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getAge()));
                break;
            case 2:
                TreeSet<Users> sortedUsersByLastName = new TreeSet<>(Comparator.comparing(Users::getLastName));
                sortedUsersByLastName.addAll(users);
                System.out.println("Users sorted successfully.");
                sortedUsersByLastName.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getAge()));
                break;
            case 3:
                TreeSet<Users> sortedUsersByAge = new TreeSet<>(Comparator.comparingInt(Users::getAge));
                sortedUsersByAge.addAll(users);
                System.out.println("Users sorted successfully.");
                sortedUsersByAge.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getAge()));
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /**
     * Sorting  for the admins' staffs based on different data members(name ,last name ,and  hours worked)
     */
    private static void sortStaffMenu() {
        System.out.println("Sort Staff By:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Hours Worked");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                TreeSet<NormalEmployee> sortedEmployeesByFirstName = new TreeSet<>(Comparator.comparing(NormalEmployee::getFirstName));
                sortedEmployeesByFirstName.addAll((Collection<? extends NormalEmployee>) employees);
                System.out.println("Staff sorted successfully.");

                for (NormalEmployee employee : sortedEmployeesByFirstName) {
                    System.out.println(employee.getFirstName() + " " + employee.getLastName() );
                }
                break;
            case 2:
                TreeSet<NormalEmployee> sortedEmployeesByLastName = new TreeSet<>(Comparator.comparing(NormalEmployee::getLastName));
                sortedEmployeesByLastName.addAll((Collection<? extends NormalEmployee>) employees);
                System.out.println("Staff sorted successfully.");
                for (NormalEmployee employee : sortedEmployeesByLastName) {
                    System.out.println(employee.getLastName() + " " + employee.getFirstName());
                }
                break;
            case 3:
                TreeSet<NormalEmployee> sortedEmployeesByHoursWorked = new TreeSet<>(Comparator.comparingDouble(NormalEmployee::getHours));
                sortedEmployeesByHoursWorked.addAll((Collection<? extends NormalEmployee>) employees);

                System.out.println("Staff sorted successfully.");
                for (NormalEmployee employee : sortedEmployeesByHoursWorked) {
                    System.out.println(employee.getFirstName() + " " + employee.getLastName() + " " + employee.getHours());
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}




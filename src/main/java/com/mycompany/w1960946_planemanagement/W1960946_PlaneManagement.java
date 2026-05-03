package com.mycompany.w1960946_planemanagement;

import java.util.Scanner;

public class W1960946_PlaneManagement {
    // Constants for seat capacities
    private static final int ROWS_A_D = 14;
    private static final int ROWS_B_C = 12;

    // Constants for seat prices
    private static final int PRICE_A = 200;
    private static final int PRICE_B = 150;
    private static final int PRICE_C = 180;

    // 2D array to represent the plane seats
    private static final char[][] seats = new char[4][];

    public static void main(String[] args) {
        Ticket[] tickets = new Ticket[ROWS_A_D + ROWS_B_C]; // Define array of tickets
        
        initializeSeats();

        System.out.println("Welcome to the Plane Management application");

        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            // Display menu
            System.out.println("*****************************************");
            System.out.println("*            MENU OPTIONS               *");
            System.out.println("*****************************************");
            System.out.println("     1. Buy a seat");
            System.out.println("     2. Cancel a seat");
            System.out.println("     3. Find first available seat");
            System.out.println("     4. Show seating plan");
            System.out.println("     5. Print tickets info");
            System.out.println("     6. Search for a ticket");
            System.out.println("     0. Exit");
            System.out.println("*****************************************");

            int choice;
            do {
                choice = scanner.nextInt();
                if (choice < 0 || choice > 6) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                    System.out.print("Please select an option: ");
                }
            } while (choice < 0 || choice > 6);

            switch (choice) {
                case 0:
                    running = false;
                    break;
                case 1:
                    buySeat(scanner, tickets);
                    break;
                case 2:
                    cancelSeat(scanner, tickets);
                    break;
                case 3:
                    findFirstAvailableSeat();
                    break;
                case 4:
                    showSeatingPlan();
                    break;
                case 5:
                    printTicketsInfoAndTotalSales(tickets);
                    break;
                case 6:
                    searchTicket(tickets);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        scanner.close();
    }

    // Method to initialize the seats
    private static void initializeSeats() {
        seats[0] = new char[ROWS_A_D];
        seats[1] = new char[ROWS_B_C];
        seats[2] = new char[ROWS_B_C];
        seats[3] = new char[ROWS_A_D];

        // Initialize all seats as available (O)
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = 'O'; // 'O' represents available seat
            }
        }
    }

    // Method to buy a seat
    private static void buySeat(Scanner scanner, Ticket[] tickets) {
    // Prompt the user to enter the row letter
    System.out.print("Enter the row letter (A-D): ");
    char row;
    // Validate the row letter input
    do {
        row = scanner.next().toUpperCase().charAt(0);
        if (row < 'A' || row > 'D') {
            System.out.println("Invalid row letter. Please enter a letter between A and D.");
            System.out.print("Enter the row letter (A-D): ");
        }
    } while (row < 'A' || row > 'D');

    // Determine the index of the row
    int rowIdx;
    switch (row) {
        case 'A':
            rowIdx = 0;
            break;
        case 'B':
            rowIdx = 1;
            break;
        case 'C':
            rowIdx = 2;
            break;
        case 'D':
            rowIdx = 3;
            break;
        default:
            System.out.println("Invalid row letter.");
            return;
    }

    // Determine the maximum seat number based on the row
    int maxSeatNumber = (rowIdx == 0 || rowIdx == 3) ? ROWS_A_D : ROWS_B_C;
    // Prompt the user to enter the seat number
    System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
    int seatNumber;
    // Validate the seat number input
    do {
        seatNumber = scanner.nextInt();
        if (seatNumber < 1 || seatNumber > maxSeatNumber) {
            System.out.println("Invalid seat number. Please enter a number between 1 and " + maxSeatNumber + ".");
            System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
        }
    } while (seatNumber < 1 || seatNumber > maxSeatNumber);

    // Calculate the seat index in the array
    int seatIndex = seatNumber - 1;

    // Calculate the seat price
    int seatPrice = calculateSeatPrice(rowIdx, seatIndex);

    // Check if the seat is available
    if (seats[rowIdx][seatIndex] == 'O') {
        // Collect information for the person buying the ticket
        System.out.print("Enter person's name: ");
        String name = scanner.next();
        System.out.print("Enter person's surname: ");
        String surname = scanner.next();
        System.out.print("Enter person's email: ");
        String email = scanner.next();

        // Create a new Person object
        Person person = new Person(name, surname, email);

        // Create a new Ticket object with seat and person information
        Ticket ticket = new Ticket(row, seatNumber, seatPrice, person);

        // Add the ticket to the array of tickets
        tickets[calculateTicketIndex(rowIdx, seatIndex)] = ticket;

        // Record the seat as sold
        seats[rowIdx][seatIndex] = 'X';

        // Save the ticket information
        ticket.save();

        // Display purchase confirmation
        System.out.println("Seat " + row + seatNumber + " has been successfully purchased for £" + seatPrice);
    } else {
        // Inform the user that the seat is already taken
        System.out.println("Sorry, the seat " + row + seatNumber + " is already taken.");
    }
}


    // Method to calculate seat price based on row and seat index
    private static int calculateSeatPrice(int rowIdx, int seatIndex) {
        if ((rowIdx == 0 || rowIdx == 3) && seatIndex < 5) {
            return PRICE_A;
        } else if (seatIndex >= 5 && seatIndex < 10) {
            return PRICE_B;
        } else {
            return PRICE_C;
        }
    }

    // Calculate the index for storing the ticket in the tickets array
    private static int calculateTicketIndex(int rowIdx, int seatIndex) {
        if (rowIdx == 0 || rowIdx == 3) {
            return seatIndex;
        } else {
            return ROWS_A_D + seatIndex;
        }
    }

    // Method to cancel a seat
    private static void cancelSeat(Scanner scanner, Ticket[] tickets) {
        System.out.print("Enter the row letter (A-D): ");
        char row;
        do {
            row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row letter. Please enter a letter between A and D.");
                System.out.print("Enter the row letter (A-D): ");
            }
        } while (row < 'A' || row > 'D');

        int rowIdx;
        switch (row) {
            case 'A':
                rowIdx = 0;
                break;
            case 'B':
                rowIdx = 1;
                break;
            case 'C':    
                rowIdx = 2;
                break;
            case 'D':
                rowIdx = 3;
                break;
            default:
                System.out.println("Invalid row letter.");
                return;
        }

        int maxSeatNumber = (rowIdx == 0) ? ROWS_A_D : ROWS_B_C;
        System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
        int seatNumber;
        do {
            seatNumber = scanner.nextInt();
            if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                System.out.println("Invalid seat number. Please enter a number between 1 and " + maxSeatNumber + ".");
                System.out.print("Enter the seat number (1-" + maxSeatNumber + "): ");
            }
        } while (seatNumber < 1 || seatNumber > maxSeatNumber);

        int seatIndex = seatNumber - 1; // Seat index in the array

        if (seats[rowIdx][seatIndex] == 'X') { // Check if the seat is sold
            seats[rowIdx][seatIndex] = 'O'; // Record the seat as available

            // Find the index of the ticket associated with the canceled seat
            int ticketIndex = calculateTicketIndex(rowIdx, seatIndex);

            // Remove the ticket by setting the corresponding element to null
            tickets[ticketIndex] = null;

            System.out.println("Seat " + row + seatNumber + " has been successfully canceled.");
        } else {
            System.out.println("Sorry, the seat " + row + seatNumber + " is already available.");
        }
    }

    // Method to find the first available seat
    private static void findFirstAvailableSeat() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 'O') {
                    char rowLetter = (i == 0 || i == 3) ? (char) ('A' + i) : (char) ('B' + i - 1);
                    System.out.println("First available seat found at row " + rowLetter + ", seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats found.");
    }

    // Method to show the seating plan
    private static void showSeatingPlan() {
    System.out.println("Seating Plan:");
    for (int i = 0; i < seats.length; i++) {
        // Determine the row index based on the row letter
        int rowIdx;
        switch (i) {
            case 0: // Row A
                rowIdx = 0;
                break;
            case 1: // Row B
                rowIdx = 1;
                break;
            case 2: // Row C
                rowIdx = 2;
                break;
            case 3: //Row D
                rowIdx = 3;
                break;
            default:
                throw new IllegalArgumentException("Invalid row index.");
        }
        
        for (int j = 0; j < seats[rowIdx].length; j++) {
            System.out.print(seats[rowIdx][j]);
        }
        System.out.println();
    }
}


    // Method to print tickets information and total sales
    private static void printTicketsInfoAndTotalSales(Ticket[] tickets) {
        // Implementation of printTicketsInfoAndTotalSales method goes here
        int totalsales = 0;
        for (Ticket ticket : tickets) {
            if (ticket != null) { //checks that a ticket exists
                ticket.printTicketInfo();
            }
        }
        for (Ticket ticket : tickets) {
            if (ticket != null) { //checks that a ticket exists
                totalsales += ticket.getPrice();
            }
        }
        System.out.println("The total sales of the tickets are: £" + totalsales);
    }
    // Method to search for a ticket
    private static void searchTicket(Ticket[] tickets) {
        Scanner scanner = new Scanner(System.in);
        char rowLetter;
        int seatNumber = -1;
        int rowIdx = -1;

        // Loop until valid row letter and seat number are provided
        do {
            System.out.println("Input a row (A, B, C, D)");
            rowLetter = scanner.next().toUpperCase().charAt(0);
            
            if (!(rowLetter == 'A' || rowLetter == 'D' || rowLetter == 'B' || rowLetter == 'C') ) {
                System.out.println("Invalid row letter.");
                continue;
            }
            // Determine the row index based on the row letter
            switch (rowLetter) {
                case 'A':
                    rowIdx = 0;
                    break;
                case 'B':
                    rowIdx = 1;
                    break;
                case 'C':
                    rowIdx = 2;
                    break;
                case 'D':
                    rowIdx = 3;
                    break;
            }

        } while (rowIdx == -1); // Repeat if rowIdx is invalid
        
        do {
        // Check if the row letter is valid
        if (rowLetter == 'A' || rowLetter == 'D') {
            System.out.println("Input a seat from 1-14:");
                
        } else if (rowLetter == 'B' || rowLetter == 'C') {
            System.out.println("Input a seat from 1-12:");

        } else {
            System.out.println("Invalid seat number please try again");
        }

            // Read the seat number
            seatNumber = scanner.nextInt();
            
            // Check if the seat number is within the valid range
            if (seatNumber < 1 || seatNumber > 14) {
                System.out.println("Invalid seat number.");
                continue;
            }
        } while (seatNumber >= 13 || seatNumber >= 15);
        
         // Calculate seat index in the array
        int seatIndex = (rowIdx == 0 || rowIdx == 3) ? (seatNumber - 1) : (seatNumber - 1 + ROWS_A_D);


        // Check if the seat is within the valid range
        if (seatIndex >= 0 && seatIndex < seats[rowIdx].length) {
            if (seats[rowIdx][seatIndex] == 'X') {
                int ticketIndex = calculateTicketIndex(rowIdx, seatIndex);
                Ticket ticket = tickets[ticketIndex];
                Person person = ticket.getPerson();
                System.out.println("This seat has been sold out");
                System.out.println("Ticket information: ");
                ticket.printTicketInfo();
                System.out.println("Person Info: ");
                person.printPersonInfo();
            } else {
                System.out.println("This seat is available");
            }
        } else {
            System.out.println("Invalid seat number.");
        }
    }
}
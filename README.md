# Airplane Seat Management System

A Java console application developed for a Software Development II coursework project. The system manages seat reservations for a private aircraft by allowing users to buy seats, cancel seats, search tickets, view the seating plan, calculate ticket sales, and save ticket records to text files.

## Project Overview

The application was designed around a private aircraft seating plan with four rows: A, B, C, and D. Each row contains a different number of seats, and seats have different prices depending on their position. The system uses standard arrays to track whether seats are available or sold.

This project demonstrates core Java programming concepts including object-oriented programming, arrays, user input validation, menu-driven program design, and file handling.

## Key Features

- Buy a seat by entering a row letter and seat number.
- Cancel an existing seat reservation.
- Find the first available seat in row order.
- Display the seating plan using symbols for available and sold seats.
- Store passenger details using a `Person` class.
- Store ticket details using a `Ticket` class.
- Search for a specific ticket and display the linked passenger information.
- Print all sold tickets and calculate the total sales value.
- Save each purchased ticket to a text file using the seat reference as the filename, such as `A2.txt`.

## Technologies Used

- Java
- Object-Oriented Programming
- Standard arrays
- Maven
- File handling
- NetBeans / Java IDE

## Object-Oriented Design

The project uses separate classes to organise the system:

### `Person`

Stores passenger information, including:

- First name
- Surname
- Email address

### `Ticket`

Stores ticket information, including:

- Row
- Seat number
- Ticket price
- Linked `Person` object

This structure keeps passenger data separate from ticket data, making the program easier to maintain and understand.

## How the System Works

When the program starts, the user is shown a menu with several options. The user can choose to buy a seat, cancel a seat, find the first available seat, view the seating plan, print ticket information, search for a ticket, or quit the program.

Seat availability is stored using arrays. A seat marked as available can be purchased, while a seat already marked as sold cannot be booked again. The program also checks that the row and seat number entered by the user are valid before completing an action.

When a ticket is purchased, the program asks for the passenger's details, creates a ticket object, stores it in the ticket array, and saves the ticket information to a text file.

## Example Ticket Output

An example saved ticket file contains:

```text
Ticket Information:
Row: A
Seat Number: 2
Price: £200
Person Information:
Name: Example
Surname: User
Email: example@email.com

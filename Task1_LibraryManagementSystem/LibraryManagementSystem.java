import java.io.*;
import java.util.*;

/**
 * Library Management System
 * A command-line application for managing books in a library.
 *
 * Features:
 *  - Add books
 *  - Remove books
 *  - Search books (by title, author, or ISBN)
 *  - View all books
 *  - Persistent storage using file handling (books.txt)
 *  - Input validation and error handling
 */
public class LibraryManagementSystem {

    // File where book data is stored persistently
    private static final String FILE_NAME = "books.txt";

    // In-memory storage of books
    private static List<Book> library = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadBooksFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    viewAllBooks();
                    break;
                case 5:
                    saveBooksToFile();
                    System.out.println("Data saved successfully. Exiting program... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.\n");
            }
        }
        scanner.close();
    }

    // ----------------------- MENU -----------------------

    private static void printMenu() {
        System.out.println("===================================");
        System.out.println("     LIBRARY MANAGEMENT SYSTEM     ");
        System.out.println("===================================");
        System.out.println("1. Add a Book");
        System.out.println("2. Remove a Book");
        System.out.println("3. Search for a Book");
        System.out.println("4. View All Books");
        System.out.println("5. Save & Exit");
        System.out.println("-----------------------------------");
    }

    // ----------------------- ADD BOOK -----------------------

    private static void addBook() {
        System.out.println("\n--- Add a New Book ---");

        String title = readNonEmptyString("Enter book title: ");
        String author = readNonEmptyString("Enter author name: ");
        String isbn = readUniqueISBN("Enter ISBN number: ");
        int quantity = readPositiveInt("Enter quantity (number of copies): ");

        Book book = new Book(title, author, isbn, quantity);
        library.add(book);

        System.out.println("Book added successfully!\n");
    }

    // ----------------------- REMOVE BOOK -----------------------

    private static void removeBook() {
        System.out.println("\n--- Remove a Book ---");

        if (library.isEmpty()) {
            System.out.println("The library is empty. Nothing to remove.\n");
            return;
        }

        String isbn = readNonEmptyString("Enter the ISBN of the book to remove: ");

        Book bookToRemove = findBookByISBN(isbn);

        if (bookToRemove == null) {
            System.out.println("No book found with ISBN: " + isbn + "\n");
            return;
        }

        System.out.println("Found: " + bookToRemove);
        String confirm = readNonEmptyString("Are you sure you want to remove this book? (yes/no): ");

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            library.remove(bookToRemove);
            System.out.println("Book removed successfully!\n");
        } else {
            System.out.println("Removal cancelled.\n");
        }
    }

    // ----------------------- SEARCH BOOK -----------------------

    private static void searchBook() {
        System.out.println("\n--- Search for a Book ---");

        if (library.isEmpty()) {
            System.out.println("The library is empty. Nothing to search.\n");
            return;
        }

        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");
        int option = readIntInput("Enter your choice: ");

        String keyword;
        List<Book> results = new ArrayList<>();

        switch (option) {
            case 1:
                keyword = readNonEmptyString("Enter title (or part of it): ").toLowerCase();
                for (Book b : library) {
                    if (b.getTitle().toLowerCase().contains(keyword)) {
                        results.add(b);
                    }
                }
                break;
            case 2:
                keyword = readNonEmptyString("Enter author (or part of it): ").toLowerCase();
                for (Book b : library) {
                    if (b.getAuthor().toLowerCase().contains(keyword)) {
                        results.add(b);
                    }
                }
                break;
            case 3:
                keyword = readNonEmptyString("Enter ISBN: ");
                Book b = findBookByISBN(keyword);
                if (b != null) {
                    results.add(b);
                }
                break;
            default:
                System.out.println("Invalid search option.\n");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("No matching books found.\n");
        } else {
            System.out.println("\nSearch Results:");
            printTableHeader();
            for (Book b : results) {
                printBookRow(b);
            }
            System.out.println();
        }
    }

    // ----------------------- VIEW ALL BOOKS -----------------------

    private static void viewAllBooks() {
        System.out.println("\n--- All Books in Library ---");

        if (library.isEmpty()) {
            System.out.println("The library is currently empty.\n");
            return;
        }

        printTableHeader();
        for (Book b : library) {
            printBookRow(b);
        }
        System.out.println();
    }

    private static void printTableHeader() {
        System.out.printf("%-25s %-20s %-15s %-10s%n", "Title", "Author", "ISBN", "Quantity");
        System.out.println("------------------------------------------------------------------");
    }

    private static void printBookRow(Book b) {
        System.out.printf("%-25s %-20s %-15s %-10d%n",
                b.getTitle(), b.getAuthor(), b.getIsbn(), b.getQuantity());
    }

    // ----------------------- HELPER METHODS -----------------------

    private static Book findBookByISBN(String isbn) {
        for (Book b : library) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                return b;
            }
        }
        return null;
    }

    // ----------------------- INPUT VALIDATION -----------------------

    /**
     * Reads an integer from the user, repeating the prompt until valid input is given.
     */
    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a whole number.");
            }
        }
    }

    /**
     * Reads a positive integer (greater than zero) from the user.
     */
    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readIntInput(prompt);
            if (value > 0) {
                return value;
            } else {
                System.out.println("Quantity must be a positive number greater than zero.");
            }
        }
    }

    /**
     * Reads a non-empty string from the user, trimming whitespace.
     */
    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("This field cannot be empty. Please try again.");
            }
        }
    }

    /**
     * Reads an ISBN, ensuring it is non-empty and not already used by another book.
     */
    private static String readUniqueISBN(String prompt) {
        while (true) {
            String isbn = readNonEmptyString(prompt);
            if (findBookByISBN(isbn) != null) {
                System.out.println("A book with this ISBN already exists. Please enter a different ISBN.");
            } else {
                return isbn;
            }
        }
    }

    // ----------------------- FILE HANDLING -----------------------

    /**
     * Loads books from the data file into memory at program startup.
     * If the file does not exist, it starts with an empty library.
     */
    private static void loadBooksFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No existing data file found. Starting with an empty library.\n");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // Expected format: title,author,isbn,quantity
                String[] parts = line.split(",", 4);

                if (parts.length != 4) {
                    System.out.println("Warning: Skipping malformed line " + lineNumber + " in data file.");
                    continue;
                }

                try {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String isbn = parts[2].trim();
                    int quantity = Integer.parseInt(parts[3].trim());

                    library.add(new Book(title, author, isbn, quantity));
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Skipping line " + lineNumber + " due to invalid quantity value.");
                }
            }

            System.out.println("Loaded " + library.size() + " book(s) from " + FILE_NAME + ".\n");

        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
            System.out.println("Starting with an empty library.\n");
        }
    }

    /**
     * Saves all books currently in memory to the data file.
     */
    private static void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book b : library) {
                writer.write(b.getTitle() + "," + b.getAuthor() + "," + b.getIsbn() + "," + b.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
}

/**
 * Represents a single Book entry in the library.
 */
class Book {
    private String title;
    private String author;
    private String isbn;
    private int quantity;

    public Book(String title, String author, String isbn, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Title: " + title + " | Author: " + author + " | ISBN: " + isbn + " | Quantity: " + quantity;
    }
}

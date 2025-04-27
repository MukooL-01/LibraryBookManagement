import java.util.Scanner;

interface LibBooks {
    void initializeBooks();

    void availableBooks();

    void bookIssue(String bookName, String Admission_Number, String issueDate);
}

class Books implements LibBooks {
    int Total_Books = 350;
    int Issued_Books = 0;
    int Remaining_Books = 350;

    String[] Books = { "That Place", "The Love Which Was Fotgotten", "Someone Else" };
    int[] copies = { 50, 50, 50 };

    public void initializeBooks() {
        System.out.println("Books Initialized Sucessfully.");
    }

    public void availableBooks() {
        System.out.println("\nAvaliable Books :");
        for (int i = 0; i < Books.length; i++) {
            System.out.println((i + 1) + ": " + Books[i] + " Copies - " + copies[i]);
        }
        System.out.println("Total Books : " + Total_Books);
        System.out.println("Issued Books : " + Issued_Books);
        System.out.println("Remaining Books : " + Remaining_Books);
    }

    public void bookIssue(String bookName, String Admission_Number, String issueDate) {
        boolean Book_Found = false;
        for (int i = 0; i < Books.length; i++) {
            if (bookName.equalsIgnoreCase(Books[i])) {
                Book_Found = true;
                if (copies[i] > 0) {
                    copies[i]--;
                    Issued_Books++;
                    Remaining_Books--;
                    System.out.println("Book " + Books[i] + " Issued to Admission Number : " + Admission_Number
                            + " On date : " + issueDate);
                } else {
                    System.out.println("Copies Not Avaliable for Book : " + bookName);
                }
                break;
            }
        }
        if (!Book_Found) {
            System.out.println("No Book Found");
        }
    }
}

class Student {
    String Admission_Number;
    int Section;
    String Name;

    Student(String Admission_Number, int Section, String Name) {
        this.Admission_Number = Admission_Number;
        this.Section = Section;
        this.Name = Name;
    }
}

abstract class StudentLogin {
    abstract void addStudent(String Admission_Number, int section, String Name);

    abstract boolean loginStudent(String Admission_Number);

}

class Library extends StudentLogin {
    Books books = new Books();
    Student[] students = new Student[100];
    int studentCount = 0;

    public void addStudent(String Admission_Number, int section, String Name) {
        students[studentCount++] = new Student(Admission_Number, section, Name);
    }

    public boolean loginStudent(String Admission_Number) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].Admission_Number.equals(Admission_Number)) {
                System.out.println("Welcome " + students[i].Name);
                return true;
            }
        }
        return false;
    }

    public void showAvailableBooks() {
        books.availableBooks();
    }

    public void issueBook(String bookName, String Admission_Number, String issueDate) {
        books.bookIssue(bookName, Admission_Number, issueDate);
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        library.books.initializeBooks();

        System.out.println("=== Welcome to Library Management System ===");

        System.out.print("How many students to register? ");
        int numberOfStudents = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numberOfStudents; i++) {
            System.out.println("\nEnter details for student " + (i + 1) + ":");
            System.out.print("Admission Number: ");
            String Admission_Number = scanner.nextLine();
            System.out.print("Section: ");
            int section = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            library.addStudent(Admission_Number, section, name);
        }

        System.out.print("\nEnter your Admission Number to login: ");
        String enteredAdmission_Number = scanner.nextLine();

        if (library.loginStudent(enteredAdmission_Number)) {
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Show Available Books");
                System.out.println("2. Issue a Book");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    library.showAvailableBooks();
                } else if (choice == 2) {
                    System.out.print("Enter the name of the book you want to issue: ");
                    String bookName = scanner.nextLine();
                    System.out.print("Enter date of issue (DD/MM/YYYY): ");
                    String issueDate = scanner.nextLine();
                    library.issueBook(bookName, enteredAdmission_Number, issueDate);
                } else if (choice == 3) {
                    System.out.println("Thank you for visiting the library!");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        } else {
            System.out.println("Login failed. Admission Number not found.");
        }

        scanner.close();
    }
}
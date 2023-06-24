// This program creates a list of students and allows the user to 
// lookup information about a student given the ID number.
// This is meant to loosely simulate what happens at the Student
// Services desk (Kodiak Corner here at Cascadia College).
//
// In real life when a student scans their Student ID card
// the computer reads their Student ID Number from the bar code
// The computer then looks up the information about the student
// and allows the employee to look and and modify the information.
//

import java.util.*;

public class Student_Services_Desk {

    private static int nextSID = 22; // must be greater than any of the Students' IDs that we pre-load
//I went through and deleated some extra spaces!
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        Map<Integer, Student> studentInfo = new TreeMap<Integer, Student>();
        studentInfo.put(21, new Student(21, "Zog", "TheDestroyer", new ArrayList<String>(List.of("BIT 143", "MATH 411", "ENG 120"))));
        studentInfo.put(20,
                new Student(20, "Mary", "Sue", new ArrayList<String>(List.of("BIT 142", "MATH 142", "ENG 100"))));
        studentInfo.put(1,
                new Student(1, "Joe", "Bloggs", new ArrayList<String>(List.of("BIT 115", "MATH 141", "ENG 101"))));
        char choice = 'L'; // anything but 'q' is fine
        while (choice != 'q') {
            System.out.println("\nWhat would you like to do next? ");
            System.out.println("F - Find a specific student");
            System.out.println("L - List all the students (for debugging purposes)");
            System.out.println("A - Add a new student");
            System.out.println("D - Delete an existing student");
            System.out.println("M - Modify an existing student");
            System.out.println("Q - Quit (exit) the program");
            System.out.print("What is your choice?\n(type in the letter & then the enter/return key) ");
            choice = keyboard.nextLine().trim().toLowerCase().charAt(0);
            System.out.println();

            switch (choice) {
                case 'f':
                    System.out.println("Find a student (based on their ID number):\n");
                    // IMPORTANT NOTE: If you call keyboard.nextInt() (or .next(), or anything
                    // except nextLine() )
                    // then you should call keyboard.nextLine() to remove the newline (enter/return
                    // key) that nextInt()
                    // left on the input stream
                    // More info:
                    // https://hyunjileetech.github.io/java/2019/02/27/scan-nextLine()-after-scan-nextInt().html
                    // https://stackoverflow.com/a/11465208/250610
                    //
                    System.out.print("What is the ID number of the student to find? ");
                    int id = keyboard.nextInt();
                    keyboard.nextLine();
                    //this checks to make sure that the id is in the system
                    if(studentInfo.containsKey(id)){
                        System.out.printf("%s, %s (SID=%d)\nClasses:\n", studentInfo.get(id).getLastName(), studentInfo.get(id).getFirstName(), id);
                        //this prints out all the classes with a tab infornt
                        int count = 0;
                        while(count < studentInfo.get(id).getClasses().size()){
                            System.out.println("\t" + studentInfo.get(id).getClasses().get(count));
                            count++;
                        }
                    } else {
                        //just incase there isnt that id number in the map
                        System.out.println("Didn't find a student with ID # " + id);
                    }
                    break;

                case 'l':
                    System.out.println("The following students are registered:");
                    //this loops through all the different values in a map and prints out the information
                    for(Student name: studentInfo.values()){
                        System.out.println(name);
                    }
                    break;

                case 'a':
                    System.out.println("Adding a new student\n");
                    System.out.println("Please provide the following information:");
                    System.out.print("Student's first name? ");
                    //sets the new id number
                    nextSID = nextSID+1;
                    //takes new first name
                    String fName = keyboard.nextLine();
                    //takes new last name
                    System.out.print("Student's last name? ");
                    //makes sure to catch any two word last names
                    String LName = keyboard.nextLine();
                    //makes a list of the new classes
                    System.out.println("Type the name of class, or leave empty to stop.  Press enter/return regardless");
                    //renamed
                    ArrayList<String> newClass = new ArrayList<>();
                    String classes = keyboard.nextLine();
                    while(!classes.isBlank()){ //this makes sure that the user input isn't a blank line
                        newClass.add(classes);
                        System.out.println("Type the name of class, or leave empty to stop.  Press enter/return regardless");
                        classes = keyboard.nextLine();
                    }
                    //now we add all the information to the map
                    studentInfo.put(nextSID, new Student(nextSID, fName, LName, newClass));
                    break;

                case 'd':
                    System.out.println("Deleting an existing student\n");
                    System.out.print("What is the ID number of the student to delete? ");
                    //takes user input
                    id = keyboard.nextInt();
                    keyboard.nextLine();
                    //checks to see if id is part of the map
                    if(studentInfo.containsKey(id)){
                        studentInfo.remove(id); //removes key and value from the map
                        System.out.println("Student account found and removed from the system!");
                    }else{
                        System.out.println("Didn't find a student with ID # " + id);
                    }
                    break;

                case 'm':
                    System.out.println("Modifying an existing student\n");
                    System.out.print("What is the ID number of the student to modify? ");
                    //takes user input
                    id = keyboard.nextInt();
                    keyboard.nextLine();
                    //makes sure the id is part of the the map
                    if(studentInfo.containsKey(id)){
                        System.out.println("Student account found!\nFor each of the following type in the new info or leave empty to keep the existing info.  Press enter/return in both cases.");
                        System.out.print("Student's first name: " + studentInfo.get(id).getFirstName() + " New first name? ");
                        String name = keyboard.nextLine();
                        //making sure the new name isn't a blank line and then replacing the old name with the new name
                        if(!name.isBlank()){
                            studentInfo.get(id).setFirstName(name);
                        }
                        System.out.print("Student's last name: " + studentInfo.get(id).getLastName() + " New last name? ");
                        name = keyboard.nextLine();
                        //making sure the new name isn't a blank line and then replacing the old name with the new name
                        if(!name.isBlank()){
                            studentInfo.get(id).setLastName(name);
                        }
                        System.out.println("Updating class list");
                        System.out.println("Here are the current classes: " + studentInfo.get(id).getClasses());
                        System.out.println("This program will go through all the current classes.");
                        System.out.println("For each class it will print the name of the class and then ask you if you'd like to delete or keep it.");
                        ArrayList<String> removedClass = new ArrayList<>(); //new array to store any classes that want to be deleated
                        //goes through each class in the ist and asks if it wants to be kept of removed
                        for(String className: studentInfo.get(id).getClasses()){
                            System.out.println(className + "\nType d<enter/return> to remove, or just <enter/return> to keep ");
                            String d = keyboard.nextLine();
                            if(d.isBlank()){
                                System.out.println("Keeping " + className + "\n");
                            }else{
                                removedClass.add(className);//adds class name to new list
                                System.out.println("Removing " + className + "\n");
                            }
                        }
                        //goes through and removes the classes after we are done seaching the array
                        for(String className: removedClass){
                            studentInfo.get(id).getClasses().remove(studentInfo.get(id).getClasses().indexOf(className));
                        }
                        //goes through and adds the new classes
                        System.out.println("Type the name of the class you'd like to add, or leave empty to stop.  Press enter/return regardless");
                        classes = keyboard.nextLine();
                        while(!classes.isBlank()){
                            studentInfo.get(id).getClasses().add(classes);
                            System.out.println("Type the name of the class you'd like to add, or leave empty to stop.  Press enter/return regardless");
                            classes = keyboard.nextLine();
                        }
                        //updates the user with the new information
                        System.out.println("Here's the student's new, updated info: " +  studentInfo.get(id));
                    } else{
                        System.out.println("Didn't find a student with ID # " + id);
                    }
                    break;

                case 'q':
                    System.out.println("Thanks for using the program - goodbye!\n");
                    break;

                default:
                    System.out.println("Sorry, but I didn't recognize the option " + choice);
                    break;
            }

        }
    }
}

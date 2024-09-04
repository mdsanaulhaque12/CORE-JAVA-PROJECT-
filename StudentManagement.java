package project.mini.sms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.*;

public class StudentManagement {
    public static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        String fileName = "StudentData1.txt";



        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Student management System");
            System.out.println("1.add student");
            System.out.println("2.remove student");
            System.out.println("3.update student");
            System.out.println("4.display student by name Ascending");
            System.out.println("5.display student by gpa descending ");
            System.out.println("6.read data from file");
            System.out.println("7.exit");
            System.out.println("enter your choice");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    removeStudent(scanner);
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    displayStudentsByName();
                    break;
                case 5:
                    displayStudentsByGpa();
                    break;
                case 6:
                    System.out.println("id\tname\tgpa\tcity\tuni");
                    writeStudentData(fileName);
                    readStudentData(fileName);
                    break;
                case 7:
                    exit=true;
                    writeStudentData(fileName); // Save data to file before exiting
                    System.out.println("thankyou for using student management system");
                    break;
                default :
                    System.out.println("invalid input please try again");
                    break;
            }

        }


    }

    private static void readStudentData(String fileName) {
        try(BufferedReader br= new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");//This splits the string line into an array of substrings wherever a comma , is encountered.

                int id =Integer.parseInt(data[0].trim());// After applying trim(), the leading and trailing spaces are removed, but internal spaces are preserved.
                String name=data[1].trim();
                double gpa = Double.parseDouble(data[2].trim());
                String city =data[3].trim();
                String university="MIT";

                Student student = new Student(id,name,gpa,city,university);
                students.add(student);
            }
            System.out.println("student data loaded successfully");
            displayStudents(students);
        }catch(IOException e){
            System.out.println("an error occurred while reading student data"+ e.getMessage());
        }catch(NumberFormatException e){
            System.out.println("invalid number format in the student data:"+e.getMessage());
        }
    }

    public static void addStudent(Scanner scanner){
        System.out.println("----add student----");
        System.out.println("enter id :");
        int id = scanner.nextInt();
        scanner.nextLine();//consume new line character
        System.out.println("enter name:");
        String name = scanner.nextLine();
        System.out.println("enter gpa:");
        double gpa=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("enter city:");
        String city=scanner.nextLine();

        Student student=new Student(id,name,gpa,city,"MIT");
        students.add(student);
        System.out.println("student added successfully");
    }

    public static void removeStudent(Scanner scanner){
        System.out.println("---- remove student-----");
        System.out.println("Enter id of student to remove");
        int id = scanner.nextInt();
        boolean found=false;
        Iterator<Student> iterator=students.iterator();
        while(iterator.hasNext()){
            Student student= iterator.next();
            if(student.getId()==id){
                iterator.remove();
                found=true;
                break;
            }
        }
        if(found){
            System.out.println("student removed successfully");

        }else{
            System.out.println("student not found");
        }
    }

    //-----------------------------------------------

    public static void updateStudent(Scanner scanner){
        System.out.println("----update student-------");
        System.out.println("enter the id of student to update :");
        int id =scanner.nextInt();

        boolean found=false;
        for (Student student:students) {
            if(student.getId()==id){
                System.out.println("Set name");
                String name=scanner.next();
                if(!name.isEmpty()){
                    student.setName(name);
                }
                System.out.println("enter gpa");
                String gpaInput=scanner.next();
                if(!gpaInput.isEmpty()){
                    double gpa=Double.parseDouble(gpaInput);
                    student.setGpa(gpa);
                }
                System.out.println("enter city");
                String city=scanner.next();
                if(!city.isEmpty()){
                    student.setCity(city);
                }
                found=true;
                break;
            }
        }
        if(found){
            System.out.println("student updated successfully");
        }else System.out.println("student not found");

    }
    public static void displayStudentsByName(){
        System.out.println("----student by name ascending------");
        List<Student> sortedStudents = new ArrayList<>(students);
        Collections.sort(sortedStudents,Comparator.comparing(Student::getName));
        System.out.println("id\tname\tgpa\tcity\tuniversity");
        displayStudents(sortedStudents);
    }
    public static void displayStudentsByGpa(){
        System.out.println("----student by gpa descending------");
        List<Student> sortedStudents = new ArrayList<>(students);
        Collections.sort(sortedStudents,Comparator.comparing(Student::getGpa).reversed());
        System.out.println("id\tname\tgpa\t\tcity\tuniversity");
        displayStudents(sortedStudents);
    }

    public static void displayStudents(List<Student> students){
        for (Student student :students) {
            System.out.println(student);
        }
    }


    private static void writeStudentData(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," + student.getGpa() + "," + student.getCity() + "\n");
            }
            System.out.println("Student data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing student data to file: " + e.getMessage());
        }
    }


}

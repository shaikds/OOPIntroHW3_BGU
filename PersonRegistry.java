package assignment3;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class PersonRegistry {
    private boolean corrupted;
    private MyArrayList<Person> personList;

    public PersonRegistry() {
        this.personList = new MyArrayList<>();
        this.corrupted = false;
    }

    public PersonRegistry(String filePath) {
        String line;
        this.personList = new MyArrayList<>();
        this.corrupted = false;
        if (filePath.endsWith(".bin")) {
            // Read serialized ArrayList<Person> from binary file
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                this.corrupted = true;
            }
            ObjectInputStream objectIn = null;
            try {
                objectIn = new ObjectInputStream(fileIn);
            } catch (IOException e) {
                this.corrupted = true;
            }
            try {
                personList = (MyArrayList<Person>) objectIn.readObject();
                objectIn.close();
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                this.corrupted = true;
            }

        } else if (filePath.endsWith(".csv")) { // .csv files
            try {
                Scanner inFile = new Scanner(new File(filePath));
                while (inFile.hasNextLine()) {
                    line = inFile.nextLine();
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        String name = data[0].trim();
                        int age = Integer.parseInt(data[1].trim());
                        double luckyNumber = Double.parseDouble(data[2].trim());
                        Person person = new Person(name, age, luckyNumber);
                        personList.add(person);
                    }   // IF
                }   // WHILE loop
                inFile.close(); // close if after
            } catch (FileNotFoundException e) {
                this.corrupted = true;
            }
        }   //ELSE IF
        else this.corrupted = true; // IF NOT THIS AND NOT THIS IT'S A CORRUPTED FILE!
    }

    public PersonRegistry(Iterable<? extends Person> persons) {
        this.corrupted = false;
        this.personList = new MyArrayList<>();
        for (Person person : persons) {
            if (person != null) {
                personList.add(person);
            }
        }
    }


    public PersonRegistry(Person[] persons) {
        this.corrupted = false;
        this.personList = new MyArrayList<>();
        for (Person person : persons) {
            if (person != null) personList.add(person);
        }
    }

    public void add(Person person) {
        personList.add(person);
    }

    public Person get(int index) {
        if (personList!=null &&personList.size() > index && index >= 0&& personList.get(index)!=null) {  // Range check.
            return personList.get(index);
        }
        this.corrupted = true ;
        throw new IndexOutOfBoundsException();
    }

    public boolean writeCSV(String filePath) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));

            for (Person person : personList) {
                if (person == null) continue;
                String name = person.getName();
                int age = person.getAge();
                double luckyNumber = person.getLuckyNumber();
                writer.printf("%s,%d,%.1f\n", name, age, luckyNumber);
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public int maxAge() {
        int maxAge = Integer.MIN_VALUE;
        for (Person person : personList) {
            if (person.getAge() > maxAge) maxAge = person.getAge();
        }
        return maxAge;
    }

    public double meanLuckyNumber() {
        double sum = 0;
        int counter = 0;
        for (Person person : personList) {
            sum += person.getLuckyNumber();
            counter++;
        }
        return sum / counter;
    }

    public MyArrayList<String> uniqueNames() {
        MyArrayList<String> uniqueList = new MyArrayList<>();
        for (Person person : personList) {
            if (uniqueList.count(person.getName()) == 0) {
                uniqueList.add(person.getName());
            }
        }
        return uniqueList;
    }

    public int count(Person person) {
        return personList.count(person);
    }

    public boolean isCorrupted() {
        return this.corrupted;
    }

//    public void writeBin(String filePath) {
//        // Assume you have populated the personList with Person objects
//
//        String fileName = "output.bin";
//
//        try {
//            FileOutputStream fileOut = new FileOutputStream(fileName);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//
//            objectOut.writeObject(personList);
//            objectOut.close();
//            fileOut.close();
//
//            System.out.println("Serialized MyArrayList<Person> is saved in " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}



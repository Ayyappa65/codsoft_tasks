package codsoft;

import java.util.ArrayList;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int currentEnrollment;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.currentEnrollment = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void enrollStudent() {
        if (currentEnrollment < capacity) {
            currentEnrollment++;
        } else {
            System.out.println("Course " + courseCode + " is already full. Cannot enroll more students.");
        }
    }

    public void removeStudent() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
        } else {
            System.out.println("Course " + courseCode + " does not have any enrolled students to remove.");
        }
    }

    @Override
    public String toString() {
        return "Course: " + courseCode + "\nTitle: " + title + "\nDescription: " + description + "\nSchedule: " + schedule +
                "\nCapacity: " + capacity + "\nCurrent Enrollment: " + currentEnrollment + "\n";
    }
}

class Student {
    private int studentId;
    private String name;
    private Course[] registeredCourses;
    private int numCoursesRegistered;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new Course[5]; // Assuming a student can register for up to 5 courses
        this.numCoursesRegistered = 0;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public Course[] getRegisteredCourses() {
        return registeredCourses;
    }

    public int getNumCoursesRegistered() {
        return numCoursesRegistered;
    }

    public boolean registerForCourse(Course course) {
        if (numCoursesRegistered < registeredCourses.length) {
            registeredCourses[numCoursesRegistered++] = course;
            course.enrollStudent();
            return true;
        } else {
            System.out.println("Cannot register for more courses. Maximum limit reached.");
            return false;
        }
    }

    public boolean dropCourse(Course course) {
        for (int i = 0; i < numCoursesRegistered; i++) {
            if (registeredCourses[i].getCourseCode().equals(course.getCourseCode())) {
                // Found the course, remove it from registered courses array
                registeredCourses[i] = registeredCourses[numCoursesRegistered - 1];
                registeredCourses[numCoursesRegistered - 1] = null;
                numCoursesRegistered--;
                course.removeStudent();
                return true;
            }
        }
        System.out.println("Course " + course.getCourseCode() + " not found in registered courses.");
        return false;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + name + "\nNumber of Courses Registered: " + numCoursesRegistered + "\n";
    }
}


public class CourseRegistrationSystem {
    private ArrayList<Course> courses;
    private ArrayList<Student> students;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayCourseList() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void displayStudentList() {
        System.out.println("Registered Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void registerStudentForCourse(int studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);

        if (student == null || course == null) {
            System.out.println("Student or course not found.");
            return;
        }

        boolean success = student.registerForCourse(course);
        if (success) {
            System.out.println("Student " + student.getName() + " registered successfully for course " + course.getTitle());
        }
    }

    public void dropStudentFromCourse(int studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);

        if (student == null || course == null) {
            System.out.println("Student or course not found.");
            return;
        }

        boolean success = student.dropCourse(course);
        if (success) {
            System.out.println("Student " + student.getName() + " dropped successfully from course " + course.getTitle());
        }
    }

    private Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Create some courses
        Course c1 = new Course("CS101", "Introduction to Computer Science", "Fundamentals of programming and algorithms", 30, "Mon/Wed 10:00 AM - 11:30 AM");
        Course c2 = new Course("MAT201", "Linear Algebra", "Basic linear algebra concepts and applications", 25, "Tue/Thu 1:00 PM - 2:30 PM");
        Course c3 = new Course("ENG301", "English Literature", "Study of classic and modern literature", 35, "Mon/Fri 9:00 AM - 10:30 AM");

        // Create course registration system
        CourseRegistrationSystem registrationSystem = new CourseRegistrationSystem();

        // Add courses to the registration system
        registrationSystem.addCourse(c1);
        registrationSystem.addCourse(c2);
        registrationSystem.addCourse(c3);

        // Create some students
        Student s1 = new Student(1001, "Alice");
        Student s2 = new Student(1002, "Bob");
        Student s3 = new Student(1003, "Charlie");

        // Add students to the registration system
        registrationSystem.addStudent(s1);
        registrationSystem.addStudent(s2);
        registrationSystem.addStudent(s3);

        // Display available courses
        registrationSystem.displayCourseList();

        // Register students for courses
        registrationSystem.registerStudentForCourse(1001, "CS101");
        registrationSystem.registerStudentForCourse(1002, "MAT201");
        registrationSystem.registerStudentForCourse(1003, "ENG301");

        // Display registered students
        registrationSystem.displayStudentList();

        // Drop a student from a course
        registrationSystem.dropStudentFromCourse(1002, "MAT201");

        // Display updated course and student information
        registrationSystem.displayCourseList();
        registrationSystem.displayStudentList();
    }
}


import java.util.*;

class User {
    private String username;
    private String password;
    private String name;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void updateProfile(String newName) {
        this.name = newName;
        System.out.println("✅ Profile updated successfully.");
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("✅ Password updated successfully.");
    }

    public String getName() {
        return this.name;
    }
}

public class OnlineExaminationSystem {
    static Scanner scanner = new Scanner(System.in);
    static User user = new User("user123", "pass123", "Anisha");
    static boolean isLoggedIn = false;

    public static void main(String[] args) {
        System.out.println("🎓 Welcome to Online Examination System");

        if (login()) {
            isLoggedIn = true;
            showMenu();
        } else {
            System.out.println("❌ Too many failed login attempts.");
        }
    }

    public static boolean login() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter Username: ");
            String uname = scanner.nextLine();
            System.out.print("Enter Password: ");
            String pwd = scanner.nextLine();

            if (user.login(uname, pwd)) {
                System.out.println("✅ Login Successful. Welcome, " + user.getName() + "!");
                return true;
            } else {
                System.out.println("❌ Invalid credentials. Try again.");
                attempts++;
            }
        }
        return false;
    }

    public static void showMenu() {
        while (isLoggedIn) {
            System.out.println("\n🔸 Main Menu 🔸");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    updatePassword();
                    break;
                case 3:
                    startExam();
                    break;
                case 4:
                    logout();
                    break;
                default:
                    System.out.println("❗ Invalid option.");
            }
        }
    }

    public static void updateProfile() {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        user.updateProfile(newName);
    }

    public static void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        user.updatePassword(newPassword);
    }

    public static void logout() {
        System.out.println("🔒 Logged out successfully. Goodbye!");
        isLoggedIn = false;
    }

    public static void startExam() {
        String[] questions = {
                "Q1: Java is a:\n1. Programming Language\n2. Database\n3. Operating System\n4. Web Browser",
                "Q2: Which keyword is used to inherit a class in Java?\n1. implement\n2. inherits\n3. extends\n4. interface",
                "Q3: What is the size of int in Java?\n1. 2 bytes\n2. 4 bytes\n3. 8 bytes\n4. Depends on system"
        };
        int[] correctAnswers = {1, 3, 2};
        int[] userAnswers = new int[questions.length];

        System.out.println("\n📝 Exam Started. You have 30 seconds to complete.\n");

        Timer timer = new Timer();
        TimerTask autoSubmitTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n⏰ Time is up! Submitting your exam automatically...");
                submitExam(userAnswers, correctAnswers);
                timer.cancel();
                isLoggedIn = false;
                System.exit(0);
            }
        };
        timer.schedule(autoSubmitTask, 30000); // 30-second timer

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("Your answer: ");
            if (scanner.hasNextInt()) {
                userAnswers[i] = scanner.nextInt();
            } else {
                userAnswers[i] = 0;
                scanner.next();
            }
        }

        timer.cancel();
        submitExam(userAnswers, correctAnswers);
    }

    public static void submitExam(int[] userAnswers, int[] correctAnswers) {
        int score = 0;
        System.out.println("\n✅ Submitting your exam...");
        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }
        System.out.println("🎯 You scored " + score + " out of " + correctAnswers.length);
    }
}
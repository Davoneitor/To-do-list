package com.example.todo;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add task");
            System.out.println("2. List tasks");
            System.out.println("3. Remove task");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (option) {
                    case 1:
                        System.out.print("Enter task description: ");
                        String description = scanner.nextLine();
                        Task task = new Task(0, description, false);
                        manager.addTask(task);
                        break;
                    case 2:
                        List<Task> tasks = manager.listTasks();
                        for (Task t : tasks) {
                            System.out.println("ID: " + t.getId() + ", Description: " + t.getDescription() + ", Completed: " + t.isCompleted());
                        }
                        break;
                    case 3:
                        System.out.print("Enter task ID to remove: ");
                        int id = scanner.nextInt();
                        manager.removeTask(id);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

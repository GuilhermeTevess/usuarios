package com.cadastro;

import java.util.List;
import java.util.Scanner;

import com.cadastro.config.DatabaseInitializer;
import com.cadastro.model.User;
import com.cadastro.repository.UserRepository;

public class App {

    public static void main(String[] args) {

        // garante que a tabela exista
        DatabaseInitializer.initialize();

        UserRepository repo = new UserRepository();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n=== USER MANAGEMENT ===");
            System.out.println("1 - Add new user");
            System.out.println("2 - List all users");
            System.out.println("3 - Delete user by ID");
            System.out.println("4 - Update user by ID");
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    // cadastrar usuário
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Age: ");
                    int age;
                    try {
                        age = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age. User not saved.");
                        break;
                    }

                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setAge(age);

                    repo.save(newUser);
                    break;

                case "2":
                    // listar usuários
                    List<User> users = repo.findAll();
                    System.out.println("\n--- Users in database ---");
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        for (User u : users) {
                            System.out.println(
                                    u.getId() + " | "
                                    + u.getName() + " | "
                                    + u.getEmail() + " | "
                                    + u.getAge()
                            );
                        }
                    }
                    break;

                case "3":
                    // deletar usuário
                    System.out.print("Enter user ID to delete: ");
                    try {
                        int idToDelete = Integer.parseInt(scanner.nextLine());
                        repo.deleteById(idToDelete);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                    break;
                case "4":
                    //atualizar usuário
                    System.out.print("Enter the ID of the user to update: ");
                    int updateId;

                    try {
                        updateId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                        break;
                    }

                    // Coletar novos dados
                    System.out.print("New name: ");
                    String newName = scanner.nextLine();

                    System.out.print("New email: ");
                    String newEmail = scanner.nextLine();

                    System.out.print("New age: ");
                    int newAge;

                    try {
                        newAge = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age.");
                        break;
                    }

                    // Criar o objeto User com os novos dados
                    User updatedUser = new User();
                    updatedUser.setId(updateId);
                    updatedUser.setName(newName);
                    updatedUser.setEmail(newEmail);
                    updatedUser.setAge(newAge);

                    repo.updateUser(updatedUser);
                    break;

                case "0":
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}

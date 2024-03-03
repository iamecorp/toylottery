import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void decreaseQuantity() {
        if (quantity > 0)
            quantity--;
    }
}

public class ToyShop {
    private List<Toy> toys = new ArrayList<>();

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateToyWeight(int toyId, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(newWeight);
                return;
            }
        }
        System.out.println("Такой игрушки не найдено.");
    }

    public void drawToy() {
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();
        double randomWeight = random.nextDouble() * totalWeight;

        double currentWeight = 0;
        for (Toy toy : toys) {
            currentWeight += toy.getWeight();
            if (randomWeight <= currentWeight) {
                System.out.println("Вы выиграли игрушку: " + toy.getName());
                toy.decreaseQuantity();
                writeToFile(toy);
                return;
            }
        }
    }

    private void writeToFile(Toy toy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prize.txt", true))) {
            writer.write(toy.getName() + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ToyShop toyShop = new ToyShop();
        toyShop.addToy(new Toy(1, "Мяч", 10, 30));
        toyShop.addToy(new Toy(2, "Кукла", 5, 20));
        toyShop.addToy(new Toy(3, "Машинка", 8, 25));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Розыгрыш игрушки");
            System.out.println("2. Выход");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    toyShop.drawToy();
                    break;
                case 2:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }
}

package org.example;

import org.example.models.Category;
import org.example.models.Product;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        int action = 0;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("0 - Вихід");
            System.out.println("1 - Додати категорію");
            System.out.println("2 - Показати всі категорії");
            System.out.println("3 - Редагувати категорію");
            System.out.println("4 - Видалити категорію");
            System.out.println("5 - Додати продукт");
            System.out.println("6 - Показати всі продукти");
            System.out.println("7 - Редагувати продукт");
            System.out.println("8 - Видалити продукт");
            System.out.println("->_");
            action = in.nextInt();
            switch (action) {
                case 1:
                    AddCategory();
                    break;
                case 2:
                    ShowCategories();
                    break;
                case 3:
                    EditCategory();
                    break;
                case 4:
                    DeleteCategory();
                    break;
                case 5:
                    AddProduct();
                    break;
                case 6:
                    ShowProducts();
                    break;
                case 7:
                    EditProduct();
                    break;
                case 8:
                    DeleteProduct();
                    break;
                case 0:
                    System.out.println("До зустрічі!!!");
                    break;
                default:
                    System.out.println("Щось пішло не так...");
            }
        }while(action!=0);
    }

    private static void AddCategory() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        Scanner in = new Scanner(System.in);
        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Category category = new Category();

            System.out.println("Вкажіть назву: ");
            String text = in.nextLine();
            category.setName(text);
            System.out.println("Вкажіть фото: ");
            String img = in.nextLine();
            category.setImage(img);
            category.setDateCreated(calendar.getTime());

            context.save(category);
            context.getTransaction().commit();
        }
    }

    private static void ShowCategories() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session context = sf.openSession()) {
            context.beginTransaction();
            List<Category> list = context.createQuery("from Category", Category.class).getResultList();
            for (Category category : list) {
                System.out.println("Category: " + category);
            }
            context.getTransaction().commit();
        }
    }

    private static void EditCategory() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Calendar calendar = Calendar.getInstance();
        Scanner in = new Scanner(System.in);
        System.out.println("Введіть номер категорії яку хочете відредагувати: ");
        int q = in.nextInt();
        in.nextLine();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Category category = context.get(Category.class, q);

            System.out.println("Вкажіть нову назву: ");
            String text = in.nextLine();
            category.setName(text);
            System.out.println("Вкажіть нове фото: ");
            String img = in.nextLine();
            category.setImage(img);

            context.update(category);
            context.getTransaction().commit();
        }
    }

    private static void DeleteCategory() {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Scanner in = new Scanner(System.in);
        System.out.println("Введіть номер категорії яку хочете видалити: ");
        int q = in.nextInt();
        in.nextLine();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Category category = context.get(Category.class, q);

            context.delete(category);

            context.getTransaction().commit();
        }
    }

    private static void AddProduct() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Scanner in = new Scanner(System.in);
        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Product product = new Product();

            System.out.println("Вкажіть назву: ");
            String text = in.nextLine();
            product.setName(text);
            System.out.println("Вкажіть опис: ");
            String desc = in.nextLine();
            product.setDescription(desc);
            System.out.println("Вкажіть ціну: ");
            double price = in.nextDouble();
            product.setPrice(price);
            System.out.println("Вкажіть id категорії");
            Category category = new Category();
            category.setId(in.nextInt());
            product.setCategory(category);

            context.save(product);
            context.getTransaction().commit();
        }
    }

    private static void ShowProducts() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession()) {
            context.beginTransaction();
            List<Product> list = context.createQuery("from Product", Product.class)
                    .getResultList();
            for (Product product : list) {
                System.out.println("Product: " + product);
            }
            context.getTransaction().commit();
        }
    }

    private static void EditProduct() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Scanner in = new Scanner(System.in);
        System.out.println("Введіть номер продукту яку хочете відредагувати: ");
        int q = in.nextInt();
        in.nextLine();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Product product = context.get(Product.class, q);

            System.out.println("Вкажіть нову назву: ");
            String text = in.nextLine();
            product.setName(text);
            System.out.println("Вкажіть новий опис: ");
            String desc = in.nextLine();
            product.setDescription(desc);
            System.out.println("Вкажіть нову ціну: ");
            int price = in.nextInt();
            in.nextLine();
            product.setPrice(price);
            System.out.println("Вкажіть нову категорію: ");
            int id = in.nextInt();
            in.nextLine();
            Category category = new Category();
            category.setId(id);
            product.setCategory(category);

            context.update(product);
            context.getTransaction().commit();
        }
    }

    private static void DeleteProduct() {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Scanner in = new Scanner(System.in);
        System.out.println("Введіть номер продукту яку хочете видалити: ");
        int q = in.nextInt();
        in.nextLine();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Product product = context.get(Product.class, q);

            context.delete(product);

            context.getTransaction().commit();
        }
    }
}

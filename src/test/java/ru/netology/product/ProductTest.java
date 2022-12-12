package ru.netology.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {
    private ProductRepo repo = new ProductRepo();
    private ProductManager manager = new ProductManager(repo);

    Product book1 = new Book(55, "Письма о добром и прекрасном", 300, "Дмитрий Сергеевич Лихачев");
    Product book2 = new Book(37, "Выбор", 550, "Эдит Ева Эгер");
    Product book3 = new Book(8, "Письма о любви от великих людей", 500, "Урсула Дойль");
    Product book4 = new Book(78, "Цифровой минимализм", 474, "Кэл Ньюпорт");
    Product smartphone1 = new Smartphone(22, "Huawei nova 10 E", 20500, "Huawei");
    Product smartphone2 = new Smartphone(45, "Samsung A51", 15300, "Samsung");
    Product smartphone3 = new Smartphone(65, "Sony Xperia Z", 19000, "Sony");
    Product smartphone4 = new Smartphone(32, "IPhone 14", 20149, "Apple");

    public void set() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(smartphone1);
        manager.add(smartphone2);
        manager.add(smartphone3);
        manager.add(smartphone4);
    }

    // TESTS FOR MANAGER

    @Test    // Добавить новый товар
    public void addNewProduct() {
        manager.add(book2);

        Product[] expected = new Product[]{book2};
        Product[] actual = repo.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Поиск телефона по названию
    public void nameSmartphoneSearch() {
        set();

        Product[] expected = new Product[]{smartphone3};
        Product[] actual = manager.searchBy("Sony Xperia Z");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Поиск книги по названию
    public void nameBookSearch() {
        set();

        Product[] expected = new Product[]{book2};
        Product[] actual = manager.searchBy("Выбор");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Поиск отсутствующей книги
    public void findNonAddedBookByName() {
        Product[] expected = {};
        Product[] actual = manager.searchBy("Гроза");
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Поиск нескольких книг по названию
    public void shouldFindMultipleBooksName() {
        set();

        Product[] expected = {book1, book3};
        Product[] actual = manager.searchBy("Письма");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Поиск книги по названию
    public void findBookByName() {
        boolean expected = true;
        boolean actual = manager.matches(book3, "Письма о любви от великих людей");
        Assertions.assertEquals(expected, actual);
    }

    @Test // Негативный поиск книги по автору
    public void NegativeFindBookByAuthor() {
        boolean expected = false;
        boolean actual = manager.matches(book1, "Иванов");
        Assertions.assertEquals(expected, actual);
    }

    @Test // Позитивный поиск книги по автору
    public void PositiveFindBookByAuthor() {
        boolean expected = true;
        boolean actual = manager.matches(book3, "Урсула Дойль");
        Assertions.assertEquals(expected, actual);
    }


    @Test // Позитивный поиск телефона по названию
    public void PositiveFindSmartphoneByName() {
        boolean expected = true;
        boolean actual = manager.matches(smartphone1, "Huawei nova 10 E");
        Assertions.assertEquals(expected, actual);
    }

    @Test // Негативный поиск телефона по названию
    public void NegativeFindSmartphoneByName() {
        boolean expected = false;
        boolean actual = manager.matches(smartphone1, "телефон");
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void FindSmartphoneByManufactureNegative() {
        boolean expected = false;
        boolean actual = manager.matches(smartphone3, "Apple");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FindSmartphoneByManufacturePositive() {
        boolean expected = true;
        boolean actual = manager.matches(smartphone4, "Apple");
        Assertions.assertEquals(expected, actual);
    }

    // TESTS FOR REPOSITORY

    @Test     // добавить одну книгу
    public void shouldAddOne() {
        repo.save(book1);

        Product[] expected = new Product[]{book1};
        Product[] actual = repo.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }


    @Test // найти пустой
    public void findEmpty() {
        Product[] expected = {};
        Product[] actual = repo.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Добавить только книги
    public void AddOnlyBooks() {
        repo.save(book1);
        repo.save(book2);
        repo.save(book3);
        repo.save(book4);

        Product[] expected = {book1, book2, book3, book4};
        Product[] actual = repo.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // Добавить только смартфоны
    public void AddOnlySmartphone() {
        repo.save(smartphone1);
        repo.save(smartphone2);
        repo.save(smartphone3);
        repo.save(smartphone4);

        Product[] expected = {smartphone1, smartphone2, smartphone3, smartphone4};
        Product[] actual = repo.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test     // успешность удаления существующего элемента
    public void shouldDeleteOne() {
        repo.save(smartphone2);

        repo.removeById(45);

        Product[] expected = new Product[]{};
        Product[] actual = repo.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test // NotFoundException при попытке удаления несуществующего элемента
    public void removeNotExistingProductFromArray() {

        repo.save(book1);
        repo.save(book2);
        repo.save(book3);
        repo.save(book4);

        Assertions.assertThrows(NotFoundException.class, () -> {
            repo.removeById(9);
        });
    }

}
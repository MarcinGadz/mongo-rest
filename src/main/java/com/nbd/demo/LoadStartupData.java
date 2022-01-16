package com.nbd.demo;

import com.nbd.demo.dao.BookRepository;
import com.nbd.demo.dao.UserRepository;
import com.nbd.demo.model.Book;
import com.nbd.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
public class LoadStartupData implements ApplicationRunner {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            addUsers();
        }
        if (bookRepository.findAll().isEmpty()) {
            addBooks();
        }
    }

    public void addUsers() {
        String[] firstNames = new String[]{"John", "Jan", "Krzysztof", "Tadzik", "Halina", "Danuta"};
        String[] lastNames = new String[]{"Doe", "Kowalski", "Kozak", "Tadziszewski", "Testowa", "Nierelacyjna"};
        LocalDate[] dates = new LocalDate[]{
                LocalDate.EPOCH,
                LocalDate.of(1988, 2, 10),
                LocalDate.of(1990, 1, 23),
                LocalDate.of(1995, 5, 17),
                LocalDate.of(2003, 11, 1),
                LocalDate.of(2000, 8, 29)
        };
        for (int i = 0; i < firstNames.length; i++) {
            User u = new User();
            u.setId(UUID.randomUUID());
            u.setFirstName(firstNames[i]);
            u.setLastName(lastNames[i]);
            u.setBirthDate(dates[i]);
            userRepository.save(u);
        }
    }

    public void addBooks() {
        LocalDate[] dates = new LocalDate[]{
                LocalDate.EPOCH,
                LocalDate.of(1988, 2, 10),
                LocalDate.of(1990, 1, 23),
                LocalDate.of(1995, 5, 17),
                LocalDate.of(2003, 11, 1),
                LocalDate.of(2000, 8, 29)
        };
        String[] isbns = new String[]{"99921-58-10-7", "9971-5-0210-0", "80-902734-1-6",
        "85-359-0277-5", "1-84356-028-3", "0-85131-041-9"};
        String[] titles = new String[]{"Test book", "Really cool book", "Super book",
                "Nice", "Dziady cz. III", "Pan Tadeusz"};
        Float[] prices = new Float[]{23.99f, 19.49f, 9.99f, 45.00f, 39.90f, 28.90f};
        for (int i = 0; i < dates.length; i++) {
            Book b = new Book();
            b.setTitle(titles[i]);
            b.setPrice(prices[i]);
            b.setIsbn(isbns[i]);
            b.setPublishingDate(dates[i]);
            bookRepository.save(b);
        }
    }
}

package com.nbd.demo.api;

import com.nbd.demo.LoadStartupData;
import com.nbd.demo.dao.UserRepository;
import com.nbd.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    LoadStartupData helper;

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll() {
        userRepo.deleteAll();
        helper.addUsers();
        return ResponseEntity.ok("Reloaded all users");
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findByID(@PathVariable UUID id) {
        //Returns user with specified id or null if such user doesn't exist
        User u = userRepo.findById(id).orElse(null);
        if (u == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        User toDelete = userRepo.findById(id).orElse(null);
        if (toDelete == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        userRepo.delete(toDelete);
        return new ResponseEntity(toDelete, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User u = userRepo.findById(id).orElse(null);
        if (u == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        u.setBirthDate(user.getBirthDate());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        userRepo.save(u);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}

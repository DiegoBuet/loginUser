    package com.applaudo.createUser.controller;

    import com.applaudo.createUser.exeptions.InvalidEmailFormatException;
    import com.applaudo.createUser.model.entity.User;
    import com.applaudo.createUser.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;


    @Controller
    public class UserController{

        @Autowired
        private UserService userService;

        @GetMapping("/create")
        public ResponseEntity<User> showUserRegistrationForm() {
            User user = new User();
            return ResponseEntity.ok(user);
        }

        @PostMapping("/create")
        public ResponseEntity<String> saveUser(@RequestBody User user) {

            if (isValidPhoneNumber(user.getPhoneNumber())) {
                user.setPhoneNumber(formatPhoneNumber(user.getPhoneNumber()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
            }

            if (!isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format");
            }

            if (!isValidPassword(user.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
            }

            User existingUser = userService.findUserByEmail(user.getEmail());
            if (existingUser != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered");
            }

            userService.saveUser(user);
            return ResponseEntity.ok("User created successfully");
        }

        @PostMapping("/update/{id}")
        public ResponseEntity<String> updateUser(
                @PathVariable Long id,
                @RequestBody User updatedUser
        ) {
            User currentUser = userService.findUserById(id);

            if (!currentUser.getEmail().equals(updatedUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email cannot be modified");
            }


            if (isValidPhoneNumber(updatedUser.getPhoneNumber())) {
                currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
            }


            currentUser.setFirstName(updatedUser.getFirstName());
            currentUser.setLastName(updatedUser.getLastName());

            userService.updateUser(currentUser);

            return ResponseEntity.ok("User with ID " + id + " has been updated");
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted");
        }

        @GetMapping("/edit/{id}")
        public ResponseEntity<User> showEditForm(@PathVariable Long id) {
            User user = userService.findUserById(id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            user.setPhoneNumber(extractLast8Digits(user.getPhoneNumber()));

            return ResponseEntity.ok(user);
        }

        @GetMapping("/list")
        public ResponseEntity<List<User>> listUsers() {
            List<User> userList = userService.listUsers();
            if (userList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(userList);
        }


        private boolean isValidEmail(String email) {

            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email.matches(emailRegex);
        }

        private boolean isValidPhoneNumber(String phoneNumber) {

            String phoneRegex = "\\d{8}";
            return phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.matches(phoneRegex);
        }

        private String formatPhoneNumber(String phoneNumber) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                return "+503 " + phoneNumber;
            }
            return "";
        }

        private String extractLast8Digits(String phoneNumber) {

            return phoneNumber.replaceAll("[^0-9]", "").substring(Math.max(0, phoneNumber.length() - 8));
        }

        private boolean isValidPassword(String password, String storedPassword) {

            return storedPassword.equals(password);
        }

        @ExceptionHandler(InvalidEmailFormatException.class)
        public String handleInvalidEmailFormat(Model model) {
            model.addAttribute("emailError", true);
            return "create_users";
        }

    }

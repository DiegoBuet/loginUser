    package com.applaudo.createUser.contoller;

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
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;


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
            // Verificar si el correo electrónico ya está registrado
            User existingUser = userService.findUserByEmail(user.getEmail());
            if (existingUser != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered");
            }

            // Validar la contraseña
            if (!isValidPassword(user.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
            }

            // Validar y formatear el número de teléfono
            String phoneNumber = user.getPhoneNumber();
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                if (!isValidPhoneNumber(phoneNumber)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
                }
                // Formatear el número de teléfono antes de guardarlo
                String formattedPhoneNumber = formatPhoneNumber(phoneNumber);
                user.setPhoneNumber(formattedPhoneNumber);
            } else {
                // El número de teléfono está vacío, no aplicamos el formato
                user.setPhoneNumber("");
            }
            // Si todas las validaciones pasan, guarda el usuario en la base de datos
            userService.saveUser(user);
            return ResponseEntity.ok("User created successfully");
        }


        @PostMapping("/update/{id}")
        public ResponseEntity<String> updateUser(
                @PathVariable Long id,
                @RequestBody User updatedUser
        ) {
            User currentUser = userService.findUserById(id);

            // Verificar si el correo electrónico está siendo modificado
            if (!currentUser.getEmail().equals(updatedUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email cannot be modified");
            }

            // Verificar y actualizar el número de teléfono
            String updatedPhoneNumber = updatedUser.getPhoneNumber();
            if (isValidPhoneNumber(updatedPhoneNumber)) {
                currentUser.setPhoneNumber(updatedPhoneNumber);
            }

            // Actualizar otros campos del usuario
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
            // Eliminar el prefijo "+503" del número de teléfono para mostrarlo en el formulario de edición
            String phoneNumberWithoutPrefix = extractLast8Digits(user.getPhoneNumber());
            user.setPhoneNumber(phoneNumberWithoutPrefix);
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
        // Método para validar el formato del correo electrónico en el lado del servidor
        private boolean isValidEmail(String email) {
            // Expresión regular para validar el correo electrónico
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        @ExceptionHandler(InvalidEmailFormatException.class)
        public String handleInvalidEmailFormat(Model model) {
            model.addAttribute("emailError", true);
            return "create_users"; // Redirigir de vuelta al formulario de creación con un mensaje de error
        }
        private String formatPhoneNumber(String phoneNumber) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                return "+503 " + phoneNumber;
            }
            return "";
        }
        private String extractLast8Digits(String phoneNumber) {
            // Eliminar caracteres no numéricos y mantener los últimos 8 dígitos
            String numericPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
            if (numericPhoneNumber.length() >= 8) {
                return numericPhoneNumber.substring(numericPhoneNumber.length() - 8);
            } else {
                return numericPhoneNumber;
            }
        }

        // Método para validar la contraseña
        private boolean isValidPassword(String password, String storedPassword) {
            // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
            return storedPassword.equals(password);
        }

        private boolean isValidPhoneNumber(String phoneNumber) {
            // Validar el formato del número de teléfono (8 dígitos)
            String phoneRegex = "\\d{8}";
            return phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.matches(phoneRegex);
        }

    }

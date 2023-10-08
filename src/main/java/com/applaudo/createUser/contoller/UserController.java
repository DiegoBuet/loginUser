    package com.applaudo.createUser.contoller;

    import com.applaudo.createUser.exeptions.InvalidEmailFormatException;
    import com.applaudo.createUser.model.entity.User;
    import com.applaudo.createUser.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;

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


        @GetMapping("/list_users/new")
        public String showUserRegistrationForm(Model model){
            User user = new User();
            model.addAttribute("user", user);
            return "created_users"; // Redirects to the purchase list page
        }

        @PostMapping("/list_users")
        public String saveUser(@ModelAttribute("user") User user, Model model) {
            // Verificar si el correo electrónico ya está registrado
            User existingUser = userService.findUserByEmail(user.getEmail());
            if (existingUser != null) {
               model.addAttribute("emailError", true);
                return "created_users"; // Vuelve al formulario de creación con un mensaje de error
            }
            if (!isValidEmail(user.getEmail())) {
                model.addAttribute("invalidEmailError", true);
                return "created_users"; // Regresar a la vista de creación con un mensaje de error
            }
            // Validar el número de teléfono
            if (!isValidPhoneNumber(user.getPhoneNumber())) {
                model.addAttribute("invalidPhoneNumberError", true);
                return "created_users"; // Regresar a la vista de creación con un mensaje de error
            }
            // Transformar el número de teléfono antes de guardarlo
            String formattedPhoneNumber = "+503 " + user.getPhoneNumber();
            user.setPhoneNumber(formattedPhoneNumber);
            // Si el correo electrónico no está registrado, guarda el usuario en la base de datos
            userService.saveUser(user);
            return "redirect:/list_users";
        }



        @PostMapping("/list_users/{id}")
        public String updateUser(@PathVariable Long id,@RequestParam("password") String password, @ModelAttribute("user") User user, Model model) {
            User currentUser = userService.findUserById(id);
            String currentEmail = currentUser.getEmail();
            // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
            if (!password.equals(currentUser.getPassword())) {
                model.addAttribute("passwordError", true);
                return "edit_users"; // Redirigir a la página de edición con un mensaje de error
            }
            // Validar el número de teléfono
            if (!isValidPhoneNumber(user.getPhoneNumber())) {
                model.addAttribute("invalidPhoneNumberError", true);
                return "edit_users"; // Redirigir a la página de edición con un mensaje de error
            }

            // Transformar el número de teléfono antes de actualizarlo en la base de datos
            String formattedPhoneNumber = "+503 " + user.getPhoneNumber();
            currentUser.setFirsName(user.getFirsName());
            currentUser.setLastName(user.getLastName());
            currentUser.setEmail(currentEmail);
            currentUser.setPhoneNumber(formattedPhoneNumber);
            currentUser.setPassword(user.getPassword());
            userService.updateUser(currentUser);
            return "redirect:/list_users";
        }
        @GetMapping({"/list_users/{id}"})
        public String deleteUser(@PathVariable Long id){
            userService.deleteUser(id);
            return "redirect:/list_users"; // Redirects to the purchase list page
        }

        @GetMapping({"/list_users/edit/{id}"})
        public String showEditForm(@PathVariable Long id, Model model){
            model.addAttribute("user", userService.findUserById(id));
            return "edit_users";
        }


        @GetMapping({"/list_users","/"})
        public String listUsers(Model model){
            List<User> userList = userService.listUsers();
           // model.addAttribute("user", userList);
            model.addAttribute("list_users", userList);
            return "list_users";
        }

        // Método para validar el formato del correo electrónico en el lado del servidor
        private boolean isValidEmail(String email) {
            // Expresión regular para validar el correo electrónico
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        // Método para validar el formato del número de teléfono en el lado del servidor
        private boolean isValidPhoneNumber(String phoneNumber) {
            // Expresión regular para validar el número de teléfono (8 dígitos)
            String phoneRegex = "\\d{8}";
            Pattern pattern = Pattern.compile(phoneRegex);
            Matcher matcher = pattern.matcher(phoneNumber);
            return matcher.matches();
        }

        @ExceptionHandler(InvalidEmailFormatException.class)
        public String handleInvalidEmailFormat(Model model) {
            model.addAttribute("emailError", true);
            return "create_users"; // Redirigir de vuelta al formulario de creación con un mensaje de error
        }


    }

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
    public class UserController2 {

/*
        @Autowired
        private UserService userService;

        @GetMapping("/listUsers")
        public List<User> listUsers(){
            return userService.listUsers();
        }



        @GetMapping("/list_users/new")
        public String showUserRegistrationForm(Model model){
            User user = new User();
            model.addAttribute("user", user);
            return "created_users"; // Redirects to the purchase list page
        }

        @PostMapping("/list_users")
        public String saveUser(@ModelAttribute("user") User user, Model model) {
            // Validar el formato del correo electrónico en el lado del servidor
            if (!isValidEmail(user.getEmail())) {
                model.addAttribute("emailExistsError", true);
                return "create_users"; // Regresar a la vista de creación con un mensaje de error
            }

            // Verificar si ya existe un usuario con el mismo correo electrónico
            User existingUser = userService.findUserByEmail(user.getEmail());

            if (existingUser != null) {
                model.addAttribute("emailExistsError", true);
                model.addAttribute("user", user); // Esto es importante para mantener los valores del formulario en caso de error.
                return "create_users"; // Regresar a la vista de creación con un mensaje de error
            }

            // Continuar guardando el usuario en la base de datos
            userService.saveUser(user);
            return "redirect:/list_users";
        }



        @GetMapping({"/list_users/edit/{id}"})
        public String showEditForm(@PathVariable Long id, Model model){
            model.addAttribute("user", userService.findUserById(id));
            return "edit_users";

        }
@PostMapping("/list_users/{id}")
public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
    User currentUser = userService.findUserById(id);

    // Verificar si el nuevo correo electrónico ya está en uso por otro usuario
    User existingUser = userService.findUserByEmail(user.getEmail());
    if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
        model.addAttribute("emailExistsError", true);
        return "edit_users"; // Mostrar un mensaje de error si el nuevo correo ya está registrado
    }

    // Actualizar los datos del usuario existente
    currentUser.setFirsName(user.getFirsName());
    currentUser.setLastName(user.getLastName());
    currentUser.setEmail(user.getEmail());
    currentUser.setPhoneNumber(user.getPhoneNumber());
    currentUser.setPassword(user.getPassword());
    userService.updateUser(currentUser);
    return "redirect:/list_users";
}
        @GetMapping({"/list_users/{id}"})
        public String deleteUser(@PathVariable Long id){
            userService.deleteUser(id);
            return "redirect:/list_users"; // Redirects to the purchase list page
        }
        @GetMapping({"/list_users","/"})
        public String listUsers(Model model){
            List<User> userList = userService.listUsers();
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
        // Método para manejar errores de formato de correo electrónico

        @ExceptionHandler(InvalidEmailFormatException.class)
        public String handleInvalidEmailFormat(Model model) {
            model.addAttribute("emailError", true);
            return "create_users"; // Redirigir de vuelta al formulario de creación con un mensaje de error
        }
*/


    }

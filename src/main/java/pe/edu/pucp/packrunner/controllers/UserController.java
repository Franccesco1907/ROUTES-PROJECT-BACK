package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.User;
import pe.edu.pucp.packrunner.services.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService objService;

    // Trae a todos
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<User> getAll() {
        return objService.getAll();
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    User get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    void register(@RequestBody User obj) {
        objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    User update(@RequestBody User obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    User login(@RequestBody User obj) {
        User user = objService.login(obj);
        if (user != null) {
            return user;
        }
        return null;
    }
}

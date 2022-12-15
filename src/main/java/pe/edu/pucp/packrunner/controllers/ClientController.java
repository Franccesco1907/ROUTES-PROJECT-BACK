package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.Client;
import pe.edu.pucp.packrunner.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    ClientService objService;

    // Get All
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Client> getAll() {
        return objService.getAll();
    }

    // Get One
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Client get(@PathVariable long id) {
        return objService.get(id);
    }

    // Register
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Client register(@RequestBody Client obj) {
        return objService.register(obj);
    }

    // Update
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Client update(@RequestBody Client obj) {
        return objService.update(obj);
    }

    // Delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    // Get All by User
    @RequestMapping(value = "/getByUser/{id}", method = RequestMethod.GET)
    Client getByUser(@PathVariable long id) {
        return objService.getByUser(id);
    }
}

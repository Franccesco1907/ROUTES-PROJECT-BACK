package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.Supervisor;
import pe.edu.pucp.packrunner.services.SupervisorService;

import java.util.List;

@RestController
@RequestMapping("supervisor")
public class SupervisorController {
    @Autowired
    SupervisorService objService;

    // Trae a todos
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Supervisor> getAll() {
        return objService.getAll();
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Supervisor get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Supervisor register(@RequestBody Supervisor obj) {
        return objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Supervisor update(@RequestBody Supervisor obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }
}

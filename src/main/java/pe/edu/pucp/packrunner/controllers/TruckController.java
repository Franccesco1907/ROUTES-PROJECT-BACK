package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.Truck;
import pe.edu.pucp.packrunner.services.TruckService;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("truck")
public class TruckController {
    @Autowired
    TruckService objService;

    // Trae a todos
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Truck> getAll() {
        return objService.getAll();
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Truck get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Truck register(@RequestBody Truck obj) {
        return objService.register(obj);
    }

    // Massive Register
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    void registerAll() throws FileNotFoundException {
        objService.registerAll();
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Truck update(@RequestBody Truck obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

}

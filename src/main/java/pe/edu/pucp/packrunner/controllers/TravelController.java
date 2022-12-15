package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.pucp.packrunner.dto.out.TravelOut;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.services.TravelService;

import java.util.List;

@RestController
@RequestMapping("travel")
public class TravelController {
    @Autowired
    TravelService objService;

    // Trae a todos
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Travel> getAll() {
        return objService.getAll();
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Travel get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Travel register(@RequestBody Travel obj) {
        return objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Travel update(@RequestBody Travel obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    @RequestMapping(value = "/getBySimulation", method = RequestMethod.GET)
    List<TravelOut> getBySimulation(@RequestParam(name = "id") Long id) {
        return objService.getBySimulation(id);
    }
}

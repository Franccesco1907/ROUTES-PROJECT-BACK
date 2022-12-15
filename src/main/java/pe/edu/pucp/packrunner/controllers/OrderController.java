package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.services.OrderService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService objService;

    // Trae a todos
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    List<Order> getAll(@RequestParam(name = "start") @Nullable @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
                       @RequestParam(name = "end") @Nullable @DateTimeFormat(pattern = "dd-MM-yyyy") Date end,
                       @RequestParam(name = "type") @Nullable Integer type) {
        return objService.getAll(start, end, type);
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Order get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Order register(@RequestBody Order obj) {
        return objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Order update(@RequestBody Order obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    // Trae por client
    @RequestMapping(value = "/getByClient/{id}", method = RequestMethod.GET)
    List<Order> getByClient(@PathVariable long id) {
        return objService.getByClient(id);
    }

    // Registrar
    @RequestMapping(value = "/runAlgorithm/{id}", method = RequestMethod.GET)
    void runAlgorithm(@PathVariable long id) {
        objService.runAlgorithm(id);
    }
}

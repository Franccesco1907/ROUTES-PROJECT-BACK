package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.DeliveryPlan;
import pe.edu.pucp.packrunner.services.DeliveryPlanService;

import java.util.List;

@RestController
@RequestMapping("deliveryPlan")
public class DeliveryPlanController {
    @Autowired
    DeliveryPlanService objService;

    // Trae a todos
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<DeliveryPlan> getAll() {
        return objService.getAll();
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    DeliveryPlan get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    DeliveryPlan register(@RequestBody DeliveryPlan obj) {
        return objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    DeliveryPlan update(@RequestBody DeliveryPlan obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

}

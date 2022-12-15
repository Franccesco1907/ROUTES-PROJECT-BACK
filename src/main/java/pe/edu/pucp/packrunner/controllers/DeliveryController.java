package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.dto.out.TravelOut;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.services.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("delivery")
public class DeliveryController {
    @Autowired
    DeliveryService objService;

    // Get All
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Delivery> getAll() {
        return objService.getAll();
    }

    // Get By Order
    @RequestMapping(value = "/getByOrder/{id}", method = RequestMethod.GET)
    List<Delivery> getByOrder (@PathVariable long id) {
        return objService.getByOrder(id);
    }

    // Get One
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Delivery get(@PathVariable long id) {
        return objService.get(id);
    }

    // Get Plans
    @RequestMapping(value = "/plans", method = RequestMethod.GET)
    List<TravelOut> getPlans(@RequestParam long id) {
        return objService.getPlans(id);
    }

    // Register
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Delivery register(@RequestBody Delivery obj) {
        return objService.register(obj);
    }

    // Update
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Delivery update(@RequestBody Delivery obj) {
        return objService.update(obj);
    }

    // Delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }


}

package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.dto.out.SimulationOut;
import pe.edu.pucp.packrunner.dto.out.TruckPlanOut;
import pe.edu.pucp.packrunner.models.TruckPlan;
import pe.edu.pucp.packrunner.services.TruckPlanService;

import java.util.List;

@RestController
@RequestMapping("truckPlan")
public class TruckPlanController {

    @Autowired
    TruckPlanService objService;

    // Get All
    @RequestMapping(value = "/", method = RequestMethod.GET)
    SimulationOut getAll(@RequestParam(name = "simulation") @Nullable Long idSimulation,
            @RequestParam(name = "run") @Nullable Integer run,
            @RequestParam(name = "status") @Nullable String status,
            @RequestParam(name = "travelling") @Nullable Boolean travelling) {
        return objService.getAll(idSimulation, run, status, travelling);
    }

    // Get by ID
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    TruckPlanOut get(@RequestParam(name = "id") long id) {
        return objService.get(id);
    }

    // Register
    @RequestMapping(value = "/", method = RequestMethod.POST)
    TruckPlan register(@RequestBody TruckPlan obj) {
        return objService.register(obj);
    }

    // Update
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    TruckPlan update(@RequestBody TruckPlan obj) {
        return objService.update(obj);
    }

    // Delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    List<TruckPlanOut> disable(@RequestParam(name = "id") Long idTruckPlan) {
        return objService.disable(idTruckPlan);
    }

    @RequestMapping(value = "/cripple", method = RequestMethod.GET)
    List<TruckPlanOut> cripple(@RequestParam(name = "id") Long idTruckPlan) {
        return objService.cripple(idTruckPlan);
    }

    @RequestMapping(value = "/kill", method = RequestMethod.GET)
    List<TruckPlanOut> kill(@RequestParam(name = "id") Long idTruckPlan) {
        return objService.kill(idTruckPlan);
    }

    @RequestMapping(value = "/getFirstBySimulation", method = RequestMethod.GET)
    TruckPlan getFirstBySimulation(@RequestParam(name = "id") Long id) {
        return objService.getFirstBySimulation(id);
    }

    @RequestMapping(value = "/listBySimulation", method = RequestMethod.GET)
    List<TruckPlan> listBySimulation(@RequestParam(name = "id") Long id) {
        return objService.listBySimulation(id);
    }

    @RequestMapping(value = "/getBySimulation", method = RequestMethod.GET)
    List<TruckPlanOut> getBySimulation(@RequestParam(name = "id") Long id) {
        return objService.getBySimulation(id);
    }
}

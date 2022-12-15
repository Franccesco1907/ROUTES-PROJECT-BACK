package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.dto.out.TruckPlanOut;
import pe.edu.pucp.packrunner.models.Simulation;
import pe.edu.pucp.packrunner.services.SimulationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("simulation")
public class SimulationController {

    @Autowired
    SimulationService simulationService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Simulation register(@RequestBody Simulation obj) {
        return simulationService.register(obj);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    Simulation startSimulation(@RequestParam(name = "type") @Nullable String type,
            @RequestParam(name = "clock") @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") Date clock) {
        return simulationService.startSimulation(type, clock);
    }

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    List<TruckPlanOut> runAlgorithm(@RequestParam(name = "simulation") Long idSimulation,
            @RequestParam(name = "run") Integer run,
            @RequestParam(name = "start") @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") Date start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") Date end) {
        return simulationService.runAlgorithm(idSimulation, run, start, end);
    }

    @RequestMapping(value = "/registerOrder", method = RequestMethod.GET)
    List<TruckPlanOut> runOrder(@RequestParam(name = "simulation") Long idSimulation,
            @RequestParam(name = "run") Integer run,
            @RequestParam(name = "order") Long idOrder) {
        return simulationService.runAlgorithm(idSimulation, run, idOrder);
    }

    @RequestMapping(value = "/getLastSimulation", method = RequestMethod.GET)
    public Simulation getLastSimulation() {
        return simulationService.getLastSimulation();
    }

    @RequestMapping(value = "/runMaintenance", method = RequestMethod.GET)
    public List<TruckPlanOut> runMaintenance(@RequestParam(name = "simulation") Long idSimulation,
                                             @RequestParam(name = "run") Integer run,
                                             @RequestParam(name = "date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date) {
        return simulationService.runMaintenance(idSimulation, run, date);
    }

}

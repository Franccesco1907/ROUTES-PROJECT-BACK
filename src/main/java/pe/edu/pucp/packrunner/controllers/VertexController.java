package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.dto.out.VertexOut;
import pe.edu.pucp.packrunner.models.Vertex;
import pe.edu.pucp.packrunner.services.VertexService;

import java.util.List;

@RestController
@RequestMapping("vertex")
public class VertexController {
    @Autowired
    VertexService objService;

    // Get All Vertexes
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<VertexOut> getAll() {
        return objService.getAll();
    }

    // Get All Offices
    @RequestMapping(value = "/offices", method = RequestMethod.GET)
    List<Vertex> getAllOffices() {
        return objService.getAllOffices();
    }

    // Get All Depots
    @RequestMapping(value = "/depots", method = RequestMethod.GET)
    List<Vertex> getAllDepots() {
        return objService.getAllDepots();
    }

    // Trae uno
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    VertexOut get(@RequestParam (name = "id") long id) {
        return objService.get(id);
    }

    @RequestMapping(value = "/getByProvince/{id}", method = RequestMethod.GET)
    Vertex getByProvince(@PathVariable long id) {
        return objService.getByProvince(id);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Vertex register(@RequestBody Vertex obj) {
        return objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Vertex update(@RequestBody Vertex obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }
}

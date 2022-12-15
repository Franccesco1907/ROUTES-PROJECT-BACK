package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.pucp.packrunner.models.Region;
import pe.edu.pucp.packrunner.services.RegionService;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {

    @Autowired
    RegionService objService;

    // Listar todos
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    List<Region> getAll() {
        return objService.getAll();
    }

    // Listar uno
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    Region get(@PathVariable long id) {
        return objService.get(id);
    }

    // Registrar
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    Region register(@PathVariable Region region) {
        ///// TODO
        return region;
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Region update(@PathVariable Region region) {
        ///// TODO
        return region;
    }

    // Eliminar
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    void delete(@PathVariable long id) {
        ///// TODO

    }
}

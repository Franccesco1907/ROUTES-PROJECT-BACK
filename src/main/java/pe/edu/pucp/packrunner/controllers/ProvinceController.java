package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.models.Province;
import pe.edu.pucp.packrunner.services.ProvinceService;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("province")
public class ProvinceController {
    @Autowired
    ProvinceService objService;

    // Trae a todos
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Province> getAll() {
        return objService.getAll();
    }

    // Trae uno
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Province get(@PathVariable long id) {
        return objService.get(id);
    }

    // Trae por Nombre de Provincia
    @RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
    Province getByName(@PathVariable String name) {
        return objService.getByName(name);
    }

    // Trae por ID de Region
    @RequestMapping(value = "/getByRegion/{id_region}", method = RequestMethod.GET)
    List<Province> getByRegion(@PathVariable long id_region) {
        return objService.getByRegion(id_region);
    }

    // Registrar
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Province register(@RequestBody Province obj) {
        return objService.register(obj);
    }

    // Actualizar
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Province update(@RequestBody Province obj) {
        return objService.update(obj);
    }

    // Eliminar
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    // Registro masivo
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    void registerAll() throws FileNotFoundException {
        objService.registerAll();
    }
}

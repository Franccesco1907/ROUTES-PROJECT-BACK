package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.services.EdgeService;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("edge")
public class EdgeController {
    @Autowired
    EdgeService objService;

    // Get All
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<EdgeOut> getAll(@RequestParam(name = "vertex") @Nullable Long idVertex) {
        return objService.getAll(idVertex);
    }

    // Get one
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    Edge get(@RequestParam(name = "id") long id) {
        return objService.get(id);
    }

    // Register
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Edge register(@RequestBody Edge obj) {
        return objService.register(obj);
    }

    // Update
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Edge update(@RequestBody Edge obj) {
        return objService.update(obj);
    }

    // Delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return objService.delete(id);
    }

    // Block
    @RequestMapping(value = "/block", method = RequestMethod.GET)
    Edge block(@RequestParam(name = "id") long id) {
        return objService.block(id);
    }

    // Registro masivo
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    void registerAll() throws FileNotFoundException {
        objService.registerAll();
    }
}

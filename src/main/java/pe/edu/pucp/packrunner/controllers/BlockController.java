package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.packrunner.dto.out.BlockOut;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.services.BlockService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("block")
public class BlockController {

    @Autowired
    BlockService blockService;

    // Get All
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<BlockOut> getAll(@RequestParam(name = "start") @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") Date start,
                          @RequestParam(name = "end") @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") Date end) {
        return blockService.getAll(start, end);
    }

    // Get one
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    Block get(@RequestParam(name = "id") long id) {
        return blockService.get(id);
    }

    // Register
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Block register(@RequestBody Block obj) {
        return blockService.register(obj);
    }

    // Update
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Block update(@RequestBody Block obj) {
        return blockService.update(obj);
    }

    // Delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int delete(@PathVariable long id) {
        return blockService.delete(id);
    }

    // Get Blocked Edges
    @RequestMapping(value = "/blocked", method = RequestMethod.GET)
    List<EdgeOut> getBlockedEdges(@RequestParam(name = "date") @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") Date date) {
        return blockService.getBlockedEdges(date);
    }

}

package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.BlockDao;
import pe.edu.pucp.packrunner.dao.ClientDao;
import pe.edu.pucp.packrunner.dto.out.BlockOut;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Client;
import pe.edu.pucp.packrunner.models.Edge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockService {

    @Autowired
    BlockDao objDao;
    
    // Get All
    public List<BlockOut> getAll(Date start, Date end){
        List<BlockOut> result = new ArrayList<>();
        for(Block block : objDao.getAll(start, end)) result.add(new BlockOut(block));
        return result;
    }

    // Get one
    public Block get(long id){
        return objDao.get(id);
    }

    // Register
    public Block register(Block obj){
        return objDao.register(obj);
    }

    // Update
    public Block update(Block obj){
        return objDao.update(obj);
    }

    // Delete
    public int delete(long id){
        return objDao.delete(id);
    }

    // Get Blocked Edges
    public List<EdgeOut> getBlockedEdges(Date date) {
        List<EdgeOut> result = new ArrayList<>();
        for (Edge edge : objDao.getBlockedEdges(date)) result.add(new EdgeOut(edge));
        return result;
    }

}

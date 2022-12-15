package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.DeliveryDao;
import pe.edu.pucp.packrunner.dto.out.TravelOut;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    DeliveryDao objDao;
    // Get All
    public List<Delivery> getAll(){
        return objDao.getAll();
    }

    // Get One
    public Delivery get(long id){
        return objDao.get(id);
    }

    // Get By Order
    public List<Delivery> getByOrder(long id){
        return objDao.getByOrder(id);
    }

    // Register
    public Delivery register(Delivery obj){
        return objDao.register(obj);
    }

    // Update
    public Delivery update(Delivery obj){
        return objDao.update(obj);
    }

    // Delete
    public int delete(long id){
        return objDao.delete(id);
    }

    // Get Travels
    public List<TravelOut> getPlans(long id) { return objDao.getPlans(id); }

}

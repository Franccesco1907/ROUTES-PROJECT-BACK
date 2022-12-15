package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.DeliveryPlanDao;
import pe.edu.pucp.packrunner.models.DeliveryPlan;

import java.util.List;

@Service
public class DeliveryPlanService {
    @Autowired
    DeliveryPlanDao objDao;
    //Trae a todos
    public List<DeliveryPlan> getAll(){
        return objDao.getAll();
    }

    //Trae uno
    public DeliveryPlan get(long id){
        return objDao.get(id);
    }

    //Registrar
    public DeliveryPlan register(DeliveryPlan obj){
        return objDao.register(obj);
    }

    //Actualizar
    public DeliveryPlan update(DeliveryPlan obj){
        return objDao.update(obj);
    }

    //Eliminar
    public int delete(long id){
        return objDao.delete(id);
    }

}

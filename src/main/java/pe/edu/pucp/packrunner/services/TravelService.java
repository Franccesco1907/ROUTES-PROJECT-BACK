package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.TravelDao;
import pe.edu.pucp.packrunner.dao.TruckPlanDao;
import pe.edu.pucp.packrunner.dto.out.TravelOut;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelService {
    @Autowired
    TravelDao objDao;

    @Autowired
    TruckPlanDao tpDao;

    // Trae a todos
    public List<Travel> getAll() {
        return objDao.getAll();
    }

    // Trae uno
    public Travel get(long id) {
        return objDao.get(id);
    }

    // Registrar
    public Travel register(Travel obj) {
        return objDao.register(obj);
    }

    // Actualizar
    public Travel update(Travel obj) {
        return objDao.update(obj);
    }

    // Eliminar
    public int delete(long id) {
        return objDao.delete(id);
    }

    public List<TravelOut> getBySimulation(Long id) {
        List<TruckPlan> truckPlans = tpDao.listBySimulation(id);
        List<Travel> travels = new ArrayList<>();
        List<TravelOut> travelOuts = new ArrayList<>();
        for (TruckPlan tp : truckPlans) {
            List<Travel> result = tp.getTravels();
            for (Travel t : result)
                travels.add(t);
        }

        for (Travel travel : travels)
            travelOuts.add(new TravelOut(travel));
        return travelOuts;
    }

}

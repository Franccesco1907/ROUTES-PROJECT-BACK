package pe.edu.pucp.packrunner.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pe.edu.pucp.packrunner.dao.*;
import pe.edu.pucp.packrunner.dto.out.VertexOut;
import pe.edu.pucp.packrunner.models.*;

@Service
public class VertexService {
  @Autowired
  ProvinceDao daoProvince;

  @Autowired
  RegionDao daoRegion;

  @Autowired
  VertexDao objDao;

  // Trae a todos
  @Cacheable(value = "vertex")
  public List<VertexOut> getAll() {
    List<VertexOut> result = new ArrayList();
    for(Vertex vertex : objDao.getAll()) result.add(new VertexOut(vertex));
    return result;
  }

  public List<Vertex> getAllOffices() {
    return objDao.getAllOffices();
  }

  public List<Vertex> getAllDepots() {
    return objDao.getAllDepots();
  }

  // Trae uno
  public VertexOut get(long id) {
    return new VertexOut(objDao.get(id));
  }

  // Registrar
  public Vertex register(Vertex obj) {
    return objDao.register(obj);
  }

  // Actualizar
  public Vertex update(Vertex obj) {
    return objDao.update(obj);
  }

  // Eliminar
  public int delete(long id) {
    return objDao.delete(id);
  }

  public Vertex getByProvince(long id) {
    return objDao.getByProvince(id);
  }
}

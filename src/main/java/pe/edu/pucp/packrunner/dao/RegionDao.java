package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Region;

import java.util.List;

public interface RegionDao {
    // Listar todos
    public List<Region> getAll();

    // Listar uno
    public Region get(long id);

    // Registrar
    public Region register(Region region);

    // Actualizar
    public Region update(Region region);

    // Eliminar
    public int delete(long id);

    // verificar en el registro que no se repitan los nombres
    public Region findByName(String name);
}

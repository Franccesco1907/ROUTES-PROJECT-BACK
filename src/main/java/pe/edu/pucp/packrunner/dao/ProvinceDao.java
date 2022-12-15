package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Province;

import java.util.List;

public interface ProvinceDao {
    // Listar todos
    public List<Province> getAll();
    
    // Listar varios
    public List<Province> getByRegion(long id);

    // Listar uno
    public Province get(long id);

    // Listar uno
    public Province getByName(String name);

    // Registrar
    public Province register(Province province);

    // Actualizar
    public Province update(Province province);

    // Eliminar
    public int delete(long id);

    // verificar en el registro que no se repitan los nombres
    public Province findByNameDepartment(String provinceNname, String departmentName);
}

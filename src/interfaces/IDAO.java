package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IDAO <Parameter> {
    public boolean create (Parameter p) throws SQLException;
    public boolean update(Parameter p) throws SQLException;
    public Parameter read(Object key) throws SQLException;
    public List<Parameter> readAll() throws SQLException;
    public boolean delete(Object key) throws SQLException;
}

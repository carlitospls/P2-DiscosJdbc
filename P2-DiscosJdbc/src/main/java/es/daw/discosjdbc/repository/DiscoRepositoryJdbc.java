package es.daw.discosjdbc.repository;

import es.daw.discosjdbc.exception.AccesoDatosException;
import es.daw.discosjdbc.model.Genero;
import es.daw.discosjdbc.model.Disco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Capa de datos: la única que escribe SQL.
@Repository
@RequiredArgsConstructor
public class DiscoRepositoryJdbc implements DiscoRepository {

    // SELECT común a findById y findAll (JOIN para traer también el nombre del genero)
    private static final String SELECT_BASE = """
            SELECT d.codigo, d.nombre, d.precio,
                   g.codigo AS g_codigo, g.nombre AS g_nombre
            FROM disco d
            JOIN genero g ON d.codigo_genero = g.codigo
            """;

    private final DataSource dataSource;

    @Override
    public Optional<Disco> findById(Integer codigo) {
        String sql = SELECT_BASE + "WHERE d.codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar el disco " + codigo, e);
        }
    }

    @Override
    public List<Disco> findAll() {
        String sql = SELECT_BASE + "ORDER BY d.codigo";
        List<Disco> discos = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                discos.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los discos", e);
        }
        return discos;
    }

    @Override
    public void save(Disco disco) {
        String sql = "INSERT INTO disco (nombre, precio, codigo_genero) VALUES (?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, disco.getNombre());
            ps.setDouble(2, disco.getPrecio());
            ps.setInt(3, disco.getGenero().getCodigo());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    disco.setCodigo(claves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al guardar el disco", e);
        }
    }

    @Override
    public void update(Disco disco) {
        String sql = "UPDATE disco SET nombre = ?, precio = ?, codigo_genero = ? WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, disco.getNombre());
            ps.setDouble(2, disco.getPrecio());
            ps.setInt(3, disco.getGenero().getCodigo());
            ps.setInt(4, disco.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar el disco " + disco.getCodigo(), e);
        }
    }

    @Override
    public void delete(Integer codigo) {
        String sql = "DELETE FROM disco WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al borrar el disco " + codigo, e);
        }
    }

    @Override
    public boolean existsByGeneroCodigo(Integer codigoGenero) {
        String sql = "SELECT 1 FROM disco WHERE codigo_genero = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoGenero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al comprobar los discos del genero " + codigoGenero, e);
        }
    }

    @Override
    public boolean existsByNombreIgnoreCase(String nombre) {
        String sql = "SELECT 1 FROM disco WHERE LOWER(nombre) = LOWER(?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // TRUE si encuentra el fabricante con ese nombre...
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al comprobar el nombre del disco: " + nombre, e);
        }
    }

    // Fila del ResultSet → objeto Disco
    private Disco mapRow(ResultSet rs) throws SQLException {
        Genero genero = new Genero(
                rs.getInt("g_codigo"),
                rs.getString("g_nombre"));

        return new Disco(
                rs.getInt("codigo"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                genero);
    }
}

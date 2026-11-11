package es.daw.discosjdbc.repository;

import es.daw.discosjdbc.exception.AccesoDatosException;
import es.daw.discosjdbc.model.Genero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GeneroRepositoryJdbc implements GeneroRepository {

    private final DataSource dataSource;

    @Override
    public Optional<Genero> findById(Integer codigo) {
        String sql = "SELECT codigo, nombre FROM genero WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar el genero " + codigo, e);
        }
    }

    @Override
    public List<Genero> findAll() {
        String sql = "SELECT codigo, nombre FROM genero ORDER BY nombre";
        List<Genero> generos = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                generos.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los generos", e);
        }
        return generos;
    }

    @Override
    public void save(Genero genero) {
        String sql = "INSERT INTO genero (nombre) VALUES (?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, genero.getNombre());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    genero.setCodigo(claves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al guardar el genero", e);
        }
    }

    @Override
    public void update(Genero genero) {
        String sql = "UPDATE genero SET nombre = ? WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, genero.getNombre());
            ps.setInt(2, genero.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar el genero " + genero.getCodigo(), e);
        }
    }

    @Override
    public void delete(Integer codigo) {
        String sql = "DELETE FROM genero WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al borrar el genero " + codigo, e);
        }
    }

    private Genero mapRow(ResultSet rs) throws SQLException {
        return new Genero(rs.getInt("codigo"), rs.getString("nombre"));
    }
}

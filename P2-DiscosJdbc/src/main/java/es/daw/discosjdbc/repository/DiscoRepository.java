package es.daw.discosjdbc.repository;

import es.daw.discosjdbc.model.Disco;

/** Repositorio de discos: CRUD genérico + consultas propias. */
public interface DiscoRepository extends GenericRepository<Disco, Integer> {

    /** ¿Hay algún disco de este genero? */
    boolean existsByGeneroCodigo(Integer codigoGenero);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndCodigoNot(String nombre, Integer codigo);

}

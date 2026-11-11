package es.daw.discosjdbc.service;

import es.daw.discosjdbc.exception.RecursoNoEncontradoException;
import es.daw.discosjdbc.exception.DiscoDuplicadoException;
import es.daw.discosjdbc.model.Genero;
import es.daw.discosjdbc.model.Disco;
import es.daw.discosjdbc.repository.GeneroRepository;
import es.daw.discosjdbc.repository.DiscoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Capa de lógica: reglas de negocio. No sabe nada de HTTP ni de SQL.
@Service
@RequiredArgsConstructor
public class DiscoService {

    private final DiscoRepository discoRepository;
    private final GeneroRepository generoRepository;

    public List<Disco> listar() {
        return discoRepository.findAll();
    }

    public Disco buscarPorCodigo(Integer codigo) {
        return discoRepository.findById(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el disco con código " + codigo));
    }

    public Disco crear(Disco disco) {
        prepararParaGuardar(disco);

        if(discoRepository.existsByNombreIgnoreCase(disco.getNombre())){
            throw new DiscoDuplicadoException(disco.getNombre());
        }

        discoRepository.save(disco);
        return disco;
    }

    public Disco actualizar(Disco disco) {
        buscarPorCodigo(disco.getCodigo());
        prepararParaGuardar(disco);

        if(discoRepository.existsByNombreIgnoreCaseAndCodigoNot(disco.getNombre(), disco.getCodigo())){
            throw new DiscoDuplicadoException(disco.getNombre());
        }

        discoRepository.update(disco);
        return disco;
    }

    public void borrar(Integer codigo) {
        buscarPorCodigo(codigo);
        discoRepository.delete(codigo);
    }

    // Reglas comunes a crear y actualizar
    private void prepararParaGuardar(Disco disco) {
        disco.setNombre(disco.getNombre().trim());

        Integer codigoGenero = disco.getGenero().getCodigo();
        Genero genero = generoRepository.findById(codigoGenero)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el genero con código " + codigoGenero));
        disco.setGenero(genero);
    }
}

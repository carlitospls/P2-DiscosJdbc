package es.daw.discosjdbc.service;

import es.daw.discosjdbc.model.Genero;
import es.daw.discosjdbc.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public List<Genero> listar() {
        return generoRepository.findAll();
    }
}

package es.daw.discosjdbc.exception;

public class discoDuplicadoException extends RuntimeException {
    public discoDuplicadoException(String disco) {
        super("Ya existe un disco llamado \""+disco+"\"");
    }
}

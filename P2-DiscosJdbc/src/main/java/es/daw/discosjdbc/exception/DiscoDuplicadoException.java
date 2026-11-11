package es.daw.discosjdbc.exception;

public class DiscoDuplicadoException extends RuntimeException {
    public DiscoDuplicadoException(String disco) {
        super("Ya existe un disco llamado \""+disco+"\"");
    }
}

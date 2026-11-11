package es.daw.discosjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Fila de la tabla GENERO. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genero {

    private Integer codigo;
    private String nombre;
}

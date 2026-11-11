package es.daw.discosjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fila de la tabla DISCO.
 * En la tabla hay una columna codigo_genero (FK); en Java guardamos el objeto Genero completo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disco {

    private Integer codigo;
    private String nombre;
    private double precio;
    private Genero genero;
}

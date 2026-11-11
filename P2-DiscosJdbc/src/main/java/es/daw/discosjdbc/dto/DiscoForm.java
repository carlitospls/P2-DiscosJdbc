package es.daw.discosjdbc.dto;

import es.daw.discosjdbc.model.Genero;
import es.daw.discosjdbc.model.Disco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto del formulario de disco (viaja entre la vista y el controlador).
 * El <select> solo envía el código del genero, por eso aquí es un Integer.
 */
@Data
@NoArgsConstructor
public class DiscoForm {

    private Integer codigo;            // null = alta, con valor = edición

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @NotNull(message = "Selecciona un género")
    private Integer codigoGenero;

    /** Disco (modelo) → formulario. Se usa al editar. */
    public static DiscoForm toDto(Disco p) {
        DiscoForm form = new DiscoForm();
        form.setCodigo(p.getCodigo());
        form.setNombre(p.getNombre());
        form.setPrecio(p.getPrecio());
        form.setCodigoGenero(p.getGenero().getCodigo());
        return form;
    }

    /** Formulario → Disco (modelo). De el genero solo tenemos el código. */
    public Disco toModelEntity() {
        Genero genero = new Genero(codigoGenero, null);
        return new Disco(codigo, nombre, precio, genero);
    }
}

package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDetailDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String perfilesNecesarios;
    private String fase;
    private String visibilidad;
    private String repositorioUrl;
    private String demoUrl;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private Integer numVisitas;
    private UsuarioDTO usuario;
}

package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoCreateDTO {

    private Long id;

    @NotBlank(message = "{msg.proyecto.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.proyecto.nombre.size}")
    private String nombre;

    @NotBlank(message = "{msg.proyecto.descripcion.notEmpty}")
    @Size(max = 1000, message = "{msg.proyecto.descripcion.size}")
    private String descripcion;

    @Size(max = 500, message = "{msg.proyecto.perfilesNecesarios.size}")
    private String perfilesNecesarios;

    @NotBlank(message = "{msg.proyecto.fase.notEmpty}")
    @Pattern(regexp = "IDEA|DESARROLLO|FINALIZADO", message = "{msg.proyecto.fase.invalid}")
    private String fase;

    @NotBlank(message = "{msg.proyecto.visibilidad.notEmpty}")
    @Pattern(regexp = "PUBLICO|PRIVADO", message = "{msg.proyecto.visibilidad.invalid}")
    private String visibilidad;

    @Size(max = 255, message = "{msg.proyecto.repositorioUrl.size}")
    @Pattern(regexp = "^$|^https?://.+", message = "{msg.proyecto.repositorioUrl.invalid}")
    private String repositorioUrl;

    @Size(max = 255, message = "{msg.proyecto.demoUrl.size}")
    @Pattern(regexp = "^$|^https?://.+", message = "{msg.proyecto.demoUrl.invalid}")
    private String demoUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaFinalizacion;

    @NotNull(message = "{msg.proyecto.usuario.notNull}")
    private Long usuarioId;
}

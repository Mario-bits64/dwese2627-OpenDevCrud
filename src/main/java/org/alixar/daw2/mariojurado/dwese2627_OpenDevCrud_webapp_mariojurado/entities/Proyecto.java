package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "perfiles_necesarios", length = 500)
    private String perfilesNecesarios;

    @Column(name = "fase", nullable = false, length = 20)
    private String fase;

    @Column(name = "visibilidad", nullable = false, length = 20)
    private String visibilidad;

    @Column(name = "repositorio_url", length = 255)
    private String repositorioUrl;

    @Column(name = "demo_url", length = 255)
    private String demoUrl;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "num_visitas", nullable = false)
    private Integer numVisitas = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;
}

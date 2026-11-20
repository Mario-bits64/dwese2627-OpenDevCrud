package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 500)
    private String passwordHash;

    @Column(name = "rol_sistema", nullable = false, length = 20)
    private String rolSistema;

    @Column(name = "activo", nullable = false)
    private Boolean activo = Boolean.TRUE;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "doble_factor_activo", nullable = false)
    private Boolean dobleFactorActivo = Boolean.FALSE;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Proyecto> proyectos = new ArrayList<>();
}

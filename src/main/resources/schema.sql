-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email               VARCHAR(100) NOT NULL UNIQUE,
    password_hash       VARCHAR(500) NOT NULL,
    rol_sistema         VARCHAR(20)  NOT NULL,
    activo              BOOLEAN      NOT NULL DEFAULT TRUE,
    avatar_url          VARCHAR(255) NULL,
    doble_factor_activo BOOLEAN      NOT NULL DEFAULT FALSE,
    fecha_registro      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de proyectos (cada proyecto lo crea un usuario)
CREATE TABLE IF NOT EXISTS proyectos (
    id_proyecto         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre              VARCHAR(100)  NOT NULL UNIQUE,
    descripcion         VARCHAR(1000) NOT NULL,
    perfiles_necesarios VARCHAR(500)  NULL,
    fase                VARCHAR(20)   NOT NULL,
    visibilidad         VARCHAR(20)   NOT NULL,
    repositorio_url     VARCHAR(255)  NULL,
    demo_url            VARCHAR(255)  NULL,
    fecha_creacion      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_finalizacion  DATETIME      NULL,
    num_visitas         INT           NOT NULL DEFAULT 0,
    id_usuario          BIGINT        NOT NULL,
    CONSTRAINT fk_proyectos_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

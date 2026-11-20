-- Inserta si no existe (ignora duplicados por UNIQUE o por id)
INSERT IGNORE INTO usuarios (
    id_usuario, email, password_hash, rol_sistema, activo,
    avatar_url, doble_factor_activo, fecha_registro
) VALUES
(1, 'mario@opendev.local', 'mario123', 'DESARROLLADOR', TRUE, NULL, FALSE, NOW()),
(2, 'laura@opendev.local', 'laura123', 'DESARROLLADOR', TRUE, NULL, TRUE,  NOW());

INSERT IGNORE INTO proyectos (
    id_proyecto, nombre, descripcion, perfiles_necesarios, fase, visibilidad,
    repositorio_url, demo_url, fecha_creacion, fecha_finalizacion, num_visitas, id_usuario
) VALUES
(1, 'OpenDev', 'Plataforma para que desarrolladores publiquen proyectos y encuentren colaboradores.',
    'Backend Java, Frontend', 'DESARROLLO', 'PUBLICO',
    'https://github.com/opendev/opendev', NULL, NOW(), NULL, 15, 1),
(2, 'TaskBoard', 'Tablero Kanban sencillo para organizar las tareas de un equipo.',
    'Frontend', 'FINALIZADO', 'PRIVADO',
    'https://github.com/opendev/taskboard', 'https://taskboard.opendev.local', DATE_SUB(NOW(), INTERVAL 2 MONTH), DATE_SUB(NOW(), INTERVAL 1 WEEK), 42, 2);

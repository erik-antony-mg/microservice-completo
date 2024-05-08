
-- Insertar un nuevo usuario con usuario_id 1
INSERT INTO usuarios (usuario_id, email, password)
VALUES (1, 'erik@gmail.com', '$2a$10$lsnYb8pu.znBO5eBehvN9eOQ2d7HW9BX9AWL9VKXnRnb571HSVrxy');

-- Insertar el rol "ADMIN" con rol_id 1
INSERT INTO roles (roles_id, role_name)
VALUES (1, 'ADMIN');

-- Asignar el rol "ADMIN" al usuario con usuario_id 1
INSERT INTO usuarios_roles (usuario_id, rol_id)
VALUES (1, 1);


use dbclientes_in5am;

SET SQL_SAFE_UPDATES = 0;

INSERT INTO usuarios (username, password, email, rol, estado, fecha_registro)
SELECT 'admin', '$2a$10$CPVGQoKqaibvqwH4nxt0re87v0KWWW6fRNdyUmYhA3ljULUoKe9E6', 'admin@kinalapp.com', 'ADMIN', 1, CURDATE()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username = 'admin');

INSERT INTO usuarios (username, password, email, rol, estado, fecha_registro)
SELECT 'usuario', '$2a$10$GikhKbU93IynluLOeKElJ.iDnvBc.BkRQJH3WUxKSFNO.JKQOAy4K', 'usuario@kinalapp.com', 'USER', 1, CURDATE()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username = 'usuario');

SET SQL_SAFE_UPDATES = 1;
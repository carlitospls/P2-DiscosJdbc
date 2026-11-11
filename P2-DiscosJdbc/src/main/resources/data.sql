-- Géneros (Clásica no tiene discos)
INSERT INTO genero (nombre) VALUES ('Rock');     -- 1
INSERT INTO genero (nombre) VALUES ('Pop');      -- 2
INSERT INTO genero (nombre) VALUES ('Jazz');     -- 3
INSERT INTO genero (nombre) VALUES ('Flamenco'); -- 4
INSERT INTO genero (nombre) VALUES ('Clásica');  -- 5

-- Discos
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Abbey Road', 29.90, 1);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Led Zeppelin IV', 27.50, 1);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('The Dark Side of the Moon', 31.00, 1);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Thriller', 24.90, 2);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Rumours', 26.00, 2);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Purple Rain', 23.50, 2);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Kind of Blue', 22.00, 3);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('A Love Supreme', 21.50, 3);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('Entre dos aguas', 18.90, 4);
INSERT INTO disco (nombre, precio, codigo_genero) VALUES ('La leyenda del tiempo', 19.90, 4);

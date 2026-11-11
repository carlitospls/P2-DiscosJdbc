DROP TABLE IF EXISTS disco;
DROP TABLE IF EXISTS genero;

CREATE TABLE genero (
    codigo INT          AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE disco (
    codigo           INT          AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    precio           DOUBLE       NOT NULL,
    codigo_genero INT          NOT NULL,
    CONSTRAINT fk_disco_genero
        FOREIGN KEY (codigo_genero) REFERENCES genero (codigo)
);

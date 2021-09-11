CREATE TABLE products.produits (
 id integer not null,
 nom varchar(100) not null,
 prix double not null,
 prixAchat double null
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;


INSERT INTO produits VALUES(1, 'Ordinateur portable' , 350, 120);
INSERT INTO produits VALUES(2, 'Aspirateur Robot' , 500, 200);
INSERT INTO produits VALUES(3, 'Table de Ping Pong' , 750, 400);
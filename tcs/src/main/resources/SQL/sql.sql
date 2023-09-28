CREATE TABLE usuarios (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(255) DEFAULT NULL,
  descricao varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL UNIQUE, 
  cpf varchar(255) DEFAULT NULL,
  idade date DEFAULT NULL,
  url_foto varchar(255) DEFAULT NULL,
  senha varchar(255) DEFAULT NULL,
  url varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_username (username)  
);
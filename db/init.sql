USE springangular;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Evaluacion_Item, Item, Evaluacion, Prueba, User, Participante, Especialidad;
SET FOREIGN_KEY_CHECKS = 1;

-- Tabla Especialidad
CREATE TABLE Especialidad (
    idEspecialidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(45) NOT NULL,
    Codigo VARCHAR(45) NOT NULL
);

INSERT INTO Especialidad (idEspecialidad, Nombre, Codigo)
VALUES (1, 'informatica', 'C001'), (2, 'Medicina', 'D635');

-- Tabla Participante
CREATE TABLE Participante (
    idParticipante BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(45) NOT NULL,
    Apellidos VARCHAR(45) NOT NULL,
    Centro VARCHAR(45) NOT NULL,
    Especialidad_idEspecialidad BIGINT,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad) ON DELETE CASCADE
);

INSERT INTO Participante (Nombre, Apellidos, Centro, Especialidad_idEspecialidad)
VALUES 
('Juan', 'Pérez García', 'Centro A', 1),
('Ana', 'Martínez López', 'Centro B', 1),
('Carlos', 'Gómez Pérez', 'Centro C', 1),
('Laura', 'Rodríguez Sánchez', 'Centro D', 1),
('Pedro', 'Fernández Álvarez', 'Centro E', 1),
('Manuel', 'Gómez Álvarez', 'Fuentezuelas', 2);

-- Tabla User
CREATE TABLE User (
    idUser BIGINT AUTO_INCREMENT PRIMARY KEY,
    Role VARCHAR(45) NOT NULL,
    Username VARCHAR(45) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Especialidad_idEspecialidad BIGINT,
    Nombre VARCHAR(45) NOT NULL,
    Apellidos VARCHAR(45) NOT NULL,
    DNI VARCHAR(9) UNIQUE NOT NULL,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad) ON DELETE CASCADE
);

INSERT INTO User (Role, Username, Password, Especialidad_idEspecialidad, Nombre, Apellidos, DNI) 
VALUES 
('ROLE_EXPERTO','Jose','$2a$10$YCQxQ9kahXuZQ5TSdU4VqOs7X34GuDq2ODVFNnduf5UfNJJZza6gi',1,'Jose','Pérez Romero','91234567A'),
('ROLE_ADMIN','Manuel','$2a$10$MQHmPDYsgGSIvVZEJpQaEu2lGTHc1feRVlnL0z8YktISYEylvjCxW',1,'Manuel','Pérez','12345678A');

-- Tabla Prueba
CREATE TABLE Prueba (
    idPrueba BIGINT AUTO_INCREMENT PRIMARY KEY,
    Enunciado VARCHAR(200) NOT NULL,
    Especialidad_idEspecialidad BIGINT,
    Puntuacion_Maxima INT NOT NULL,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad) ON DELETE CASCADE
);

INSERT INTO Prueba (Enunciado, Especialidad_idEspecialidad, Puntuacion_Maxima) 
VALUES 
('Resolver un problema de algoritmos', 1, 100),
('Desarrollar una aplicación web', 1, 150),
('Optimizar una consulta SQL', 1, 50);

-- Tabla Evaluacion
CREATE TABLE Evaluacion (
    idEvaluacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    Participante_idParticipante BIGINT,
    Prueba_idPrueba BIGINT,
    User_idUser BIGINT,
    Nota_Final DOUBLE NOT NULL,
    FOREIGN KEY (Participante_idParticipante) REFERENCES Participante(idParticipante) ON DELETE CASCADE,
    FOREIGN KEY (Prueba_idPrueba) REFERENCES Prueba(idPrueba) ON DELETE CASCADE,
    FOREIGN KEY (User_idUser) REFERENCES User(idUser) ON DELETE CASCADE
);

INSERT INTO Evaluacion (Participante_idParticipante, Prueba_idPrueba, User_idUser, Nota_Final)
VALUES (1, 2, 1, 85.5);

-- Tabla Item
CREATE TABLE Item (
    idItem BIGINT AUTO_INCREMENT PRIMARY KEY,
    Descripcion VARCHAR(200) NOT NULL,
    Peso INT NOT NULL,
    Grados_Consecucion INT NOT NULL,
    Prueba_idPrueba BIGINT,
    FOREIGN KEY (Prueba_idPrueba) REFERENCES Prueba(idPrueba) ON DELETE CASCADE
);

INSERT INTO Item (Descripcion, Peso, Grados_Consecucion, Prueba_idPrueba) 
VALUES 
('Corrección del algoritmo', 50, 5, 1),
('Eficiencia del código', 30, 5, 1),
('Legibilidad y estructura', 20, 5, 1),
('Funcionalidad completa', 50, 5, 2),
('Diseño y usabilidad', 30, 5, 2),
('Código limpio y estructurado', 20, 5, 2);

-- Tabla Evaluacion_Item
CREATE TABLE Evaluacion_Item (
    idEvaluacionItem BIGINT AUTO_INCREMENT PRIMARY KEY,
    Evaluacion_idEvaluacion BIGINT,
    Item_idItem BIGINT,
    Valoracion INT NOT NULL,
    Explicacion VARCHAR(200),
    FOREIGN KEY (Evaluacion_idEvaluacion) REFERENCES Evaluacion(idEvaluacion) ON DELETE CASCADE,
    FOREIGN KEY (Item_idItem) REFERENCES Item(idItem) ON DELETE CASCADE
);

INSERT INTO Evaluacion_Item (Evaluacion_idEvaluacion, Item_idItem, Valoracion, Explicacion) 
VALUES 
(1, 4, 4, 'Buena implementación de funcionalidades principales'),
(1, 5, 5, 'Excelente diseño de interfaz'),
(1, 6, 3, 'Código funcional pero necesita mejor organización');
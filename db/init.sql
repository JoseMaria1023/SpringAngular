-- Tabla Especialidad
CREATE TABLE Especialidad (
    idEspecialidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(45) NOT NULL,
    Codigo VARCHAR(45) NOT NULL
);

-- Tabla Participante
CREATE TABLE Participante (
    idParticipante BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(45) NOT NULL,
    Apellidos VARCHAR(45) NOT NULL,
    Centro VARCHAR(45) NOT NULL,
    Especialidad_idEspecialidad BIGINT,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

-- Tabla User
CREATE TABLE User (
    idUser BIGINT AUTO_INCREMENT PRIMARY KEY,
    Role VARCHAR(45) NOT NULL,
    Username VARCHAR(45) UNIQUE NOT NULL,
    Password VARCHAR(45) NOT NULL,
    Especialidad_idEspecialidad BIGINT,
    Nombre VARCHAR(45) NOT NULL,
    Apellidos VARCHAR(45) NOT NULL,
    DNI VARCHAR(9) UNIQUE NOT NULL,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

-- Tabla Prueba
CREATE TABLE Prueba (
    idPrueba BIGINT AUTO_INCREMENT PRIMARY KEY,
    Enunciado VARCHAR(200) NOT NULL,
    Especialidad_idEspecialidad BIGINT,
    Puntuacion_Maxima BIGINT NOT NULL,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

-- Tabla Evaluacion
CREATE TABLE Evaluacion (
    idEvaluacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    Participante_idParticipante BIGINT,
    Prueba_idPrueba BIGINT,
    User_idUser BIGINT,
    Nota_Final DOUBLE NOT NULL,
    FOREIGN KEY (Participante_idParticipante) REFERENCES Participante(idParticipante),
    FOREIGN KEY (Prueba_idPrueba) REFERENCES Prueba(idPrueba),
    FOREIGN KEY (User_idUser) REFERENCES User(idUser)
);

-- Tabla Item
CREATE TABLE Item (
    idItem BIGINT AUTO_INCREMENT PRIMARY KEY,
    Descripcion VARCHAR(200) NOT NULL,
    Peso BIGINT NOT NULL,
    Grados_Consecucion BIGINT NOT NULL,
    Prueba_idPrueba BIGINT,
    FOREIGN KEY (Prueba_idPrueba) REFERENCES Prueba(idPrueba)
);

-- Tabla Evaluacion_Item
CREATE TABLE Evaluacion_Item (
    idEvaluacionItem BIGINT AUTO_INCREMENT PRIMARY KEY,
    Evaluacion_idEvaluacion BIGINT,
    Item_idItem BIGINT,
    Valoracion BIGINT NOT NULL,
    Explicacion VARCHAR(200),
    FOREIGN KEY (Evaluacion_idEvaluacion) REFERENCES Evaluacion(idEvaluacion),
    FOREIGN KEY (Item_idItem) REFERENCES Item(idItem)
);

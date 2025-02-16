-- Tabla Especialidad
CREATE TABLE Especialidad (
    idEspecialidad INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(45) NOT NULL,
    Codigo VARCHAR(45) NOT NULL
);

-- Tabla Participante
CREATE TABLE Participante (
    idParticipante INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(45) NOT NULL,
    Apellidos VARCHAR(45) NOT NULL,
    Centro VARCHAR(45) NOT NULL,
    Especialidad_idEspecialidad INT,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

-- Tabla User
CREATE TABLE User (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    Role VARCHAR(45) NOT NULL,
    Username VARCHAR(45) UNIQUE NOT NULL,
    Password VARCHAR(45) NOT NULL,
    Especialidad_idEspecialidad INT,
    Nombre VARCHAR(45) NOT NULL,
    Apellidos VARCHAR(45) NOT NULL,
    DNI VARCHAR(9) UNIQUE NOT NULL,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

-- Tabla Prueba
CREATE TABLE Prueba (
    idPrueba INT AUTO_INCREMENT PRIMARY KEY,
    Enunciado VARCHAR(200) NOT NULL,
    Especialidad_idEspecialidad INT,
    Puntuacion_Maxima INT NOT NULL,
    FOREIGN KEY (Especialidad_idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

-- Tabla Evaluacion
CREATE TABLE Evaluacion (
    idEvaluacion INT AUTO_INCREMENT PRIMARY KEY,
    Participante_idParticipante INT,
    Prueba_idPrueba INT,
    User_idUser INT,
    Nota_Final DOUBLE NOT NULL,
    FOREIGN KEY (Participante_idParticipante) REFERENCES Participante(idParticipante),
    FOREIGN KEY (Prueba_idPrueba) REFERENCES Prueba(idPrueba),
    FOREIGN KEY (User_idUser) REFERENCES User(idUser)
);

-- Tabla Item
CREATE TABLE Item (
    idItem INT AUTO_INCREMENT PRIMARY KEY,
    Descripcion VARCHAR(200) NOT NULL,
    Peso INT NOT NULL,
    Grados_Consecucion INT NOT NULL,
    Prueba_idPrueba INT,
    FOREIGN KEY (Prueba_idPrueba) REFERENCES Prueba(idPrueba)
);

-- Tabla Evaluacion_Item
CREATE TABLE Evaluacion_Item (
    idEvaluacionItem INT AUTO_INCREMENT PRIMARY KEY,
    Evaluacion_idEvaluacion INT,
    Item_idItem INT,
    Valoracion INT NOT NULL,
    Explicacion VARCHAR(200),
    FOREIGN KEY (Evaluacion_idEvaluacion) REFERENCES Evaluacion(idEvaluacion),
    FOREIGN KEY (Item_idItem) REFERENCES Item(idItem)
);

-- ================================
--  SCRIPT DE CREATION DES TABLES
--        (jarialtekin_db)
-- ================================
--
-- Ordre de création des tables :
--   (1) : Priorities
--   (2) : Statuses
--   (3) : Tasks
--   (4) : ParentTasks
--   (5) : TasksDependencies
--   (6) : TasksPlannings
--   (7) : Projects
--   (8) : ProjectsTasks


-- Suppression des tables (si elles existent) avant leur création.
-- RMQ : les tables sont supprimées dans l'ordre inverse de leur création.
DROP TABLE IF EXISTS ProjectsTasks;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS TasksPlannings;
DROP TABLE IF EXISTS TasksDependencies;
DROP TABLE IF EXISTS ParentTasks;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS Statuses;
DROP TABLE IF EXISTS Priorities;


-- { Création de la table Statuses }
--
-- Attributs :
--   id (INT) : identifiant unique de la priorité
--   label (VARCHAR) : label de la priorité
--
-- Contraintes :
--   pk_priorities : (id) est la clé primaire de la table Priorities
--
CREATE OR REPLACE TABLE Priorities (

    id     INT AUTO_INCREMENT,
    label  VARCHAR(255) NOT NULL,

    CONSTRAINT pk_priorities PRIMARY KEY (id)
);
-- Valeurs initiales :
INSERT INTO Priorities (id, label) VALUES (-1, 'LOW');
INSERT INTO Priorities (id, label) VALUES (0, 'NORMAL');
INSERT INTO Priorities (id, label) VALUES (1, 'HIGH');


-- { Création de la table Statuses }
--
-- Attributs :
--   id (INT) : identifiant unique du statut
--   label (VARCHAR) : label du statut
--
-- Contraintes :
--   pk_statuses : (id) est la clé primaire de la table Statuses
--
CREATE OR REPLACE TABLE Statuses (

    id     INT AUTO_INCREMENT,
    label  VARCHAR(255) NOT NULL,

    CONSTRAINT pk_statuses PRIMARY KEY (id)
);
-- Valeurs initiales :
INSERT INTO Statuses (id, label) VALUES (-1, 'NONE');
INSERT INTO Statuses (id, label) VALUES (0, 'TODO');
INSERT INTO Statuses (id, label) VALUES (1, 'DOING');
INSERT INTO Statuses (id, label) VALUES (2, 'DONE');


-- { Création de la table Tasks }
--
-- Attributs :
--   id (INT) : identifiant unique de la tâche
--   name (VARCHAR) : nom de la tâche
--   description (TEXT) : texte descriptif de la tâche
--   idPriority (INT) : identifiant de la priorité de la tâche
--   idStatus (INT) : identifiant du statut de la tâche
--
-- Contraintes :
--   pk_tasks : (id) est la clé primaire de la table Tasks
--   fk_tasks_01 : (idPriority) fait référence à Priorities(id)
--   fk_tasks_02 : (idStatus) fait référence à Statuses(id)
--
CREATE OR REPLACE TABLE Tasks (

	id           INT AUTO_INCREMENT,
	name         VARCHAR(255) NOT NULL,
	description  TEXT,
	idPriority   INT DEFAULT 0,
	idStatus     INT DEFAULT -1,

	CONSTRAINT pk_tasks PRIMARY KEY (id),
	CONSTRAINT fk_tasks_01 FOREIGN KEY (idPriority) REFERENCES Priorities(id),
	CONSTRAINT fk_tasks_02 FOREIGN KEY (idStatus) REFERENCES Statuses(id)
);


-- { Création de la table ParentTasks }
--
-- Attributs :
--   idTask (INT) : identifiant d'une tâche
--   idParent (INT) : identifiant de la tâche parente de cette tâche
--
-- Contraintes :
--   pk_parentTasks : (idTask) est la clé primaire de la table ParentTasks
--   fk_parentTasks_01 : (idTask) fait référence à Tasks(id)
--   fk_parentTasks_02 : (idParent) fait référence à Tasks(id)
--
CREATE OR REPLACE TABLE ParentTasks (

	idTask    INT NOT NULL,
	idParent  INT NOT NULL,

	CONSTRAINT pk_parentTasks PRIMARY KEY (idTask),
	CONSTRAINT fk_parentTasks_01 FOREIGN KEY (idTask) REFERENCES Tasks(id),
	CONSTRAINT fk_parentTasks_02 FOREIGN KEY (idParent) REFERENCES Tasks(id)
	-- CONSTRAINT ck_parentTasks_01 CHECK (isValidParent(idTask, idParent))
);


-- { Création de la table TasksDependencies }
--
-- Attributs :
--   idTask (INT) : identifiant d'une tâche
--   idDependency (INT) : identifiant de la tâche dont dépend cette tâche
--
-- Contraintes :
--   pk_tasksDependencies : (idTask, idDependency) est la clé primaire de la table TasksDependencies
--   fk_tasksDependencies_01 : (idTask) fait référence à Tasks(id)
--   fk_tasksDependencies_02 : (idDependency) fait référence à Tasks(id)
--
CREATE OR REPLACE TABLE TasksDependencies (

	idTask        INT NOT NULL,
	idDependency  INT NOT NULL,

	CONSTRAINT pk_tasksDependencies PRIMARY KEY (idTask, idDependency),
	CONSTRAINT fk_tasksDependencies_01 FOREIGN KEY (idTask) REFERENCES Tasks(id),
	CONSTRAINT fk_tasksDependencies_02 FOREIGN KEY (idDependency) REFERENCES Tasks(id)
	-- CONSTRAINT ck_tasksDependencies_01 CHECK (isValidDependency(idTask, idDependency))
);


-- { Création de la table TasksPlannings }
--
-- Attributs :
--   idTask (INT) : identifiant d'une tâche
--   startDateTime (DATETIME) : date et heure de début de la tâche
--   endDateTime (DATETIME) : date et heure de fin de la tâche
--
-- Contraintes :
--   pk_tasksPlannings : (idTask) est la clé primaire de la table TasksPlannings
--   fk_tasksPlannings_01 : (idTask) fait référence à Tasks(id)
--   ck_tasksPlannings_01 : vérifie que la date/heure de fin est supérieure ou égale à date/heure de début
--
CREATE OR REPLACE TABLE TasksPlannings (

	idTask     INT NOT NULL,
	startDate  DATE NOT NULL,
	startTime  TIME NOT NULL,
	endDate    DATE NOT NULL,
	endTime    TIME NOT NULL,

	CONSTRAINT pk_tasksPlannings PRIMARY KEY (idTask),
	CONSTRAINT fk_tasksPlannings_01 FOREIGN KEY (idTask) REFERENCES Tasks(id),
	CONSTRAINT ck_tasksPlannings_01 CHECK (datediff(endDate, startDate)>0 or (datediff(endDate, startDate)=0 and time_to_sec(timediff(endTime, startTime))>=0))
);


-- { Création de la table Projects }
--
-- Attributs :
--   id (INT) : identifiant unique du projet
--   name (VARCHAR) : nom du projet
--   description (TEXT) : texte descriptif du projet
--   startDate (DATE) : date de début du projet
--   endDate (DATE) : date de fin du projet
--
-- Contraintes :
--   pk_projects : (id) est la clé primaire de la table Projects
--   ck_projects_01 : vérifie que (endDate) est supérieure ou égale à (startDate)
--
CREATE OR REPLACE TABLE Projects (

	id           INT AUTO_INCREMENT,
	name         VARCHAR(255) NOT NULL,
	description  TEXT,
	startDate    DATE,
	endDate      DATE,

	CONSTRAINT pk_projects PRIMARY KEY (id),
	CONSTRAINT ck_projects_01 CHECK (startDate is NULL or endDate is NULL or datediff(endDate, startDate)>=0)
);


-- { Création de la table ProjectsTasks }
--
-- Attributs :
--   idProject (INT) : identifiant d'un projet
--   idTask (INT) : identifiant d'une tâche
--
-- Contraintes :
--   pk_projectsTasks : (idProject, idTask) est la clé primaire de la table ProjectsTasks
--   fk_projectsTasks_01 : (idProject) fait référence à Projects(id)
--   fk_projectsTasks_02 : (idTask) fait référence à Tasks(id)
--
CREATE OR REPLACE TABLE ProjectsTasks (

	idProject  INT NOT NULL,
	idTask     INT NOT NULL,

	CONSTRAINT pk_projectsTasks PRIMARY KEY (idProject, idTask),
	CONSTRAINT fk_projectsTasks_01 FOREIGN KEY (idProject) REFERENCES Projects(id),
	CONSTRAINT fk_projectsTasks_02 FOREIGN KEY (idTask) REFERENCES Tasks(id)
);

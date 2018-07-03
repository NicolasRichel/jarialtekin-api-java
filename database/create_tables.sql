-- ================================
--  SCRIPT DE CREATION DES TABLES
--        (jarialtekin_db)
-- ================================
--
-- Ordre de création des tables :
--   (1) : Tasks
--   (2) : ParentTasks
--   (3) : TasksDependencies
--   (4) : TasksPlannings
--   (5) : Projects
--   (6) : ProjectsTasks


-- Suppression des tables (si elles existent) avant leur création.
-- RMQ : les tables sont supprimées dans l'ordre inverse de leur création.
DROP TABLE IF EXISTS ProjectsTasks;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS TasksPlannings;
DROP TABLE IF EXISTS TasksDependencies;
DROP TABLE IF EXISTS ParentTasks;
DROP TABLE IF EXISTS Tasks;


-- { Création de la table Tasks }
--
-- Attributs :
--   id (INT) : identifiant unique de la tâche
--   name (VARCHAR) : nom de la tâche
--   description (TEXT) : texte descriptif de la tâche
--   priority (INT) : niveau de priorité de la tâche
--   status (INT) : status de la tâche
--
-- Contraintes :
--   pk_tasks : (id) est la clé primaire de la table Tasks
--
CREATE OR REPLACE TABLE Tasks (
-- Attributs :
	id           INT AUTO_INCREMENT,
	name         VARCHAR(255) NOT NULL,
	description  TEXT,
	priority     INT,
	status       INT,
-- Contraintes :
	CONSTRAINT pk_tasks PRIMARY KEY (id)
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
-- Attributs :
	idTask    INT NOT NULL,
	idParent  INT NOT NULL,
-- Contraintes :
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
-- Attributs :
	idTask        INT NOT NULL,
	idDependency  INT NOT NULL,
-- Contraintes :
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
-- Attributs :
	idTask     INT NOT NULL,
	startDate  DATE NOT NULL,
	startTime  TIME NOT NULL,
	endDate    DATE NOT NULL,
	endTime    TIME NOT NULL,
-- Contraintes :
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
-- Attributs :
	id           INT AUTO_INCREMENT,
	name         VARCHAR(255) NOT NULL,
	description  TEXT,
	startDate    DATE,
	endDate      DATE,
-- Contraintes :
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
-- Attributs :
	idProject  INT NOT NULL,
	idTask     INT NOT NULL,
-- Contraintes :
	CONSTRAINT pk_projectsTasks PRIMARY KEY (idProject, idTask),
	CONSTRAINT fk_projectsTasks_01 FOREIGN KEY (idProject) REFERENCES Projects(id),
	CONSTRAINT fk_projectsTasks_02 FOREIGN KEY (idTask) REFERENCES Tasks(id)
);

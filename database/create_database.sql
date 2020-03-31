-- =======================
--  SCRIPT DE CREATION DE
--   LA BASE DE DONNEES
--     JARIALTEKIN_DB
-- =======================


-- !!![[ This script will drop 'jarialtekin_db' if it already exists ]]!!!


-- Database creation
-- ~~~~~~~~~~~~~~~~~

DROP DATABASE IF EXISTS jarialtekin_db;
CREATE DATABASE jarialtekin_db CHARACTER SET 'utf8';
USE jarialtekin_db;


-- Tables creation
-- ~~~~~~~~~~~~~~~

CREATE OR REPLACE TABLE Priorities (
    id     INT AUTO_INCREMENT,
    label  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_priorities PRIMARY KEY (id)
);
INSERT INTO Priorities (id, label) VALUES (-1, 'LOW');
INSERT INTO Priorities (id, label) VALUES (0, 'NORMAL');
INSERT INTO Priorities (id, label) VALUES (1, 'HIGH');

CREATE OR REPLACE TABLE Statuses (
    id     INT AUTO_INCREMENT,
    label  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_statuses PRIMARY KEY (id)
);
INSERT INTO Statuses (id, label) VALUES (-1, 'NONE');
INSERT INTO Statuses (id, label) VALUES (0, 'TODO');
INSERT INTO Statuses (id, label) VALUES (1, 'DOING');
INSERT INTO Statuses (id, label) VALUES (2, 'DONE');

CREATE TABLE Tasks (
	id           INT AUTO_INCREMENT,
	name         VARCHAR(255) NOT NULL,
	description  TEXT,
	priority     INT DEFAULT 0,
	status       INT DEFAULT 0,
	CONSTRAINT pk_tasks PRIMARY KEY (id),
	CONSTRAINT fk_tasks_01 FOREIGN KEY (idPriority) REFERENCES Priorities(id),
    CONSTRAINT fk_tasks_02 FOREIGN KEY (idStatus) REFERENCES Statuses(id)
);

CREATE TABLE ParentTasks (
	idTask    INT NOT NULL,
	idParent  INT NOT NULL,
	CONSTRAINT pk_parentTasks PRIMARY KEY (idTask),
	CONSTRAINT fk_parentTasks_01 FOREIGN KEY (idTask) REFERENCES Tasks(id),
	CONSTRAINT fk_parentTasks_02 FOREIGN KEY (idParent) REFERENCES Tasks(id)
	-- CONSTRAINT ck_parentTasks_01 CHECK (isValidParent(idTask, idParent))
);

CREATE TABLE TasksDependencies (
	idTask        INT NOT NULL,
	idDependency  INT NOT NULL,
	CONSTRAINT pk_tasksDependencies PRIMARY KEY (idTask, idDependency),
	CONSTRAINT fk_tasksDependencies_01 FOREIGN KEY (idTask) REFERENCES Tasks(id),
	CONSTRAINT fk_tasksDependencies_02 FOREIGN KEY (idDependency) REFERENCES Tasks(id)
	-- CONSTRAINT ck_tasksDependencies_01 CHECK (isValidDependency(idTask, idDependency))
);

CREATE TABLE TasksPlannings (
	idTask     INT NOT NULL,
	startDate  DATE NOT NULL,
	startTime  TIME NOT NULL,
	endDate    DATE NOT NULL,
	endTime    TIME NOT NULL,
	CONSTRAINT pk_tasksPlannings PRIMARY KEY (idTask),
	CONSTRAINT fk_tasksPlannings_01 FOREIGN KEY (idTask) REFERENCES Tasks(id),
	CONSTRAINT ck_tasksPlannings_01 CHECK (datediff(endDate, startDate)>0 or (datediff(endDate, startDate)=0 and time_to_sec(timediff(endTime, startTime))>=0))
);

CREATE TABLE Projects (
	id           INT AUTO_INCREMENT,
	name         VARCHAR(255) NOT NULL,
	description  TEXT,
	startDate    DATE,
	endDate      DATE,
	CONSTRAINT pk_projects PRIMARY KEY (id),
	CONSTRAINT ck_projects_01 CHECK (startDate is NULL or endDate is NULL or datediff(endDate, startDate)>=0)
);

CREATE TABLE ProjectsTasks (
	idProject  INT NOT NULL,
	idTask     INT NOT NULL,
	CONSTRAINT pk_projectsTasks PRIMARY KEY (idProject, idTask),
	CONSTRAINT fk_projectsTasks_01 FOREIGN KEY (idProject) REFERENCES Projects(id),
	CONSTRAINT fk_projectsTasks_02 FOREIGN KEY (idTask) REFERENCES Tasks(id)
);


-- Views creation
-- ~~~~~~~~~~~~~~

CREATE VIEW TaskLevels (lvl, idNode) AS
WITH RECURSIVE Tree AS (
	SELECT 1 AS lvl, p.idParent, t.id AS idNode
		FROM Tasks t
		LEFT JOIN ParentTasks p ON p.idTask=t.id
		WHERE p.idParent IS NULL
	UNION ALL
	SELECT r.lvl+1 AS lvl, p.idParent, t.id AS idNode
		FROM Tasks t
		JOIN ParentTasks p ON p.idTask=t.id
		JOIN Tree r ON r.idNode=p.idParent
)
SELECT lvl, idNode FROM Tree ORDER BY lvl;

SET standard_compliant_cte=0;
CREATE VIEW TaskTree (lvl, idNode, idParent, dist) AS
WITH RECURSIVE Tree AS (
	SELECT lvl, idNode, idParent, CAST(idParent/idParent AS INT) AS dist
		FROM (SELECT * FROM TaskLevels l LEFT JOIN ParentTasks p ON p.idTask=l.idNode) AS base
	UNION
	SELECT r2.lvl, r2.idNode, r3.idParent, r2.dist+1 AS dist
		FROM (SELECT MAX(dist) AS maxDist FROM Tree GROUP BY idNode) AS r1
		JOIN  Tree AS r2 ON r2.dist=r1.maxDist
		JOIN (SELECT idNode, idParent FROM Tree WHERE dist=1) AS r3 ON r3.idNode=r2.idParent
)
SELECT * FROM Tree ORDER BY lvl, idNode, dist;
SET standard_compliant_cte=1;

SET standard_compliant_cte=0;
CREATE VIEW DependencyGraph (idNode, idDependency, dist) AS
WITH RECURSIVE Graph AS (
	SELECT id AS idNode, idDependency, CAST(idDependency/idDependency AS INT) AS dist
		FROM (SELECT * FROM Tasks t LEFT JOIN TasksDependencies d ON d.idTask=t.id) AS base
	UNION
	SELECT r2.idNode, r3.idDependency, r2.dist+1 AS dist
		FROM (SELECT MAX(dist) AS maxDist FROM Graph GROUP BY idNode) AS r1
		JOIN  Graph AS r2 ON r2.dist=r1.maxDist
		JOIN (SELECT idNode, idDependency FROM Graph WHERE dist=1) AS r3 ON r3.idNode=r2.idDependency
)
SELECT * FROM Graph ORDER BY idNode, dist;
SET standard_compliant_cte=1;


-- Functions creation
-- ~~~~~~~~~~~~~~~~~~

DELIMITER //
CREATE FUNCTION isValidParent(task INT, parent INT)
RETURNS TINYINT DETERMINISTIC
BEGIN
	SET standard_compliant_cte=0;
	IF NOT EXISTS (SELECT * FROM TaskTree WHERE idNode=parent AND idParent=task) THEN
		IF NOT EXISTS (SELECT * FROM DependencyGraph WHERE idNode=parent AND idDependency=task) THEN
			SET standard_compliant_cte=1;
			RETURN 1;
		END IF;
	END IF;
	SET standard_compliant_cte=1;
	RETURN 0;
END //
DELIMITER ;

DELIMITER //
CREATE FUNCTION isValidDependency(task INT, dependency INT)
RETURNS TINYINT DETERMINISTIC
BEGIN
	SET standard_compliant_cte=0;
	IF NOT EXISTS (SELECT * FROM DependencyGraph WHERE idNode=dependency AND idDependency=task) THEN
		IF NOT EXISTS (SELECT * FROM TasksTree WHERE idNode=dependency AND idParent=task) THEN
			SET standard_compliant_cte=1;
			RETURN 1;
		END IF;
	END IF;
	SET standard_compliant_cte=1;
	RETURN 0;
END //
DELIMITER ;

-- =============================
--  SCRIPT DE CREATION DES VUES
--       (jarialtekin_db)
-- =============================


-- Suppression des vues (si elles existent) avant leur création.
DROP VIEW IF EXISTS TaskLevels;
DROP VIEW IF EXISTS TaskTree;
DROP VIEW IF EXISTS DependencyGraph;


-- { Création de la vue TaskLevels }
--
-- Cette vue permet d'obtenir le niveau de chaque tâche
-- dans l'arbre des tâches.
--
-- Attributs :
--   lvl (INT) : le niveau (ou la profondeur) du noeud
--   idNode (INT) : l'identifiant du noeud
--
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


-- { Création de la vue TaskTree }
--
-- Cette vue décrit l'arbre des tâche en indiquant la
-- liste des parents de chaque tâche avec pour chaque
-- parent la distance entre ce parent et la tâche fille.
--
-- Attributs :
--   lvl (INT) : le niveau du noeud dans l'arbre
--   idNode (INT) : l'id du noeud
--   idParent (INT) : l'id du parent
--   dist (INT) : la distance (en nombre de noeud) entre le noeud et son parent
--
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


-- { Création de la vue DependencyGraph }
--
-- Cette vue décrit le graphe des dépendances entre tâches
-- en indiquant pour chaque tâche la liste de ces dépendances
-- avec pour chaque dépendance la distance entre cette dépen-
-- dance et la tâche dépendante.
--
-- Attributs :
--   idNode (INT) : l'id du noeud
--   idDependency (INT) : l'id de la dépendance
--   dist (INT) : la distance (en nombre de noeud) entre le noeud et sa dépendance
--
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

-- ==================================
--  SCRIPT DE CREATION DES FONCTIONS
--         (jarialtekin_db)
-- ==================================


-- Suppression des fonctions (si elles existent) avant leur création.
DROP FUNCTION IF EXISTS isValidParent;
DROP FUNCTION IF EXISTS isValidDependency;


-- { Création de la fonction isValidParent }
--
-- Indique si la tâche dont l'id est 'parent' peut être une
-- parente valide de la tâche dont l'id est 'task' ou non.
--
-- Paramètres :
--   task (INT) : id de la tâche par rapport à laquelle on teste
--   parent (INT) : id de la tâche à tester
--
DELIMITER //
CREATE FUNCTION isValidParent(task INT, parent INT)
RETURNS TINYINT DETERMINISTIC
BEGIN
	SET standard_compliant_cte=0;
	-- If the child task is not already higher in the task tree
	IF NOT EXISTS (SELECT * FROM TaskTree WHERE idNode=parent AND idParent=task) THEN
		-- And if the parent task does not depends on the child task
		IF NOT EXISTS (SELECT * FROM DependencyGraph WHERE idNode=parent AND idDependency=task) THEN
			-- Then its OK
			SET standard_compliant_cte=1;
			RETURN 1;
		END IF;
	END IF;
	-- Otherwise there is a problem
	SET standard_compliant_cte=1;
	RETURN 0;
END //
DELIMITER ;


-- { Création de la fonction isValidDependency }
--
-- Indique si la tâche dont l'id est 'dependency' peut être une
-- dépendance valide de la tâche dont l'id est 'task' ou non.
--
-- Paramètres :
--   task (INT) : id de la tâche par rapport à laquelle on teste
--   dependency (INT) : id de la tâche à tester
--
DELIMITER //
CREATE FUNCTION isValidDependency(task INT, dependency INT)
RETURNS TINYINT DETERMINISTIC
BEGIN
	SET standard_compliant_cte=0;
	-- If the task does not depends on a task that depends on it
	IF NOT EXISTS (SELECT * FROM DependencyGraph WHERE idNode=dependency AND idDependency=task) THEN
		-- And if the task does not depends on one of its child
		IF NOT EXISTS (SELECT * FROM TasksTree WHERE idNode=dependency AND idParent=task) THEN
			-- Then its OK
			SET standard_compliant_cte=1;
			RETURN 1;
		END IF;
	END IF;
	-- Otherwise there is a problem
	SET standard_compliant_cte=1;
	RETURN 0;
END //
DELIMITER ;

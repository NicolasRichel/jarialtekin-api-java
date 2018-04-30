-- =============================
--  DB DATA FOR [ TaskDAOTest ]
-- =============================

-- Tasks :
INSERT INTO Tasks (id, name, description, priority, status) VALUES (1, 'T1', 'Une tâche élémentaire', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (2, 'T2', 'Une tâche compliquée', 0, 1);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (3, 'T3', 'Une tâche importante', 1, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (4, 'T4', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (5, 'T5', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (6, 'T6', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (7, 'T7', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (8, 'T8', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (9, 'T9', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (10, 'T10', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (11, 'T11', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (12, 'T12', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (13, 'T13', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (14, 'T14', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (15, 'T15', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (16, 'T16', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (17, 'T17', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (18, 'T18', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (19, 'T19', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (20, 'T20', null, null, null);

-- ParentTasks :
INSERT INTO ParentTasks (idTask, idParent) VALUES (2, 1);
INSERT INTO ParentTasks (idTask, idParent) VALUES (3, 1);
INSERT INTO ParentTasks (idTask, idParent) VALUES (4, 1);
INSERT INTO ParentTasks (idTask, idParent) VALUES (5, 4);
INSERT INTO ParentTasks (idTask, idParent) VALUES (6, 4);
INSERT INTO ParentTasks (idTask, idParent) VALUES (8, 7);

-- TasksDependencies :
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (4, 3);
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (11, 10);
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (12, 11);
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (12, 14);
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (13, 11);
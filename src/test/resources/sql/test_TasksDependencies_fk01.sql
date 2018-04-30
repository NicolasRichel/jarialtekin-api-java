-- SQL script to test [ fk_tasksDependencies_01 ] constraint
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (2, 1);
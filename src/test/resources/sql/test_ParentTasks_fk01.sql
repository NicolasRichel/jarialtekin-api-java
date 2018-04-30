-- SQL script to test [ fk_parentTasks_01 ] constraint
INSERT INTO Tasks (id, name) VALUES (1, 'T1');
INSERT INTO ParentTasks (idTask, idParent) VALUES (2, 1);
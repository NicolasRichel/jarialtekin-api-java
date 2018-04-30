-- SQL script to test that idTask can not be null
INSERT INTO Tasks (id , name) VALUES (1, 'T1');
INSERT INTO ParentTasks (idTask, idParent) VALUES (null, 1);
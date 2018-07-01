-- ==================================
--  SCRIPT DE PEUPLEMENT DES TABLES
--       POUR LE DEVELOPPEMENT
--         (jarialtekin_db)
-- ==================================


-- Ré-initialisation des tables avant le peuplement.
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE Tasks;
TRUNCATE TABLE ParentTasks;
TRUNCATE TABLE TasksDependencies;
TRUNCATE TABLE TasksPlannings;
TRUNCATE TABLE Projects;
TRUNCATE TABLE ProjectsTasks;
SET FOREIGN_KEY_CHECKS=1;


-- { Peuplement de la table Tasks }
INSERT INTO Tasks (id, name, description, priority, status) VALUES (1, 'T1', 'Développement', 0, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (2, 'T2', 'Description', 1, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (3, 'T3', 'Tâche importante', 2, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (4, 'T4', '', 0, 1);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (5, 'T5', '', 0, 2);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (6, 'T6', '', 0, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (7, 'T7', '', null, 0);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (8, 'T8 : Tâche avec un nom très long, très très long, très très très long, très très très très long, très très très très très long, très très très très très très long!', null, null, null);
INSERT INTO Tasks (id, name, description, priority, status) VALUES (9, 'T9 : Tâche avec une description très longue', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed massa erat, rhoncus sit amet quam a, faucibus feugiat dui. Etiam facilisis dignissim nisl, at condimentum purus porta vitae. Maecenas gravida eros faucibus ante posuere commodo. Proin a nunc a lacus rutrum vehicula et et enim. Nullam in magna at massa tincidunt consectetur id mattis felis. Suspendisse semper semper odio sit amet luctus. Integer mollis, lacus mattis auctor rhoncus, arcu augue suscipit sem, ut sodales ligula dui in dui. Vivamus vitae neque orci. Cras condimentum vitae nulla at molestie. Donec luctus turpis vitae arcu tincidunt porta at mollis lacus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam lorem lectus, efficitur ac metus consequat, elementum pulvinar justo. Nullam nec sollicitudin nisi, at lacinia urna. Pellentesque lobortis leo in leo pulvinar, ac sollicitudin urna sodales. Aenean eu dapibus massa, quis eleifend neque. Cras purus sapien, blandit sit amet urna sit amet, cursus pretium nisi. Donec nisi ante, elementum et venenatis non, blandit condimentum turpis. Fusce vel est sit amet ante dictum vestibulum. Quisque non vulputate nisl, quis iaculis purus. Proin ac turpis enim. Fusce ut suscipit libero. In rhoncus ligula vel lacus suscipit, id ultricies risus venenatis. Etiam at aliquet sem. Phasellus egestas porttitor lacus, non blandit arcu ullamcorper sed. Phasellus enim quam, aliquam nec ullamcorper sit amet, ornare ac lorem. In et pellentesque ex. Nulla ornare risus a tempus auctor. Vivamus eget ipsum euismod, hendrerit lacus quis, tristique risus. Nam nec mi in arcu rutrum efficitur eget quis augue. Nam sed orci odio. In laoreet bibendum erat, et vulputate arcu posuere in. Integer fringilla id arcu vitae mattis. Nullam faucibus enim non lobortis semper. In convallis maximus dapibus. Suspendisse a nisl a eros luctus convallis. Donec luctus elementum convallis. Praesent consectetur laoreet sem. Nulla mollis dui tincidunt, aliquet diam eu, consequat felis. Integer vehicula id felis laoreet sagittis. Praesent laoreet malesuada nisl, quis commodo velit fringilla vel. Integer neque nulla, tincidunt rhoncus nulla vitae, molestie fermentum elit. In eu volutpat magna, et accumsan ex. Aliquam ac elit suscipit, gravida quam eu, fringilla est. Maecenas a eros non neque elementum consectetur. Quisque pellentesque tortor ac dolor finibus, a vestibulum leo aliquet. Donec convallis ultrices metus, in malesuada tellus viverra quis. Quisque dignissim, nibh in sollicitudin mollis, arcu justo tincidunt tellus, ut ultrices sapien nisl ac orci. Aliquam feugiat condimentum ipsum cursus hendrerit. Aenean fringilla metus a odio viverra dignissim. Ut nisi purus, varius eu egestas nec, tempor in nisi. Integer non laoreet sapien. Integer vestibulum lectus sed massa rhoncus dictum. Nunc tempor volutpat diam, quis posuere massa dignissim id. Nam auctor nibh ut blandit pellentesque. Vivamus feugiat, elit sit amet dictum maximus, purus urna rhoncus lectus, eget facilisis massa nulla at nisi. Mauris tincidunt tellus ac odio faucibus rutrum non vitae ligula. Curabitur scelerisque quis risus non mattis. Vivamus a condimentum ante. Donec sollicitudin id purus et consequat. Donec in ultricies arcu, at maximus tellus. Aliquam erat volutpat. Quisque tempus auctor felis, quis maximus purus auctor sed. Ut facilisis commodo sapien at interdum. Donec hendrerit lacinia nibh a placerat. Morbi vel purus lacinia, ullamcorper diam vitae, condimentum mauris. Morbi at porta turpis. Etiam luctus consequat est, eu accumsan tortor. Donec ut lectus lorem. Duis ac volutpat nibh, in pretium augue. Etiam faucibus lacus porta orci iaculis vulputate. Nunc in ante iaculis, egestas urna eget, aliquet nisl. Integer ultricies et libero tempus viverra. Suspendisse porta libero ac lectus eleifend, et pharetra nunc viverra.', null, null);
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


-- { Peuplement de la table ParentTasks }
INSERT INTO ParentTasks (idTask, idParent) VALUES (12, 11);
INSERT INTO ParentTasks (idTask, idParent) VALUES (13, 11);
INSERT INTO ParentTasks (idTask, idParent) VALUES (14, 11);
INSERT INTO ParentTasks (idTask, idParent) VALUES (15, 12);
INSERT INTO ParentTasks (idTask, idParent) VALUES (16, 12);
INSERT INTO ParentTasks (idTask, idParent) VALUES (18, 17);
INSERT INTO ParentTasks (idTask, idParent) VALUES (19, 17);
INSERT INTO ParentTasks (idTask, idParent) VALUES (20, 18);


-- { Peuplement de la table TasksDependencies }
INSERT INTO TasksDependencies (idTask, idDependency) VALUES (5, 4);


-- { Peuplement de la table TasksPlannings }
INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (4, '2018-01-02', '09:30:00', '2018-01-02', '12:00:00');


-- { Peuplement de la table Projects }
INSERT INTO Projects (id, name, description, startDate, endDate) VALUES (1, 'P1', null, null, null);


-- { Peuplement de la table ProjectsTasks }
INSERT INTO ProjectsTasks (idProject, idTask) VALUES (1, 3);

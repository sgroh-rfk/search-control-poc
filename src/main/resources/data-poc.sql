-- Users
INSERT INTO user (id, username, email, enabled)  VALUES (1, 'sgroh', 'sebastian.groh@reflektion.com', true) ;
INSERT INTO user (id, username, email, enabled)  VALUES (2, 'apersson', 'agustin.persson@reflektion.com', true);
INSERT INTO user (id, username, email, enabled)  VALUES (3, 'jdoe', 'jon.doe@reflektion.com', true);
--
-- Roles --
--
INSERT INTO role (id, name)  VALUES (1, 'Basic');
INSERT INTO role (id, name)  VALUES (2, 'Expert');
INSERT INTO role (id, name)  VALUES (3, 'Admin');
--
-- User-Roles --
--
INSERT INTO user_roles (user_id, role_id)  VALUES (1, 3);
INSERT INTO user_roles (user_id, role_id)  VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id)  VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id)  VALUES (3, 1);
--
-- Permission --
--
INSERT INTO permission (id, permission)  VALUES (1, '000');
INSERT INTO permission (id, permission)  VALUES (2, '010');
INSERT INTO permission (id, permission)  VALUES (3, '011');
INSERT INTO permission (id, permission)  VALUES (4, '100');
INSERT INTO permission (id, permission)  VALUES (5, '101');
INSERT INTO permission (id, permission)  VALUES (6, '110');
INSERT INTO permission (id, permission)  VALUES (7, '111');
--
-- Key --
--
INSERT INTO keytable (id, description, name, `schema`, parent_id )  VALUES (1, '--', 'noname', null, null);
--
-- Permission - roles --
--
INSERT INTO permission_role (role_id, permission_id)  VALUES (3, 7);
INSERT INTO permission_role (role_id, permission_id)  VALUES (2, 6);
INSERT INTO permission_role (role_id, permission_id)  VALUES (1, 2);
--
-- Permission - keys --
--
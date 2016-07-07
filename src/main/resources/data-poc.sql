-- Users
INSERT IGNORE INTO user (id, username, email, enabled)  VALUES (1, 'sgroh', 'sebastian.groh@reflektion.com', true) ;
INSERT IGNORE INTO user (id, username, email, enabled)  VALUES (2, 'apersson', 'agustin.persson@reflektion.com', true);
INSERT IGNORE INTO user (id, username, email, enabled)  VALUES (3, 'jdoe', 'jon.doe@reflektion.com', true);
--
-- Roles --
--
INSERT IGNORE INTO role (id, name)  VALUES (1, 'Basic');
INSERT IGNORE INTO role (id, name)  VALUES (2, 'Expert');
INSERT IGNORE INTO role (id, name)  VALUES (3, 'Admin');
--
-- User-Roles --
--
INSERT IGNORE INTO user_roles (user_id, role_id)  VALUES (1, 3);
INSERT IGNORE INTO user_roles (user_id, role_id)  VALUES (1, 2);
INSERT IGNORE INTO user_roles (user_id, role_id)  VALUES (2, 2);
INSERT IGNORE INTO user_roles (user_id, role_id)  VALUES (3, 1);
--
-- Permission --
--
INSERT IGNORE INTO permission (id, permission)  VALUES (1, '000');
INSERT IGNORE INTO permission (id, permission)  VALUES (2, '010');
INSERT IGNORE INTO permission (id, permission)  VALUES (3, '011');
INSERT IGNORE INTO permission (id, permission)  VALUES (4, '100');
INSERT IGNORE INTO permission (id, permission)  VALUES (5, '101');
INSERT IGNORE INTO permission (id, permission)  VALUES (6, '110');
INSERT IGNORE INTO permission (id, permission)  VALUES (7, '111');
--
-- Key --
--
INSERT IGNORE INTO keytable (id, description, name, `schema`, parent_id )  VALUES (1, '--', 'noname', null, null);
--
-- Permission - roles --
--
INSERT IGNORE INTO permission_role (role_id, permission_id)  VALUES (3, 7);
INSERT IGNORE INTO permission_role (role_id, permission_id)  VALUES (2, 6);
INSERT IGNORE INTO permission_role (role_id, permission_id)  VALUES (1, 2);
--
-- Permission - keys --
--
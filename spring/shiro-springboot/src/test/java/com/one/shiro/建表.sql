DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`permission` varchar(128) NOT NULL,
`role_name` varchar(128) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES ('1', 'user:add', '超级管理员');
INSERT INTO `roles_permissions` VALUES ('2', 'user:update', '超级管理员');
INSERT INTO `roles_permissions` VALUES ('3', 'user:select', '运营');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(32) NOT NULL,
`password` varchar(32) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', 'admin');

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`role_name` varchar(128) NOT NULL,
`username` varchar(32) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '超级管理员', 'admin');
INSERT INTO `user_roles` VALUES ('2', '运营', 'admin');
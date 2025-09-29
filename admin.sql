CREATE TABLE `admin` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `password` varchar(255) DEFAULT NULL COMMENT '密码',
                         `username` varchar(255) DEFAULT NULL COMMENT '用户名',
                         `name` varchar(255) DEFAULT NULL COMMENT '昵称',
                         `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
                         `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
                         `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'ADMIN' COMMENT '角色',
                         `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `username_index` (`username`) USING BTREE COMMENT '账号'
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员信息\r\n';
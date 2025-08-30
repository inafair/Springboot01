CREATE TABLE `admin` (
                         `id` int NOT NULL,
                         `password` varchar(255) DEFAULT NULL COMMENT '密码',
                         `username` varchar(255) DEFAULT NULL COMMENT '用户名',
                         `name` varchar(255) DEFAULT NULL COMMENT '昵称',
                         `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
                         `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `username_index` (`username`) USING BTREE COMMENT '账号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员信息\r\n';
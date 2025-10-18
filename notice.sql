CREATE TABLE `notice` (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `title` varchar(255) DEFAULT NULL COMMENT '公告标题',
                          `content` varchar(255) DEFAULT NULL COMMENT '公告内容',
                          `time` varchar(255) DEFAULT NULL COMMENT '发布时间',
                          PRIMARY KEY (`id` DESC)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统公告表\r\n';
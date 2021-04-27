DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `account` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
    `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
    `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
    `count` int(11) DEFAULT '0',
    `locked` tinyint(1) DEFAULT '0',
    `enabled` tinyint(1) DEFAULT '1',
    `update_time` bigint(20) DEFAULT '0',
    `update_user` bigint(20) DEFAULT '0',
    `create_time` bigint(20) DEFAULT '0',
    `create_user` bigint(20) DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

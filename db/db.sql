DROP TABLE IF EXISTS `sky_user`;

CREATE TABLE IF NOT EXISTS `sky_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(256) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(64) NOT NULL COMMENT '秘钥',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`name`),
  KEY `idx_user_nick_name` (`nick_name`),
  KEY `idx_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';

DROP TABLE IF EXISTS `spider_parser_type`;

CREATE TABLE IF NOT EXISTS `spider_parser_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `just_admin` bigint(20) NOT NULL COMMENT '仅admin使用',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `key` varchar(256) DEFAULT NULL COMMENT 'key',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spider_parser`;

CREATE TABLE IF NOT EXISTS `spider_parser` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `url` varchar(256) DEFAULT NULL COMMENT '地址',
  `parser_type_id` bigint(20) NOT NULL COMMENT '爬虫类型Id',
  `spider_rule` text DEFAULT NULL COMMENT '爬虫规则',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spider_task`;

CREATE TABLE IF NOT EXISTS `spider_task` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `parser_id` bigint(20) NOT NULL COMMENT '爬虫Id',
  `url` varchar(256) DEFAULT NULL COMMENT '地址',
  `spider_rule` text DEFAULT NULL COMMENT '爬虫规则',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：0.待抓取，1.抓取中，2.抓取完成',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spider_data`;

CREATE TABLE IF NOT EXISTS `spider_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `parser_id` bigint(20) NOT NULL COMMENT '爬虫Id',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务Id',
  `type` tinyint(4) DEFAULT NULL COMMENT '1.视频, 2.图片, 3.文本, 4.标签',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态:0.已抓取, 1.等待下载数据, 2.下载数据完毕',
  `level` int(10) DEFAULT NULL,
  `content` text COMMENT '内容',
  `url` text CHARACTER SET utf8 COMMENT '链接',
  `source` text CHARACTER SET utf8 COMMENT '源链接',
  `path` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '文件路径',
  `unique_code` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '唯一约束',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
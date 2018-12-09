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
  `parser_type_id` bigint(20) NOT NULL COMMENT '爬虫类型Id',
  `spider_rule` text DEFAULT NULL COMMENT '爬虫规则',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spider_rules`;

CREATE TABLE IF NOT EXISTS `spider_rules` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `name` varchar(4096) DEFAULT NULL COMMENT '名称',
  `code` varchar(128) DEFAULT NULL,
  `key_words` varchar(256) DEFAULT NULL COMMENT '关键字',
  `url` varchar(256) DEFAULT NULL COMMENT '爬虫地址',
  `main_image` varchar(512) DEFAULT NULL COMMENT '主图',
  `parser_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '状态:0.初始化, 1.等待抓取数据, 2.抓取数据完毕，-1.抓取数据出错',
  `image_count` int(8) DEFAULT NULL,
  `video_count` int(8) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `uq_rules_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spider_data`;

CREATE TABLE IF NOT EXISTS `spider_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `rule_id` bigint(20) DEFAULT NULL COMMENT '规则编号',
  `type` tinyint(4) DEFAULT NULL COMMENT '1.视频, 2.图片, 3.文本, 4.标签',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态:0.已抓取, 1.等待下载数据, 2.下载数据完毕',
  `level` int(10) DEFAULT NULL,
  `content` text COMMENT '内容',
  `url` text COMMENT '链接',
  `source` text COMMENT '源链接',
  `path` varchar(256) DEFAULT NULL COMMENT '文件路径',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
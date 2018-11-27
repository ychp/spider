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

DROP TABLE IF EXISTS `sky_user_profile`;

CREATE TABLE IF NOT EXISTS `sky_user_profile` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `home_page` varchar(256) DEFAULT NULL COMMENT '主页',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `gender` varchar(8) DEFAULT NULL COMMENT '性别：male,female',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `country_id` int(9) DEFAULT NULL COMMENT '国家ID',
  `province_id` int(9) DEFAULT NULL COMMENT '省份ID',
  `city_id` int(9) DEFAULT NULL COMMENT '城市ID',
  `country` varchar(256) DEFAULT NULL COMMENT '国家',
  `province` varchar(256) DEFAULT NULL COMMENT '省份',
  `city` varchar(256) DEFAULT NULL COMMENT '城市',
  `synopsis` varchar(1024) DEFAULT NULL COMMENT '简介',
  `profile` varchar(512) DEFAULT NULL COMMENT '职业',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_profile_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户信息表';

DROP TABLE IF EXISTS `sky_address`;

CREATE TABLE IF NOT EXISTS `sky_address` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `level` int(11) DEFAULT NULL COMMENT '级别',
  `pinyin` varchar(128) DEFAULT NULL COMMENT '拼音',
  `english_name` varchar(128) DEFAULT NULL COMMENT '英文名',
  `unicode_code` varchar(256) DEFAULT NULL COMMENT 'ASCII码',
  PRIMARY KEY (`id`),
  KEY `idx_address_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '地址树表';

DROP TABLE IF EXISTS `sky_user_login_log`;

CREATE TABLE IF NOT EXISTS `sky_user_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `ip` varchar(256) NOT NULL COMMENT 'ip地址',
  `login_at` datetime NOT NULL,
  `logout_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_login_log_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户登录信息表';

DROP TABLE IF EXISTS `sky_category`;

CREATE TABLE IF NOT EXISTS `sky_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `name` varchar(256) NOT NULL COMMENT '名称',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '分类表';

DROP TABLE IF EXISTS `sky_article`;

CREATE TABLE IF NOT EXISTS `sky_article` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) NOT NULL COMMENT '标题',
  `category_id` bigint(20) NOT NULL COMMENT '类目ID',
  `category_name` varchar(256) NOT NULL COMMENT '类目名称',
  `image` varchar(256) DEFAULT NULL COMMENT '预览图链接',
  `synopsis` varchar(512) DEFAULT NULL COMMENT '简介',
  `user_id` bigint(20) DEFAULT NULL COMMENT '作者Id',
  `author` varchar(256) DEFAULT NULL COMMENT '作者',
  `status` tinyint(4) NOT NULL COMMENT '状态：0.私有，1.公开，-1.撤下，-99.删除',
  `publish_at` datetime NOT NULL COMMENT '发布时间',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文章表';

DROP TABLE IF EXISTS `sky_article_detail`;

CREATE TABLE IF NOT EXISTS `sky_article_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `is_markdown` tinyint(1) NOT NULL COMMENT '是否为markdown内容',
  `content` text COMMENT '内容',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_article_detail_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文章详情表';

DROP TABLE IF EXISTS `sky_article_summary`;

CREATE TABLE IF NOT EXISTS `sky_article_summary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `popular` int(20) DEFAULT '0' COMMENT '浏览量',
  `like` int(10) DEFAULT '0' COMMENT '点赞数',
  `favorite` int(10) DEFAULT '0' COMMENT '收藏数',
  `comments` int(10) DEFAULT '0' COMMENT '评论数量',
  `is_valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_article_summary_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '文章数据统计表';

DROP TABLE IF EXISTS `sky_friend_link`;

CREATE TABLE IF NOT EXISTS `sky_friend_link` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `web_name` VARCHAR(512) NOT NULL COMMENT '网站名称',
  `link` VARCHAR(512) NOT NULL COMMENT '链接',
  `visible` tinyint(1) NOT NULL COMMENT '是否可见，0.不可见，1.可见',
  `priority` int(10) DEFAULT 0 COMMENT '优先级',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '友情链接表';

DROP TABLE IF EXISTS `sky_ip_info`;

CREATE TABLE IF NOT EXISTS `sky_ip_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `country` varchar(128) DEFAULT NULL COMMENT '国家',
  `province` varchar(128) DEFAULT NULL COMMENT '省份',
  `city` varchar(128) DEFAULT NULL COMMENT '城市',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ip_info_ip` (`ip`),
  UNIQUE KEY `uq_ip_info_ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ip信息表';

DROP TABLE IF EXISTS `sky_device_info`;

CREATE TABLE IF NOT EXISTS `sky_device_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `os` varchar(64) DEFAULT NULL COMMENT '系统',
  `browser` varchar(64) DEFAULT NULL COMMENT '浏览器',
  `browser_version` varchar(64) DEFAULT NULL COMMENT '浏览器版本',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备信息表';

DROP TABLE IF EXISTS `sky_see_log`;

CREATE TABLE IF NOT EXISTS `sky_see_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `device_id` bigint(20) DEFAULT NULL COMMENT '设备id',
  `url` varchar(256) DEFAULT NULL COMMENT '访问页面',
  `uri` varchar(256) DEFAULT NULL COMMENT '请求',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问记录表';

DROP TABLE IF EXISTS `sky_like_log`;

CREATE TABLE IF NOT EXISTS `sky_like_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `type` int(4) DEFAULT NULL COMMENT '类型:1.文章,2.说说,3.照片',
  `aim_id` bigint(20) DEFAULT NULL COMMENT '目标id',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `device_id` bigint(20) DEFAULT NULL COMMENT '设备id',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞记录表';

DROP TABLE IF EXISTS `sky_comment`;

CREATE TABLE IF NOT EXISTS `sky_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `owner_id` bigint(20) DEFAULT NULL COMMENT '拥有者',
  `pid` bigint(20) DEFAULT NULL COMMENT '父节点',
  `level` int(2) DEFAULT NULL COMMENT '级别',
  `type` int(2) DEFAULT NULL COMMENT '类型:1.文章,2.说说,3.照片',
  `aim_id` bigint(20) DEFAULT NULL,
  `replier` bigint(20) DEFAULT NULL COMMENT '回复者',
  `replier_name` varchar(128) DEFAULT NULL COMMENT '回复者昵称',
  `replier_avatar` varchar(256) DEFAULT NULL COMMENT '回复者头像',
  `receiver` bigint(20) DEFAULT NULL COMMENT '接收者',
  `receiver_name` varchar(128) DEFAULT NULL COMMENT '接收者昵称',
  `content` text COMMENT '评论内容',
  `status` int(2) DEFAULT NULL COMMENT '状态：0.隐藏，1.显示，-1.删除',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';


DROP TABLE IF EXISTS `sky_follow_relation`;

CREATE TABLE IF NOT EXISTS `sky_follow_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `follower_id` bigint(20) NOT NULL COMMENT '关注人',
  `followee_id` bigint(20) NOT NULL COMMENT '被关注人',
  `is_subscribe` tinyint(4) DEFAULT 0 COMMENT '是否订阅',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注信息';

DROP TABLE IF EXISTS `sky_user_summary`;

CREATE TABLE IF NOT EXISTS `sky_user_summary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户',
  `follower_num` bigint(20) NOT NULL COMMENT '关注人数量',
  `fans_num` bigint(20) DEFAULT 0 COMMENT '粉丝数量',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户统计信息';
INSERT INTO `sky_user` (`id`, `name`, `nick_name`, `password`, `salt`, `status`, `created_at`, `updated_at`)
VALUES
	(1, 'admin', 'admin', 'ZCyqCBiFf5ky6m/BeFJFyGuk6LSVlu5f', 'grqx5iCM2Ma8KT9x1hja6acW', 1, now(), now()),
	(1, 'user', 'user', 'ZCyqCBiFf5ky6m/BeFJFyGuk6LSVlu5f', 'grqx5iCM2Ma8KT9x1hja6acW', 1, now(), now());

INSERT INTO `spider_parser_type` (`id`, `just_admin`, `name`, `key`, `created_at`, `updated_at`)
VALUES
(1, false, '默认爬虫', 'default', now(), now())，
(2, false, '外包爬虫', 'project', now(), now());


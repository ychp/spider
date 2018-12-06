INSERT INTO `sky_user` (`id`, `name`, `nick_name`, `password`, `salt`, `status`, `created_at`, `updated_at`)
VALUES
	(1, 'admin', 'admin', 'ZCyqCBiFf5ky6m/BeFJFyGuk6LSVlu5f', 'grqx5iCM2Ma8KT9x1hja6acW', 1, now(), now()),
	(1, 'user', 'user', 'ZCyqCBiFf5ky6m/BeFJFyGuk6LSVlu5f', 'grqx5iCM2Ma8KT9x1hja6acW', 1, now(), now());

INSERT INTO `parser_node` (`id`, `user_id`, `name`, `key`, `video_tag`, `image_tag`, `text_tag`, `sub_tag`, `created_at`, `updated_at`)
VALUES
(1, 1, 'default', 'default', 'video', 'img', 'a', NULL, now(), now());


-- 类目
insert blog_new.sky_category(id,user_id,name,created_at,updated_at)
select id,2 as user_id,name,created_at,updated_at from blog.categories;

-- 用户
insert blog_new.sky_user(id,name,nick_name,mobile,email,password,salt,status,created_at,updated_at)
select id,login_name,nick_name,mobile,email,password,salt,1 as status,created_at,updated_at from blog.users;

-- 用户信息
insert blog_new.sky_user_profile(user_id,home_page,avatar,created_at,updated_at)
select id,home_page,header,created_at,updated_at from blog.users;

-- 文章
insert blog_new.sky_article(`id`, `title`, `category_id`, `category_name`, `image`, `synopsis`, `user_id`, `author`, `status`, `publish_at`, `created_at`, `updated_at`)
select `id`, `title`, `category_id`, `category_name`, null as `image`, `synopsis`, `user_id`, `author`, 1 as `status`, `created_at`, `created_at`, `updated_at` from blog.blogs;

-- 文章详情
insert blog_new.sky_article_detail(`article_id`, `is_markdown`, `content`, `created_at`, `updated_at`)
select `id`, 0 as `is_markdown`, `content`, `created_at`, `updated_at` from blog.blogs;

-- 文章数据统计
insert blog_new.sky_article_summary(`article_id`, `popular`, `like`, `favorite`, `comments`, `is_valid`, `created_at`, `updated_at`)
select `id`, `popular`, `like`, 0 as `favorite`, `comments`, 1 AS `is_valid`, `created_at`, `updated_at` from blog.blogs;

-- ip地址信息
INSERT INTO blog_new.`sky_ip_info` (`ip`, `country`, `province`, `city`, `created_at`, `updated_at`)
select `ip`, case when `province` like '%市' or `province` like '%省' or `province` like '%区' then '中国' else `province` end, `province`, `city`, `created_at`, `updated_at`
from (select `ip`, min(`province`) as `province`, min(`city`) as `city`, min(`created_at`) as `created_at`, min(`updated_at`) as `updated_at`
from blog.see_logs group by `ip`) tmp;

-- 设备信息
INSERT INTO blog_new.`sky_device_info` (`os`, `browser`, `browser_version`, `device`, `created_at`, `updated_at`)
select `os`, `browser`, `browser_version`, `device`, min(`created_at`), min(`updated_at`)
from blog.see_logs where os is not null group by `os`, `browser`, `browser_version`, `device`;

-- 点赞记录
insert blog_new.sky_like_log(`id`, `user_id`, `type`, `aim_id`, `ip`, `device_id`, `created_at`, `updated_at`)
select ll.`id`, 2 as `user_id`, `type`, `aim_id`, `ip`, di.id, ll.`created_at`, ll.`updated_at`
from blog.like_logs as ll left join blog_new.`sky_device_info` di
on ll.os = di.os and ll.browser = di.browser and ll.browser_version = di.browser_version and ll.device = di.device;

-- 浏览记录
INSERT INTO blog_new.`sky_see_log` (`id`, `ip`, `device_id`, `url`, `uri`, `created_at`, `updated_at`)
select sl.`id`, `ip`, di.`id`, `url`, `uri`, sl.`created_at`, sl.`updated_at`
from blog.see_logs sl left join blog_new.`sky_device_info` di
on sl.os = di.os and sl.browser = di.browser and sl.browser_version = di.browser_version and sl.device = di.device;

-- 评价记录
INSERT INTO `blog_new`.`sky_comment` (`id`, `owner_id`, `pid`, `level`, `type`, `aim_id`, `replier`, `replier_name`, `replier_avatar`, `receiver`, `receiver_name`, `content`, `status`, `created_at`, `updated_at`)
select `id`, 2 as `owner_id`, `pid`, `level`, `type`, `aim_id`, `replyer`, `replyer_name`, `replyer_header`, `receiver`, `receiver_name`, `content`, `status`, `created_at`, `updated_at` from `blog`.`comments`;

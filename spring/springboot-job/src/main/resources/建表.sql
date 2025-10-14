CREATE TABLE `sys_job` (
`id` INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT 'ID',
`job_name` VARCHAR ( 512 ) NOT NULL COMMENT '任务名称',
`job_group` VARCHAR ( 512 ) NOT NULL COMMENT '任务组名',
`job_cron` VARCHAR ( 512 ) NOT NULL COMMENT '时间表达式',
`job_class_path` VARCHAR ( 1024 ) NOT NULL COMMENT '类路径,全类型',
`job_data_map` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '传递map参数',
`job_status` INT ( 2 ) NOT NULL COMMENT '状态:1启用 0停用',
`job_describe` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '任务功能描述',
PRIMARY KEY ( `id` )
) ENGINE = INNODB AUTO_INCREMENT = 25 DEFAULT CHARSET = utf8;
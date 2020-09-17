CREATE DATABASE `test`;

USE `test`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`       int(11)     NOT NULL AUTO_INCREMENT,
    `uid`      varchar(11) NOT NULL COMMENT '用户id',
    `username` varchar(255) COMMENT '用户名',
    `address`  varchar(255) COMMENT '用户密码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8 COMMENT '用户表';

/*Data for the table `user` */

INSERT INTO `user`(`uid`, `username`, `address`)
VALUES (1, '刘滋滋', '北京东城区'),
       (2, '王大妈', '北京朝阳区'),
       (3, '杨二娜', '北京门头沟区'),
       (4, '李咪咪', '上海浦东区'),
       (5, '阿美', '深圳');
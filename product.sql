# Host: 192.168.3.109  (Version 5.6.41)
# Date: 2018-08-29 11:17:02
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "product"
#

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `Id` varchar(50) NOT NULL DEFAULT '' COMMENT '产品编号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '产品名称',
  `threshold_amount` decimal(15,3) NOT NULL DEFAULT '0.000' COMMENT '起投金额',
  `step_amount` decimal(15,3) NOT NULL DEFAULT '0.000' COMMENT '投资补偿',
  `lock_term` smallint(6) NOT NULL DEFAULT '0' COMMENT '锁定期',
  `reward_rate` decimal(5,3) NOT NULL DEFAULT '0.000' COMMENT '收益率',
  `status` varchar(20) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT 'audining审核中',
  `memo` varchar(200) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "product"
#


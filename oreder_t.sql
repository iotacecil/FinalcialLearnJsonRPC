# Host: 192.168.3.109  (Version 5.6.41)
# Date: 2018-08-29 11:39:39
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "oreder_t"
#

DROP TABLE IF EXISTS `oreder_t`;
CREATE TABLE `oreder_t` (
  `order_id` varchar(50) NOT NULL DEFAULT '',
  `ch_id` varchar(50) NOT NULL DEFAULT '' COMMENT '渠道编号',
  `product_id` varchar(50) NOT NULL DEFAULT '' COMMENT '产品编号',
  `chan_user_id` varchar(50) NOT NULL DEFAULT '',
  `order_type` varchar(50) NOT NULL DEFAULT '' COMMENT '状态购买赎回',
  `order_status` varchar(50) NOT NULL DEFAULT '' COMMENT '状态初始化处理中成功失败',
  `outer_order_id` varchar(50) NOT NULL DEFAULT '' COMMENT '外部订单编号',
  `amount` decimal(15,3) NOT NULL DEFAULT '0.000' COMMENT '金额',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "oreder_t"
#


DROP TABLE IF EXISTS `tbl_snp`;
CREATE TABLE `tbl_snp` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `chrom` varchar(50) DEFAULT NULL COMMENT '基因名称',
  `pos` varchar(30) DEFAULT NULL COMMENT '基因位置',
  `pos_id` varchar(30) DEFAULT NULL,
  `ref` varchar(30) DEFAULT NULL COMMENT '参考值',
  `alt` varchar(30) DEFAULT NULL COMMENT '变异值',
  `qual` varchar(30) DEFAULT NULL,
  `filter` varchar(30) DEFAULT NULL,
  `info` varchar(4000) DEFAULT NULL,
  `create_time` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_snp_format`;
CREATE TABLE `tbl_snp_format` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `snp_id` int(11) NOT NULL,
  `sick` varchar(50) DEFAULT NULL,
  `format` varchar(50) DEFAULT NULL,
  `format_val` varchar(50) DEFAULT NULL,
  `format_rate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_sick`;
CREATE TABLE `tbl_sick`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sick_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sick_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病人姓名',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `family` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭成员',
  `disease_gene_focused` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '疾病基因',
  `medical_history` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病史药史',
  `family_history` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家族病史',
  `panel_name` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病情描述',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8;

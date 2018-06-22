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
) ENGINE=InnoDB AUTO_INCREMENT=10350 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_snp_format`;
CREATE TABLE `tbl_snp_format` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `snp_id` int(11) NOT NULL,
  `sick` varchar(50) DEFAULT NULL,
  `format` varchar(50) DEFAULT NULL,
  `format_val` varchar(50) DEFAULT NULL,
  `format_rate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10347 DEFAULT CHARSET=utf8;

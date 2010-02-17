# MySQL-Front 5.0  (Build 1.19)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: localhost    Database: versia
# ------------------------------------------------------
# Server version 5.0.51b-community-nt

#
# Table structure for table t_domain
#

DROP TABLE IF EXISTS `t_domain`;
CREATE TABLE `t_domain` (
  `domain_id` int(11) unsigned NOT NULL auto_increment,
  `release_id` int(10) unsigned default NULL,
  `ws_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`domain_id`),
  KEY `FK_Domain_Main_WS` (`ws_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

#
# Table structure for table t_product
#

DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `pr_id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY  (`pr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

#
# Table structure for table t_release
#

DROP TABLE IF EXISTS `t_release`;
CREATE TABLE `t_release` (
  `r_id` int(10) unsigned NOT NULL auto_increment,
  `pr_id` int(11) unsigned NOT NULL default '0',
  `name` varchar(255) NOT NULL,
  PRIMARY KEY  (`r_id`),
  KEY `FK_release_product` (`pr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

#
# Table structure for table t_release_arc
#

DROP TABLE IF EXISTS `t_release_arc`;
CREATE TABLE `t_release_arc` (
  `source` int(11) unsigned NOT NULL default '0',
  `target` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`source`,`target`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Table structure for table t_release_holder
#

DROP TABLE IF EXISTS `t_release_holder`;
CREATE TABLE `t_release_holder` (
  `r_id` int(10) unsigned NOT NULL,
  `vo_id` int(10) unsigned NOT NULL,
  `vp_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`vo_id`,`r_id`,`vp_id`),
  KEY `FK_R_VO_P1` (`r_id`),
  KEY `FK_R_VO_P2` (`vo_id`,`vp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Table structure for table t_versioned_object
#

DROP TABLE IF EXISTS `t_versioned_object`;
CREATE TABLE `t_versioned_object` (
  `vo_id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `super_vo` int(11) unsigned default NULL,
  `vo_type` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`vo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

#
# Table structure for table t_versioned_primitive
#

DROP TABLE IF EXISTS `t_versioned_primitive`;
CREATE TABLE `t_versioned_primitive` (
  `vo_id` int(10) unsigned NOT NULL,
  `vp_id` int(10) unsigned NOT NULL,
  `content` text,
  PRIMARY KEY  (`vo_id`,`vp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Table structure for table t_versioned_primitive_arc
#

DROP TABLE IF EXISTS `t_versioned_primitive_arc`;
CREATE TABLE `t_versioned_primitive_arc` (
  `source` int(11) unsigned NOT NULL default '0',
  `target` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`source`,`target`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Table structure for table t_vo_type
#

DROP TABLE IF EXISTS `t_vo_type`;
CREATE TABLE `t_vo_type` (
  `vo_type_id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(255) character set cp1251 collate cp1251_bulgarian_ci NOT NULL default '0',
  `super_vo_type` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`vo_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Table structure for table t_workspace
#

DROP TABLE IF EXISTS `t_workspace`;
CREATE TABLE `t_workspace` (
  `ws_id` int(10) unsigned NOT NULL auto_increment,
  `parent_ws_id` int(10) unsigned NOT NULL,
  `name` varchar(255) default NULL,
  `domain_id` int(11) unsigned default NULL COMMENT 'performence issue',
  PRIMARY KEY  (`ws_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

#
# Table structure for table t_workspace_holder
#

DROP TABLE IF EXISTS `t_workspace_holder`;
CREATE TABLE `t_workspace_holder` (
  `ws_id` int(10) unsigned NOT NULL,
  `vo_id` int(10) unsigned NOT NULL,
  `vp_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`ws_id`,`vo_id`,`vp_id`),
  KEY `FK_W_VO_P2` (`vo_id`,`vp_id`),
  KEY `FK_W_VO_P` (`ws_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# View structure for view last_vo_version
#

DROP VIEW IF EXISTS `last_vo_version`;
CREATE ALGORITHM=TEMPTABLE SQL SECURITY DEFINER VIEW `last_vo_version` AS select `t_versioned_primitive`.`vo_id` AS `vo_id`,max(`t_versioned_primitive`.`vp_id`) AS `last_vo_version` from `t_versioned_primitive` group by `t_versioned_primitive`.`vo_id`;

#
#  Foreign keys for table t_domain
#

ALTER TABLE `t_domain`
ADD CONSTRAINT `FK_Domain_Main_WS` FOREIGN KEY (`ws_id`) REFERENCES `t_workspace` (`ws_id`);

#
#  Foreign keys for table t_release
#

ALTER TABLE `t_release`
ADD CONSTRAINT `FK_release_product` FOREIGN KEY (`pr_id`) REFERENCES `t_product` (`pr_id`);

#
#  Foreign keys for table t_release_holder
#

ALTER TABLE `t_release_holder`
ADD CONSTRAINT `FK_R_VO_P1` FOREIGN KEY (`r_id`) REFERENCES `t_release` (`r_id`),
  ADD CONSTRAINT `FK_R_VO_P2` FOREIGN KEY (`vo_id`, `vp_id`) REFERENCES `t_versioned_primitive` (`vo_id`, `vp_id`),
  ADD CONSTRAINT `FK_R_VO_P3` FOREIGN KEY (`vo_id`) REFERENCES `t_versioned_object` (`vo_id`);

#
#  Foreign keys for table t_versioned_primitive
#

ALTER TABLE `t_versioned_primitive`
ADD CONSTRAINT `FK_VO_P` FOREIGN KEY (`vo_id`) REFERENCES `t_versioned_object` (`vo_id`);

#
#  Foreign keys for table t_workspace_holder
#

ALTER TABLE `t_workspace_holder`
ADD CONSTRAINT `FK_W_VO_P1` FOREIGN KEY (`ws_id`) REFERENCES `t_workspace` (`ws_id`),
  ADD CONSTRAINT `FK_W_VO_P2` FOREIGN KEY (`vo_id`, `vp_id`) REFERENCES `t_versioned_primitive` (`vo_id`, `vp_id`),
  ADD CONSTRAINT `FK_W_VO_P3` FOREIGN KEY (`vo_id`) REFERENCES `t_versioned_object` (`vo_id`);


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

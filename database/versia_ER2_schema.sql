/*
SQLyog Enterprise - MySQL GUI v8.12 
MySQL - 5.1.37 : Database - versia_er2
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`versia_er2` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `versia_er2`;

/*Table structure for table `t_action` */

DROP TABLE IF EXISTS `t_action`;

CREATE TABLE `t_action` (
  `action_id` int(11) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(100) NOT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

/*Data for the table `t_action` */

LOCK TABLES `t_action` WRITE;

insert  into `t_action`(`action_id`,`action_name`) values (1,'create_action'),(12,'create_worspace'),(3,'update_action'),(4,'delete_action'),(5,'create_user'),(6,'grant_user_right'),(7,'create_model'),(8,'update_model'),(9,'create_product'),(10,'update_product'),(11,'create_release'),(13,'get_product'),(14,'get_release'),(15,'get_workspace'),(16,'update_workspace'),(17,'get_model'),(18,'create_versioned_object'),(19,'get_versioned_object'),(20,'save_versioned_object'),(21,'create_workitem'),(22,'update_workitem'),(23,'attach_detach_workitem'),(24,'get_workitem'),(25,'publish_versioned_object'),(26,'putback_versioned_object'),(27,'view_versioned_object_distribution'),(28,'get_user_list'),(29,'create_user'),(30,'update_permition'),(31,'get_action_list');

UNLOCK TABLES;

/*Table structure for table `t_debug` */

DROP TABLE IF EXISTS `t_debug`;

CREATE TABLE `t_debug` (
  `debug_txt` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `t_debug` */

LOCK TABLES `t_debug` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_history` */

DROP TABLE IF EXISTS `t_history`;

CREATE TABLE `t_history` (
  `h_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `vo_id` int(11) DEFAULT NULL,
  `vp_id` int(11) DEFAULT NULL,
  `wi_id` int(11) DEFAULT NULL,
  `on_date_time` datetime NOT NULL,
  `action` enum('create','save','publish','putback','delete') DEFAULT NULL,
  PRIMARY KEY (`h_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `t_history` */

LOCK TABLES `t_history` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_initiator_effector` */

DROP TABLE IF EXISTS `t_initiator_effector`;

CREATE TABLE `t_initiator_effector` (
  `wi_id` int(11) NOT NULL,
  `effector_vo_id` int(11) NOT NULL,
  `effector_vp_id` int(11) NOT NULL,
  `initiator_vp_id` int(11) NOT NULL,
  PRIMARY KEY (`wi_id`,`effector_vo_id`,`effector_vp_id`,`initiator_vp_id`),
  KEY `FK_initiator` (`initiator_vp_id`),
  KEY `FK_effector` (`effector_vp_id`,`effector_vo_id`),
  CONSTRAINT `FK_effector` FOREIGN KEY (`effector_vp_id`, `effector_vo_id`) REFERENCES `t_versioned_primitive` (`vp_id`, `vo_id`),
  CONSTRAINT `FK_initiator` FOREIGN KEY (`initiator_vp_id`) REFERENCES `t_versioned_primitive` (`vp_id`),
  CONSTRAINT `FK_initiator_workitem` FOREIGN KEY (`wi_id`) REFERENCES `t_workitem` (`wi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_initiator_effector` */

LOCK TABLES `t_initiator_effector` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_log` */

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `uid` int(11) NOT NULL,
  `action_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`date_time`,`uid`,`action_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `t_log` */

LOCK TABLES `t_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_object_type` */

DROP TABLE IF EXISTS `t_object_type`;

CREATE TABLE `t_object_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_object_type` */

LOCK TABLES `t_object_type` WRITE;

insert  into `t_object_type`(`type_id`,`name`) values (1,'Atom'),(2,'Meta-data');

UNLOCK TABLES;

/*Table structure for table `t_permition` */

DROP TABLE IF EXISTS `t_permition`;

CREATE TABLE `t_permition` (
  `permition_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `action_id` int(11) NOT NULL,
  `permited` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`permition_id`)
) ENGINE=MyISAM AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;

/*Data for the table `t_permition` */

LOCK TABLES `t_permition` WRITE;

insert  into `t_permition`(`permition_id`,`uid`,`action_id`,`permited`) values (1,1,1,1),(2,1,3,1),(3,1,4,1),(4,1,5,1),(5,1,6,1),(6,1,7,1),(7,1,8,1),(8,1,9,1),(9,1,10,1),(10,1,11,1),(11,1,12,1),(12,1,13,1),(13,1,14,1),(14,1,15,1),(15,1,16,1),(16,1,17,1),(17,1,18,1),(18,1,19,1),(19,1,20,1),(20,1,21,1),(21,1,22,1),(22,1,23,1),(23,1,24,1),(24,1,25,1),(25,1,26,1),(26,1,27,1),(27,2,1,0),(28,2,3,0),(29,2,4,0),(30,2,5,0),(31,2,6,0),(32,2,7,0),(33,2,8,0),(34,2,9,0),(35,2,10,0),(36,2,11,0),(37,2,12,0),(38,2,13,0),(39,2,14,0),(40,2,15,0),(41,2,16,0),(42,2,17,0),(43,2,18,0),(44,2,19,0),(45,2,20,0),(46,2,21,0),(47,2,22,0),(48,2,23,0),(49,2,24,0),(50,2,25,0),(51,2,26,0),(52,2,27,0),(53,2,28,0),(54,2,29,0),(55,2,30,0),(56,2,31,0),(57,1,28,1),(58,1,29,1),(59,1,30,1),(60,1,31,1);

UNLOCK TABLES;

/*Table structure for table `t_product` */

DROP TABLE IF EXISTS `t_product`;

CREATE TABLE `t_product` (
  `pr_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`pr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_product` */

LOCK TABLES `t_product` WRITE;

insert  into `t_product`(`pr_id`,`name`) values (1,'Product ONE'),(2,'P two');

UNLOCK TABLES;

/*Table structure for table `t_release` */

DROP TABLE IF EXISTS `t_release`;

CREATE TABLE `t_release` (
  `release_id` int(11) NOT NULL AUTO_INCREMENT,
  `master_ws_id` int(11) DEFAULT NULL,
  `pr_id` int(11) unsigned DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`release_id`),
  KEY `FK_master_workspace` (`master_ws_id`),
  KEY `FK_product` (`pr_id`),
  CONSTRAINT `FK_master_workspace` FOREIGN KEY (`master_ws_id`) REFERENCES `t_workspace` (`ws_id`) ON DELETE SET NULL,
  CONSTRAINT `FK_t_release` FOREIGN KEY (`pr_id`) REFERENCES `t_product` (`pr_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_release` */

LOCK TABLES `t_release` WRITE;

insert  into `t_release`(`release_id`,`master_ws_id`,`pr_id`,`name`) values (1,1,1,'Zero Release'),(2,1,2,'Zero Release');

UNLOCK TABLES;

/*Table structure for table `t_release_vgraph_arcs` */

DROP TABLE IF EXISTS `t_release_vgraph_arcs`;

CREATE TABLE `t_release_vgraph_arcs` (
  `source_release_id` int(11) NOT NULL,
  `target_release_id` int(11) NOT NULL,
  PRIMARY KEY (`source_release_id`,`target_release_id`),
  KEY `FK_r_trg` (`target_release_id`),
  CONSTRAINT `FK_r_src` FOREIGN KEY (`source_release_id`) REFERENCES `t_release` (`release_id`),
  CONSTRAINT `FK_r_trg` FOREIGN KEY (`target_release_id`) REFERENCES `t_release` (`release_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_release_vgraph_arcs` */

LOCK TABLES `t_release_vgraph_arcs` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_user` */

LOCK TABLES `t_user` WRITE;

insert  into `t_user`(`uid`,`username`) values (1,'admin'),(2,'guest');

UNLOCK TABLES;

/*Table structure for table `t_version_graph_arcs` */

DROP TABLE IF EXISTS `t_version_graph_arcs`;

CREATE TABLE `t_version_graph_arcs` (
  `vo_id` int(11) NOT NULL,
  `source_vp_id` int(11) NOT NULL,
  `target_vp_id` int(11) NOT NULL,
  PRIMARY KEY (`vo_id`,`source_vp_id`,`target_vp_id`),
  KEY `FK_vp_src` (`source_vp_id`,`vo_id`),
  KEY `FK_vp_trg` (`target_vp_id`,`vo_id`),
  CONSTRAINT `FK_vp_src` FOREIGN KEY (`source_vp_id`, `vo_id`) REFERENCES `t_versioned_primitive` (`vp_id`, `vo_id`),
  CONSTRAINT `FK_vp_trg` FOREIGN KEY (`target_vp_id`, `vo_id`) REFERENCES `t_versioned_primitive` (`vp_id`, `vo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_version_graph_arcs` */

LOCK TABLES `t_version_graph_arcs` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_versioned_object` */

DROP TABLE IF EXISTS `t_versioned_object`;

CREATE TABLE `t_versioned_object` (
  `vo_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`vo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_versioned_object` */

LOCK TABLES `t_versioned_object` WRITE;

insert  into `t_versioned_object`(`vo_id`) values (1),(2);

UNLOCK TABLES;

/*Table structure for table `t_versioned_primitive` */

DROP TABLE IF EXISTS `t_versioned_primitive`;

CREATE TABLE `t_versioned_primitive` (
  `vp_id` int(11) NOT NULL,
  `vo_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type_id` int(11) NOT NULL,
  `constructs` int(11) NOT NULL DEFAULT '0',
  `datum` text,
  PRIMARY KEY (`vp_id`,`vo_id`),
  KEY `FK_vo_type` (`type_id`),
  KEY `FK_versioned_object` (`vo_id`),
  CONSTRAINT `FK_versioned_object` FOREIGN KEY (`vo_id`) REFERENCES `t_versioned_object` (`vo_id`),
  CONSTRAINT `FK_vo_type` FOREIGN KEY (`type_id`) REFERENCES `t_object_type` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_versioned_primitive` */

LOCK TABLES `t_versioned_primitive` WRITE;

insert  into `t_versioned_primitive`(`vp_id`,`vo_id`,`name`,`type_id`,`constructs`,`datum`) values (0,1,'General Workitem 1',1,0,NULL),(0,2,'General Workitem 2',1,0,NULL);

UNLOCK TABLES;

/*Table structure for table `t_workitem` */

DROP TABLE IF EXISTS `t_workitem`;

CREATE TABLE `t_workitem` (
  `wi_id` int(11) NOT NULL AUTO_INCREMENT,
  `initiator_vo_id` int(11) NOT NULL,
  `wi_name` char(50) DEFAULT NULL,
  `release_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`wi_id`),
  KEY `FK_t_workitem` (`release_id`),
  KEY `FK_initiator_object` (`initiator_vo_id`),
  CONSTRAINT `FK_initiator_object` FOREIGN KEY (`initiator_vo_id`) REFERENCES `t_versioned_object` (`vo_id`),
  CONSTRAINT `FK_t_workitem` FOREIGN KEY (`release_id`) REFERENCES `t_release` (`release_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_workitem` */

LOCK TABLES `t_workitem` WRITE;

insert  into `t_workitem`(`wi_id`,`initiator_vo_id`,`wi_name`,`release_id`) values (1,1,'General Workitem WI name',1),(2,2,'General Workitem',2);

UNLOCK TABLES;

/*Table structure for table `t_workspace` */

DROP TABLE IF EXISTS `t_workspace`;

CREATE TABLE `t_workspace` (
  `ws_id` int(11) NOT NULL AUTO_INCREMENT,
  `release_id` int(11) DEFAULT NULL,
  `ancestor_ws_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `lft` int(11) DEFAULT NULL,
  `rgt` int(11) DEFAULT NULL,
  PRIMARY KEY (`ws_id`),
  KEY `FK_release` (`release_id`),
  KEY `FK_ws_ancestor_offsring` (`ancestor_ws_id`),
  CONSTRAINT `FK_release` FOREIGN KEY (`release_id`) REFERENCES `t_release` (`release_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_workspace` */

LOCK TABLES `t_workspace` WRITE;

insert  into `t_workspace`(`ws_id`,`release_id`,`ancestor_ws_id`,`name`,`lft`,`rgt`) values (1,1,0,'Master Workspace',1,2),(2,2,0,'Master Workspace',1,2);

UNLOCK TABLES;

/*Table structure for table `t_ws_obj_ver_selector` */

DROP TABLE IF EXISTS `t_ws_obj_ver_selector`;

CREATE TABLE `t_ws_obj_ver_selector` (
  `vp_id` int(11) NOT NULL,
  `vo_id` int(11) NOT NULL,
  `ws_id` int(11) NOT NULL,
  `locked` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vp_id`,`vo_id`,`ws_id`),
  KEY `FK_ws_object` (`ws_id`),
  KEY `FK_version_selector` (`vo_id`,`vp_id`),
  CONSTRAINT `FK_version_selector` FOREIGN KEY (`vo_id`, `vp_id`) REFERENCES `t_versioned_primitive` (`vo_id`, `vp_id`),
  CONSTRAINT `FK_ws_object` FOREIGN KEY (`ws_id`) REFERENCES `t_workspace` (`ws_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_ws_obj_ver_selector` */

LOCK TABLES `t_ws_obj_ver_selector` WRITE;

insert  into `t_ws_obj_ver_selector`(`vp_id`,`vo_id`,`ws_id`,`locked`) values (0,1,1,0),(0,2,2,0);

UNLOCK TABLES;

/*Table structure for table `t_ws_workitem` */

DROP TABLE IF EXISTS `t_ws_workitem`;

CREATE TABLE `t_ws_workitem` (
  `ws_id` int(11) NOT NULL,
  `wi_id` int(11) NOT NULL,
  PRIMARY KEY (`ws_id`,`wi_id`),
  KEY `FK_workitem` (`wi_id`),
  CONSTRAINT `FK_workitem` FOREIGN KEY (`wi_id`) REFERENCES `t_workitem` (`wi_id`),
  CONSTRAINT `FK_workspace` FOREIGN KEY (`ws_id`) REFERENCES `t_workspace` (`ws_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_ws_workitem` */

LOCK TABLES `t_ws_workitem` WRITE;

insert  into `t_ws_workitem`(`ws_id`,`wi_id`) values (1,1),(2,2);

UNLOCK TABLES;

/* Trigger structure for table `t_product` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `new_product` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'192.168.56.1' */ /*!50003 TRIGGER `new_product` AFTER INSERT ON `t_product` FOR EACH ROW BEGIN
	DECLARE new_release_id, mws, srvc_vo_id, srvc_wi_id INT(11);
	
	INSERT INTO t_release (release_id, pr_id, `name`) VALUES (NULL, New.pr_id, 'Zero Release');
	SET new_release_id = LAST_INSERT_ID();
	
	INSERT INTO t_workspace (release_id, `name`, lft, rgt) VALUES (new_release_id, 'Master Workspace', 1, 2);
	SET mws = LAST_INSERT_ID();
	UPDATE t_release SET master_ws_id =  mws WHERE release_id = new_release_id;
	
	INSERT INTO t_versioned_object (vo_id) VALUES (0);
	SET srvc_vo_id = LAST_INSERT_ID();
	
	INSERT INTO t_versioned_primitive (vo_id, vp_id, `name`, type_id) VALUES (srvc_vo_id, 0, 'General Workitem', 1);
	
	INSERT INTO t_ws_obj_ver_selector (ws_id, vo_id, vp_id, locked) VALUES (mws, srvc_vo_id, 0, 1);
	INSERT INTO t_workitem (release_id, initiator_vo_id) VALUES (new_release_id, srvc_vo_id);
	SET srvc_wi_id = LAST_INSERT_ID();
	
	INSERT INTO t_ws_workitem (ws_id, wi_id) VALUES (mws, srvc_wi_id);
    END */$$


DELIMITER ;

/* Trigger structure for table `t_user` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `new_user` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'192.168.56.1' */ /*!50003 TRIGGER `new_user` AFTER INSERT ON `t_user` FOR EACH ROW BEGIN
	Insert INTO t_permition (uid, action_id, permited) SELECT New.uid, action_id, 0 FROM t_action;
    END */$$


DELIMITER ;

/* Function  structure for function  `create_versioned_object` */

/*!50003 DROP FUNCTION IF EXISTS `create_versioned_object` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `create_versioned_object`(in_ws_id INT(11), 
									in_name CHAR(50), 
									in_datum TEXT, 
									in_uid INT(11), 
									in_type INT(11), 
									in_constructs INT(11)) RETURNS int(11)
BEGIN
	DECLARE new_vo_id INT(11);	
	DECLARE has_wi, type_exists, has_constructed INT(11) DEFAULT 0;
	# verification whether in current workspace there are workitems
	SELECT count(*) FROM t_ws_workitem WHERE ws_id = in_ws_id INTO has_wi;
	IF has_wi < 1 THEN RETURN -1; END IF;
	# verification whether in_type_id exists in table t_object_type
	SELECT COUNT(*) FROM t_object_type WHERE type_id = in_type INTO type_exists;
	IF type_exists < 1 THEN RETURN -2; END IF;
	# verification whether the in_constructs is visible for current workspace
	IF in_constructs > 0 THEN 
		SELECT count(*) FROM v_vo_visibility_fast WHERE ws_id = in_ws_id AND vo_id = in_constructs INTO has_constructed;
		IF has_constructed < 1 THEN RETURN -3; END IF;
	END IF;
	INSERT INTO t_versioned_object (vo_id) VALUES (NULL);
	SET new_vo_id = LAST_INSERT_ID();
	INSERT INTO t_versioned_primitive (vp_id, vo_id, datum, `name`, type_id, constructs) VALUES (0, new_vo_id, in_datum, in_name, in_type, in_constructs);
	INSERT INTO t_ws_obj_ver_selector (ws_id, vo_id, vp_id) VALUES (in_ws_id, new_vo_id, 0);
	INSERT INTO t_history (uid, vo_id, vp_id, on_date_time, wi_id, `action`) VALUES (in_uid, new_vo_id, 0, now(), in_ws_id, 'create');
	#open ws_wi_cursor;
	
	CALL make_trace(in_ws_id, new_vo_id, 0);
	
	RETURN new_vo_id;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_create_workspace` */

/*!50003 DROP FUNCTION IF EXISTS `f_create_workspace` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_create_workspace`(in_ancestor_ws_id INT(11), in_name VARCHAR(255), in_release_id INT(11)) RETURNS int(11)
BEGIN
	DECLARE out_result INT(11) DEFAULT 0;
	DECLARE anc_rgt INT(11);
	IF in_ancestor_ws_id <= 0 THEN RETURN -10; END IF;
	SELECT rgt FROM t_workspace 
		WHERE ws_id = in_ancestor_ws_id AND release_id = in_release_id
		INTO anc_rgt;
	IF anc_rgt <=0 THEN RETURN -20; END IF;
	
	UPDATE t_workspace SET rgt = rgt + 2 WHERE rgt >= anc_rgt AND release_id = in_release_id;
	UPDATE t_workspace SET lft = lft + 2 WHERE rgt > anc_rgt AND release_id = in_release_id;
	
	INSERT INTO t_workspace (ancestor_ws_id, `name`, release_id, lft, rgt) 
		VALUES (in_ancestor_ws_id, in_name, in_release_id, anc_rgt, anc_rgt+1);
	RETURN out_result;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_get_master_workspace` */

/*!50003 DROP FUNCTION IF EXISTS `f_get_master_workspace` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_get_master_workspace`(in_ws_id int(11)) RETURNS int(11)
BEGIN
	declare m_ws int(11) DEFAULT 0;
	SELECT master_ws_id 
		FROM t_workspace W INNER JOIN t_release  USING(release_id)
		WHERE W.ws_id = in_ws_id LIMIT 1 
        INTO m_ws; 
        
        RETURN m_ws;    
    END */$$
DELIMITER ;

/* Function  structure for function  `f_get_visible_vp` */

/*!50003 DROP FUNCTION IF EXISTS `f_get_visible_vp` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_get_visible_vp`(in_vo_id int(11), in_ws_id int(11)) RETURNS int(11)
BEGIN
	DECLARE result int(11);
	
	SELECT wsos. vp_id 
	FROM t_ws_obj_ver_selector wsos INNER JOIN t_workspace ws USING (ws_id)
	WHERE vo_id = in_vo_id AND
	ws_id IN 
	(
		SELECT `parent`.`ws_id` AS `vis_ws_id` 
		FROM `t_workspace` `node` JOIN `t_workspace` `parent`
		WHERE `node`.`lft` BETWEEN `parent`.`lft` AND `parent`.`rgt` 
		AND `node`.`ws_id` = in_ws_id
		order by `parent`.lft
	)
	ORDER BY ws.lft DESC
	LIMIT 1 
	INTO result;
	
	RETURN result;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_is_ancestor_workspace` */

/*!50003 DROP FUNCTION IF EXISTS `f_is_ancestor_workspace` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_is_ancestor_workspace`(Start_ws_id INT(11), possible_ancestor_ws_id INT(11)) RETURNS int(11)
BEGIN
	DECLARE ancestor_ws INT(11);  
	DECLARE currnet_ws INT(11);  
	DECLARE result INT(11) DEFAULT 0;  
	SET currnet_ws = Start_ws_id;
	main_loop: LOOP
		SELECT ancestor_ws_id FROM t_workspace WHERE ws_id = currnet_ws INTO ancestor_ws;
		IF ancestor_ws = 0 THEN BEGIN      
			SET result = 0;
			LEAVE main_loop;      
		END;   
		ELSEIF ancestor_ws = possible_ancestor_ws_id THEN BEGIN 
			SET result = 1;         
			LEAVE main_loop;      
		END;   
		ELSE
			SET currnet_ws = ancestor_ws;         
		END IF; 
		ITERATE main_loop;
	END LOOP main_loop;  
	RETURN result;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_publish_versioned_object` */

/*!50003 DROP FUNCTION IF EXISTS `f_publish_versioned_object` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_publish_versioned_object`(in_ws_id INT(11), in_vo_id INT(11)) RETURNS int(11)
BEGIN
	DECLARE has_local, is_in_anc, anc_ws_id, is_locked, l_vp_id INT(11) DEFAULT 0;
	
	SELECT count(*), vp_id FROM t_ws_obj_ver_selector WHERE ws_id=in_ws_id AND vo_id=in_vo_id 
		INTO has_local, l_vp_id;
	IF has_local = 0 THEN
		RETURN -1;
	END IF;
	
	SELECT ancestor_ws_id FROM t_workspace WHERE ws_id=in_ws_id INTO anc_ws_id;
	SELECT COUNT(*), locked FROM t_ws_obj_ver_selector WHERE vo_id=in_vo_id AND ws_id = (
		SELECT ancestor_ws_id FROM t_workspace WHERE ws_id=in_ws_id
		) INTO is_in_anc, is_locked;
	
	IF anc_ws_id = 0 THEN # in_ws_id is MASTER WS
		RETURN -2;
	ELSEIF is_locked != 0 OR is_locked IS NOT NULL THEN # LOCKED
		RETURN -3;
	END IF;
	IF is_in_anc = 0 THEN 
		INSERT INTO t_ws_obj_ver_selector VALUES (l_vp_id, in_vo_id, anc_ws_id, 0);
	ELSE 
		UPDATE t_ws_obj_ver_selector SET vp_id=l_vp_id WHERE vo_id=in_vo_id AND ws_id = anc_ws_id;
	END IF;
	
	DELETE FROM t_ws_obj_ver_selector WHERE  ws_id=in_ws_id AND vo_id=in_vo_id;
	RETURN 1;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_putback_versioned_object` */

/*!50003 DROP FUNCTION IF EXISTS `f_putback_versioned_object` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_putback_versioned_object`(in_ws_id INT(11), in_vo_id INT(11)) RETURNS int(11)
BEGIN
	DECLARE has_local, anc_ws_id, is_locked INT(11) DEFAULT 0;
	
	SELECT COUNT(*), locked, ancestor_ws_id FROM t_ws_obj_ver_selector INNER JOIN t_workspace USING (ws_id)
		WHERE ws_id=in_ws_id AND vo_id=in_vo_id 
		INTO has_local, is_locked, anc_ws_id;
	IF has_local = 0 THEN
		RETURN -1;
	ELSEIF anc_ws_id = 0 THEN
		RETURN -2;
	elseIF is_locked != 0 OR is_locked = NULL THEN
		RETURN -3;
	END IF;
	
	
	DELETE FROM t_ws_obj_ver_selector WHERE ws_id=in_ws_id AND vo_id=in_vo_id;
	return 1;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_read_visibility_vector` */

/*!50003 DROP FUNCTION IF EXISTS `f_read_visibility_vector` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_read_visibility_vector`(in_vector INT(11)) RETURNS varchar(10) CHARSET latin1
BEGIN
	DECLARE s1, s2, s3, s4, s5  CHAR(1) DEFAULT "_";
	DECLARE st VARCHAR(10);
	IF in_vector & 1 THEN SET s1 = "R"; END IF;
	IF in_vector & 2 THEN SET s2 = "P"; END IF;
	IF in_vector & 4 THEN SET s3 = "C"; END IF;
	IF in_vector & 8 THEN SET s4 = "L"; END IF;
	IF in_vector & 16 THEN SET s5 = "T"; END IF;
	SET st = s1.s2 ;#+ s3 + s4 + s5;
	RETURN st;
    END */$$
DELIMITER ;

/* Function  structure for function  `f_save_vo_state` */

/*!50003 DROP FUNCTION IF EXISTS `f_save_vo_state` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_save_vo_state`(in_vo_id INT(11), 
								in_ws_id INT(11), 
								in_vo_datum TEXT, 
								in_name VARCHAR(255), 
								in_uid INT(11), 
								in_type INT(11), 
								in_constructs INT(11)) RETURNS int(11)
BEGIN
	
	DECLARE next_vp_id INT(11);
	DECLARE v_vector, is_locked, src_vp_id INT(11) DEFAULT 0;
	DECLARE has_wi, type_exists, has_constructed INT(11) DEFAULT 0;
	# verification whether in current workspace there are workitems
	SELECT count(*) FROM t_ws_workitem WHERE ws_id = in_ws_id INTO has_wi;
	IF has_wi < 1 THEN RETURN -1; END IF;
	# verification whether in_type_id exists in table t_object_type
	SELECT COUNT(*) FROM t_object_type WHERE type_id = in_type INTO type_exists;
	IF type_exists < 1 THEN RETURN -2; END IF;
	# verification whether the in_constructs is visible for current workspace
	IF in_constructs > 0 THEN 
		SELECT count(*) FROM v_vo_visibility_fast WHERE ws_id = in_ws_id AND vo_id = in_constructs INTO has_constructed;
		IF has_constructed < 1 THEN RETURN -3; END IF;
	END IF;
	
	#get closed parent's vp_id and locked
	SELECT wovs.vp_id, wovs.locked  
		FROM (`t_workspace` `node` JOIN `t_workspace` `parent`) 
			INNER JOIN t_ws_obj_ver_selector wovs ON parent.ws_id = wovs.ws_id
		WHERE `node`.`lft` BETWEEN `parent`.`lft` AND `parent`.`rgt` 
			AND `node`.`ws_id` = in_ws_id AND wovs.vo_id = in_vo_id
		ORDER BY `parent`.lft DESC
		LIMIT 1 INTO src_vp_id, is_locked;
	IF is_locked != 0 THEN
		RETURN 0;
	END IF;
	
	SELECT last_vo_version + 1, f_visibility_vector(in_vo_id, in_ws_id)FROM v_last_vo_version WHERE vo_id = in_vo_id 
		INTO next_vp_id, v_vector;
	
	INSERT INTO t_versioned_primitive (vp_id, vo_id, datum, `name`, constructs, type_id) VALUES (next_vp_id, in_vo_id, in_vo_datum, in_name, in_constructs, in_type);
	INSERT INTO t_version_graph_arcs (vo_id, source_vp_id, target_vp_id)VALUES (in_vo_id, src_vp_id, next_vp_id);	
	INSERT INTO t_history (uid, vo_id, vp_id, on_date_time, wi_id, `action`) VALUES (in_uid, in_vo_id, next_vp_id, now(), in_ws_id, 'save');
	CALL make_trace(in_ws_id, in_vo_id, next_vp_id);
	IF v_vector & 8 THEN 
		UPDATE t_ws_obj_ver_selector SET vp_id = next_vp_id WHERE vo_id = in_vo_id AND ws_id = in_ws_id;
		RETURN 1;
	ELSE	
		INSERT INTO t_ws_obj_ver_selector (vp_id, vo_id, ws_id, locked) VALUES (next_vp_id, in_vo_id, in_ws_id, 0);
		RETURN 2;
	END IF;
	
    END */$$
DELIMITER ;

/* Function  structure for function  `f_visibility_vector` */

/*!50003 DROP FUNCTION IF EXISTS `f_visibility_vector` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `f_visibility_vector`(in_vo_id INT(11), 
       in_ws_id INT(11)) RETURNS int(11)
BEGIN
	DECLARE m_ws_id, is_child, crsr_ws INT(11);    
	DECLARE l_loop_end INT(1) DEFAULT 0;
	DECLARE vector, v2 INT(11) DEFAULT 0;
	DECLARE crsr_vo_distribution CURSOR FOR 
		SELECT `w`.`ws_id` 
		FROM (`v_workspace_hierarchy` `w` 
			LEFT JOIN `t_ws_obj_ver_selector` `h` 
			ON(`w`.`ws_id` = `h`.`ws_id`))
		WHERE vo_id = in_vo_id;
		#SELECT local_ws_id 
		#FROM v_vo_distribution 
		#WHERE vo_id = in_vo_id; # AND release_id = r_id;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET l_loop_end = 1;
	SELECT f_get_master_workspace(in_ws_id) INTO m_ws_id;
	
	OPEN crsr_vo_distribution;  
	main_loop: LOOP      
		FETCH crsr_vo_distribution INTO crsr_ws;    
		IF l_loop_end THEN 
			LEAVE main_loop;    
		END IF;
		SET is_child = f_is_ancestor_workspace(crsr_ws, in_ws_id);
		
		IF NOT vector & 1 AND crsr_ws = m_ws_id THEN                               
		# Release 1  
			SET vector = vector + 1;
		ELSEIF NOT vector & 2 AND f_is_ancestor_workspace(in_ws_id, crsr_ws) = 1 THEN    
		# Parent  2      
			SET vector = vector + 2;
		ELSEIF NOT vector & 4 AND is_child = 1 THEN     
		# Child   4      
			SET vector = vector + 4;            
		ELSEIF NOT vector & 8 AND crsr_ws = in_ws_id THEN                    
		# Local   8    
			SET vector = vector + 8;        
		ELSEIF NOT vector & 16 and not is_child THEN                                          
		# Other  16         
			SET vector = vector + 16;
		END IF;
	END LOOP main_loop;     
	CLOSE crsr_vo_distribution;	
	return vector;
    END */$$
DELIMITER ;

/* Function  structure for function  `validate_session_n_permition` */

/*!50003 DROP FUNCTION IF EXISTS `validate_session_n_permition` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` FUNCTION `validate_session_n_permition`(session_id INT(11), required_action CHAR(100)) RETURNS int(1)
BEGIN
	DECLARE result INT(11) DEFAULT 0;
	SELECT permited FROM t_permition INNER JOIN t_action USING (action_id)
		WHERE uid = session_id
		AND action_name = required_action INTO result;
	RETURN result;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `make_trace` */

/*!50003 DROP PROCEDURE IF EXISTS  `make_trace` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`192.168.56.1` PROCEDURE `make_trace`(in_ws_id INT(11), in_effector_vo_id INT(11), in_effector_vp_id INT(11))
BEGIN
 INSERT INTO t_initiator_effector (wi_id, initiator_vp_id, effector_vo_id, effector_vp_id) 
	SELECT wswi.wi_id, 
	(
		SELECT vvv.vp_id 
		FROM v_vo_visibility_fast vvv INNER JOIN t_workitem wi ON(vvv.vo_id = wi.initiator_vo_id)
		WHERE vvv.ws_id = in_ws_id 
	),
	in_effector_vo_id, 
	in_effector_vp_id 
 FROM t_ws_workitem wswi 
 WHERE wswi.ws_id = in_ws_id;
 END */$$
DELIMITER ;

/*Table structure for table `v_last_vo_version` */

DROP TABLE IF EXISTS `v_last_vo_version`;

/*!50001 DROP VIEW IF EXISTS `v_last_vo_version` */;
/*!50001 DROP TABLE IF EXISTS `v_last_vo_version` */;

/*!50001 CREATE TABLE `v_last_vo_version` (
  `vo_id` int(11) NOT NULL,
  `last_vo_version` int(11)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 */;

/*Table structure for table `v_vo_distribution` */

DROP TABLE IF EXISTS `v_vo_distribution`;

/*!50001 DROP VIEW IF EXISTS `v_vo_distribution` */;
/*!50001 DROP TABLE IF EXISTS `v_vo_distribution` */;

/*!50001 CREATE TABLE `v_vo_distribution` (
  `ancestor_ws_id` bigint(11) NOT NULL DEFAULT '0',
  `ws_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `release_id` int(11) DEFAULT NULL,
  `vo_id` int(11)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 */;

/*Table structure for table `v_vo_visibility` */

DROP TABLE IF EXISTS `v_vo_visibility`;

/*!50001 DROP VIEW IF EXISTS `v_vo_visibility` */;
/*!50001 DROP TABLE IF EXISTS `v_vo_visibility` */;

/*!50001 CREATE TABLE `v_vo_visibility` (
  `ancestor_ws_id` int(11) NOT NULL DEFAULT '0',
  `ws_id` int(11) NOT NULL DEFAULT '0',
  `release_id` int(11) DEFAULT NULL,
  `vo_id` int(11) NOT NULL,
  `vp_id` int(11) DEFAULT NULL,
  `vp_name` varchar(255) DEFAULT NULL,
  `locked` int(11) NOT NULL DEFAULT '0',
  `v_vector` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 */;

/*Table structure for table `v_vo_visibility_fast` */

DROP TABLE IF EXISTS `v_vo_visibility_fast`;

/*!50001 DROP VIEW IF EXISTS `v_vo_visibility_fast` */;
/*!50001 DROP TABLE IF EXISTS `v_vo_visibility_fast` */;

/*!50001 CREATE TABLE `v_vo_visibility_fast` (
  `ancestor_ws_id` int(11) NOT NULL DEFAULT '0',
  `ws_id` int(11) NOT NULL DEFAULT '0',
  `release_id` int(11) DEFAULT NULL,
  `vo_id` int(11) NOT NULL,
  `vp_id` int(11) DEFAULT NULL,
  `vp_name` varchar(255) DEFAULT NULL,
  `locked` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1 */;

/*Table structure for table `v_workitem_distribution` */

DROP TABLE IF EXISTS `v_workitem_distribution`;

/*!50001 DROP VIEW IF EXISTS `v_workitem_distribution` */;
/*!50001 DROP TABLE IF EXISTS `v_workitem_distribution` */;

/*!50001 CREATE TABLE `v_workitem_distribution` (
  `ws_id` int(11) NOT NULL DEFAULT '0',
  `wi_id` int(11) NOT NULL DEFAULT '0',
  `vo_id` int(11) NOT NULL,
  `vp_id` int(11) DEFAULT NULL,
  `wi_name` char(50) DEFAULT NULL,
  `is_active` bigint(21) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 */;

/*Table structure for table `v_workspace_hierarchy` */

DROP TABLE IF EXISTS `v_workspace_hierarchy`;

/*!50001 DROP VIEW IF EXISTS `v_workspace_hierarchy` */;
/*!50001 DROP TABLE IF EXISTS `v_workspace_hierarchy` */;

/*!50001 CREATE TABLE `v_workspace_hierarchy` (
  `ancestor_ws_id` bigint(11) NOT NULL DEFAULT '0',
  `ws_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `release_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 */;

/*View structure for view v_last_vo_version */

/*!50001 DROP TABLE IF EXISTS `v_last_vo_version` */;
/*!50001 DROP VIEW IF EXISTS `v_last_vo_version` */;

/*!50001 CREATE ALGORITHM=TEMPTABLE DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_last_vo_version` AS select `t_versioned_primitive`.`vo_id` AS `vo_id`,max(`t_versioned_primitive`.`vp_id`) AS `last_vo_version` from `t_versioned_primitive` group by `t_versioned_primitive`.`vo_id` */;

/*View structure for view v_vo_distribution */

/*!50001 DROP TABLE IF EXISTS `v_vo_distribution` */;
/*!50001 DROP VIEW IF EXISTS `v_vo_distribution` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_vo_distribution` AS select `w`.`ancestor_ws_id` AS `ancestor_ws_id`,`w`.`ws_id` AS `ws_id`,`w`.`name` AS `name`,`w`.`release_id` AS `release_id`,`h`.`vo_id` AS `vo_id` from (`v_workspace_hierarchy` `w` left join `t_ws_obj_ver_selector` `h` on((`w`.`ws_id` = `h`.`ws_id`))) */;

/*View structure for view v_vo_visibility */

/*!50001 DROP TABLE IF EXISTS `v_vo_visibility` */;
/*!50001 DROP VIEW IF EXISTS `v_vo_visibility` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.56.1` SQL SECURITY DEFINER VIEW `v_vo_visibility` AS select distinct `ws`.`ancestor_ws_id` AS `ancestor_ws_id`,`ws`.`ws_id` AS `ws_id`,`ws`.`release_id` AS `release_id`,`vos`.`vo_id` AS `vo_id`,`f_get_visible_vp`(`vos`.`vo_id`,`ws`.`ws_id`) AS `vp_id`,`vp`.`name` AS `vp_name`,`vos`.`locked` AS `locked`,`f_visibility_vector`(`vos`.`vo_id`,`ws`.`ws_id`) AS `v_vector` from ((`t_ws_obj_ver_selector` `vos` join `t_versioned_primitive` `vp` on(((`vos`.`vo_id` = `vp`.`vo_id`) and (`vos`.`vp_id` = `vp`.`vp_id`)))) join `t_workspace` `ws`) where `vos`.`ws_id` in (select `parent`.`ws_id` AS `vis_ws_id` from (`t_workspace` `node` join `t_workspace` `parent`) where ((`node`.`lft` between `parent`.`lft` and `parent`.`rgt`) and (`node`.`ws_id` = `ws`.`ws_id`))) order by `ws`.`ancestor_ws_id`,`ws`.`ws_id`,`vos`.`vo_id` */;

/*View structure for view v_vo_visibility_fast */

/*!50001 DROP TABLE IF EXISTS `v_vo_visibility_fast` */;
/*!50001 DROP VIEW IF EXISTS `v_vo_visibility_fast` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.56.1` SQL SECURITY DEFINER VIEW `v_vo_visibility_fast` AS select distinct `ws`.`ancestor_ws_id` AS `ancestor_ws_id`,`ws`.`ws_id` AS `ws_id`,`ws`.`release_id` AS `release_id`,`vos`.`vo_id` AS `vo_id`,`f_get_visible_vp`(`vos`.`vo_id`,`ws`.`ws_id`) AS `vp_id`,`vp`.`name` AS `vp_name`,`vos`.`locked` AS `locked` from ((`t_ws_obj_ver_selector` `vos` join `t_versioned_primitive` `vp` on(((`vos`.`vo_id` = `vp`.`vo_id`) and (`vos`.`vp_id` = `vp`.`vp_id`)))) join `t_workspace` `ws`) where `vos`.`ws_id` in (select `parent`.`ws_id` AS `vis_ws_id` from (`t_workspace` `node` join `t_workspace` `parent`) where ((`node`.`lft` between `parent`.`lft` and `parent`.`rgt`) and (`node`.`ws_id` = `ws`.`ws_id`))) order by `ws`.`ancestor_ws_id`,`ws`.`ws_id`,`vos`.`vo_id` */;

/*View structure for view v_workitem_distribution */

/*!50001 DROP TABLE IF EXISTS `v_workitem_distribution` */;
/*!50001 DROP VIEW IF EXISTS `v_workitem_distribution` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.56.1` SQL SECURITY DEFINER VIEW `v_workitem_distribution` AS (select `vf`.`ws_id` AS `ws_id`,`wi`.`wi_id` AS `wi_id`,`vf`.`vo_id` AS `vo_id`,`vf`.`vp_id` AS `vp_id`,`wi`.`wi_name` AS `wi_name`,(select count(0) AS `COUNT(*)` from `t_ws_workitem` `wswi` where ((`wswi`.`wi_id` = `wi`.`wi_id`) and (`wswi`.`ws_id` = `vf`.`ws_id`))) AS `is_active` from (`v_vo_visibility_fast` `vf` join `t_workitem` `wi` on((`wi`.`initiator_vo_id` = `vf`.`vo_id`))) order by `vf`.`ws_id`) */;

/*View structure for view v_workspace_hierarchy */

/*!50001 DROP TABLE IF EXISTS `v_workspace_hierarchy` */;
/*!50001 DROP VIEW IF EXISTS `v_workspace_hierarchy` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`192.168.56.1` SQL SECURITY DEFINER VIEW `v_workspace_hierarchy` AS select 0 AS `ancestor_ws_id`,`t_workspace`.`ws_id` AS `ws_id`,`t_workspace`.`name` AS `name`,`t_workspace`.`release_id` AS `release_id` from `t_workspace` where (`t_workspace`.`ancestor_ws_id` = 0) union select `p`.`ws_id` AS `ancestor_ws_id`,`c`.`ws_id` AS `local_ws_id`,`c`.`name` AS `local_ws_name`,`c`.`release_id` AS `release_id` from (`t_workspace` `p` join `t_workspace` `c` on((`p`.`ws_id` = `c`.`ancestor_ws_id`))) order by `ancestor_ws_id`,`name` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

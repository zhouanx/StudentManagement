/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.5.28 : Database - studentsdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`studentsdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `studentsdb`;

/*Table structure for table `studentinfo` */

DROP TABLE IF EXISTS `studentinfo`;

CREATE TABLE `studentinfo` (
  `sid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '学号',
  `sname` varchar(30) NOT NULL COMMENT '学员姓名',
  `sgender` varchar(2) NOT NULL DEFAULT '男' COMMENT '学员性别',
  `sage` int(11) NOT NULL COMMENT '学员年龄',
  `saddress` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `semail` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `studentinfo` */

insert  into `studentinfo`(`sid`,`sname`,`sgender`,`sage`,`saddress`,`semail`) values 
(1,'张全芳','女',23,'北京市朝阳区','quanfang@163.com'),
(2,'李思阳','男',19,'北京市崇文区','siyang@126.com'),
(3,'候全如','女',21,'江苏省南京市','quanru@126.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- MySQL dump 10.13  Distrib 5.7.44, for Linux (x86_64)
--
-- Host: localhost    Database: cl12811977
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `cl12811977`
--

/*!40000 DROP DATABASE IF EXISTS `cl12811977`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cl12811977` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cl12811977`;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `value` longtext COLLATE utf8mb4_unicode_ci COMMENT '值',
  `url` longtext COLLATE utf8mb4_unicode_ci COMMENT '链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'2025-02-23 13:09:52','swiper1','file/swiperPicture1.jpg',NULL),(2,'2025-02-23 13:09:52','swiper2','file/swiperPicture2.jpg',NULL),(3,'2025-02-23 13:09:52','swiper3','file/1740316959612.png',NULL);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exampaper`
--

DROP TABLE IF EXISTS `exampaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exampaper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `time` int(11) NOT NULL COMMENT '考试时长(分钟)',
  `status` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '启用' COMMENT '试卷状态',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康小测';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exampaper`
--

LOCK TABLES `exampaper` WRITE;
/*!40000 ALTER TABLE `exampaper` DISABLE KEYS */;
INSERT INTO `exampaper` VALUES (1,'2025-02-23 13:09:52',120,'1','试卷1');
/*!40000 ALTER TABLE `exampaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examquestion`
--

DROP TABLE IF EXISTS `examquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examquestion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `paperid` bigint(20) NOT NULL COMMENT '试卷id（外键）',
  `papername` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
  `questionname` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题名称',
  `options` longtext COLLATE utf8mb4_unicode_ci COMMENT '选项，json字符串',
  `score` bigint(20) DEFAULT '0' COMMENT '分值',
  `answer` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正确答案',
  `analysis` longtext COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
  `type` bigint(20) DEFAULT NULL COMMENT '试题类型',
  `sequence` bigint(20) DEFAULT NULL COMMENT '试题排序，值越大排越前面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='试题管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examquestion`
--

LOCK TABLES `examquestion` WRITE;
/*!40000 ALTER TABLE `examquestion` DISABLE KEYS */;
INSERT INTO `examquestion` VALUES (1,'2025-02-23 13:09:52',1,'试卷1','1+1等于多少?','[{\"text\":\"2\",\"code\":\"A\"},{\"text\":\"11\",\"code\":\"B\"},{\"text\":\"十\",\"code\":\"C\"},{\"text\":\"王\",\"code\":\"D\"}]',20,'A','2',0,1),(2,'2025-02-23 13:09:52',1,'试卷1','2-1等于1，对吗?','[{\"text\":\"对\",\"code\":\"A\"},{\"text\":\"错\",\"code\":\"B\"}]',20,'A','2-1等于1',2,2),(3,'2025-02-23 13:09:52',1,'试卷1','下面哪些数是质数？。','[{\"text\":\"2\",\"code\":\"A\"},{\"text\":\"3\",\"code\":\"B\"},{\"text\":\"4\",\"code\":\"C\"},{\"text\":\"5\",\"code\":\"D\"}]',30,'A,B,D','2,3,5属于质数',1,3),(4,'2025-02-23 13:09:52',1,'试卷1','最小的自然是( )。','[]',30,'0','最小的自然数是0',3,4);
/*!40000 ALTER TABLE `examquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examquestionbank`
--

DROP TABLE IF EXISTS `examquestionbank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examquestionbank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `questionname` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题名称',
  `options` longtext COLLATE utf8mb4_unicode_ci COMMENT '选项，json字符串',
  `score` bigint(20) DEFAULT '0' COMMENT '分值',
  `answer` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正确答案',
  `analysis` longtext COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
  `type` bigint(20) DEFAULT NULL COMMENT '试题类型',
  `sequence` bigint(20) DEFAULT NULL COMMENT '试题排序，值越大排越前面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='试题库管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examquestionbank`
--

LOCK TABLES `examquestionbank` WRITE;
/*!40000 ALTER TABLE `examquestionbank` DISABLE KEYS */;
INSERT INTO `examquestionbank` VALUES (1,'2025-02-23 13:09:52','试题名称1','选项，json字符串1',1,'正确答案1','答案解析1',1,1),(2,'2025-02-23 13:09:52','试题名称2','选项，json字符串2',2,'正确答案2','答案解析2',2,2),(3,'2025-02-23 13:09:52','试题名称3','选项，json字符串3',3,'正确答案3','答案解析3',3,3),(4,'2025-02-23 13:09:52','试题名称4','选项，json字符串4',4,'正确答案4','答案解析4',4,4),(5,'2025-02-23 13:09:52','试题名称5','选项，json字符串5',5,'正确答案5','答案解析5',5,5),(6,'2025-02-23 13:09:52','试题名称6','选项，json字符串6',6,'正确答案6','答案解析6',6,6),(7,'2025-02-23 13:09:52','试题名称7','选项，json字符串7',7,'正确答案7','答案解析7',7,7),(8,'2025-02-23 13:09:52','试题名称8','选项，json字符串8',8,'正确答案8','答案解析8',8,8),(9,'2025-02-23 13:09:52','试题名称9','选项，json字符串9',9,'正确答案9','答案解析9',9,9),(10,'2025-02-23 13:09:52','试题名称10','选项，json字符串10',10,'正确答案10','答案解析10',10,10);
/*!40000 ALTER TABLE `examquestionbank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examrecord`
--

DROP TABLE IF EXISTS `examrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examrecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `paperid` bigint(20) NOT NULL COMMENT '试卷id（外键）',
  `papername` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
  `questionid` bigint(20) NOT NULL COMMENT '试题id（外键）',
  `questionname` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题名称',
  `type` bigint(20) DEFAULT NULL COMMENT '试题类型',
  `ismark` bigint(20) DEFAULT NULL COMMENT '是否批卷',
  `options` longtext COLLATE utf8mb4_unicode_ci COMMENT '选项，json字符串',
  `score` bigint(20) DEFAULT '0' COMMENT '分值',
  `answer` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正确答案',
  `analysis` longtext COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
  `myscore` bigint(20) DEFAULT '0' COMMENT '试题得分',
  `myanswer` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '考生答案',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康小测';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examrecord`
--

LOCK TABLES `examrecord` WRITE;
/*!40000 ALTER TABLE `examrecord` DISABLE KEYS */;
INSERT INTO `examrecord` VALUES (1,'2025-02-23 13:17:46','账号1',1,'试卷1',1,'1+1等于多少?',0,1,'[{\"text\":\"2\",\"code\":\"A\",\"checked\":false},{\"text\":\"11\",\"code\":\"B\",\"checked\":false},{\"text\":\"十\",\"code\":\"C\",\"checked\":true},{\"text\":\"王\",\"code\":\"D\",\"checked\":false}]',20,'A','2',0,NULL,111),(2,'2025-02-23 13:17:46','账号1',1,'试卷1',3,'下面哪些数是质数？。',1,1,'[{\"text\":\"2\",\"code\":\"A\",\"checked\":false},{\"text\":\"3\",\"code\":\"B\",\"checked\":false},{\"text\":\"4\",\"code\":\"C\",\"checked\":true},{\"text\":\"5\",\"code\":\"D\",\"checked\":true}]',30,'A,B,D','2,3,5属于质数',0,'C,D',111),(3,'2025-02-23 13:19:56','111',1,'试卷1',1,'1+1等于多少?',0,1,'[{\"text\":\"2\",\"code\":\"A\",\"checked\":false},{\"text\":\"11\",\"code\":\"B\",\"checked\":true},{\"text\":\"十\",\"code\":\"C\",\"checked\":false},{\"text\":\"王\",\"code\":\"D\",\"checked\":false}]',20,'A','2',0,NULL,1740316782534),(4,'2025-02-23 13:19:56','111',1,'试卷1',3,'下面哪些数是质数？。',1,1,'[{\"text\":\"2\",\"code\":\"A\",\"checked\":false},{\"text\":\"3\",\"code\":\"B\",\"checked\":true},{\"text\":\"4\",\"code\":\"C\",\"checked\":true},{\"text\":\"5\",\"code\":\"D\",\"checked\":true}]',30,'A,B,D','2,3,5属于质数',0,'B,C,D',1740316782534);
/*!40000 ALTER TABLE `examrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子标题',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子内容',
  `parentid` bigint(20) DEFAULT NULL COMMENT '父节点id',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `isdone` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `is_top` int(11) DEFAULT '0' COMMENT '是否置顶',
  `top_time` datetime DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='论坛交流';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1,'2025-02-23 13:09:52','帖子标题1','帖子内容1',0,1,'用户名1','file/forumAvatarurl1.jpg,file/forumAvatarurl2.jpg,file/forumAvatarurl3.jpg','开放',0,'2025-02-23 21:09:52'),(2,'2025-02-23 13:09:52','帖子标题2','帖子内容2',0,2,'用户名2','file/forumAvatarurl2.jpg,file/forumAvatarurl3.jpg,file/forumAvatarurl4.jpg','开放',0,'2025-02-23 21:09:52'),(3,'2025-02-23 13:09:52','帖子标题3','帖子内容3',0,3,'用户名3','file/forumAvatarurl3.jpg,file/forumAvatarurl4.jpg,file/forumAvatarurl5.jpg','开放',0,'2025-02-23 21:09:52'),(4,'2025-02-23 13:09:52','帖子标题4','帖子内容4',0,4,'用户名4','file/forumAvatarurl4.jpg,file/forumAvatarurl5.jpg,file/forumAvatarurl6.jpg','开放',0,'2025-02-23 21:09:52'),(5,'2025-02-23 13:09:52','帖子标题5','帖子内容5',0,5,'用户名5','file/forumAvatarurl5.jpg,file/forumAvatarurl6.jpg,file/forumAvatarurl7.jpg','开放',0,'2025-02-23 21:09:52'),(6,'2025-02-23 13:09:52','帖子标题6','帖子内容6',0,6,'用户名6','file/forumAvatarurl6.jpg,file/forumAvatarurl7.jpg,file/forumAvatarurl8.jpg','开放',0,'2025-02-23 21:09:52'),(7,'2025-02-23 13:09:52','帖子标题7','帖子内容7',0,7,'用户名7','file/forumAvatarurl7.jpg,file/forumAvatarurl8.jpg,file/forumAvatarurl9.jpg','开放',0,'2025-02-23 21:09:52'),(8,'2025-02-23 13:09:52','帖子标题8','帖子内容8',0,8,'用户名8','file/forumAvatarurl8.jpg,file/forumAvatarurl9.jpg,file/forumAvatarurl10.jpg','开放',0,'2025-02-23 21:09:52'),(9,'2025-02-23 13:09:52','帖子标题9','帖子内容9',0,9,'用户名9','file/forumAvatarurl9.jpg,file/forumAvatarurl10.jpg,file/forumAvatarurl11.jpg','开放',0,'2025-02-23 21:09:52'),(10,'2025-02-23 13:09:52','帖子标题10','帖子内容10',0,10,'用户名10','file/forumAvatarurl10.jpg,file/forumAvatarurl11.jpg,file/forumAvatarurl12.jpg','开放',0,'2025-02-23 21:09:52'),(12,'2025-02-23 13:20:53','管理啊尬发啦尬','<p>管理啊尬发啦尬管理啊尬发啦尬</p>',0,1740316782534,'111',NULL,'开放',1,'2025-02-23 21:20:53');
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genzongjianyi`
--

DROP TABLE IF EXISTS `genzongjianyi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genzongjianyi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `yundongmubiao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运动目标',
  `diaozhengmubiao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调整目标',
  `diaozhengjianyi` longtext COLLATE utf8mb4_unicode_ci COMMENT '调整建议',
  `gengxinshijian` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='跟踪建议';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genzongjianyi`
--

LOCK TABLES `genzongjianyi` WRITE;
/*!40000 ALTER TABLE `genzongjianyi` DISABLE KEYS */;
INSERT INTO `genzongjianyi` VALUES (1,'2025-02-23 13:09:52','账号1','姓名1','运动目标1','调整目标1','调整建议1','2025-02-23 21:09:52'),(2,'2025-02-23 13:09:52','账号2','姓名2','运动目标2','调整目标2','调整建议2','2025-02-23 21:09:52'),(3,'2025-02-23 13:09:52','账号3','姓名3','运动目标3','调整目标3','调整建议3','2025-02-23 21:09:52'),(4,'2025-02-23 13:09:52','账号4','姓名4','运动目标4','调整目标4','调整建议4','2025-02-23 21:09:52'),(5,'2025-02-23 13:09:52','账号5','姓名5','运动目标5','调整目标5','调整建议5','2025-02-23 21:09:52'),(6,'2025-02-23 13:09:52','账号6','姓名6','运动目标6','调整目标6','调整建议6','2025-02-23 21:09:52'),(7,'2025-02-23 13:09:52','账号7','姓名7','运动目标7','调整目标7','调整建议7','2025-02-23 21:09:52'),(8,'2025-02-23 13:09:52','账号8','姓名8','运动目标8','调整目标8','调整建议8','2025-02-23 21:09:52'),(9,'2025-02-23 13:09:52','账号9','姓名9','运动目标9','调整目标9','调整建议9','2025-02-23 21:09:52'),(10,'2025-02-23 13:09:52','账号10','姓名10','运动目标10','调整目标10','调整建议10','2025-02-23 21:09:52'),(11,'2025-02-23 13:18:25','账号10','姓名10','运动目标10','啊啊啊','啊啊啊','2025-02-23 21:18:20'),(12,'2025-02-23 13:18:25','账号10','姓名10','运动目标10','啊啊啊','啊啊啊','2025-02-23 21:18:20'),(13,'2025-02-23 13:21:31','111','小胡','CC','DD','DDDDD ','2025-02-23 21:21:26');
/*!40000 ALTER TABLE `genzongjianyi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jiankangmubiao`
--

DROP TABLE IF EXISTS `jiankangmubiao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jiankangmubiao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mubiaomingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标名称',
  `zhidingshijian` datetime DEFAULT NULL COMMENT '制定时间',
  `duanlianmubiao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '锻炼目标',
  `yundongmubiao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运动目标',
  `yinshimubiao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮食目标',
  `jihuakaishi` date DEFAULT NULL COMMENT '计划开始',
  `jihuajieshu` date DEFAULT NULL COMMENT '计划结束',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康目标';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jiankangmubiao`
--

LOCK TABLES `jiankangmubiao` WRITE;
/*!40000 ALTER TABLE `jiankangmubiao` DISABLE KEYS */;
INSERT INTO `jiankangmubiao` VALUES (1,'2025-02-23 13:09:52','目标名称1','2025-02-23 21:09:52','锻炼目标1','运动目标1','饮食目标1','2025-02-23','2025-02-23','账号1','姓名1'),(2,'2025-02-23 13:09:52','目标名称2','2025-02-23 21:09:52','锻炼目标2','运动目标2','饮食目标2','2025-02-23','2025-02-23','账号2','姓名2'),(3,'2025-02-23 13:09:52','目标名称3','2025-02-23 21:09:52','锻炼目标3','运动目标3','饮食目标3','2025-02-23','2025-02-23','账号3','姓名3'),(4,'2025-02-23 13:09:52','目标名称4','2025-02-23 21:09:52','锻炼目标4','运动目标4','饮食目标4','2025-02-23','2025-02-23','账号4','姓名4'),(5,'2025-02-23 13:09:52','目标名称5','2025-02-23 21:09:52','锻炼目标5','运动目标5','饮食目标5','2025-02-23','2025-02-23','账号5','姓名5'),(6,'2025-02-23 13:09:52','目标名称6','2025-02-23 21:09:52','锻炼目标6','运动目标6','饮食目标6','2025-02-23','2025-02-23','账号6','姓名6'),(7,'2025-02-23 13:09:52','目标名称7','2025-02-23 21:09:52','锻炼目标7','运动目标7','饮食目标7','2025-02-23','2025-02-23','账号7','姓名7'),(8,'2025-02-23 13:09:52','目标名称8','2025-02-23 21:09:52','锻炼目标8','运动目标8','饮食目标8','2025-02-23','2025-02-23','账号8','姓名8'),(9,'2025-02-23 13:09:52','目标名称9','2025-02-23 21:09:52','锻炼目标9','运动目标9','饮食目标9','2025-02-23','2025-02-23','账号9','姓名9'),(10,'2025-02-23 13:09:52','目标名称10','2025-02-23 21:09:52','锻炼目标10','运动目标10','饮食目标10','2025-02-23','2025-02-23','账号10','姓名10'),(11,'2025-02-23 13:20:13','CC','2025-02-23 21:20:06','CC','CC','CC','2025-02-23','2025-03-23','111','小胡');
/*!40000 ALTER TABLE `jiankangmubiao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jiankangtixing`
--

DROP TABLE IF EXISTS `jiankangtixing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jiankangtixing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `tixingneirong` longtext COLLATE utf8mb4_unicode_ci COMMENT '提醒内容',
  `tixingshijian` date DEFAULT NULL COMMENT '提醒时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康提醒';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jiankangtixing`
--

LOCK TABLES `jiankangtixing` WRITE;
/*!40000 ALTER TABLE `jiankangtixing` DISABLE KEYS */;
INSERT INTO `jiankangtixing` VALUES (1,'2025-02-23 13:09:52','账号1','姓名1','提醒内容1','2025-02-23'),(2,'2025-02-23 13:09:52','账号2','姓名2','提醒内容2','2025-02-23'),(3,'2025-02-23 13:09:52','账号3','姓名3','提醒内容3','2025-02-23'),(4,'2025-02-23 13:09:52','账号4','姓名4','提醒内容4','2025-02-23'),(5,'2025-02-23 13:09:52','账号5','姓名5','提醒内容5','2025-02-23'),(6,'2025-02-23 13:09:52','账号6','姓名6','提醒内容6','2025-02-23'),(7,'2025-02-23 13:09:52','账号7','姓名7','提醒内容7','2025-02-23'),(8,'2025-02-23 13:09:52','账号8','姓名8','提醒内容8','2025-02-23'),(9,'2025-02-23 13:09:52','账号9','姓名9','提醒内容9','2025-02-23'),(10,'2025-02-23 13:09:52','账号10','姓名10','提醒内容10','2025-02-23'),(11,'2025-02-23 13:18:37','账号1','姓名1','啊啊','2025-02-23'),(12,'2025-02-23 13:18:37','账号1','姓名1','啊啊','2025-02-23'),(13,'2025-02-23 13:21:46','111','小胡','的阿公个 的发','2025-02-23');
/*!40000 ALTER TABLE `jiankangtixing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `menujson` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'2025-02-23 13:09:52','[{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goodsnew\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"yonghu\",\"menu\":\"用户\",\"menuJump\":\"列表\",\"tableName\":\"yonghu\"}],\"fontClass\":\"icon-user1\",\"menu\":\"用户管理\",\"unicode\":\"&#xef97;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"开始运动\",\"发送建议\"],\"appFrontIcon\":\"cuIcon-goodsnew\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"发送建议\"],\"classname\":\"jiankangmubiao\",\"menu\":\"健康目标\",\"menuJump\":\"列表\",\"tableName\":\"jiankangmubiao\"}],\"fontClass\":\"icon-common34\",\"menu\":\"健康目标管理\",\"unicode\":\"&#xee85;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-circle\",\"buttons\":[\"查看\",\"修改\",\"删除\"],\"classname\":\"yundongjilu\",\"menu\":\"运动记录\",\"menuJump\":\"列表\",\"tableName\":\"yundongjilu\"}],\"fontClass\":\"icon-common43\",\"menu\":\"运动记录管理\",\"unicode\":\"&#xef27;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-pay\",\"buttons\":[\"查看\",\"修改\",\"删除\"],\"classname\":\"genzongjianyi\",\"menu\":\"跟踪建议\",\"menuJump\":\"列表\",\"tableName\":\"genzongjianyi\"}],\"fontClass\":\"icon-common39\",\"menu\":\"跟踪建议管理\",\"unicode\":\"&#xeeba;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goods\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"jiankangtixing\",\"menu\":\"健康提醒\",\"menuJump\":\"列表\",\"tableName\":\"jiankangtixing\"}],\"fontClass\":\"icon-common3\",\"menu\":\"健康提醒管理\",\"unicode\":\"&#xeda5;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-keyboard\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"查看评论\"],\"classname\":\"forum\",\"menu\":\"论坛交流\",\"tableName\":\"forum\"}],\"fontClass\":\"icon-common38\",\"menu\":\"论坛管理\",\"unicode\":\"&#xeeb2;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"组卷\",\"调查统计\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"查看\",\"调查统计\"],\"classname\":\"exampaper\",\"menu\":\"健康小测\",\"tableName\":\"exampaper\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"打印\",\"导出\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"打印\"],\"classname\":\"examquestion\",\"menu\":\"试题管理\",\"menuJump\":\"列表\",\"tableName\":\"examquestion\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"打印\",\"导出\"],\"appFrontIcon\":\"cuIcon-full\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"examquestionbank\",\"menu\":\"试题库管理\",\"menuJump\":\"列表\",\"tableName\":\"examquestionbank\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"批卷\"],\"appFrontIcon\":\"cuIcon-keyboard\",\"buttons\":[\"查看\"],\"classname\":\"examrecord\",\"menu\":\"健康小测\",\"tableName\":\"examrecord\"}],\"fontClass\":\"icon-common22\",\"menu\":\"试卷管理\",\"unicode\":\"&#xee04;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-camera\",\"buttons\":[\"查看\",\"修改\"],\"classname\":\"config\",\"menu\":\"轮播图\",\"menuJump\":\"列表\",\"tableName\":\"config\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-brand\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"news\",\"menu\":\"健康资讯\",\"menuJump\":\"列表\",\"tableName\":\"news\"}],\"fontClass\":\"icon-common13\",\"menu\":\"系统管理\",\"unicode\":\"&#xedf7;\"}],\"frontMenu\":[{\"child\":[{\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"新增\"],\"classname\":\"forum\",\"menu\":\"论坛交流\",\"menuJump\":\"列表\",\"tableName\":\"forum\"}],\"menu\":\"论坛交流\"}],\"hasBackLogin\":\"是\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"否\",\"hasFrontRegister\":\"否\",\"pathName\":\"users\",\"roleName\":\"管理员\",\"tableName\":\"users\"},{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"开始运动\",\"发送建议\"],\"appFrontIcon\":\"cuIcon-goodsnew\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"开始运动\"],\"classname\":\"jiankangmubiao\",\"menu\":\"健康目标\",\"menuJump\":\"列表\",\"tableName\":\"jiankangmubiao\"}],\"fontClass\":\"icon-common34\",\"menu\":\"健康目标管理\",\"unicode\":\"&#xee85;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-circle\",\"buttons\":[\"查看\",\"修改\"],\"classname\":\"yundongjilu\",\"menu\":\"运动记录\",\"menuJump\":\"列表\",\"tableName\":\"yundongjilu\"}],\"fontClass\":\"icon-common43\",\"menu\":\"运动记录管理\",\"unicode\":\"&#xef27;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-pay\",\"buttons\":[\"查看\"],\"classname\":\"genzongjianyi\",\"menu\":\"跟踪建议\",\"menuJump\":\"列表\",\"tableName\":\"genzongjianyi\"}],\"fontClass\":\"icon-common39\",\"menu\":\"跟踪建议管理\",\"unicode\":\"&#xeeba;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goods\",\"buttons\":[\"查看\"],\"classname\":\"jiankangtixing\",\"menu\":\"健康提醒\",\"menuJump\":\"列表\",\"tableName\":\"jiankangtixing\"}],\"fontClass\":\"icon-common3\",\"menu\":\"健康提醒管理\",\"unicode\":\"&#xeda5;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"批卷\"],\"appFrontIcon\":\"cuIcon-keyboard\",\"buttons\":[\"查看\"],\"classname\":\"examrecord\",\"menu\":\"健康小测\",\"tableName\":\"examrecord\"}],\"fontClass\":\"icon-common22\",\"menu\":\"试卷管理\",\"unicode\":\"&#xee04;\"}],\"frontMenu\":[{\"child\":[{\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"新增\"],\"classname\":\"forum\",\"menu\":\"论坛交流\",\"menuJump\":\"列表\",\"tableName\":\"forum\"}],\"menu\":\"论坛交流\"}],\"hasBackLogin\":\"否\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"是\",\"hasFrontRegister\":\"是\",\"pathName\":\"yonghu\",\"roleName\":\"用户\",\"tableName\":\"yonghu\"}]');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `introduction` longtext COLLATE utf8mb4_unicode_ci COMMENT '简介',
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `thumbsup_number` int(11) DEFAULT '0' COMMENT '赞',
  `crazily_number` int(11) DEFAULT '0' COMMENT '踩',
  `storeup_number` int(11) DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康资讯';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'2025-02-23 13:09:52','余生，愿你活出自己的精彩','曾几何时，为了让自己显得合群，选择去做自己不喜欢的事;为了讨好喜欢的人，一次次地委屈自己;甚至，为了陌生人的一个眼神，就陷入了深深的自我怀疑。','file/newsPicture1.jpg','可是，这样小心翼翼的你，一定很累吧。你以为照顾了所有人的想法，就能得到他们的喜欢;你以为逼着自己合群，就能真的合群。直到后来才发现，在日复一日对自己的为难中，你逐渐弄丢了真实的自己.\n殊不知，人生在世，最不值得的事情，就是在别人的眼光中，迷失自己。其实，正如世界上没有十全十美的人，你也同样无法做到让每个人满意。\n世上没有不快乐的人，只有不肯让自己快乐的心。有一句话说:\n如果一段关系让你变得卑微，你可以选择抽身离去;如果一个人总是让你悲伤哭泣，你要学会放手。\n始终相信，每个人最初的样子，都是最美好的。真正喜欢你的人，会喜欢你原来的样子:真正爱你的从来不需要你的委曲求全。\n后半生，请把身上的枷锁，全部解除。去做喜欢的事，去听自己内心的声音。你会发现，这世上没有什么事情比活出自己更加幸福。\n要相信，真实的自己亦是一道风景线。你若盛开，清风自来。愿你此生尽兴，不负韶华，',1,1,1),(2,'2025-02-23 13:09:52','岁月无常，好好珍惜','常听到这样的话，如果一切可以重新开始，我一定改掉任性，学会坚持，如果有下辈子，我一定好好珍惜，不会再弄丢原本属于自己的幸福。','file/newsPicture2.jpg','然而，世上有很多事情没有再来一次的机会，一旦错失就意味着无法弥补，正如人与人之间，有的只是一世的缘分，一旦转身，就意味着再无可能。\n这一生只活一次，有些人也只爱一回。\n所以，在拥有时就好好把握，对于来世，可以适当憧憬，但不要一昧去幻想，活着，唯有珍惜身边做好眼前事，才能让明天少一些遗憾。人，\n既然有幸来到这个世上，那就要努力把这辈子活好。岁月其实远没有想象中的那么漫长，若是能爱就好好爱，别让说好相伴一生的人，中途散走;若是当下能相知相守，就别去等待虚无缥缈的未来。\n就像一位网友留言说的:我从来不去期待下辈子，我要的只有今生，世事无常，来世是怎样的光景，谁都无法预知。对我来说，在有限的时光里，善待爱自己及自己爱的人，用心珍惜相聚的每一时刻，努力过好当下就足够了。\n时光匆匆，韶华似水，每个人都只有这短暂的一生。\n有些事，如果喜欢就坚定努力的去做，有些人，如果爱就真诚用心地去爱，别幻想着重来，别总是寄希望于下辈子。\n下辈子，彼此也许再也不会遇见，就算遇见了，谁还会记得谁，今生相互承诺的两个人，来生也许相见不相识。\n想起之前看过的一句话:人生没有假如，我们没有来世，下辈子，你我也许只是大千世界里互不相识的两个陌生人。\n的确，生命只有一次，今生有幸相遇的人，下辈子不一定能遇见，就算能遇见，谁能把谁陪伴，谁又是谁的谁?\n余生不长，趁着岁月未老，好好珍惜这辈子的缘分吧，别等到失去了才悔不该当初。',2,2,2),(3,'2025-02-23 13:09:52','与其背着烦恼活，不如带着美好过','一直觉得，感情里，无疾而终并不可怕，可怕的是结束后，你还抱着回忆不肯放;岁月中，失去并不可怕，可怕的是失去后，你还揪着曾经不肯忘。\n有些事，明知道不应该继续，却始终没有转身的勇气;有些人，明知道已经成为过去，却还是舍不得放手;有时候，明知道纠缠于往事旧人只会徒增伤悲，却仍然执着的去回忆，去留恋。','file/newsPicture3.jpg','人生短短几十年，为什么要蹉跎浪费在无可挽回的失去上，为什么不酒脱一点，看开一点，活得轻松快乐一点。\n这世间，没有谁的人生是十全十美的，总要面对一些离别，总要经历一些失去，如果总对身边的一切执念太深，只会越活越累。\n时间不停向前，人也不可避免的要向前看。面对一些不如意、不顺心的事，该放的就放，该忘的就忘，别总把自己困在失败的悲伤中。\n生而为人，放不是无能，而是一种洒脱，忘更不是懦弱，而是一种智慧。\n在来去匆匆，聚散不定的现实里，放下不属于自己的感情，才能有新的开始，忘记心中的烦恼，才能收获愉快的心情。\n所以，有些离开的人，该放就放下吧，一直念念不忘，伤害的是自己，有些烦心事，该忘就忘了吧一直耿耿于怀，痛苦的是自己。\n-辈子本就不长，何必让自己活得那么累，过去的情就让它过去，别再留恋，想不通的事就算了，别再纠结。\n人生的旅途，总有些事让人烦，总有些情让人伤。只有学会忘记过往的不如意，忘记一些不必要的烦恼，以轻盈的身心重新出发，才不会被旧人旧事所累。\n记得，每个人心灵的空间都是有限的，当装多了纠结与伤感，就装不下自在与欢快了。要想日子过得简单轻松一点，就要懂得放下执念，忘掉痛苦，清除伤。',3,3,3),(4,'2025-02-23 13:09:52','理想很丰满，现实很骨感','游戏里的你帅气无比，乘风御剑，通关杀敌无所不能，你满足于那其中的成就感，你满足于通关的畅快淋漓。','file/newsPicture4.jpg','年轻人，可那毕竟不是现实，理想很丰满，现实很骨感这不是玩笑，你不走出来，怎么会知\n你真的想等到某天喜欢上一个姑娘时，才去纠结咖啡钱电影钱你要如何与爸妈张口，才去苦恼为她买礼物你要怎样省吃俭用节衣缩食?\n你没有挥金如土的.魄力，没有殷实的家境，没有既定的光芒锦绣的前程，二十出头的年纪，你不努力，你想拿什么报答那个在你身边用最好的青春陪伴你的姑娘?你又拿什么报答含辛茹苦养育你这么多年的父母?\n生活不是游戏，不会给你那么多反复再来的机会，青春年少风华正茂，年轻人，你不努力，你想指望什么?\n年轻人，走出来奋斗吧!累也好苦也罢。如果你现在对自己各种放纵，你指望以后你用什么条件来放松?\n年轻人，你要努力赚钱，为了自己，也为了你爸妈。为了他们在以后逛商场的时候，可以给自己买东西像给你买东西一样毫不犹豫。为了他们可以在同老友谈起你时可以一脸安详，而不是想着，这个月，儿子交了房租还有钱吃饭么。我们这个年龄的人，已经像吸血鬼一样巴着父母活了二十多年了，还真的要继续吸着他们的血活下去么?\n年轻人，停止期望凡事简单容易。如果总是挑简单的事情做，那你永远成不了大器，生活总会在始料不及时考验你，所以你必须挑战困难，保持坚强，世上任何值得拥有的东西都需要付诸努力，勤奋与自律，倘若自甘平庸，那你就会变得庸碌无为。\n年轻人，与我们一起，挑战一下自己，拥抱一下梦想。你想要的生活，跳起来，一定够得到!',4,4,4),(5,'2025-02-23 13:09:52','理想是一种精神上的追求','如果前方是一片黑暗，理想是否会黯淡?黑暗终究不能限制理想的步伐，只是需要时间。','file/newsPicture5.jpg','是否会撕裂黑暗的结界?给理想一点时间，是否会完成理想的目标?太多时候，黑给黑暗一缕曙光，暗限制了理想的脚步，时间成了撕裂黑暗的曙光。社会的压抑，未知的迷茫，甚至是事实的打击，都成了所谓的黑暗。\n理想应该是一种精神上的追求，一种我们内心深处的渴望。也许理想的种类太多，不一样的人也有不一样的理想。但唯一的共同点就是我们在追求，追求自己精神上亦或是心中的目标。但总是不尽人意，一来存在太多不确定因素，二来时间还不够。理想并不是一而就的空话。\n想过放弃，因为理想与现实的差距。但又不想放弃，因为理想是精神上的追求心中的渴望。纠结了太多，却是否想过在纠结的时候，已经选择了错过。被刻意选择的错过是什么?是路过。我们在不经意间路过了多少理想，只是因为纠结。如果坚定自己的理想，用时间去行动，是否会少几次刻意的错过:-次，与一位朋友谈到如何乐观，然后追求理想。朋友想了一会儿，给我的回答是正能量与时间。他说，想要乐观就要不断给自己正能量。事物有阴阳两面，如果阳代表乐观，那在我们看待一件事时要看阳面千万别看阴面。这样自己不会消极，某种程度上来说就是一种正能量。想要追求理想，务必要花费时间。正如没有风平浪静的大海，想要追求理想，要时间。给理想一点时间，让自己去追求，不顾切的追求。\n点点滴滴的小事构成了我们的生活，点点滴滴的努力给理想铺平了路。每一次小成功都为理想迈进了一步。每一次失败，都告诉我们一些教训，反方向来说为下一次成功提供了经验。不要觉得失败是可怕的，长远来看，正是失败才有了成功。\n时间在流逝。如果安于现状，就会乐不思。理想需要时间不代表我们可以挥霍，我们能做的是珍惜时间，去为理想拼搏。\n间接意义上讲，理想的时间是我们的努力。\n如果正在路上，一直任性向前走，忘记时间不回头。',5,5,5),(6,'2025-02-23 13:09:52','人生如梦，梦想是帆','人生如梦，梦想是帆，每个人都有一个只属于自己的梦，但我们同属一个国家，所以每个人的梦又与国家的兴衰荣辱紧密相连。先哲顾炎武曾说:天下兴亡，匹夫有责。只有国家好，大家才能好。','file/newsPicture6.jpg','我依然清楚的记得:\n当甲午战争战败，日寇无礼踏破中国的门户;当八国联军侵入北京，无情掠夺中国的财产:当七七事变发生，中国的老人、妇孺被残忍杀害的时候，我在想那时中国的梦是怎样的!\n我虽不曾亲眼看到，但那却是铁一般的事实。因为从老人们那深邃的眼神中可以感到无尽的愤懑;从他们干瘪的脸颊可以看到深情的泪水，从他们嘹亮的军歌中可以想到那奋勇杀敌时的豪迈;从他们激昂话语中听到那誓要捍卫家园振兴中华的誓言。作为新一代青年的我们难道不应该树立远大的理想，付之以踏实的行动，去继承先辈们的使命。去实现中华民族的伟大崛起和复兴吗?\n有梦才能使中国繁荣!\n在改革开放以来中国取得了一系列的可以载入中国史册的成就。香港、澳门的回归，经济特区的建立，使中国成为发展国家中的经济大国，科技先进国和军事强国。当中国成功举办奥运的时候，当神九飞天的时候，当蛟龙入海的时候，当航母下水的时候，当莫言荣获诺贝尔文学奖的时候。我相信每个人都感觉到了无比的自豪。但是现在的中国与其他发达国家还有很大差距。作为新一代的我们，难道不应该志存高远吗?\n我想有的人会说，我们的力量是有限的。的确个人的力量很渺小，但是中国梦就是因一个个微不足道的个人的梦一直汇集、汇集，然后凝聚成的一个巨大的梦。冯至在《十四行诗》中写道，我们准备着，深深领受，那些意想不到的奇迹，在漫长的岁月里，忽然有彗星的出现，狂风乍起。\n梦想是美丽的，它是美的期望;梦想是阳光的，它使人由浮躁走向踏实;梦想是充满力量的，它可以激发人身体里无限的潜能。我们期盼的是国泰民安、经济发展、政治清明、文化繁荣、社会和谐、生态良\n好、公平正义。这才是中国人伟大的梦',6,6,6),(7,'2025-02-23 13:09:52','若月亮没来-王宇宙Leto，乔浚丞','《若月亮没来(若是月亮还没来)》是王宇宙Leto，乔浚丞演唱的歌曲，于2024年1月22日上线发行。','file/newsPicture7.jpg','风吹过山 船靠了岸  风光呀 一点点看  我走向北 你去往南\n故事呀 一篇篇翻  好烦 又加班到很晚  你搭上空荡的 地铁已是末班\n好烦 很爱却要分开  恋爱谈不明白  好烦 接近理想好难 却又还很不甘\n如何拥抱平淡  如果 最难得到圆满  不如选择坦然 若是月亮还没来\n路灯也可照窗台  照着白色的山茶花微微开  若是晨风还没来\n晚风也可吹入怀  吹着那一地树影温柔摇摆  你总以为你不够好\n不够苗条和美貌  可是完美谁能达到  做你自己就很好\n烦恼烦恼拥有太少  没有房车没有钞票  可爱你的人永远会 \n把你当做心尖珍宝  我也懂大多数的时候  你只想逃离这世界\n我也懂太多的情绪在  一个人失眠的深夜 你可以是悲伤或者埋怨\n但请不要放弃明天  这一直灰暗的世界  我想看见你的笑脸\n若是月亮还没来  路灯也可照窗台  照着白色的山茶花微微开\n若是晨风还没来  晚风也可吹入怀  吹着那一地树影温柔摇摆\n若是月亮还没来  路灯也可照窗台  照着白色的山茶花微微开\n若是晨风还没来  晚风也可吹入怀  吹着那一地树影温柔摇摆\n李哲今年1月份创作了《若月亮没来》这首歌。随着歌曲的热度不断上升，约他写歌的人越来越多，他的工作变得更加繁忙。\n近年来，李哲凭借对音乐的热爱和不懈的努力，创作了几百首音乐作品，其中不乏广为人知的佳作，例如王栎鑫的《一个人》、何赛飞与叶炫清在央视演唱的《木兰诗》等歌曲，都是由李哲作曲。\n如今，李哲的音乐才华和努力得到了业界的广泛认可，他的音乐作品多次登上各大音乐平台的热歌榜，成为备受瞩目的新生代音乐人。现在，走在街头、坐在车里，经常会听到自己的作品。看到自己的音乐作品受到如此广泛的喜爱和认可，我感到非常有成就感。李哲笑着说，他会努力创作更多优秀音乐作品，为家乡争光。',7,7,7),(8,'2025-02-23 13:09:52','-米阳光传递一份温暖','黑暗中跌倒，坚强里苦笑。只是因为青春还在，所以梦想一直在路上。当年华未曾老去，青春没有散场，留给幽暗的岁月，还有几分值得珍藏?孤独无助时、伤心难过时总爱恣意挥霍愤怒。伤了、痛了原本以为就都结束了;可是心里还不甘愿放弃，就只为一个理由，有梦想的人永不孤单。','file/newsPicture8.jpg','放进去了的青春，走不出来的悲伤却总是给年少时的我们留下太多无法割舍的情怀。记录岁月的足迹，聆听时光的故事。然轻吟浅唱出，青春嘉年华。\n故事里总会有，那些太多感慨于时间的无情，太多对往事的追忆，太多的太多，而今刻在了心里也停泻在了流年里。一段无法抹去的回忆，也只不过是一场烟火燃放的瞬间，却也一辈子在脑海里兜兜转转。很是怀念那些相遇的日子，邂逅了幸福也留在了时间轨迹里。时而想起，那些曾肩并肩奋斗的日子，那些写满感动与快乐的岁月。\n如今，我们似乎渐行渐远，彼此奋斗着青春的轮回。渐渐的，我们都长大了，时间改变了我们时刻分享彼此快乐与不快乐的习惯，青春的渐逝，岁月的更迭让我们变得都不再联系，身边的朋友也越来越少;那些欢笑，那些幸福的流年也随着我们的陌生而变得更加奢侈。也许多年以后，我们都有了自己的朋友，有了自己关心的人。那时，我们再细数那些癫狂在你生命里一辈子的记忆故事，蓦然发现一切都停留在了回忆里。',8,8,8),(9,'2025-02-23 13:09:52','自己的路自己走，自己的心自己懂','岛上书店》中有句话:每个人的生命里，都有最艰难的那一年，将人生变得美好而辽阔。\n你有没有经历过那么一年，在走过的岁月中跋涉得异常艰难困苦，却仍无法灭前行的勇气。哪怕所有人都极力劝阻，也要不甘心地试一试;哪怕前方已没有道路，却还在坚定前行;哪怕再看不到任何希望，也要竭尽全力地为自己争取一个机会。','file/newsPicture9.jpg','你开始不动声色的扮演着一个大人，经历着生活日复一日的打。\n每天醒在天还没亮的五六点，挤过早高峰的地下铁，面对成山的工作，加班到深夜倒下就睡更成为了日常，还要应付各色的人际关系，逐渐开始接受努力也不一定有回报的道理。\n这段日子也许很长，但你没有放弃对生活的信心，始终相信人生总会变得一帆风顺，更坚信每件事的最后都会有一个好的结局，如果不是，说明还没到最后。\n于是你树立新的目标，让这一年的奔波更有方向。\n虽然永远无法预料明天是晴是雨，也无法预知你在乎的人是否还在身边，你一直以来的坚持究竟能换来什么。\n但你能决定的是，今天有没有备好雨伞，有没有好好爱人以及是否为自己追求的理想拼尽全力。\n永远不要只看见前方路途遥远而忘了从前的自己坚持了多久才走到了这里，\n今天拼尽全力虽然艰辛万分，在未来都将成为落在你身上的礼物。',9,9,9),(10,'2025-02-23 13:09:52','愿你有明天可奔赴，有过往可回头','此去经年，用一张离家的车票，换一段有故事可说的人生。\n大概就是那时候怀揣着梦想，背上了行囊，一腔热血决定远行的你。独自拎着一只行李箱，背着双肩包在深夜抵达一个陌生的城市，站在人潮涌动的车站出口，望着宽敞公路上车水马龙的喧嚣，城市里亮如白昼的繁华。','file/newsPicture10.jpg','这世界上的人们都活的匆匆忙忙，每个人看起来都很焦虑，每个人都有自己的苦楚，却往往找不到人可以诉说。也许你的倾诉，对别人来说是一种打扰，而且别人没经历过你的经历，所以无法体会你的心情。我们总是在心情不好的时候拿着手机，翻翻通讯录，然后再默默放下手机，压住心中那股想要找人说说话的想法。\n等到低落的情绪慢慢有些好转，才开始明白，人这一生，无论是什么样的路，都是自己的选择，冲动也好，深思熟虑也罢，自己选的路，不管多难，都要自己走下去。心中的感受，不管是复杂还是简单，都只能自己来懂，别人帮不了你，也安慰不了你，顶多给你一些无济于事的劝告，最后还得靠自己想开。你选的幸福，你自己享受;你选的眼泪，你自己来流，生活就是如此，自己的路，自己走;自己的心，自己懂。',10,10,10),(11,'2025-02-23 13:23:00','这里发布健康资讯','这里发布健康资讯这里发布健康资讯这里发布健康资讯','file/1740316976666.jpg','<p>这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯这里发布健康资讯<img src=\"http://localhost:8080/cl12811977/file/1740316979781.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>',0,0,0);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeup`
--

DROP TABLE IF EXISTS `storeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint(20) DEFAULT NULL COMMENT 'refid',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `picture` longtext COLLATE utf8mb4_unicode_ci COMMENT '图片',
  `type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '类型(1:收藏,21:赞,22:踩,31:竞拍参与,41:关注)',
  `inteltype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推荐类型',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='我的收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeup`
--

LOCK TABLES `storeup` WRITE;
/*!40000 ALTER TABLE `storeup` DISABLE KEYS */;
INSERT INTO `storeup` VALUES (1,'2025-02-23 13:19:49',1,'news','余生，愿你活出自己的精彩','file/newsPicture1.jpg','1',NULL,NULL,1740316782534),(2,'2025-02-23 13:19:49',1,'news','余生，愿你活出自己的精彩','file/newsPicture1.jpg','21',NULL,NULL,1740316782534);
/*!40000 ALTER TABLE `storeup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `tablename` varchar(100) DEFAULT NULL COMMENT '表名',
  `role` varchar(100) DEFAULT NULL COMMENT '角色',
  `token` varchar(200) NOT NULL COMMENT '密码',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='token表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,111,'账号1','yonghu','用户','wxf1pqd7ahbox371a9uyk4vmcdfjhchy','2025-02-23 13:17:42','2025-02-23 14:17:42'),(2,1,'admin','users','管理员','smhv0gl7sju40nvq7pcwzkorw0r4ihn2','2025-02-23 13:18:17','2025-02-23 14:21:05'),(3,1740316782534,'111','yonghu','用户','jvwvniecyd8vghp9xliq0rwain6fdtbh','2025-02-23 13:19:46','2025-02-23 14:23:11');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '管理员' COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-02-23 13:09:52','admin','admin','管理员');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yonghu`
--

DROP TABLE IF EXISTS `yonghu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yonghu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `xingbie` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `nianling` int(11) DEFAULT NULL COMMENT '年龄',
  `shouji` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机',
  PRIMARY KEY (`id`),
  UNIQUE KEY `zhanghao` (`zhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=1740316782535 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yonghu`
--

LOCK TABLES `yonghu` WRITE;
/*!40000 ALTER TABLE `yonghu` DISABLE KEYS */;
INSERT INTO `yonghu` VALUES (111,'2025-02-23 13:09:52','账号1','123456','姓名1','file/yonghuTouxiang1.jpg','男',1,'19819881111'),(112,'2025-02-23 13:09:52','账号2','123456','姓名2','file/yonghuTouxiang2.jpg','男',2,'19819881112'),(113,'2025-02-23 13:09:52','账号3','123456','姓名3','file/yonghuTouxiang3.jpg','男',3,'19819881113'),(114,'2025-02-23 13:09:52','账号4','123456','姓名4','file/yonghuTouxiang4.jpg','男',4,'19819881114'),(115,'2025-02-23 13:09:52','账号5','123456','姓名5','file/yonghuTouxiang5.jpg','男',5,'19819881115'),(116,'2025-02-23 13:09:52','账号6','123456','姓名6','file/yonghuTouxiang6.jpg','男',6,'19819881116'),(117,'2025-02-23 13:09:52','账号7','123456','姓名7','file/yonghuTouxiang7.jpg','男',7,'19819881117'),(118,'2025-02-23 13:09:52','账号8','123456','姓名8','file/yonghuTouxiang8.jpg','男',8,'19819881118'),(119,'2025-02-23 13:09:52','账号9','123456','姓名9','file/yonghuTouxiang9.jpg','男',9,'19819881119'),(120,'2025-02-23 13:09:52','账号10','123456','姓名10','file/yonghuTouxiang10.jpg','男',10,'198198811110'),(1740316782534,'2025-02-23 13:19:42','111','111','小胡','file/1740316775350.jpg','女',28,'13953231232');
/*!40000 ALTER TABLE `yonghu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yundongjilu`
--

DROP TABLE IF EXISTS `yundongjilu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yundongjilu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mubiaomingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标名称',
  `yundongleixing` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运动类型',
  `kaishishijian` datetime DEFAULT NULL COMMENT '开始时间',
  `jieshushijian` datetime DEFAULT NULL COMMENT '结束时间',
  `yundongshizhang` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运动时长(m)',
  `xiaohaokaluli` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消耗卡路里',
  `jilushijian` datetime DEFAULT NULL COMMENT '记录时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运动记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yundongjilu`
--

LOCK TABLES `yundongjilu` WRITE;
/*!40000 ALTER TABLE `yundongjilu` DISABLE KEYS */;
INSERT INTO `yundongjilu` VALUES (1,'2025-02-23 13:09:52','目标名称1','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)1','消耗卡路里1','2025-02-23 21:09:52','账号1','姓名1'),(2,'2025-02-23 13:09:52','目标名称2','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)2','消耗卡路里2','2025-02-23 21:09:52','账号2','姓名2'),(3,'2025-02-23 13:09:52','目标名称3','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)3','消耗卡路里3','2025-02-23 21:09:52','账号3','姓名3'),(4,'2025-02-23 13:09:52','目标名称4','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)4','消耗卡路里4','2025-02-23 21:09:52','账号4','姓名4'),(5,'2025-02-23 13:09:52','目标名称5','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)5','消耗卡路里5','2025-02-23 21:09:52','账号5','姓名5'),(6,'2025-02-23 13:09:52','目标名称6','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)6','消耗卡路里6','2025-02-23 21:09:52','账号6','姓名6'),(7,'2025-02-23 13:09:52','目标名称7','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)7','消耗卡路里7','2025-02-23 21:09:52','账号7','姓名7'),(8,'2025-02-23 13:09:52','目标名称8','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)8','消耗卡路里8','2025-02-23 21:09:52','账号8','姓名8'),(9,'2025-02-23 13:09:52','目标名称9','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)9','消耗卡路里9','2025-02-23 21:09:52','账号9','姓名9'),(10,'2025-02-23 13:09:52','目标名称10','有氧','2025-02-23 21:09:52','2025-02-23 21:09:52','运动时长(m)10','消耗卡路里10','2025-02-23 21:09:52','账号10','姓名10'),(11,'2025-02-23 13:18:10','目标名称1','无氧','2025-02-23 15:17:51','2025-02-23 16:17:51','60','80','2025-02-23 21:17:51','账号1','姓名1'),(12,'2025-02-23 13:20:34','CC','无氧','2025-02-23 21:20:17','2025-02-23 22:20:17','60','852','2025-02-23 21:20:17','111','小胡');
/*!40000 ALTER TABLE `yundongjilu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-21  9:31:02

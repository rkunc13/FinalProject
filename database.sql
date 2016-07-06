-- phpMyAdmin SQL Dump
-- version 4.4.0
-- http://www.phpmyadmin.net
--
-- Host: MYSQL5005
-- Generation Time: Jun 27, 2015 at 01:48 AM
-- Server version: 5.6.21-log
-- PHP Version: 5.5.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_9b9328_sandro`
--

-- --------------------------------------------------------

--
-- Table structure for table `achievements`
--

CREATE TABLE IF NOT EXISTS `achievements` (
  `achievement_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `achievements`
--

INSERT INTO `achievements` (`achievement_id`, `user_id`, `description`) VALUES
(1, 2, 'Amateur Author'),
(2, 2, 'I am the Greatest1'),
(3, 4, 'Amateur Author'),
(4, 4, 'I am the Greatest2');

-- --------------------------------------------------------

--
-- Table structure for table `announcements`
--

CREATE TABLE IF NOT EXISTS `announcements` (
  `announcement_id` int(10) unsigned NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `announcements`
--

INSERT INTO `announcements` (`announcement_id`, `description`) VALUES
(1, 'dges visvenebt');

-- --------------------------------------------------------

--
-- Table structure for table `challenges`
--

CREATE TABLE IF NOT EXISTS `challenges` (
  `challenge_id` int(10) unsigned NOT NULL,
  `sender_id` int(10) unsigned DEFAULT NULL,
  `receiver_id` int(10) unsigned DEFAULT NULL,
  `quiz_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `friend_requests`
--

CREATE TABLE IF NOT EXISTS `friend_requests` (
  `friend_request_id` int(10) unsigned NOT NULL,
  `from_user_id` int(10) unsigned DEFAULT NULL,
  `to_user_id` int(10) unsigned DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `friend_requests`
--

INSERT INTO `friend_requests` (`friend_request_id`, `from_user_id`, `to_user_id`) VALUES
(2, 4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
  `friends_id` int(11) NOT NULL,
  `user_id1` int(10) unsigned DEFAULT NULL,
  `user_id2` int(10) unsigned DEFAULT NULL,
  `date_time` datetime DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`friends_id`, `user_id1`, `user_id2`, `date_time`) VALUES
(1, 2, 3, '2015-06-21 17:56:04');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `message_id` int(10) unsigned NOT NULL,
  `sender_id` int(10) unsigned DEFAULT NULL,
  `receiver_id` int(10) unsigned DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `question_id` int(10) unsigned NOT NULL,
  `quiz_id` int(11) DEFAULT NULL,
  `question_type` enum('QuestionResponse','FillInTheBlank','MultipleChoice','PictureResponse','MultiAnswer','MultipleChoiceMultipleAnswers') DEFAULT NULL,
  `metadata` longblob
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`question_id`, `quiz_id`, `question_type`, `metadata`) VALUES
(1, 1, 'QuestionResponse', 0xaced0005737200164d6f64656c2e5175657374696f6e526573706f6e736500000000000000010200007872000e4d6f64656c2e5175657374696f6e0000000000000001020004490008617574686f7249444900067175697a49444c0006616e737765727400124c6a6176612f6c616e672f537472696e673b4c00087175657374696f6e71007e00027870000000020000000174000134740003322b32);

-- --------------------------------------------------------

--
-- Table structure for table `quiz_history`
--

CREATE TABLE IF NOT EXISTS `quiz_history` (
  `quiz_history_id` int(10) unsigned NOT NULL,
  `quiz_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `rating` int(10) unsigned DEFAULT NULL,
  `review` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `time_taken` datetime DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `quiz_history`
--

INSERT INTO `quiz_history` (`quiz_history_id`, `quiz_id`, `user_id`, `score`, `total`, `rating`, `review`, `name`, `date_time`, `time_taken`) VALUES
(1, 1, 2, 1, 1, 5, 'so easy quiz', 'Test Quiz', '2015-06-21 17:53:58', '1899-12-31 00:00:05'),
(2, 1, 3, 0, 1, 5, '2 +2 must equals 5 wtf ? ', 'Test Quiz', '2015-06-21 18:21:14', '1899-12-31 00:00:04'),
(3, 2, 4, 0, 0, 5, 'dzalian advili iyo', 'test2 ', '2015-06-27 12:30:03', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `quizzes`
--

CREATE TABLE IF NOT EXISTS `quizzes` (
  `quiz_id` int(10) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `random_order` tinyint(1) DEFAULT NULL,
  `multiple_pages` tinyint(1) DEFAULT NULL,
  `immediate_correction` tinyint(1) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `reported` tinyint(1) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `quizzes`
--

INSERT INTO `quizzes` (`quiz_id`, `name`, `description`, `author_id`, `category`, `tags`, `random_order`, `multiple_pages`, `immediate_correction`, `date_time`, `points`, `reported`) VALUES
(1, 'Test Quiz', 'simple test quiz', 2, 'animals', 'mawoni', 1, 0, 1, '2015-06-21 17:52:58', 1, 1),
(2, 'test2 ', 'test2', 4, 'animals', 'magida,skami,proeqtori', 1, 0, 0, '2015-06-27 12:26:11', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) unsigned NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `admin_privilege` tinyint(1) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `password`, `salt`, `name`, `admin_privilege`) VALUES
(1, 'admin', '9d5c98f621aa48d7560eed9f38e8da2662a5ebe5', 'f688cccc6934407c432d4bd15cbb210133d398168486be3dacc76b59a4d5ad2f', 'admin adminishvili', 1),
(2, 'user', '0da4635872289d2e53276d94fe4d2d20732d2d02', 'ffb98f886873c437c105a70924a2308db14cbd7977ac385421a87208238ea724', 'user userisdze', 1),
(3, 'user1', '5b9ffbbbaea13dbf43f629b506887442d7e227e1', '2edd9fa3678be0b28efd82975db9acb6e2d5d4f8694e55b0890a1ded3f593398', 'user1 user1iani', 0),
(4, 'user2', '2657cb28a93fb1533c58d8160fcb4eacbfd352ea', '73745ed59d83871bc364ce7ae41671ea953ca91b69989be738c2556225302c92', 'user2 user2dze', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `achievements`
--
ALTER TABLE `achievements`
  ADD PRIMARY KEY (`achievement_id`);

--
-- Indexes for table `announcements`
--
ALTER TABLE `announcements`
  ADD PRIMARY KEY (`announcement_id`);

--
-- Indexes for table `challenges`
--
ALTER TABLE `challenges`
  ADD PRIMARY KEY (`challenge_id`);

--
-- Indexes for table `friend_requests`
--
ALTER TABLE `friend_requests`
  ADD PRIMARY KEY (`friend_request_id`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`friends_id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `quiz_history`
--
ALTER TABLE `quiz_history`
  ADD PRIMARY KEY (`quiz_history_id`);

--
-- Indexes for table `quizzes`
--
ALTER TABLE `quizzes`
  ADD PRIMARY KEY (`quiz_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `achievements`
--
ALTER TABLE `achievements`
  MODIFY `achievement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `announcements`
--
ALTER TABLE `announcements`
  MODIFY `announcement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `challenges`
--
ALTER TABLE `challenges`
  MODIFY `challenge_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `friend_requests`
--
ALTER TABLE `friend_requests`
  MODIFY `friend_request_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
  MODIFY `friends_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `message_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `question_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `quiz_history`
--
ALTER TABLE `quiz_history`
  MODIFY `quiz_history_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `quizzes`
--
ALTER TABLE `quizzes`
  MODIFY `quiz_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

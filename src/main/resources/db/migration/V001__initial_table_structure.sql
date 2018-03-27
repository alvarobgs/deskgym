--
-- Estrutura da tabela `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `complement` varchar(100) DEFAULT NULL,
  `neighborhood` varchar(100) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `zip_code` varchar(9) DEFAULT NULL,
  `city_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_city` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `city`
--

DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `state_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_city` (`name`,`state_id`),
  KEY `fk_city_state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `class_payment`
--

DROP TABLE IF EXISTS `class_payment`;
CREATE TABLE IF NOT EXISTS `class_payment` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `discount` decimal(10,2) NOT NULL,
  `experimental_class` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  `outDated` bit(1) NOT NULL,
  `payment_date` datetime NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `plots` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `start_date` date NOT NULL,
  `validity_date` date NOT NULL,
  `plan_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_class_payment_plan` (`plan_id`),
  KEY `fk_class_payment_student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `contact`
--

DROP TABLE IF EXISTS `contact`;
CREATE TABLE IF NOT EXISTS `contact` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `cellphone` varchar(12) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `telephone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `country`
--

DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `abbreviation` varchar(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_country` (`name`,`abbreviation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `birth_day` date NOT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `gender` varchar(25) NOT NULL,
  `name` varchar(100) NOT NULL,
  `observation` varchar(500) DEFAULT NULL,
  `registration_date` datetime NOT NULL,
  `rg` varchar(20) DEFAULT NULL,
  `admission_date` datetime DEFAULT NULL,
  `cref` varchar(100) DEFAULT NULL,
  `employee_role` varchar(100) DEFAULT NULL,
  `payment_day` int(11) DEFAULT NULL,
  `salary` decimal(10,2) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `contact_id` bigint(20) DEFAULT NULL,
  `picture_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_employee` (`name`,`birth_day`),
  KEY `fk_person_address` (`address_id`),
  KEY `fk_person_contact` (`contact_id`),
  KEY `fk_person_picture` (`picture_id`),
  KEY `fk_employee_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `employee_log`
--

DROP TABLE IF EXISTS `employee_log`;
CREATE TABLE IF NOT EXISTS `employee_log` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `log_type` varchar(255) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_log_employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `employee_panel_text`
--

DROP TABLE IF EXISTS `employee_panel_text`;
CREATE TABLE IF NOT EXISTS `employee_panel_text` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `text` varchar(5000) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l6bly72pdiov72xqq65tv4njg` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `employee_payment`
--

DROP TABLE IF EXISTS `employee_payment`;
CREATE TABLE IF NOT EXISTS `employee_payment` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `outDated` bit(1) NOT NULL,
  `payment_date` datetime NOT NULL,
  `value` decimal(10,2) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_payment_employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `experimental_class_schedule`
--

DROP TABLE IF EXISTS `experimental_class_schedule`;
CREATE TABLE IF NOT EXISTS `experimental_class_schedule` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `cellphone` varchar(12) DEFAULT NULL,
  `date` datetime NOT NULL,
  `student_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `extra_tax_payment`
--

DROP TABLE IF EXISTS `extra_tax_payment`;
CREATE TABLE IF NOT EXISTS `extra_tax_payment` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `discount` decimal(10,2) NOT NULL,
  `payment_date` datetime NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_registration_tax_student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `medical_certificate`
--

DROP TABLE IF EXISTS `medical_certificate`;
CREATE TABLE IF NOT EXISTS `medical_certificate` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `medical_certificate_stream` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `monetary_movement`
--

DROP TABLE IF EXISTS `monetary_movement`;
CREATE TABLE IF NOT EXISTS `monetary_movement` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(255) NOT NULL,
  `value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `permission`
--

DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `entity` varchar(100) DEFAULT NULL,
  `operation` varchar(255) NOT NULL,
  `path` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `picture`
--

DROP TABLE IF EXISTS `picture`;
CREATE TABLE IF NOT EXISTS `picture` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `picture_stream` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `plan`
--

DROP TABLE IF EXISTS `plan`;
CREATE TABLE IF NOT EXISTS `plan` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `duration` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_plan` (`name`,`duration`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `plot`
--

DROP TABLE IF EXISTS `plot`;
CREATE TABLE IF NOT EXISTS `plot` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `payment_date` datetime NOT NULL,
  `value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `state`
--

DROP TABLE IF EXISTS `state`;
CREATE TABLE IF NOT EXISTS `state` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `uf` varchar(10) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_state` (`name`,`country_id`),
  KEY `fk_state_country` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `birth_day` date NOT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `gender` varchar(25) NOT NULL,
  `name` varchar(100) NOT NULL,
  `observation` varchar(500) DEFAULT NULL,
  `registration_date` datetime NOT NULL,
  `rg` varchar(20) DEFAULT NULL,
  `medic_observation` varchar(500) DEFAULT NULL,
  `payment_day` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `contact_id` bigint(20) DEFAULT NULL,
  `picture_id` bigint(20) DEFAULT NULL,
  `medical_certificate_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_student` (`name`,`birth_day`),
  KEY `fk_student_medical_certificate` (`medical_certificate_id`),
  KEY `fk_student_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `student_status_change`
--

DROP TABLE IF EXISTS `student_status_change`;
CREATE TABLE IF NOT EXISTS `student_status_change` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `new_status` varchar(50) DEFAULT NULL,
  `old_status` varchar(50) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_status_student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `system_configuration`
--

DROP TABLE IF EXISTS `system_configuration`;
CREATE TABLE IF NOT EXISTS `system_configuration` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `backup_path` varchar(255) NOT NULL,
  `credit_card_tax` decimal(10,4) NOT NULL,
  `debit_card_tax` decimal(10,4) NOT NULL,
  `enable_experimental_class` bit(1) NOT NULL,
  `enable_registration_tax` bit(1) NOT NULL,
  `experimental_class_price` decimal(10,2) NOT NULL,
  `free_panel_text` varchar(10000) DEFAULT NULL,
  `inactivity_minutes_to_loggout` int(11) NOT NULL,
  `last_login` date DEFAULT NULL,
  `mysql_path` varchar(255) NOT NULL,
  `pending_days` int(11) NOT NULL,
  `pending_days_to_inactive` int(11) NOT NULL,
  `registration_tax` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`user_name`),
  KEY `fk_user_employee` (`employee_id`),
  KEY `fk_user_student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `user_has_permission`
--

DROP TABLE IF EXISTS `user_has_permission`;
CREATE TABLE IF NOT EXISTS `user_has_permission` (
  `user_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  KEY `user_has_permission_permission` (`permission_id`),
  KEY `user_has_permission_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `fk_address_city` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);

--
-- Limitadores para a tabela `city`
--
ALTER TABLE `city`
  ADD CONSTRAINT `fk_city_state` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`);

--
-- Limitadores para a tabela `class_payment`
--
ALTER TABLE `class_payment`
  ADD CONSTRAINT `fk_class_payment_plan` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`),
  ADD CONSTRAINT `fk_class_payment_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Limitadores para a tabela `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `fk_employee_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_person_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  ADD CONSTRAINT `fk_person_contact` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`),
  ADD CONSTRAINT `fk_person_picture` FOREIGN KEY (`picture_id`) REFERENCES `picture` (`id`);

--
-- Limitadores para a tabela `employee_log`
--
ALTER TABLE `employee_log`
  ADD CONSTRAINT `fk_employee_log_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Limitadores para a tabela `employee_panel_text`
--
ALTER TABLE `employee_panel_text`
  ADD CONSTRAINT `fk_employee_panel_text_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Limitadores para a tabela `employee_payment`
--
ALTER TABLE `employee_payment`
  ADD CONSTRAINT `fk_employee_payment_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Limitadores para a tabela `extra_tax_payment`
--
ALTER TABLE `extra_tax_payment`
  ADD CONSTRAINT `fk_registration_tax_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Limitadores para a tabela `state`
--
ALTER TABLE `state`
  ADD CONSTRAINT `fk_state_country` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);

--
-- Limitadores para a tabela `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_medical_certificate` FOREIGN KEY (`medical_certificate_id`) REFERENCES `medical_certificate` (`id`),
  ADD CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Limitadores para a tabela `student_status_change`
--
ALTER TABLE `student_status_change`
  ADD CONSTRAINT `fk_student_status_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Limitadores para a tabela `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `fk_user_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Limitadores para a tabela `user_has_permission`
--
ALTER TABLE `user_has_permission`
  ADD CONSTRAINT `user_has_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  ADD CONSTRAINT `user_has_permission_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

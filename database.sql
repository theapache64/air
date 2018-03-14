DROP DATABASE IF EXISTS `air`;
CREATE DATABASE `air`;
USE `air`;

CREATE TABLE `channels` (
  `id`         INT       NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `songs` (
  `id`         INT       NOT NULL AUTO_INCREMENT,
  `path`       TEXT      NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `channel_songs` (
  `id`         INT       NOT NULL AUTO_INCREMENT,
  `channel_id` INT       NOT NULL,
  `song_id`    INT       NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (channel_id) REFERENCES channels (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (song_id) REFERENCES songs (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
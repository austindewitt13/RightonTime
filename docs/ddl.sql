CREATE TABLE IF NOT EXISTS `Alarm`
(
    `alarm_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `hour`     INTEGER                           NOT NULL,
    `minute`   INTEGER                           NOT NULL
);

CREATE TABLE IF NOT EXISTS `Event`
(
    `event_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `date`     INTEGER,
    `event`    TEXT
);
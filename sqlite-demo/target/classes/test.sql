-- 创建user表
CREATE TABLE user
(
    id            INTEGER PRIMARY KEY NOT NULL,
    username      TEXT                NOT NULL,
    password      TEXT                NOT NULL,
    register_time TEXT                NOT NULL,
    create_time   TEXT                NOT NULL
);

INSERT INTO user (username, password, register_time, create_time)
VALUES ('admin', '123456', '2020-10-10 11:39:44', '2020-10-10 11:39:44');

INSERT INTO user (username, password, register_time, create_time)
VALUES ('sqlite', '123456', '2020-10-10 11:39:44', '2020-10-10 11:39:44');
SET GLOBAL log_bin_trust_function_creators = 1;
CREATE DATABASE stevedb CHARACTER SET utf8 COLLATE utf8_unicode_ci;
GRANT ALL PRIVILEGES ON stevedb.* TO 'steve'@'localhost' IDENTIFIED BY 'neuss';
GRANT SELECT ON mysql.proc TO 'steve'@'localhost' IDENTIFIED BY 'neuss';

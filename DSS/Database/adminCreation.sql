CREATE USER 'admin'@'localhost';
	SET PASSWORD FOR 'admin'@'localhost' = 'admin';

GRANT SELECT, INSERT, UPDATE, DELETE ON mydb.* TO 'admin'@'localhost';
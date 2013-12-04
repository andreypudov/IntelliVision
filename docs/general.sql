# restrict MySQL from opening a network socket
# [mysqld] section of my.cnf/my.ini file
skip-networking

# force MySQL to listen only the localhost
# [mysqld] section of my.cnf/my.ini file
bind-address=127.0.0.1

# disable of the "Load Data Local Infile" command
# [mysqld] section of my.cnf/my.ini file
set-variable=local-infile=0

# rename default administrator username
RENAME USER root TO andrey;
SET PASSWORD FOR 'andrey'@'%localhost' = PASSWORD('sdfgsdsfg');

# for the first time
mysqladmin -u root password NEWPASSWORD
# next time
mysqladmin -u root -p'OLDPASSWORD' password NEWPASSWORD

# remove the test database
DROP DATABASE test;

# remove anonymous and obsolete accounts
SELECT * FROM mysql.user WHERE user="";

SHOW GRANTS FOR ''@'localhost';
SHOW GRANTS FOR ''@'syscreat.local';

DROP USER '';

# remove MySQL history
cat /dev/null > ~/.mysql_history
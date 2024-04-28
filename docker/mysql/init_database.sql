CREATE DATABASE IF NOT EXISTS iot_project;
CREATE USER 'springuser'@'%' identified by 'ThePassword';
CREATE USER 'grafanaReader'@'%' identified by 'grafana';
GRANT ALL ON iot_project.* to 'springuser'@'%';
GRANT SELECT ON iot_project.* to 'grafanaReader'@'%';
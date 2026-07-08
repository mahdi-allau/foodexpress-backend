@echo off
set MYSQL=C:\Users\mahdi\Desktop\mysql-9.7.1-winx64
"%MYSQL%\bin\mysqld.exe" --console --basedir="%MYSQL%" --datadir="%MYSQL%\data" --port=3306

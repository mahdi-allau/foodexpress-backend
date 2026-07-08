@echo off
REM ============================================================
REM  FoodExpress - avvia MySQL + Backend + Frontend
REM  Apre 3 finestre separate. Chiudile per fermare i server.
REM ============================================================
set MYSQL=C:\Users\mahdi\Desktop\mysql-9.7.1-winx64

echo Avvio MySQL...
start "FoodExpress - MySQL" cmd /k ""%MYSQL%\bin\mysqld.exe" --console --basedir="%MYSQL%" --datadir="%MYSQL%\data" --port=3306"

echo Attendo che MySQL sia pronto (10s)...
timeout /t 10 /nobreak >nul

echo Avvio Backend (Spring Boot, porta 8081)...
start "FoodExpress - Backend" cmd /k "cd /d "%~dp0Backend-Spring" && gradlew.bat bootRun"

echo Avvio Frontend (Angular, porta 4300)...
start "FoodExpress - Frontend" cmd /k "cd /d "%~dp0Frontend" && npm start"

echo.
echo ============================================================
echo  Apri il browser su:  http://localhost:4300
echo  (il backend impiega ~15-20s al primo avvio)
echo ============================================================
echo Questa finestra si puo' chiudere. Le altre 3 tengono vivi i server.
pause

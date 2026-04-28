@echo off
cd /d %~dp0
echo Starting Agriculture Rental project on http://localhost:8082
echo.
call mvnw.cmd spring-boot:run
pause

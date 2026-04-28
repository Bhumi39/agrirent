AGRICULTURE RENTAL - VS CODE DIRECT RUN PROJECT

1) Extract ZIP.
2) Open VS Code.
3) File > Open Folder > select Agriculture_VSCode folder.
4) Install Java Extension Pack if VS Code asks.
5) Make sure JDK 17 or newer is installed.
6) Run directly:
   - Double click run.bat
   OR
   - In VS Code terminal: .\mvnw.cmd spring-boot:run
7) Open browser:
   http://localhost:8082

Demo login:
Email: bhumikashyap1505@gmail.com
Password: 123456

H2 Database console:
http://localhost:8082/h2-console
JDBC URL: jdbc:h2:mem:agridb
User: sa
Password: blank

No external MySQL setup required. Database is H2 in-memory and demo data loads automatically.

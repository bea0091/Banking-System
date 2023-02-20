## To Run the Banking System

```bash
db2 -tvf p1_create.sql 
```
Execute p1_create.sql inside the Db2 container.

```bash
javac *.java
java -cp ":./db2jcc4.jar" ProgramLauncher ./db.properties
```
Compile all the java files and execute ProgramLauncher to start the welcome page of the system.
- GUI window will pop up to start with.
- Given data will be handled and show up in the terminal.


## Extra files for GUI

- p1_welcomePage
- p1_customerPage
- p1_administratorPage

These files replace p1.java.\
ProgramLauncher will navigate to p1_welcomePage for GUI window.



# Self Service Banking System
This is a database application which acts as a simple banking system, implemented using DB2, Java and JDBC.\
This application is able to do basic banking functions according to the specifications.


## Installation

__1. Execute p1_create.sql inside the Db2 container.__

```bash
db2 -tvf p1_create.sql 
```
---------------

__2. Compile all the java (JDBC) program files.__

```javascript
javac *.java
```
---------------

__3. Replace the parameters in the db2.properties file.__

_\<url\>_ = Replace you connection IP address and port # followed by your DB_NAME (e.g. SAMPLE or CS157A)

_\<username\>_ = db username (should be your windows login id that was used during the DB2 Setup)

_\<password\>_ = password for the above db username

---------------

__4. Call on the compiled java program with the properties file as a parameter.__\
First download the _db2jcc4.jar_ to your current directory, then run the command below.

For Mac: 
```javascript
java -cp ":./db2jcc4.jar" ProgramLauncher ./db.properties
```

For Windows: 
```javascript
java -cp ";.\db2jcc4.jar" ProgramLauncher ./db.properties
```

---------------

__5. You should get a comma separated output from the Employee table of this query with a few tabs in between:__

```javascript
"SELECT FIRSTNME, LASTNAME, EDLEVEL, SALARY FROM EMPLOYEE"
```
---------------

__6. The program will be started by launching GUI window for the input.__\
Given data will be handled and show up in the terminal.



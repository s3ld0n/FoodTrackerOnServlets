### Food Tracker

Requirements:

- Java 8+
- PostgreSQL 9.6
- Maven

#### **How to install**
- clone project
- run sequentially: 
    + sql/create_food_tracker_DB_and_user.sql
    + sql/create_tables.sql
    + sql/insert_data_into_tables.sql
- run `mvn clean tomcat7:run` in command line tool
- open `localhost:8080` in web browser
# mongotest

This is a simple project that plays around with mongoDB a little bit.
It creates some test data, runs some simple calculations, and updates
a record.

To run it, you will need to have MongoDB installed and running 
https://www.mongodb.org/ Then follow these steps.

first clone the repo:
- git clone https://github.com/erprice/mongotest

build the jar:
- cd mongotest
- mvn clean install

and run it:
- java -jar target/mongodb_example-1.0.0-jar-with-dependencies.jar mongotest.MainClass

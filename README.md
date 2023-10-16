# arithmetic-calculator-backend

This project is an arithmetic calculator built with Scala, Play Framework and Slick library. The calculator includes the following operations:

- Addition
- Subtraction
- Multiplication
- Division
- Square Root
- Random String

## How to run it locally

- Clone the repository
- From the root directory, run: `docker-compose build` and then `docker-compose up`
- Access the following address through your browser http://localhost:9000
- You should see a red screen with the title "Database 'default' needs evolution!". Click on the button "Apply this script now" to create the tables and records needed for the application to function properly.

## How to run the tests 

- In order to run the tests, you should have sbt installed.
- From the root directory, run: `sbt test` to run the unit tests.

## Live version
The live version of this project is on the following link: https://5e33-2804-29b8-509a-1734-1deb-e208-3c23-2ec6.ngrok-free.app

# Tom-Aquarium

## Overview
Tom-Aquarium is a Java-based simulation of an aquarium where fish live, grow, reproduce, and eventually die. The program runs each fish in a separate thread and uses collections to manage them. It provides real-time updates in the console about various events in the aquarium.

## Features
- Randomly generates the number of male and female fish at the start.
- Each fish has a randomly assigned lifespan.
- Fish transition through different life stages (Born, Child, Mature, Married, Adult, Dead).
- If a male and female fish meet at the right stage, they reproduce.
- Newly born fish are added with randomly assigned genders.
- Console outputs show real-time events such as fish birth, marriage, and death.
- Ensures thread safety using `ReentrantLock`.

## Technologies Used
- Java 21
- Multi-threading (Each fish runs in its own thread)
- Collections (`ConcurrentHashMap` to manage fish list)
- Lombok (for reducing boilerplate code)
- Locking mechanisms (`ReentrantLock` for thread safety)
- Maven (for build automation)

## How to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/alizoog/Tom-aquarium.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Tom-Aquarium
   ```
3. Compile and run the program:
   ```sh
   mvn clean install
   java -jar target/tom-aquarium.jar
   ```

## Project Structure
```
Tom-Aquarium
│── src
│   ├── main
│   │   ├── java
│   │   │   ├── org.aquarium
│   │   │   │   ├── enums
│   │   │   │   │   ├── FishState.java
│   │   │   │   │   ├── GenderEnum.java
│   │   │   │   ├── model
│   │   │   │   │   ├── Aquarium.java
│   │   │   │   │   ├── Fish.java
│   │   │   │   │   ├── Wedding.java
│   │   │   │   ├── util
│   │   │   │   │   ├── FishUtils.java
│   │   │   │   │   ├── PrintUtils.java
│   │   │   ├── Main.java
│── pom.xml
│── README.md
```

## Author
Created by Ibroxim Xamidov.

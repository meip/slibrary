# slibrary - A HSR Uint1 Miniproject
==================
Miniprojekt für das Modul User Interfaces 1 an der Hochschule für Technik Rapperswil (HSR) im Herbstsemester 2013 (HS13).

## Getting Started
* Checkout Project
* `$ ./sbt`
$ `$ update`
* `$ gen-idea`

You can open the project in IntelliJ IDEA afer SBT-Task `gen-idea`.

## Build JAR
* `$ ./sbt clean`
* In IntelliJ IDEA use `Build -> Rebuild Project`
* `$ ./sbt assembly`
$ `java -jar target/scala-2.9.2/slibrary.jar`

The /data folder must be present when you start the project from jar
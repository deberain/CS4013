How to compile and run the Command Line Interface:
	1. Open command prompt and navigate to the source directory using the cd command (example: cd C:\Users\yourName\Downloads\PCMSystem\source)
	2. To compile the program, do: javac -d ../bin *.java
	3. Now navigate to the bin directpry using: cd ../bin
	3. Finally, to run the program, do: java PCMSystem

Alternatively, I have left a batch file in this directory that will execute the above commands for you and run the program. The file is called "RunPCMSystem.bat"

How to compile and run the GUI:
	1. Open command prompt and navigate to the source directory using the cd command (example: cd C:\Users\yourName\Downloads\PCMSystem\source)
	2. To compile the program, do: javac -d ../bin *.java
	3. Now navigate to the bin directpry using: cd ../bin
	3. Finally, to run the program, do: java PCMMenu

Again, a batch file named "RunPCMMenu.bat" wil do this for you

Please ensure you are using JDK 8 before running this program! 
JavaFX is not natively supported in JDK versions 11 and above so our program will not work with those versions.
We currently do not have the know how to add JavaFX as an external SDK to our program.

The link to our team's Github page for this project is: https://github.com/JDirac/CS4013

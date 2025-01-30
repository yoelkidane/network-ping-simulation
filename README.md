# Network Ping Simulation
A network traffic/ host pinging simulation tool that implements ping requests, RTT calculation, and event scheduling in a star topology network.



## Overview

This project simulates a **star topology network** where a central host communicates with a variable amount of neighboring hosts. It sends **ping requests** and calculates the **Round-Trip Time (RTT)** 
based on the time it takes for a ping to travel from one host to another and back. This continues until the timer's duration has fully elapsed, where in-progress messages will be canceled.
The output regarding the simulation results is printed to the console.

This program takes an input file containing the host address, neighbor address, and desired simulation scenarios. This can be customized by the user
and is fully explained in the **Custom Simulation Guide** section.


## Installation
### Prerequisites
- Git
- Java 8 or higher
- Basic text editor and/or IDE to change simulation files.

### Steps

1. **Clone the repository**
```
git clone https://github.com/yoelkidane/network-ping-simulation.git
cd network-ping-simulation
```

2. **Build the project**:
Compile the project using `javac`:
```bash
javac Main.java
```

  
3. **Run the Simulation**:
Run the simulation using:
```bash
java Main
 ```


## Custom Simulation Guide
You can create your network simulation by defining custom host and neighbor configurations, along with ping behaviors in a text file. 
The following is an explanation following **simulation1.txt**.
```
5          # Total number of hosts in the simulation
4 8        # Host 4 is connected to Host 8
1 12       # Host 1 is connected to Host 12
19 2       # Host 19 is connected to Host 2
3 7        # Host 3 is connected to Host 7
-1         # End of the neighbor list
19 5 4 43  # Connect Host 19 to 5, and send pings every 4 ticks until 43 elapsed
5 3 6 39   # Connect Host 5 to 3, and send pings every 6 ticks until 39 elapsed
	   # Each connection should include the original (center) host
```
_**Note**: Only digits in the shown format should be present in the simulation file, the text is there for explanation._

To create your simulation file, follow the above format and place it into the /Simulation Files directory. Then, in a text editor or IDE, change the read file to match the desired name.


## Sample Output
Test that your program is compiling properly by comparing output for **simulation1.txt**.
```
[15ts] Host 6: Sent ping to host 4
[17ts] Host 4: Ping request from host 6
[19ts] Host 6: Ping response from host 4 (RTT = 4ts)
[30ts] Host 6: Sent ping to host 4
[32ts] Host 4: Ping request from host 6
[34ts] Host 6: Ping response from host 4 (RTT = 4ts)
[45ts] Host 6: Sent ping to host 4
[47ts] Host 4: Ping request from host 6
[49ts] Host 6: Ping response from host 4 (RTT = 4ts)
[50ts] Host 6: Stopped sending pings
```

# ukuFlow
_The ukuFlow Macroprogramming System_


ukuFlow is a workflow engine for wireless sensor networks and energy-efficient networked embedded devices.
With ukuFlow, developers model the application logic using processes, or workflows, as programming abstraction.
This macroprogramming approach enables domain experts to focus on the application logic, hiding away low-level system issues.
The main goal of ukuFlow is to provide a system that runs entirely in-network.
Users first define their process logic using a process editor in an IDE.
An off-network converter maps the logic to a compact format, which is uploaded to the network via a serial connection to a sensor node.
From there on, the ukuFlow engine takes over by instantiating and running the workflow as specified, with no need for a back-end controlling the execution.

ukuFlow ukuFlow adopts a standard notation called Business Process Model and Notation (BPMN) v2.0, which we extend with a set of abstractions for its suitability to sensor networks.
Processes are modeled with the help of an IDE, particularly an open-source Eclipse plug-in enhanced with our toolchain to convert and deploy process into a running sensor network.
Want to find out more? Take a look at our current [poster].


ukuFlow is written in the C programming language.
It is implemented using Contiki, therefore it runs on a variety of platforms.
We do focus, however, on the MSP430-line of sensor nodes like the TelosB and the Z1.

### Build

You need [Contiki] installed in your system:

```sh
$ make ukuflow-serial TARGET=tmote
```



[poster]:https://www.dvs.tu-darmstadt.de/publications/posters/pdf/guerrero2012-ukuFlow.pdf
[Contiki]:https://github.com/contiki-os/contiki

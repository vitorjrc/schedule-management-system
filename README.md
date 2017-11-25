# Schedule Manager

Java application that manages the schedule of practical classes in a given major.

## Getting Started

Simply open the project located in the `netbeans-project` folder in netbeans (or eclipse).

## How it's built

The model package is the application itself.
The view package is the GUI.
The controller is a class that makes the connection between these two modules, and isolates them at the same time.
Changes to the view should never affect the code in the model, and vice versa. The controller is the one who has to conform to changes on both sides.
Only the controller has a reference to both the model and the view. Neither the view nor the model have any references to each other.

![Our flavor of MVC](https://i.imgur.com/zDl3HeP.png)

## Miscellaneous

* Information about class diagrams can be hard to parse when checking through various sources. IBM has created a nice summary on the topic [here](https://www.ibm.com/developerworks/rational/library/content/RationalEdge/sep04/bell/index.html).

## Authors

* @diisnc
* @sergiotj
* @vitorindeep
* @marcospgp

## License

This project is unlicensed.

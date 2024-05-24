## Description

This project implements a PID (Proportional-Integral-Derivative) controller within a 3D rocket simulation environment. Under specific restrictions and assumptions, the controller is designed to manage the rocket's stability, rotation and movement with respect to a target location. The simulation operates within the physics engine of the Unity game engine. A Unity project build is included.

## Preview

### Maneuverability
<img src="assets/preview-1.gif" alt="Alt Text" width="600" height="350" />

The rocket will make precise adjustments based on the target (represented by the red sphere) to smoothly move to the desired location, minimizing oscillations.

### Descent
<img src="assets/preview-2.gif" alt="Alt Text" width="600" height="350" />

Observe the rocket's transition from high altitude to ground level as it adjusts its trajectory to a new target by initiating a pre-burn for momentum gain, engine shutdown and finally a 'suicide burn' landing maneuver at the designated location.


## How to Use

To run the Unity build, simply download the entire build directory and execute the program found within it.

## Implementations

### PID Controllers

In total, there are three different PID controllers for the rocket: one for the altitude, one for the x rotation and one for the z rotation. Each of the controllers rely on the PID pseudocode given by:

```pseudo
// Calculate error
error = target - current

// Proportional term
proportional = kp * error

// Integral term
integral += error * deltaTime
integralTerm = ki * integral

// Derivative term
derivative = (error - previousError) / deltaTime
derivativeTerm = kd * derivative

// Update previous error
previousError = error

// Calculate PID output
output = proportional + integralTerm + derivativeTerm
```

Depending on the controller, the values of 'current' and 'target' are the altitudes of the rocket and target position respectively or the distance in x or z direction. The PID controllers will then give an output trying to reach the target as fast and efficient as possible. By efficient we mean without and extensive amount of osciliating.

## Dependencies

Uses the org.junit package in Java for testing purposes.

## Author
Marius H. Naasen, originally created in the spring of 2019 as part of the object-oriented programming course [INF101](https://www4.uib.no/en/courses/INF101) at UiB.

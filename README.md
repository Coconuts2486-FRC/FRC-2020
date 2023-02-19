# FRC 2022
FIRST FRC : Rapid React Robot Code

The CocoNut's 2022 season robot "Recusant" was designed to be quick, maneuverable, and accurate to compete in the Rapid React game challenge.

# Drivetrain
Recusant makes use of a coaxial swerve drive to meet our quick and maneuverable objectives.

The swerve drive uses a Pigeon IMU to identify a forward direction that is relative to the field rather than forward to the robot. This field-centric property allows the robot to be driven with extreme ease and accuracy.

The swerve drive also takes input from a LimeLight vision camera that allows the robot to switch from field-centric drive to goal-centric drive. This allows the robot to align to the center of the goal.

# Intake
Recusant is equipped with a pneumatically-actuated arm that was designed to pick up 9.5 inch diameter balls. This arm feeds the balls into the first stage of the intake that stores the balls using motors and a belt system. The second stage of the intake then brings the balls through the mortar.

# Shooting
Recusant uses a motorized coleson wheel to launch balls into the goal. The LimeLight used to help aim the drivetrain is also used to help determine the speed of the firing wheel. The LimeLight returns the distance away from the goal and through the combination of a regression algorithm and a PID controller, the wheel automatically adjusts its speed to make the shot into the goal.

# Climbing
Recusant uses a pneumatically actuated, vulcan spring driven elevator to traverse the slanted monkey bars. This climber is manually operated

# Autonomous
The autonomous routine for this robot was created by manually driving out a path and recording all the driver inputs that were sent to the robot while driving out this path. This data was then pushed through a for loop to simulate the driver inputs. The robot had multiple autonomous routines that would allow the team work with our alliance partners to create the most optimal auto strategy.


# Baconator Game

## Premise
Seven Degrees of Kevin Bacon is a game where you try to find the shortest path of co-stars from Kevin Bacon to any other actor. The result is commonly called an actor's "Bacon Number". It's a riff off of the degrees of seperation theory, which states that anyone in the world can be connected to anyone else via no more than 6 "jumps" (totalling 7 people in the chain from source to destination). 

Our game is going to leverage the weights on a graph to find the shortest path via weights instead of only looking at number of jumps. This may result in longer paths, but the people along those paths will be more closely connected. 

Make a new file named Baconator.java and ensure it has a main method: this file is a driver! The game has 2 modes. When a graph has 9 nodes or fewer, the game is in Guess mode. When a graph has 10 nodes or more, the game is in Gamble mode. In both modes, the game starts at round 1 and the player starts with 9 points. The game ends when the player hits 0 points or if every possible node-to-node combination has been exhausted. Their final score is the number of rounds they won multiplied by 1 + their ending points.

Guess Mode:
The user is given 2 random nodes and they must guess which nodes are the connections between them. They get +1 point for every correct node, -1 point for every incorrect node they guessed, and -1 point for every correct node they did not guess. Points for a round can be negative, resulting in lost points. If two nodes are directly linked and the player enters no nodes as their guess, they are awarded +1 point.

Gamble Mode:
The user is given 2 random nodes and they must guess how many connections they think are between the nodes. If their guess is correct, they are awarded that amount in points. If their guess is incorrect, the difference between the guesses is deducted from the correct answer, and the new value is awarded. If the difference is large enough, it can cause a deduction. 
e.g.: round 1, actual = 5, guess = 7, difference = 2, awarded = 3. round 2, actual = 2, guess = 5, difference = 3, awarded = -1.

## Specs
Add:
- Baconator.java, a game that includes its own driver to play

## Reflection Questions:
Provide your answer at the top of the NEW Baconator.java file, in a block comment.
- What was your process to create the game? What problems did you encounter and how did you solve them?
- If you could change the game rules, what would you do and why?

## Learning Outcomes
The goal of this extra credit unit is to have the student practice: critical thinking, problem solving, independent code development, independent research, and game development.

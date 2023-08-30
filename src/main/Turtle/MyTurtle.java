import turtleClasses.World;
import turtleClasses.Turtle;
import java.awt.Color;

//Turtle Library
//    https://cseweb.ucsd.edu/~ricko/CSE11/turtleClasses.jar

//Turtle Documentation
//    https://cseweb.ucsd.edu/~ricko/CSE11/turtleClassesDocs

public class MyTurtle {
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 400;

    public static void main(String[] args) {
        //Create world using width and height
        World myWorld;

        // create turtle
        int turtleDelay; // set delay between actions
        Turtle myTurtle; // create turtle using world and delay

        // TODO: set starting position at left of screen, facing east
        myTurtle; //set pen up or down?
        myTurtle; //can we turn turtle?
        myTurtle; //how to move turtle? how to get position numbers? (height/width)

        //TODO: change pen settings
        myTurtle; //can we change width?
        myTurtle; //can we change pen color?

        int characterF = 0b0011011;
        int characterS = 0b1101011;
        int characterI = 0b0010001;

        drawCharacter(myTurtle, characterF);
        drawCharacter(myTurtle, characterS);
        drawCharacter(myTurtle, characterI);
    }

    /**
     * Draws a character shape using the provided mask
     * 
     * mask example (F): 0b 0 0 1 1 0 1 1
     *                      | | | | | | |
     *                      6 5 4 3 2 1 0
     * 
     *               _    _    _    _    _    _
     * start_  |    |    | |  |_|  |_|  |_|  |_|  _end
     *                             |    |_   |_|  
     * 
     *          0    1    2    3    4    5    6
     *          L    R    R    R    L    L    L   R
     * 
     * following this drawing order, each bit in bitmask is used to
     * toggle pen on/off for each character component
     * 
     * note the bits in characterSegments are read from right to left
     * 
     * @param turtle The Turtle object that is used to draw the square. 
     */
    public static void drawCharacter(Turtle turtle, int characterSegments) {
        // Define the turn sequence as a string
        String turnSequence = "LRRRLLLR";
        
        // each character has 7 segments
        for (int i = 0; i < 7; i++) {
            // Turn left or right based on the turn sequence
            if (turnSequence.charAt(i) == 'L') {
                turtle.turnLeft();
            } else {
                turtle.turnRight();
            }
    
            // Set the pen down or up based on the character component
            boolean penDown = (characterSegments & (1 << i)) != 0;
            turtle.setPenDown(penDown);
    
            // Move forward
            turtle.forward();
        }
    
        // Final actions after the loop
        turtle.penUp();
        turtle.turnRight();
        turtle.forward();
    }
}

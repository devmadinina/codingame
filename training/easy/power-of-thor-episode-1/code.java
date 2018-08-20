import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt();      // the X position of the light of power
        int lightY = in.nextInt();      // the Y position of the light of power
        int initialTX = in.nextInt();   // Thor's starting X position
        int initialTY = in.nextInt();   // Thor's starting Y position

        // game loop
        while (true) {
            int remainingTurns = in.nextInt(); // The remaining amount of turns Thor can move. Do not remove this line.

            //Message de DEBUG
            System.err.println("Remaining turns = " + remainingTurns);

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            String moveX = "";                      //Direction and number of steps toward X axis
            String moveY = "";                      //Direction and number of steps toward Y axis

            //*******************
            //* X axis movement *
            //*******************

            int diffX = initialTX - lightX;         // Calculate the difference between Thor and Light position

            System.err.println("Différence sur X = " + diffX);

            if ( (diffX < 0) &&
                    (initialTX < 39) ) {            // Don't move out of the plan
                moveX = "E";                        // Move East
                initialTX++;
            }

            if ( (diffX > 0) &&
                    (initialTX > 0) ) {
                moveX = "W";                        // Move West
                initialTX--;
            }

            System.err.println("  ==> " + moveX);

            //*******************
            //* Y axis movement *
            //*******************


            int diffY = initialTY - lightY;         // Calculate the difference between Thor and Light position
            System.err.println("Différence sur Y = " + diffY);
            if ( (diffY < 0) &&
                    (initialTY < 17) ) {            // Don't move out of the plan
                moveY = "S";                        // Move South
                initialTY++;
            }

            if ( (diffY > 0) &&
                    (initialTY > 0) ) {
                moveY = "N";                        // Move North
                initialTY--;
            }

            System.err.println("  ==> " + moveY);

            // A single line providing the move to be made: N, NE, E, SE, S, SW, W or NW
            System.out.println(moveY + moveX);
        }
    }
}

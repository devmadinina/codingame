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

    public static void main(String args[])
    {
        Scanner in = new Scanner(System.in);

        //***************************
        //* Init objects in the map *
        //***************************

        Player.Light light = new Player().new Light(in.nextInt(), in.nextInt());
        Player.Thor  thor  = new Player().new Thor( in.nextInt(), in.nextInt());

        //*************
        //* Game loop *
        //*************

        while (true)
        {
            int remainingTurns = in.nextInt(); // The remaining amount of turns Thor can move. Do not remove this line.

            //DEBUG message
            System.err.println("Remaining turns = " + remainingTurns);

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            //****************
            //* Time to move *
            //****************

            // A single line providing the move to be made: N, NE, E, SE, S, SW, W or NW
            System.out.println(thor.moveOneFootTo(light));
        }
    }



    //**********************
    //* Useful sub classes *
    //**********************


    // Managing coordinates
    public class Coord
    {

        // Constants
        private static final int X = 0;                // Index of X coord in the array
        private static final int Y = 1;                // Index of Y coord in the array

        private final String[] axisNames = {"X","Y"};  // Axis names

        // Properties
        private int[] origin = {0,0};                  // Default origin point : [0,0]

        // Constructors
        public Coord()
        {
        }

        public Coord(final int _x, final int _y)
        {
            this.setCoord(_x, _y);
        }

        // Getters
        public int getX()
        {
            return this.origin[Coord.X];
        }
        public int getY()
        {
            return this.origin[Coord.Y];
        }

        // Setters
        public void setX(final int _x) {
            this.origin[Coord.X] = _x;
        }
        public void setY(final int _y) {
            this.origin[Coord.Y] = _y;
        }
        public void setCoord(final int _x, final int _y) {
            this.setX(_x);
            this.setY(_y);
        }

        // Other methods

        /**
         * @return "X" or "Y"
         */
        public String getAxisName(final int _index) {
            return this.axisNames[this.validateIndex(_index)];
        }

        /**
         * @return the value of coordinate X or Y
         */
        public int getAxisValue(final int _index) {
            return this.origin[this.validateIndex(_index)];
        }

        /**
         * set the value of coordinate X or Y
         */
        public void setAxisValue(final int _index, final int _value) {
            this.origin[this.validateIndex(_index)] = _value;
        }

        /**
         * increments the value of coordinate X or Y
         */
        public void incrementAxis(int _index) {
            this.origin[this.validateIndex(_index)]++;
        }

        /**
         * decrements the value of coordinate X or Y
         */
        public void decrementAxis(int _index) {
            this.origin[this.validateIndex(_index)]--;
        }

        /**
         * avoids error
         * @return 0 or 1
         */
        public int validateIndex(final int _index) {
            int index = Coord.X;
            if (_index != 0 ) {
                index = Coord.Y;
            }
            return index;
        }


    }

    // Managing objects on the map
    public abstract class AObject {

        public Coord position = new Coord();

        public AObject() {

        }

        public AObject(int _x, int _y) {
            this.position.setCoord(_x, _y);
        }

    }

    // Description of the map
    public class Valhalla {

        public final Coord topLeftHandCorner     = new Coord(0 , 0);
        public final Coord bottomRightHandCorner = new Coord(39,17);

        public Valhalla() {

        }
    }

    // Description of Thor
    public class Thor extends AObject {

        // Constants : Movements allowed for Thor
        private final String[] positiveDir = {"E", "S"};  // Move East or South
        private final String[] negativeDir = {"W", "N"};  // Move West or North

        // Properties
        private  final Valhalla valhalla = new Valhalla();

        // Constructors
        public Thor() {
            super();
        }

        public Thor(int _x, int _y) {
            super(_x, _y);
        }

        // Other methods


        // Managing Thor movements
        /**
         * Allowing Thor to move a step to a direction on an axis
         * @return String : direction on axis X or Y
         */
        private String doOneMove(int _axis, boolean positive) {

            String direction = "";

            if (positive)
            {
                this.position.incrementAxis(_axis);
                direction = this.positiveDir[this.position.validateIndex(_axis)];
            }
            else
            {
                this.position.decrementAxis(_axis);
                direction = this.negativeDir[this.position.validateIndex(_axis)];
            }

            // Is Thor still in the map ?
            if (! this.stayedInValhalla(_axis)) {
                direction = "";
            }

            return direction;

        }

        private String positiveMove(int _axis) {
            return this.doOneMove(_axis, true);
        }

        private String negativeMove(int _axis) {
            return this.doOneMove(_axis, false);
        }


        // Thor's coordinates must always be valid
        public boolean stayedInValhalla(int _axis) {

            boolean result = true;
            int thorPositionOnAxis = this.position.getAxisValue(_axis);
            int valhallaTop = this.valhalla.topLeftHandCorner.getAxisValue(_axis);
            int valhallaBottom = this.valhalla.bottomRightHandCorner.getAxisValue(_axis);

            if (thorPositionOnAxis < valhallaTop) {
                result = false;                                     // Thor is out
                this.position.setAxisValue(_axis, valhallaTop);     // Update coordinates

                //DEBUG message
                System.err.print("\n    Thor is out : (top " + this.position.getAxisName(_axis) + ")" + thorPositionOnAxis + " / " + valhallaTop);
            }

            if (thorPositionOnAxis > valhallaBottom) {
                result = false;                                     // Thor is out
                this.position.setAxisValue(_axis, valhallaBottom);  // Update coordinates

                //DEBUG message
                System.err.print("\n    Thor is out (bottom" + this.position.getAxisName(_axis) + "): " + thorPositionOnAxis + " / " + valhallaBottom);
            }

            return result;

        }

        // Moves Thor one step
        public String moveOneFootTo(Coord _cible) {

            String finalDirection = "";
            for (int axis = Coord.Y; axis >= Coord.X; axis--) {

                // Calculate the difference between Thor and Light position
                int diff = this.position.getAxisValue(axis) - _cible.getAxisValue(axis);

                //DEBUG message
                System.err.print("Difference on " + this.position.getAxisName(axis) + " = " + diff);

                String direction = "";  // If no difference, don't move

                if (diff < 0) {
                    direction = positiveMove(axis);
                }

                if (diff > 0) {
                    direction = negativeMove(axis);
                }

                finalDirection += direction;

                //DEBUG message
                System.err.println("  ==> " + direction);
            }

            return finalDirection;
        };

        // Casting for better reading
        public String moveOneFootTo(AObject _cible) {
            return moveOneFootTo(_cible.position);
        }

    }

    // Managing light pit
    public class Light extends AObject {
        public Light() {
            super();
        }

        public Light(int _x, int _y) {
            super(_x, _y);
        }
    }

}

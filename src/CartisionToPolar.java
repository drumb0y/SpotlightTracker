public class CartisionToPolar {

    public static int XOffset = 270;
    public static int YOffset = 380;

    public static double[] convert(int x, int y) {
        x += XOffset;
        y += YOffset;

        double R = Math.sqrt((x*x) + (y*y));
        //convert R into degrees for the
        double thetaTilt = (32/15)*R;

        double thetaPan = Math.asin(x/R)/(Math.PI/180);

        return new double[]{thetaTilt, thetaPan};

    }

}

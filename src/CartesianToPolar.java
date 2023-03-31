public class CartesianToPolar {

    public static int XOffset = 300;
    public static int YOffset = 330;
    public static double Rx = 9.4;
    public static double Ry = 5.3;

    public static double[] convert(int x, int y) {
        double T = Math.sqrt( ( Math.pow( (x-XOffset)/Rx,2) + Math.pow( ((y)-YOffset)/Ry,2))/2);

        double P;

        if (T == 0) {
            System.out.println("T is 0");
            P = 0;
        }
        else P = Math.acos( ( (x-XOffset)/Rx)/T) * (180/Math.PI);

        //System.out.println("T: " + T + " P: " + P);


        return new double[]{T,-(P-90)};

    }


//    public static double[] convert(int x, int y) {
//        x += XOffset;
//        y += YOffset;
//
//        double R = Math.sqrt((x*x) + (y*y));
//        //convert R into degrees for the
//        double thetaTilt = (32/15)*R;
//
//        //System.out.println("made it into the convert function");
//        double thetaPan = Math.asin(x/R)/(Math.PI/180);
//
//        return new double[]{thetaTilt, thetaPan+180};
//
//    }

}

package pe.edu.pucp.packrunner.utils;

public class Mercator {
    final static double RADIUS_MAJOR = 6378137.0/1000;//km
    final static double RADIUS_MINOR = 6356752.3142/1000;//km
    final static double R = 40.075/(2*Math.PI);//km
    final static double FE=180;
    static double MAP_HEIGHT = 909;
    static double MAP_WIDTH = 626;

    public static double xAxisProjection(double longitude) {
        return RADIUS_MAJOR * Math.toRadians(longitude);
    }

    public static double yAxisProjection(double latitude) {
        //return Math.log(Math.tan(Math.PI / 4 + Math.toRadians(latitude) / 2)) * RADIUS_MAJOR;
        latitude = Math.min(Math.max(latitude, -89.5), 89.5);
        double earthDimensionalRateNormalized = 1.0 - Math.pow(RADIUS_MINOR / RADIUS_MAJOR, 2);

        double latitudeOnEarthProj = Math.sqrt(earthDimensionalRateNormalized) *
                Math.sin( Math.toRadians(latitude));

        latitudeOnEarthProj = Math.pow(((1.0 - latitudeOnEarthProj) / (1.0+latitudeOnEarthProj)),
                0.5 * Math.sqrt(earthDimensionalRateNormalized));

        double latitudeOnEarthProjNormalized =
                Math.tan(0.5 * ((Math.PI * 0.5) - Math.toRadians(latitude))) / latitudeOnEarthProj;

        return (-1) * RADIUS_MAJOR * Math.log(latitudeOnEarthProjNormalized);
    }
}

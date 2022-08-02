public class NBody {
    public static double readRadius(String s){
        In reader = new In(s);
        reader.readDouble();
        double res = reader.readDouble();
        return res;
    }
    public static Planet[] readPlanets(String s){
        In reader = new In(s);
        int nums = reader.readInt();
        reader.readDouble();
        Planet[] all = new Planet[nums];
        int i=0;
        for (i=0;i<nums;i++){
            double xP = reader.readDouble();
            double yP = reader.readDouble();
            double xV = reader.readDouble();
            double yV = reader.readDouble();
            double mass = reader.readDouble();
            String img = "images/"+reader.readString();
            Planet newPlanet = new Planet(xP,yP,xV,yV,mass,img);
            all[i]=newPlanet;
        }
        return all;
    }

    public static void main(String[] args){
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    Planet[] all = readPlanets(filename);
    StdDraw.setScale(-radius,radius);
    StdDraw.picture(0,0,"images/starfield.jpg");
    int i=0;
    for (i=0;i<all.length;i++){
        StdDraw.picture(all[i].xxPos,all[i].yyPos,all[i].imgFileName);
    }
    StdDraw.enableDoubleBuffering();
    double currentTime =0;
    StdDraw.clear();
    for(currentTime=0;currentTime<T;currentTime+=dt){
        double[] xForces = new double[all.length];
        double[] yForces = new double[all.length];
        for (i = 0; i < all.length; i++) {
            Planet p = all[i];
            xForces[i] = p.calcNetForceExertedByX(all);
            yForces[i] = p.calcNetForceExertedByY(all);
        }
        for (i = 0; i < all.length; i++) {
            Planet p = all[i];
            p.update(dt,xForces[i],yForces[i]);
        }
        StdDraw.picture(0,0,"images/starfield.jpg");
        for (i=0;i<all.length;i++){
            StdDraw.picture(all[i].xxPos,all[i].yyPos,all[i].imgFileName);
        }
        StdDraw.show();
        StdDraw.pause(10);
    }
    }

}

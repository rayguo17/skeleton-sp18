public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double gravityConstant = 6.67e-11;
    public Planet(double xP, double yP, double xV,double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        double dx = xxPos-p.xxPos;
        double dy = yyPos-p.yyPos;
        double square = dx*dx + dy*dy;
        return Math.sqrt(square);
    }
    public double calcForceExertedBy(Planet p){
        double dis = calcDistance(p);
        return (gravityConstant * mass * p.mass)/(dis*dis);
    }
    public double calcForceExertedByX(Planet p){
        double force = calcForceExertedBy(p);
        double dis = calcDistance(p);
        double dx = p.xxPos-xxPos;
        double xForce = force * dx/dis;
        return xForce;
    }
    public double calcForceExertedByY(Planet p){
        double force = calcForceExertedBy(p);
        double dis = calcDistance(p);
        double dy = p.yyPos-yyPos;
        double yForce = force * dy/dis;
        return yForce;
    }
    public double calcNetForceExertedByX(Planet[] all){
        double totalForceX =0;
        int i;
        for(i=0;i<all.length;i++){
            if(this.equals(all[i])){
                continue;
            }
            totalForceX += calcForceExertedByX(all[i]);
        }
        return totalForceX;
    }
    public double calcNetForceExertedByY(Planet[] all){
        double totalForceY =0;
        int i;
        for(i = 0;i<all.length;i++){
            if (this.equals(all[i])){
                continue;
            }
            totalForceY += calcForceExertedByY(all[i]);
        }
        return totalForceY;
    }
    public void update(double dt,double fx,double fy){
        double accX = fx/mass;
        double accY = fy/mass;
        double newVx = xxVel + accX * dt;
        double newVy = yyVel + accY * dt;
        double newPosX = xxPos + dt * newVx;
        double newPosY = yyPos + dt * newVy;
        xxVel = newVx;
        yyVel = newVy;
        xxPos = newPosX;
        yyPos = newPosY;
    }
}

package CG.Objects;

public class LineSegment {
    public Point p1;
    public Point p2;

    public LineSegment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double crossProductToPoint(Point p) {
        double Bx = this.p2.x;
        double By = this.p2.y;
        Bx -= this.p1.x;
        By -= this.p1.y;
        p.x -= this.p1.x;
        p.y -= this.p1.y;

        return Bx * p.y - By * p.x;
    }

}

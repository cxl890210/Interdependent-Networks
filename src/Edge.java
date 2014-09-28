
/*
 * 边
 */
public class Edge {

	private Point point1;
	public Boolean inter;//记录该边是否内部连边
	
	public Point getPoint2() {
		return point2;
	}
	public void setPoint2(Point point2) {
		this.point2 = point2;
	}
	public Point getPoint1() {
		return point1;
	}
	public void setPoint1(Point point1) {
		this.point1 = point1;
	}
	private Point point2;
	public Edge(Point point1,Point point2,Boolean inter)
	{
		this.point1 = point1;
		this.point2 = point2;
		this.inter=inter;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Edge temEdge=(Edge) obj;
		return (this.point1==temEdge.point1&&this.point2==temEdge.point2);
	}
	
}

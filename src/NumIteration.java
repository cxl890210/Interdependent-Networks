import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class NumIteration {

	public static double iterSolution(HashMap<Integer,Double> degreeSecA,HashMap<Integer,Double> degreeSecB,
			int meanDegree,double prob,double initX,double initY)
	{
		double eps = 20;//误差界
		int step=0;
		double lastX,lastY;
		double currentX,currentY;
		lastX = currentX = initX;lastY = currentY = initY;
	
		double result=0;
		double[] totalValue = new double[4];
		while(step<eps)
		{
			for(int i=0;i<4;i++)
			{
				totalValue[i]=0;
			}
			Entry<Integer,Double> entry;
			for(Iterator<Entry<Integer,Double>> it=degreeSecA.entrySet().iterator();it.hasNext();)
			{
				entry = it.next();
				int degree = entry.getKey();
				double degreeProb = entry.getValue();
				totalValue[0]+=(1.0/meanDegree)*(degreeProb*degree)*Math.pow(1-lastX, degree-1);
				totalValue[1]+=degreeProb*Math.pow(1-lastX, degree);
			}
			for(Iterator<Entry<Integer,Double>> it=degreeSecB.entrySet().iterator();it.hasNext();)
			{
				entry = it.next();
				int degree = entry.getKey();
				double degreeProb = entry.getValue();
				totalValue[2]+=(1.0/meanDegree)*(degreeProb*degree)*Math.pow(1-lastY, degree-1);
				totalValue[3]+=degreeProb*Math.pow(1-lastY, degree);
			}
			currentX = prob*(1-totalValue[0])*(1-totalValue[3]);
			currentY = prob*(1-totalValue[2])*(1-totalValue[1]);
			lastX=currentX;
			lastY=currentY;
			step++;
		}
		result = prob*(1-totalValue[1])*(1-totalValue[3]);
//		返回最大连通分支尺度比例的迭代估计值
		return result;
	}
}

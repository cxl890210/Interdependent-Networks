
public class demo {

	public void display(int[] a)
	{
		for(int num:a)
		{
			System.out.println(num);
		}
	}
	public static void main(String[] args)
	{
		int a[] = {1,2,3,4};
		demo test = new demo();
		test.display(a);
	}

}


public class Community {

    public int communitySize;//ÉçÇø³ß¶È
    public int beginCount;
    public int endCount;
	public Community(int beginCount,int endCount)
	{
		this.beginCount = beginCount;
		this.endCount = endCount;
		this.communitySize = endCount-beginCount;
	}
}
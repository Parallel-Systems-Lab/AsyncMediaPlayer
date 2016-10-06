package editor;
public class data implements Comparable<data> {
	
	public enum SyncType {
		SyncTypeDontWait,
		SyncTypeWait,
		SyncTypeJump,
		SyncTypeDT
	}

	public int startInSeconds;
	public float videoRate;
	public float soundRate;
	public String videoFunc;
	public String audioFunc;
	public String userRateFunction;
	public SyncType type;
	public float dt;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		return ((data) obj).startInSeconds == this.startInSeconds;
	}

	@Override
	public int compareTo(data o) {
		if (o.startInSeconds == this.startInSeconds )
		{
			return 0;
		}
		return o.startInSeconds < this.startInSeconds ? 1 : -1;
	}
}

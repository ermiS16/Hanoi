package logic.hanoi;
import java.util.Comparator;

public class PlateComperator {

	public final Comparator<Plate> PLATE_SORTING_ORDER = new Comparator<Plate>() {
		@Override
		public int compare(Plate o1, Plate o2) {
			//o1 - o2 <=> sort from thin to thick
			//o2 - o1 <=> sort from thick to thin
//			return o2.getValue() - o1.getValue();
			return o1.getValue() - o2.getValue();
		}
	};
	
}

import java.util.ArrayList;
import java.util.Arrays;


public class Relations
{
	public static RelationMatrix toTransitiveClosure(RelationMatrix matrix) {
		boolean[][] relationMatrix = matrix.getRelationMatrix();
		int size = matrix.size();
		boolean[][] newMatrix = Arrays.copyOf(relationMatrix, size);
		for(int i = 0; i < size; i++) {
			ArrayList<Integer> horizontalMask = new ArrayList<Integer>();
			ArrayList<Integer> verticalMask = new ArrayList<Integer>();
			// Filling the horizontal mask
			for(int j = 0; j < size; j++) {
				if(relationMatrix[i][j]) horizontalMask.add(j);
				if(relationMatrix[j][i]) verticalMask.add(j);
			}
			for(int k = 0; k < horizontalMask.size(); k++) {
				for(int l = 0; l < verticalMask.size(); l++) {
					int hzIndex = horizontalMask.get(k);
					int verIndex = verticalMask.get(l);
					newMatrix[verIndex][hzIndex] = true;
				}
			}
		}
		return new RelationMatrix(newMatrix);
	}
}

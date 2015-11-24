import java.util.ArrayList;
import java.util.Arrays;


public class Relations
{	
	/**
	 * Method to get transitive closure of the relation
	 * Using Warshall's Algorithm
	 * @return RelatonMatrix with the transitive property
	 */
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
	
	public static RelationMatrix toReflectiveClosure(RelationMatrix matrix) {
		boolean[][] relationMatrix = matrix.getRelationMatrix();
		boolean[][] newMatrix = Arrays.copyOf(relationMatrix, matrix.size());
		for (int i = 0; i < matrix.size(); i++) {
			if (!relationMatrix[i][i]) {
				newMatrix[i][i] = true;
			}
		}
		return new RelationMatrix(newMatrix);	
	}
	
	public static RelationMatrix toSymmetricClosure(RelationMatrix matrix){
		boolean[][] relationMatrix = matrix.getRelationMatrix();
		int size = matrix.size();
		boolean[][] newMatrix = Arrays.copyOf(relationMatrix, size);
		for(int i = 0; i < size; i++){
			for(int j = i + 1; j < size; j++){
				if(newMatrix[i][j] || newMatrix[j][i]){
					newMatrix[i][j] = true;
					newMatrix[j][i] = true;
				}
			}
		}
		return new RelationMatrix(newMatrix);
	}
}

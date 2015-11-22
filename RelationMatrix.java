import java.util.ArrayList;
import java.util.Arrays;

public class RelationMatrix {
	private boolean[][] relationMatrix;
	private int size;
	
	private char isReflective;
	private char isIrreflective;
	private char isSymmetrical;
	private char isAntisymmetrical;
	private char isTransitive;
	
	public static final char U = 'U';
	public static final char T = 'T';
	public static final char F = 'F';

	/**
	 * Construct a new empty relation matrix with a specified size of unknown type.
	 * @param size
	 */
	public RelationMatrix(int size){
		this.relationMatrix = new boolean[size][size];
		this.isReflective = F;
		this.isIrreflective = T;
		this.isSymmetrical = T;
		this.isAntisymmetrical = T;
		this.isTransitive = T;
	}
	
	public RelationMatrix(boolean[][] relationMatrix) {
		this.relationMatrix = relationMatrix;
		this.size = relationMatrix[0].length;
		this.isReflective = U;
		this.isIrreflective = U;
		this.isSymmetrical = U;
		this.isAntisymmetrical = U;
		this.isTransitive = U;
	}
	
	public void add(int i, int j){
		relationMatrix[i][j] = true;
		if(isReflective != T) isReflective = U;
		this.isIrreflective = (i == j ? F : U);
		if((isSymmetrical == T) && (i != j)) {
			isSymmetrical = (relationMatrix[j][i] ? T : F);
		}
		if(isAntisymmetrical == T) {
			isAntisymmetrical = (!relationMatrix[j][i] ? T : F);
		};
		this.isTransitive = U;
	}
	
	public void remove(int i, int j){
		relationMatrix[i][j] = false;
		this.isReflective = (i == j ? F : U);
		if(isIrreflective != T) isIrreflective = U;
		if(isSymmetrical == T) {
			isSymmetrical = (!relationMatrix[j][i] ? T : F);
		}
		if((isAntisymmetrical == T) && (i != j)) {
			isAntisymmetrical = (relationMatrix[j][i] ? T : F);
		};
		this.isTransitive = U;
	}
	
	public void setReflectivity() {
		boolean setReflective = true;
		boolean setIrreflective = true;
		
		for(int i = 0; i < size; i++) {
			setReflective &= relationMatrix[i][i];
			setIrreflective &= !relationMatrix[i][i];
		}
		this.isReflective = (setReflective ? T : F);
		this.isIrreflective = (setIrreflective ? T : F);
	}
	
	public void setSymmetry() {
		boolean setSymmetrical = true;
		boolean setAntisymmetrical = true;
		
		for(int i = 0; i < size; i++) for(int j = i; j < size; j++) if(i != j) {
			setSymmetrical &= !(relationMatrix[i][j] ^ relationMatrix[j][i]);
			setAntisymmetrical &= !(relationMatrix[i][j] || relationMatrix[j][i]);
		}
		this.isSymmetrical = (setSymmetrical ? T : F);
		this.isAntisymmetrical = (setAntisymmetrical ? T : F);
	}
	
	public boolean isReflective() {
		if(isReflective != U) return isReflective == T ? true : false;
		else {
			setReflectivity();
			return isReflective == T ? true : false;
		}
	}
	
	public boolean isIrreflective() {
		if(isIrreflective != U) return isIrreflective == T ? true : false;
		else {
			setReflectivity();
			return isIrreflective == T ? true : false;
		}
	}
	
	public boolean isSymmetrical() {
		if(isSymmetrical != U) return isSymmetrical == T ? true : false;
		else {
			setSymmetry();
			return isSymmetrical == T ? true : false;
		}
	}
	
	public boolean isAntisymmetrical() {
		if(isAntisymmetrical != U) return isAntisymmetrical == T ? true : false;
		else {
			setSymmetry();
			return isAntisymmetrical == T ? true : false;
		}
	}
	
	public boolean isAsymmetrical() {
		return isIrreflective() & isAntisymmetrical();
	}
	
	/**
	 * Using modified Warshall's algorithm
	 * @return
	 */
	public boolean isTransitive() {
		if(isTransitive != U) return isTransitive == T ? true : false;
		else for(int i = 0; i < size; i++) {
			ArrayList<Integer> horizontalMask = new ArrayList<Integer>();
			ArrayList<Integer> verticalMask = new ArrayList<Integer>();
			
			// Filling the mask
			for(int j = 0; j < size; j++) {
				if(relationMatrix[i][j]) horizontalMask.add(j);
				if(relationMatrix[j][i]) verticalMask.add(j);
			}
			for(int k = 0; k < horizontalMask.size(); k++) {
				for(int l = 0; l < verticalMask.size(); l++) {
					int hzIndex = horizontalMask.get(k);
					int verIndex = verticalMask.get(l);
						
					if(!relationMatrix[verIndex][hzIndex]) {
						isTransitive = F;
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * Using Warshall's Algorithm
	 * @return
	 */
	public RelationMatrix toTransitiveClosure() {
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
	
	public boolean[][] getRelationMatrix() {
		return relationMatrix;
	}
}
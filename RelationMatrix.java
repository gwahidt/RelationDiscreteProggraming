import java.util.ArrayList;

/**
 * Class representating a relation matrix
 * @author Arif Budiman
 * @author Geswa Wahid 
 * @author Mahdiazhari
 *
 */
public class RelationMatrix {
	private boolean[][] relationMatrix; 
	private int size;                   
	
	private char isReflective;
	private char isIrreflective;
	private char isSymmetrical;
	private char isAntisymmetrical;
	private char isTransitive;
	
	//char to indicate, 3 conditions
	private static final char U = 'U'; //unknown
	private static final char T = 'T'; //true
	private static final char F = 'F'; //false

	/**
	 * Construct a new relation matrix, with parameter size
	 * @param size the size of the new matrix
	 */
	public RelationMatrix(int size){
		this.size = size;
		this.relationMatrix = new boolean[size][size];
		this.isReflective = F;
		this.isIrreflective = T;
		this.isSymmetrical = T;
		this.isAntisymmetrical = T;
		this.isTransitive = T;
	}
	
	/**
	 * Constructs a new relation matrix, with parameter an already created 2d boolean array
	 * @param relationMatrix the 2d boolean array
	 */
	public RelationMatrix(boolean[][] relationMatrix) {
		this.relationMatrix = relationMatrix;
		this.size = relationMatrix[0].length;
		this.isReflective = U;
		this.isIrreflective = U;
		this.isSymmetrical = U;
		this.isAntisymmetrical = U;
		this.isTransitive = U;
	}
	
	/**
	 * Adds a relation on the location specified in i and j
	 * @param i the column to add relation
	 * @param j the row to add relation
	 */
	public void add(int i, int j){
		relationMatrix[i][j] = true;
		if(isReflective != T) isReflective = U;
		this.isIrreflective = (i == j ? F : isIrreflective);
		if((isSymmetrical == T) && (i != j)) {
			isSymmetrical = (relationMatrix[j][i] ? T : F);
		}
		if(isAntisymmetrical == T) {
			isAntisymmetrical = (!relationMatrix[j][i] ? T : F);
		};
		this.isTransitive = U;
	}
	
	/**
	 * Removes a relation on the location specified in i and j
	 * @param i the column to remove relation
  	 * @param j the row to remove relation
	 */
	public void remove(int i, int j){
		relationMatrix[i][j] = false;
		this.isReflective = (i == j ? F : isReflective);
		if(isIrreflective != T) isIrreflective = U;
		if(isSymmetrical == T) {
			isSymmetrical = (!relationMatrix[j][i] ? T : F);
		}
		if((isAntisymmetrical == T) && (i != j)) {
			isAntisymmetrical = (relationMatrix[j][i] ? T : F);
		};
		this.isTransitive = U;
	}
	
	/**
	 * Finds the status of the reflectivity of the Relation Matrix
	 */
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
	
	/**
	 * Finds the status of the simmetry of the matrix
	 */
	public void setSymmetry() {
		boolean setSymmetrical = true;
		boolean setAntisymmetrical = true;
		
		for(int i = 0; i < size; i++) for(int j = i; j < size; j++) if(i != j) {
			setSymmetrical &= !(relationMatrix[i][j] ^ relationMatrix[j][i]);
			setAntisymmetrical &= !(relationMatrix[i][j] && relationMatrix[j][i]);
		}
		this.isSymmetrical = (setSymmetrical ? T : F);
		this.isAntisymmetrical = (setAntisymmetrical ? T : F);
	}
	
	/**
	 * Method to find the reflective property of the relation
	 * @return boolean that specifies wheter the relation is reflective or not
	 */
	public boolean isReflective() {
		if(isReflective == U) setReflectivity();
		
		return isReflective == T ? true : false;	
	}
	

	/**
	 * Method to find the irreflective property of the relation
	 * @return boolean that specifies wheter the relation is irreflective or not
	 */
	public boolean isIrreflective() {
		if(isIrreflective == U) setReflectivity();
		
		return isIrreflective == T ? true : false;
	}
	
	/**
	 * Method to find the symmetric property of the relation
	 * @return boolean that specifies wether given matrix is symmetrical or not
	 */
	public boolean isSymmetrical() {
		if(isSymmetrical == U) setSymmetry();
		
		return isSymmetrical == T ? true : false;
	}
	
	/**
	 * Method to find the antisymmetric property of the relation
	 * @return boolean that specifies wether given matrix is antisymmetrical or not
	 */
	public boolean isAntisymmetrical() {
		if(isAntisymmetrical == U) setSymmetry();
		
		return isAntisymmetrical == T ? true : false;
	}
	
	/**
	 * Method to find the asymmetric property of the relation
	 * @return boolean that specifies wether given matrix is asymmetrical or not
	 */
	public boolean isAsymmetrical() {
		return isIrreflective() & isAntisymmetrical();
	}
	
	/**
	 * Method to find transitive property of the relation
	 * Using modified Warshall's algorithm
	 * @return boolean that specifies the transitive property
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
	 * Method to get the 2d boolean array representation of the relation
	 * @return 2d boolean array representation
	 */
	public boolean[][] getRelationMatrix() {
		return relationMatrix;
	}
	
	/**
	 * Method to get the size of the relation
	 * @return int with the size of the relation
	 */
	public int size() {
		return size;
	}
}


public class Relation {
	private boolean[][] primary;
	private boolean[][] closer_reflection;
	private boolean[][] closer_simmetry;
	private boolean[][] closer_transitive;
	
	private boolean isRefelction;
	private boolean isIrreflection;
	private boolean isSimmetry;
	private boolean isAntisimmetry;
	private boolean isTransitive;
	
	public Relation(int n){
		primary = new boolean[n][n];
		closer_reflection  = new boolean[n][n];
		closer_simmetry  = new boolean[n][n];
		closer_transitive = new boolean[n][n];
		isRefelction = true;
		isIrreflection = true;
		isSimmetry = true;
		isAntisimmetry = true;
		isTransitive = true;
	}
	
	public void insert_matrix(boolean[][] matrix){
		primary =  matrix;
		closer_reflection = matrix;
		closer_simmetry = matrix;
		closer_transitive = matrix;
	}
	
	public void insert(int i, int j){
		primary[i][j] = closer_reflection[i][j] = closer_simmetry[i][j] = closer_transitive[i][j] = true;
	}
	
	private void checkReflectionIrreflectionAndMakeCloser(){
		boolean isCheckReflection = true;
		boolean isCheckIrreflection = true;
		
		for(int i = 0 ; i < primary.length; i++){
			if(!primary[i][i] && isCheckReflection){
				this.isRefelction = isCheckReflection = false;
			}else if(primary[i][i] && isCheckIrreflection){
				this.isIrreflection = isCheckIrreflection = false;
			}
			closer_reflection[i][i] = true;
		}
	}
	
	private void checkSimmertyAntisimmetyAndMakeCloser(){
		boolean isCheckSimmetry = true;
		boolean isCheckAntiSimmetry = true;
		
		for(int i  = 0; i < primary.length; i++){
			for(int j = i + 1; j < primary[i].length; j++ ){
				if((primary[i][j] != primary[j][i]) && isCheckSimmetry){
					this.isSimmetry = isCheckSimmetry = false;
				}else if((primary[i][j] && primary[j][i]) && isCheckAntiSimmetry){
					this.isAntisimmetry = isCheckAntiSimmetry =false;
				}
				closer_simmetry[j][i] = closer_simmetry[i][j] = primary[i][j] || primary[j][i];
			}
		}
	}
	
	private void checkTransitiveAndMakeCloser(){
		boolean isCheckTransitive = true;
		
		for(int i = 0; i < primary.length; i++){
			for(int j = 0; j < primary.length; j++){
				if(primary[i][j]){
					for(int k = 0; k < primary.length; k++){
						if(primary[j][k] && !primary[i][k]){
							if(isCheckTransitive){
								this.isTransitive = isCheckTransitive = false;
							}
							closer_transitive[i][k] = true;
						}
					}
				}
			}
		}
	}
	
	public void checkAll(){
		checkReflectionIrreflectionAndMakeCloser();
		checkSimmertyAntisimmetyAndMakeCloser();
		checkTransitiveAndMakeCloser();
	}
	
	public void checkAllAdvance(){
		boolean isCheckingRefflection = true;
		boolean isCheckingIrreflection = true;
		boolean isCheckingSimmetry = true;
		boolean isCheckingAntisimmetry = true;
		boolean isCheckingTransitive = true;
		
		int m = primary.length;
		for(int i = 0; i < m; i++){
			for(int j = 0; j < m; j++){
				
				if(i == j){
					if(!primary[i][i] && isCheckingRefflection){
						this.isRefelction = isCheckingRefflection = false;
					}else if(primary[i][i] && isCheckingIrreflection){
						this.isIrreflection = isCheckingIrreflection = false;
					}
					closer_reflection[i][i] = true;
				}
				
				if(i != j){
					if((primary[i][j] ^ primary[j][i]) && isCheckingSimmetry){
						this.isSimmetry = isCheckingSimmetry = false;
					}else if((primary[i][j] && primary[j][i]) && isCheckingAntisimmetry){
						this.isAntisimmetry = isCheckingAntisimmetry = false;
					}
					closer_simmetry[j][i] = closer_simmetry[i][j] = primary[i][j] || primary[j][i];
				}
				
				
				if(primary[i][j]){
					for(int k = 0; k < m; k++){
						if(primary[j][k] && !primary[i][k]){
							if(isCheckingTransitive){
								this.isTransitive = isCheckingTransitive = false;
							}
							closer_transitive[i][k] = true;
						}
					}
				}
				
				
			}
		}
	}
	
	public void union(boolean[][] matrix){
		int n = primary.length;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				primary[i][j] = primary[i][j] || matrix[i][j]; 
			}
		}
	}
	
	public Relation getCloser(boolean refflection, boolean simmetry, boolean transitive){
		int n = primary.length;
		
		Relation tempRelation = new Relation(n);
		tempRelation.union(primary);
		tempRelation.checkAllAdvance();
		
		if(refflection) tempRelation.union(tempRelation.getCloser_reflection());
		if(simmetry) tempRelation.union(tempRelation.getCloser_simmetry());
		if(transitive){
			tempRelation.checkAllAdvance();
			tempRelation.union(tempRelation.getCloser_transitive());
		}
		
		tempRelation.checkAllAdvance();
		
		return tempRelation;
	}
	
	public void generateRelation(boolean refflection, boolean irreflection, boolean simmetry, boolean antisimmetry, boolean asimmetry, boolean transitive){
		if(refflection && irreflection){
			System.out.println("tidak ada");
			return;
		}
		
		Relation tempRelation = getCloser(refflection, simmetry, transitive);
		if(((irreflection != tempRelation.isIrreflection()) && irreflection)||((antisimmetry != tempRelation.isAntisimmetry()) && antisimmetry)){
			System.out.println("tidak ada");
			return;
		}
		
		boolean[][] tempMatrix = tempRelation.getPrimary();
		int n = tempMatrix.length;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(tempMatrix[j][i]){
					char a = (char)(97 + i);
					char b = (char)(97 + j);
					System.out.println(a + " " + b);
				}
			}
		}
	}

	public boolean isRefelction() {
		return isRefelction;
	}

	public boolean isIrreflection() {
		return isIrreflection;
	}

	public boolean isSimmetry() {
		return isSimmetry;
	}

	public boolean isAntisimmetry() {
		return isAntisimmetry;
	}

	public boolean isAsimetry() {
		return this.isAntisimmetry && this.isIrreflection;
	}

	public boolean isTransitive() {
		return isTransitive;
	}

	public boolean[][] getCloser_reflection() {
		return closer_reflection;
	}

	public boolean[][] getCloser_simmetry() {
		return closer_simmetry;
	}

	public boolean[][] getCloser_transitive() {
		return closer_transitive;
	}
	
	public boolean[][] getPrimary() {
		return primary;
	}
	
	public void printMatrix(boolean[][] matrix){
		int m = matrix.length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if(matrix[i][j]) System.out.print("1 ");
				else System.out.print("0 ");
			}
			System.out.println();
		}
	}
	
	public void printMatrix(){
		int m = primary.length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if(primary[i][j]) System.out.print("1 ");
				else System.out.print("0 ");
			}
			System.out.println();
		}
	}
	
	public void getAllStatus(){
		if(isSimmetry || isIrreflection || isSimmetry || isAntisimmetry || isTransitive){
			if(isRefelction) System.out.println("refleksif");
			
			if(isIrreflection) System.out.println("irrefleksif");
			
			if(isSimmetry) System.out.println("simetri");
			
			if(isAntisimmetry) System.out.println("antisimetri");
			
			if(isAntisimmetry && isIrreflection) System.out.println("asimetri");
			
			if(isTransitive) System.out.println("transitif");
		}else{
			System.out.println("tidak ada");
		}
	}
	
	/**
	 * Mahdiazhari modifications
	 * Check if relation is ordered/equivalent
	 * 
	 */
	
	public void getEquivalentOrdered() {
		if (isEquivalent() && isOrdered()) {
			System.out.println("setara dan terurut");
		} else if (isEquivalent() && !isOrdered()) {
			System.out.println("setara");
		} else if (!isEquivalent() && isOrdered()) {
			System.out.println("terurut");
		} else {
			System.out.println("tidak setara dan tidak terurut");
		}
	}
	
	public boolean isEquivalent() {
		checkAll();
		if (isRefelction && isSimmetry && isTransitive) {
			return true;
		}
		return false;
	}
	
	public boolean isOrdered() {
		checkAll();
		if (isRefelction && isAntisimmetry && isTransitive) {
			return true;
		}
		return false;
	}
	
}

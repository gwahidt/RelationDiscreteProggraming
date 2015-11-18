import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int m = Integer.parseInt(in.nextLine());
		
		Relation myRelation = new Relation(m);
		
		for(int i = 0; i < m; i++){
			String line = in.nextLine();
			for(int j = 0; j < m; j++){
				if(line.charAt(j * 2) == '1'){
					myRelation.insert(i, j);
				}
			}
		}
		
		myRelation.checkAllAdvance();
		
//		myRelation.getAllStatus();
		
//		myRelation.printMatrix(myRelation.getCloser_transitive());
//		myRelation.printMatrix(myRelation.getCloser(false, true, false));
		myRelation.generateRelation(false, false, true, false, false, true);
		
		in.close();
	}
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Soal3 {
	public static void main(String[] args) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int m = Integer.parseInt(in.readLine());
		//RelationMatrix myRelation = new RelationMatrix(m);
		boolean[][] inputRelation = new boolean[m][m];
		for (int i = 0; i < m; i++) {
			String line = in.readLine();
			for (int j = 0; j < m; j++) {
				if (line.charAt(j * 2) == '1') {
					inputRelation[i][j] = true;	
				}
			}
		}
		RelationMatrix newRelation = new RelationMatrix(inputRelation);
		if (newRelation.isEquivalent() && newRelation.isOrdered()) {
			System.out.println("setara dan terurut");
		}  else if (newRelation.isEquivalent() && !newRelation.isOrdered()) {
			System.out.println("setara");
		} else if (!newRelation.isEquivalent() && newRelation.isOrdered()) {
			System.out.println("terurut");
		} else {
			System.out.println("tidak setara dan tidak terurut");
		}
		in.close();
	}
	
}

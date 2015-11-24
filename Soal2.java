import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Soal2 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String[] firstLine = in.readLine().split(" ");
		
		int size = Integer.parseInt(firstLine[0]);
		int numberOfProperties = Integer.parseInt(firstLine[1]);
		boolean[][] matrix = new boolean[size][size];
		
		boolean toReflecsive = false;
		boolean toSymmetrical = false;
		boolean toTransitive = false;
		
		String secondLine = in.readLine();
		for(int i = 0; i < numberOfProperties; i++){
			int properties = secondLine.charAt(i * 2) - '0';
			
			switch (properties) {
			case 1:
				toReflecsive = true;
				break;
			
			case 2:
				toSymmetrical = true;
				break;
			
			case 3:
				toTransitive = true;
				break;

			}
		}
		
		for(int i = 0; i < size; i++){
			String line = in.readLine();
			for(int j = 0 ; j < size; j++){
				if(line.charAt(j * 2) == '1'){
					matrix[i][j] = true; 
				}
			}
		}
		
		RelationMatrix relationMatrix = new RelationMatrix(matrix);
		
		if(toReflecsive){
			relationMatrix = Relations.toReflectiveClosure(relationMatrix);
		}
		
		if(toSymmetrical){
			relationMatrix = Relations.toSymmetricClosure(relationMatrix);
		}
		
		if(toTransitive){
			relationMatrix = Relations.toTransitiveClosure(relationMatrix);
		}
		
		matrix = relationMatrix.getRelationMatrix();
		
		for(int i = 0; i < size; i++){
			for(int j = 0 ; j < size; j++){
				if(matrix[i][j]){
					 out.write("1 ");
				}else{
					out.write("0 ");
				}
			}
			out.newLine();
		}
		out.flush();
		
		in.close();
		out.close();
	}
}

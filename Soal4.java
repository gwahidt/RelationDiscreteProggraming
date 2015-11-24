import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Soal4 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		
		char[] elements = {'a','b','c','d','e', 'f'};
		RelationMatrix relationMatrix = new RelationMatrix(elements.length);
		
		
		/**
		 * index 0 = reflective
		 * index 1 = irreflective
		 * index 2 = symmetrical
		 * index 3 = antisimetrical
		 * index 4 = asimetrical
		 * index 5 = transitive
		 */
		boolean[] properties = new boolean[6];
		
		String[] firstLine = in.readLine().split(" ");
		int numberOfProperties = Integer.parseInt(firstLine[0]);
		int numberOfTuples = Integer.parseInt(firstLine[1]);
		
		
		String secondLine = in.readLine();
		for(int i = 0; i < numberOfProperties; i++){
			switch (secondLine.charAt(i * 2)) {
			case '1':// case reflective properties
					properties[0] = true;
				break;
			
			case '2':// case irreflective properties
					properties[1] = true;
				break;
				
			case '3':// case symmertical properties
					properties[2] = true;
				break;
				
			case '4':// case antisymmertical properties
					properties[3] = true;
				break;
				
			case '5':// case asimmertycal properties
					properties[4] = true;
				break;
				
			case '6':// case transitive properties
					properties[5] = true;
				break;
				
			}
		}
		
		for(int i = 0; i < numberOfTuples; i++){
			String tuples = in.readLine();
			/*
			 * formToInt tuple
			 * a = 0
			 * b = 1
			 * c = 2
			 * d = 3
			 * e = 4
			 * f = 5
			*/
			int formToIntFirstTuple = tuples.charAt(0) - 'a';
			int formToIntSecondTuple = tuples.charAt(2) - 'a';
			
			relationMatrix.add(formToIntFirstTuple, formToIntSecondTuple);
		}
		
		/*
		 * tidak akan ada relasi yang refkesif dan irrefleksif
		 * dan tidak akan ada relasi yang refleksif dan asimetri
		 */
		if(properties[0] && (properties[1] || properties[4])){
			out.write("tidak ada");
		}else{
			// check refleksif
			if(properties[0]){
				relationMatrix = Relations.toReflectiveClosure(relationMatrix);
			}
			
			// check simetri
			if(properties[2]){
				relationMatrix = Relations.toSymmetricClosure(relationMatrix);
			}
			
			//check transitive
			if(properties[5]){
				relationMatrix = Relations.toTransitiveClosure(relationMatrix);
			}
			
			// check irreflesif dan antisimetri
			boolean isIrreflective = false;
			boolean isAntisymmetrical = false;
			boolean isAsmmetrical = false;
			
			// check irrefleksif
			if(properties[1]){
				isIrreflective =  relationMatrix.isIrreflective();
			}
			
			// check antisimerti
			if(properties[3]){
				isAntisymmetrical = relationMatrix.isAntisymmetrical();
			}
			
			// check asimetri
			if(properties[4]){
				isAsmmetrical = relationMatrix.isAsymmetrical();
			}
			
			if((properties[1] ^ isIrreflecsive) ||(properties[3] ^ isAntisymmetrical) || (properties[4] ^ isAsmmetrical) ){
				out.write("tidak ada");
			}else{
				boolean[][] matrix = relationMatrix.getRelationMatrix();
				
				// print all tuples
				for(int i = 0; i < elements.length; i++){
					for(int j = 0; j < elements.length; j++){
						if(matrix[i][j]){
							// print "first tuple" "second tuple"
							out.write(((char) (i + 'a')) + " " + ((char) (j + 'a')));
							out.newLine();
						}
					}
				}
				out.flush();
			}
		}
		
		
		in.close();
		out.close();
	}
}

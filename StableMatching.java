import java.util.*;
import java.io.*;
import java.io.IOException;


public class StableMatching {
	
	// just associate each man or woman ranker with an index
	List<String> men = null;
	List<String> women = null;
	Map<String, List<String>> menRanking = null;
	Map<String, List<String>> womenRanking = null;
	
	public static void main(String[] args) {
		// first, we need to parse the input data into appropriate data structures
		new StableMatching(args[0]);
		
	}
	
	public StableMatching(String filePath) {
		BufferedReader fileReader = null;
		
		// initialize our data structures
		men = new ArrayList<String>();
		women = new ArrayList<String>();
		menRanking = new HashMap<String, List<String>>();
		womenRanking = new HashMap<String, List<String>>();
		
		// we will parse the input data in this method, work on matching in the doMatching() method
		// also make sure we're safe from file I/O error
		try {
			String currentLineString = null;
			String[] currentLineArray = null;
			// input file reader
			fileReader = new BufferedReader(new FileReader(filePath));
			
			// parse data and store in data structures we can work with later
			while ((currentLineString = fileReader.readLine()) != null) {
				
				// now split the line into array using spaces as delimeter
				currentLineArray = currentLineString.split(" ");
				int numberOfPeople = currentLineArray.length - 1;
				String ranker = currentLineArray[0];
				
				// preference list for the current ranker
				List<String> prefList = Arrays.asList(Arrays.copyOfRange(currentLineArray, 1, currentLineArray.length));
				
				// add the women if they haven't already
				if (women.size() == 0) {
					// women is a subset of the line array
					women.addAll(prefList);
				}
				
				boolean isWoman = false;
				
				for(String currentWoman : women) {
					if (ranker.equals(currentWoman)) {
						isWoman = true;
					}
				}
				
				// is this a man currently ranking??
				if (isWoman == false) {
					// add male individual to our list
					men.add(ranker);
					
					// store their ranking
					menRanking.put(ranker, prefList);
				} else {
					// now store this woman's ranking
					womenRanking.put(ranker, prefList);
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileReader != null) {
					// close reader, good practice
					fileReader.close();
					
					// assuming no file I/O errors, and we're done storing data, let's now match couples
					// this is the bulk of the algorithm
					Map<String, String> matches = doMatching();
					
					// output results
					for(Map.Entry<String, String> matching:matches.entrySet()){
			            System.out.println(matching.getKey() + " " + matching.getValue());
			        }
					
					// ensure stability
			        // if(checkMatches(matches)){
			        //     System.out.println("Stable matching");
			        // } else {
			        //     System.out.println("Unstable matching");
			        // }
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private Map<String, String> doMatching() {
		// where we map woman to man
		Map<String, String> matches = new TreeMap<String, String>();
		// free men starts off as all men
		List<String> freeMen = new LinkedList<String>();
		freeMen.addAll(men);
		
		// loop until no more free men
		while(!freeMen.isEmpty()) {
			String currentMan = freeMen.remove(0);
			List<String> currentManPrefers = menRanking.get(currentMan);
			
			for(String woman : currentManPrefers) {
				if(matches.get(woman) == null) { // this woman is not matched
					// match these two
	                matches.put(woman, currentMan);
	                break;
	            } else {
	                String otherMan = matches.get(woman);
	                List<String> currentWomanRanking = womenRanking.get(woman);
	                if(currentWomanRanking.indexOf(currentMan) < currentWomanRanking.indexOf(otherMan)){
	                    //this woman prefers this man to the man she's engaged to
	                    matches.put(woman, currentMan);
	                    freeMen.add(otherMan);
	                    break;
	                }
	            }
	        }
		}
		
		// return the matching
		return matches;
	}
	
			//     private boolean checkMatches(Map<String, String> matches) {
			//         if(!matches.keySet().containsAll(women)) {
			//             return false;
			//         }
			//  
			//         if(!matches.values().containsAll(men)) {
			//             return false;
			//         }
			//  	   	
			//         Map<String, String> invertedMatches = new TreeMap<String, String>();
			// 		
			//         for(Map.Entry<String, String> couple:matches.entrySet()) {
			//             invertedMatches.put(couple.getValue(), couple.getKey());
			//         }
			//  
			//         for(Map.Entry<String, String> couple:matches.entrySet()) {
			// // women
			//             List<String> shePrefers = womenRanking.get(couple.getKey());
			//             List<String> herPrefList = new LinkedList<String>();
			//             shePrefers.addAll(shePrefers.subList(0, shePrefers.indexOf(couple.getValue())));
			// 
			// // men
			//             List<String> hePrefers = menRanking.get(couple.getValue());
			//             List<String> hisPrefList = new LinkedList<String>();
			//             hePrefers.addAll(hePrefers.subList(0, hePrefers.indexOf(couple.getValue())));
			//  
			//             for(String man : herPrefList) {
			//                 String menFinace = invertedMatches.get(man);
			//                 List<String> thisManPrefers = menRanking.get(man);
			// 	
			//                 if(thisManPrefers.indexOf(menFinace) > thisManPrefers.indexOf(couple.getKey())){
			// 	    			return false;
			//                 }
			//             }
			//  
			//             for(String woman : hisPrefList){
			//                 String womenFinace = matches.get(woman);
			//                 List<String> thisWomanPrefers = womenRanking.get(woman);
			// 	
			//                 if(thisWomanPrefers.indexOf(womenFinace) > thisWomanPrefers.indexOf(couple.getValue())) {
			//                     return false;
			//                 }
			//             }
			// 
			//         }
			// 		
			//         return true;
			//     }
	
}
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ans {
    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            String inputFile = i + ".in";
            String outPutFile = i + ".out";
            try {
                /* parse all guards' shifts */
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                String s = br.readLine();
                int numOfGuards = Integer.parseInt(s);
                List<Guard> guardsList = new ArrayList<>();
                for (int j = 0; j < numOfGuards; j++) {
                    s = br.readLine();
                    int index = s.indexOf(" ");
                    int start = Integer.parseInt(s.substring(0, index));
                    int end = Integer.parseInt(s.substring(index + 1, s.length()));
                    guardsList.add(new Guard(start, end));
                }

                /* sort all guards' shifts by the starting point; if the starting time
                 *  is the same, sort by the end time */
                Comparator cp = new Comparator<Guard>() {
                    @Override
                    public int compare(Guard o1, Guard o2) {
                        if (o1.getStart() < o2.getStart())
                            return -1;
                        else if (o1.getStart() == o2.getStart() && o1.getEnd() < o2.getEnd())
                            return -1;
                        else return 1;
                    }
                };
                guardsList.sort(cp);

                /* find the non-overlapping time of every guard
                 *  if the time <= 0, which means the guard's shift can be totally overlapped, when firing
                 *  this guard, we still have the same coverage
                 *  if the time > 0, when firing this guard, the coverage = old coverage - time */
                List<Integer> minusList = new ArrayList<>();
                for (int k = 0; k < numOfGuards; k++) {
                    Guard g1 = guardsList.get(k);

                    int start = g1.getStart();
                    if (k != 0) {
                        Guard g0 = guardsList.get(k - 1);
                        start = (g1.getStart() < g0.getEnd()) ? g0.getEnd() : g1.getStart();
                    }
                    int end = g1.getEnd();
                    if (k != numOfGuards - 1) {
                        Guard g2 = guardsList.get(k + 1);
                        end = (g1.getEnd() > g2.getStart()) ? g2.getStart() : g1.getEnd();
                    }
                    minusList.add(end - start);
                }

                /* calculate the time coverage before firing a guard */
                int addup = 0;
                int startInterval = guardsList.get(0).getStart();
                int endInterval = guardsList.get(0).getEnd();
                for (int k = 1; k < numOfGuards; k++) {
                    Guard g1 = guardsList.get(k);
                    int s1 = g1.getStart();
                    int e1 = g1.getEnd();

                    if (s1 >= endInterval) {
                        addup += (endInterval - startInterval);
                        startInterval = s1;
                        endInterval = e1;
                    } else if (e1 > endInterval)
                        endInterval = e1;
                    if (k == numOfGuards - 1) {
                        addup += (endInterval - startInterval);
                    }
                }

                /* the maximum coverage after firing a guard is equal to the old coverage minus the least
                 * non-overlapping time, which could be 0*/
                Collections.sort(minusList);
                int minus = 0;
                if (minusList.get(0) > minus) minus = minusList.get(0);
                int result = addup - minus;

                /* write result to i.out  */
                BufferedWriter bw = new BufferedWriter(new FileWriter(outPutFile));
                bw.write(String.valueOf(result));
                bw.close();

            } catch (
                    FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (
                    IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

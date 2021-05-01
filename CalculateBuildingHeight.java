/* SUBHASHREE JAYACHANDRAN (66222414), SREELALITHA BUDIGI (36717336) */
import java.sql.SQLOutput;
import java.util.*;

public class CalculateBuildingHeight 
{
    public List<int[]> getSilhouette(int[][] building_data) 
    {
        if (building_data.length == 0)
        {
            return new LinkedList<int[]>();
        }
        return recursiveContourCalculation(building_data, 0, building_data.length - 1);
    }

    private LinkedList<int[]> recursiveContourCalculation(int[][] building_data, int left, int right) 
    {
        if (left < right) 
        {
            int mid_point = left + (right - left) / 2;
            return mergeBuildings(recursiveContourCalculation(building_data, left, mid_point), recursiveContourCalculation(building_data, mid_point + 1, right));
        } 
        else 
        {
            LinkedList<int[]> contours_list = new LinkedList<int[]>();
            contours_list.add(new int[] { building_data[left][0], building_data[left][1] });
            contours_list.add(new int[] { building_data[left][2], 0 });
            return contours_list;
        }
    }

    private LinkedList<int[]> mergeBuildings(LinkedList<int[]> temp_list_1, LinkedList<int[]> temp_list_2) 
    {
        LinkedList<int[]> contours_list = new LinkedList<int[]>();
        int height_1 = 0, height_2 = 0;
        while (temp_list_1.size() > 0 && temp_list_2.size() > 0) 
        {
            int element = 0, height = 0;
            if (temp_list_1.getFirst()[0] < temp_list_2.getFirst()[0]) 
            {
                element = temp_list_1.getFirst()[0];
                height_1 = temp_list_1.getFirst()[1];
                height = Math.max(height_1, height_2);
                temp_list_1.removeFirst();
            } 
            else if (temp_list_1.getFirst()[0] > temp_list_2.getFirst()[0]) 
            {
                element = temp_list_2.getFirst()[0];
                height_2 = temp_list_2.getFirst()[1];
                height = Math.max(height_1, height_2);
                temp_list_2.removeFirst();
            } 
            else 
            {
                element = temp_list_1.getFirst()[0];
                height_1 = temp_list_1.getFirst()[1];
                height_2 = temp_list_2.getFirst()[1];
                height = Math.max(height_1, height_2);
                temp_list_1.removeFirst();
                temp_list_2.removeFirst();
            }
            if (contours_list.size() == 0 || height != contours_list.getLast()[1]) 
            {
                contours_list.add(new int[] { element, height });
            }
        }
        contours_list.addAll(temp_list_1);
        contours_list.addAll(temp_list_2);
        return contours_list;
    }

    private int[][] generateRandomPoints(int limit)
    {
        Random rand_func = new Random();
        int[][] building_points = new int[limit][3];
        for(int i = 0; i < limit; i++)
        {
            for(int j = 2; j >= 0; j--)
            {
                if(j == 0)
                {
                    building_points[i][j] = rand_func.nextInt(building_points[i][2]);
                    if(building_points[i][j] == 0) 
                    {
                        building_points[i][j] += 1;
                    }
                }
                else
                {
                    building_points[i][j] = rand_func.nextInt(99);
                    building_points[i][j] += 2;
                }
            }
        }
        return building_points;
    }
    
    public static void main(String[] args)
    {
        CalculateBuildingHeight obj = new CalculateBuildingHeight();

        //Testing on given data-points
        /*int[][] buildings = {{1,5,11},{2,7,6},{3,19,13},{3,25,14},{4,28,24},{7,16,12},{13,29,23},{18,22,19}};
        List<int[]> output = obj.getSilhouette(buildings);
        for(int[] list_items : output)
        {
            for(int values : list_items)
            {
                System.out.println(values);
            }
        }*/

        //int[][] points =obj.generateRandomPoints(10);
        //int[][] points =obj.generateRandomPoints(100);
        //int[][] points =obj.generateRandomPoints(1000);
        //int[][] points =obj.generateRandomPoints(10000);
        int[][] points =obj.generateRandomPoints(100000);
        System.out.println("INPUT POINTS");
        for(int[] temp_holder: points)
        {
            System.out.println("("+temp_holder[0]+","+temp_holder[1]+","+temp_holder[2]+")");
        }
        System.out.println("OUTPUT POINTS");
        long startTime = new Date().getTime();
        List<int[]> output = obj.getSilhouette(points);
        long endTime = new Date().getTime();
        long timeElapsed = endTime - startTime;
        for(int[] list_items: output)
        {
            System.out.println("("+list_items[0]+","+list_items[1]+")");
        }
        System.out.println("Execution time in milliseconds: " + timeElapsed);
    }
}
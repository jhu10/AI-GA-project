import java.util.Random;
import java.util.ArrayList;

public class Generate {

    /**
     *��ӡ��ά���飬�������� 
     */
    public static void printArray(int a[][])
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" "+a[i][j]);
                if (0==((j+1)%3)) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            if(0==((i+1)%3))
            {
                System.out.println();
            }
        }
    }

    /**
     * ����һ��1-9�Ĳ��ظ�����Ϊ9��һά���� 
     */
    public static ArrayList<Integer> creatNineRondomArray()
    {
        ArrayList <Integer>list = new ArrayList<Integer>();
        Random random=new Random();
        for (int i = 0; i < 9; i++) {
            int randomNum=random.nextInt(9)+1;
            while (true) {
                if (!list.contains(randomNum)) {
                    list.add(randomNum);
                    break;
                }
                randomNum=random.nextInt(9)+1;
            }

        } 
        System.out.println("Array Generated:");
        for (Integer integer : list) {
            System.out.print(" "+integer.toString());
        }
        System.out.println();
        return list;
    }


    /**
     *ͨ��һά�����ԭ���������������������
     *
     *������ά����������ݣ���һά�����ҵ���ǰֵ��λ�ã�����һά����
     *��ǰλ�ü�һ��λ�õ�ֵ������ǰ��ά�����С�Ŀ�ľ��ǽ�һά����Ϊ
     *���ݣ��������������˳�򣬽����9�����ݽ���ѭ������������һ����
     *������������
     *
     */
    public static void creatSudokuArray(int[][]seedArray,ArrayList<Integer> randomList)
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if(seedArray[i][j]==randomList.get(k))
                    {
                        seedArray[i][j]=randomList.get((k+1)%9);
                        break;
                    }
                }
            }
        }
        System.out.println("Randomized matrix:");
        Generate.printArray(seedArray);
    }
    public static void creatSudokuQuestion(int [][] a)
    {
        Random rand=new Random();
        for(int i=0;i<9;i++){
            for(int j=0;j<4;j++){
            a[i][(int)rand.nextInt(9)]=0;
            }
        }
        Generate.printArray(a);
    }

    //
    public static void main(String[] args) {
        int seedArray[][]={
                {9,7,8,3,1,2,6,4,5},
                {3,1,2,6,4,5,9,7,8},
                {6,4,5,9,7,8,3,1,2},
                {7,8,9,1,2,3,4,5,6},
                {1,2,3,4,5,6,7,8,9},
                {4,5,6,7,8,9,1,2,3},
                {8,9,7,2,3,1,5,6,4},
                {2,3,1,5,6,4,8,9,7},
                {5,6,4,8,9,7,2,3,1}
        };      
        System.out.println("Initial matrix:");
        Generate.printArray(seedArray);
        ArrayList<Integer> randomList=Generate.creatNineRondomArray();
        Generate.creatSudokuArray(seedArray, randomList);
        System.out.println("Sudoku Generated:");
        Generate.creatSudokuQuestion(seedArray);
    }
}


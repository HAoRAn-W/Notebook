import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.tree.TreeNode;

public class IOTemplate {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // ==========================多行输入=========================
        // 第一行三个整数n,r,avg(n大于等于1小于等于1e5，r大于等于1小于等于1e9,avg大于等于1小于等于1e6)，
        // 接下来n行，每行两个整数ai和bi，均小于等于1e6大于等于1
        // 示例1
        // 输入
        // 5 10 9
        // 0 5
        // 9 1
        // 8 1
        // 0 1
        // 9 100
        // Scanner类默认的分隔符就是空格
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int r = sc.nextInt();
            int avg = sc.nextInt();

            int[][] nums = new int[n][2];
            for (int i = 0; i < n; i++) {
                nums[i][0] = sc.nextInt();
                nums[i][1] = sc.nextInt();
            }
        }

        // =====================数组输入=====================
        // 每组数据第一行两个整数n和l（n大于0小于等于1000，l小于等于1000000000大于0）。
        // 第二行有n个整数(均大于等于0小于等于l)，为每盏灯的坐标，多个路灯可以在同一点。
        // 输入
        // 7 15
        // 15 5 3 7 9 14 0
        while (sc.hasNext()) {
            int n = sc.nextInt();
            long l = sc.nextLong();

            long[] nums = new long[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextLong();
            }
        }

        // 输入是一串数字，请将其转换成单链表格式之后，再进行操作
        // 输入描述:
        // 一串数字，用逗号分隔
        // 输入
        // 1,2,3,4,5
        String str = sc.next().toString();
        String[] arr = str.split(",");
        int[] ints = new int[arr.length];
        // 给整数数组赋值
        for (int j = 0; j < ints.length; j++) {
            ints[j] = Integer.parseInt(arr[j]);
        }

        // 输入描述:
        // 第一行两个数n,root，分别表示二叉树有n个节点，第root个节点时二叉树的根
        // 接下来共n行，第i行三个数val_i,left_i,right_i，
        // 分别表示第i个节点的值val是val_i,左儿子left是第left_i个节点，右儿子right是第right_i个节点。
        // 节点0表示空。
        // 1<=n<=100000,保证是合法的二叉树
        // 输入
        // 5 1
        // 5 2 3
        // 1 0 0
        // 3 4 5
        // 4 0 0
        // 6 0 0
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String[] s = reader.readLine().split(" ");
            int n = Integer.parseInt(s[0]);
            int root = Integer.parseInt(s[1]);
            TreeNode[] tree = new TreeNode[n + 1];
            int[][] leaf = new int[n + 1][2];
            for (int i = 1; i <= n; i++) {
                String[] ss = reader.readLine().split(" ");
                int val_i = Integer.parseInt(ss[0]);
                int left_i = Integer.parseInt(ss[1]);
                int right_i = Integer.parseInt(ss[2]);
                leaf[i][0] = left_i;
                leaf[i][1] = right_i;
            }
        } catch (IOException e) {

        }

    }
}

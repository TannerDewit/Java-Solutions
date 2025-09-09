/* Problem: 10 Kinds of People
Link: https://open.kattis.com/problems/10kindsofpeople
Summary: Given a grid of 0s and 1s check to see if a person who can only travel on 0s can reach 
the end from the start point and vice versa for a person who can only travel on 1s, the start points and end points are given.
Movement is allowed up, down, left, or right. I used BFS to check for connectivity, and printed "binary", "decimal", or "neither" for each query.
Binary is printed if a person who walks on only 0s can reach the end point from the start point. Decimal is printed if a person who can only walk on 1s can reach
the end point from the start point. Neither is printed if neither type of person can reach the end point from the start point*/
import java.util.Scanner;
public class kindsofpeople{
	public static void main(String[] args) {
    Scanner s=new Scanner(System.in);
    int rows= s.nextInt();
    int cols= s.nextInt();
    int m[][]= new int[rows][cols];//exploration matrix
    s.nextLine();
    for(int i=0;i<rows;i++){
    	String l=s.nextLine();
        for(int j=0;j<cols;j++) {
        m[i][j]=l.charAt(j)-'0';
      }
    }
 
    int n= s.nextInt();
    int c[][]= new int[n][6];//coordinates
    for(int i=0;i<n;i++){
        s.nextLine();
        c[i][0]=s.nextInt()-1;
        c[i][1]=s.nextInt()-1;
        c[i][2]=s.nextInt()-1;
        c[i][3]=s.nextInt()-1;
        int flag=-1;
        boolean done=false;
        int startRow=c[i][0];
        int startCol=c[i][1];
        int endRow=c[i][2];
        int endCol=c[i][3];
            if(m[startRow][startCol]==1&&m[endRow][endCol]==1){
                c[i][4]=0;
                c[i][5]=1;
                flag=1;
            }
            else if(m[startRow][startCol]==0&&m[endRow][endCol]==0){
                c[i][4]=1;
                c[i][5]=0;
                flag=0;
            }
            else{
                c[i][4]=0;
                c[i][5]=0;
                System.out.println("neither");
                continue;
            }
              done=connect(m,startRow,startCol, endRow, endCol);
                      if(done==true){
                          if(flag==1){
                              System.out.println("decimal");
                          }else{
                              System.out.println("binary");
                          }
                          }else{
                              System.out.println("neither");
                          }
}
        
    }
	public static boolean connect(int[][] grid, int r1, int c1, int r2, int c2) {
	    int rows = grid.length;
	    int cols = grid[0].length;
	    boolean[][] visited = new boolean[rows][cols];

	    int[] dx = {-1, 1, 0, 0};
	    int[] dy = {0, 0, -1, 1};
	    int[][] queue = new int[rows * cols][2];
	    int front = 0;
	    int rear = 0;
	    int num = grid[r1][c1];
	    
	    queue[rear][0] = r1;
	    queue[rear][1] = c1;
	    rear++;
	    visited[r1][c1] = true;


	    while (front < rear) {
	        int x = queue[front][0];
	        int y = queue[front][1];
	        front++;

	        if (x == r2 && y == c2) {
	            return true;
	        }

	 
	        for (int d = 0; d < 4; d++) {
	            int nx = x + dx[d];
	            int ny = y + dy[d];

	        
	            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {

	                if (!visited[nx][ny] && grid[nx][ny] == num) {
	                    visited[nx][ny] = true;

	                    queue[rear][0] = nx;
	                    queue[rear][1] = ny;
	                    rear++;
	                }
	            }
	        }
	    }

	    return false;
	}}


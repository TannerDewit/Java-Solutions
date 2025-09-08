/* Problem: A Rank Problem
Link: https://open.kattis.com/problems/rankproblem
Summary: Maintain rankings of teams based on match outcomes.
If a lower-ranked team beats a higher-ranked one, move it just above the loser.
Otherwise, keep rankings unchanged.
Process each match in order and output the final ranking.*/


import java.util.Scanner;
public class rankproblem {
public static void main(String[] args) {
	Scanner s=new Scanner(System.in);
	int tc=s.nextInt();
	int gc=s.nextInt();
	s.nextLine();
	int[] teams=new int[tc];
	for(int j=0;j<tc;j++) {
		teams[j]=j+1;
	}
	for(int i=0;i<gc;i++) {
		String game=s.nextLine();
		Scanner gs=new Scanner(game);
		String win=gs.next();
		String lose=gs.next();
		int w=Integer.parseInt(win.substring(1));
		int l=Integer.parseInt(lose.substring(1));
		if(indexOf(w,teams)<indexOf(l,teams)) {
			continue;
		}
		int p=indexOf(w,teams);
		int h=0;
		int index=indexOf(l,teams);
		while((h+index)!=p) {
		teams[index+h]=teams[index+h+1];
		h++;}
		teams[h+index]=l;
	}
	for(int y=0;y<tc;y++) {
		System.out.print("T"+teams[y]);
		if(y!=tc-1) {
			System.out.print(" ");
		}
	}
	s.close();
}
public static int indexOf(int num,int[] list) {
	int pos=-1;
	for(int i=0;i<list.length;i++) {
		if(list[i]==num) {
			pos=i;
			break;
		}
	}
	return pos;
}
}

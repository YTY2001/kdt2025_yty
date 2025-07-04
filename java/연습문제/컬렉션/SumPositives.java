import java.util.*;

public class SumPositives {
    private Vector<Integer> v = new Vector<Integer>();

    public void read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("정수를 입력하세요 (0 입력 시 종료):");

        while (true) {
            System.out.print("입력: ");
            int num = scanner.nextInt();
            if (num == 0) break;
            v.add(num);
        }
    }

    public void changeToZero() {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i) < 0) {
                v.set(i, 0);  // 음수를 0으로 수정
            }
        }
    }

    public void showAll() {
        System.out.print("벡터의 모든 값: ");
        for (int n : v) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public int add() {
        int sum = 0;
        for (int n : v) {
            if (n > 0) {
                sum += n;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        SumPositives sp = new SumPositives();
        sp.read();
        sp.changeToZero();
        sp.showAll();
        System.out.println("양수들의 합은 " + sp.add());
    }
}
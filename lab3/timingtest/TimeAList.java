package timingtest;
/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int[] tmp = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        for (int i = 0; i < tmp.length; i++) {
            Ns.addLast(tmp[i]);
            AList<Integer> test = new AList<>();
            double startTime = System.currentTimeMillis();  // 获取开始时间
            for (int j = 0; j < tmp[i]; j++) {
                test.addLast(j);
            }
            double endTime = System.currentTimeMillis();    // 获取结束时间
            double duration = endTime - startTime;          // 计算持续时间（单位：毫秒）
            times.addLast(duration / 1000);
            opCounts.addLast(tmp[i]);
        }
        printTimingTable(Ns, times, opCounts);
    }
}

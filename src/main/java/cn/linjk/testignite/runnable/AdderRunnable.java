package cn.linjk.testignite.runnable;

import org.apache.ignite.lang.IgniteRunnable;

public class AdderRunnable implements IgniteRunnable {
    private final int first;
    private final int second;

    public AdderRunnable(int first, int second) {
        super();
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        System.out.println(String.format("IgniteRunnable Adder adding %s and %s, the result = %s", first, second, (first + second)));
    }
}

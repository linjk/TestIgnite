package cn.linjk.testignite.runnable;

import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteRunnable;

public class AdderCallable implements IgniteCallable<Integer> {
    private final int first;
    private final int second;

    public AdderCallable(int first, int second) {
        super();
        this.first = first;
        this.second = second;
    }

    @Override
    public Integer call() {
        Integer res = first + second;
        System.out.println(String.format("AdderCallable Adder adding %s and %s, the result = %s", first, second, res));
        return res;
    }
}

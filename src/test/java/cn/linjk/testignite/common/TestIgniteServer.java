package cn.linjk.testignite.common;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.stream.IntStream;

public class TestIgniteServer {
    public static void main(String[] args) {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(false);
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        try (Ignite ignite = Ignition.start(igniteConfiguration)) {
            IgniteCache<String, Integer> cache = ignite.getOrCreateCache("server-cache");
            IntStream.range(0, 5).forEach(i -> {
                cache.put(String.valueOf(i), i);
            });

            IntStream.range(0, 5).forEach(i -> {
                System.out.println(cache.get(String.valueOf(i)));
            });
        }
    }
}

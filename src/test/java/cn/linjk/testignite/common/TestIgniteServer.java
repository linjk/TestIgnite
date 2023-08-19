package cn.linjk.testignite.common;

import cn.linjk.testignite.bean.Player;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.Test;

import javax.cache.Cache;

public class TestIgniteServer {
    public static void main(String[] args) {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(false);
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        try (Ignite ignite = Ignition.start(igniteConfiguration)) {
            IgniteCache<Integer, Player> cache = ignite.getOrCreateCache("server-cache");
            int id = 1;

            cache.put(id, new Player(id++, "Leo Messi", 9966));
            cache.put(id, new Player(id++, "Christiano Ronaldo", 1111));
            cache.put(id, new Player(id++, "Paul Pogba", 2222));
            cache.put(id, new Player(id++, "Neymar", 3333));
            cache.put(id, new Player(id++, "Luis Suarez", 4444));

            System.out.println("Leo player");

            QueryCursor<Cache.Entry<Integer, Player>> cursor = cache.query(new ScanQuery<Integer, Player>((i, p) -> p.getWages() > 3333));

            cursor.forEach(e -> {
                System.out.println(e.getValue());
            });
        }
    }

    @Test
    public void testTextQuery() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(false);
        igniteConfiguration.setPeerClassLoadingEnabled(true);

        CacheConfiguration<Integer, Player> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName("text-cache");
        cacheConfiguration.setIndexedTypes(Integer.class, Player.class);

        igniteConfiguration.setCacheConfiguration(cacheConfiguration);

        try (Ignite ignite = Ignition.start(igniteConfiguration)) {
            IgniteCache<Integer, Player> cache = ignite.getOrCreateCache("text-cache");
            int id = 1;

            cache.put(id, new Player(id++, "Leo Messi", 9966));
            cache.put(id, new Player(id++, "Christiano Ronaldo", 1111));
            cache.put(id, new Player(id++, "Paul Pogba", 2222));
            cache.put(id, new Player(id++, "Neymar", 3333));
            cache.put(id, new Player(id++, "Luis Suarez", 4444));

            System.out.println("Leo player");

            TextQuery<Integer, Player> txt = new TextQuery<Integer, Player>(Player.class, "Neymar");
            QueryCursor<Cache.Entry<Integer, Player>> cursor = cache.query(txt);

            cursor.forEach(e -> {
                System.out.println(e.getValue());
            });
        }
    }
}

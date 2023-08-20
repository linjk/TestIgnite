package cn.linjk.testignite.common;

import cn.linjk.testignite.bean.Country;
import cn.linjk.testignite.bean.Player;
import cn.linjk.testignite.runnable.AdderRunnable;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.*;
import org.apache.ignite.cache.query.annotations.QuerySqlFunction;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteFuture;
import org.junit.jupiter.api.Test;

import javax.cache.Cache;
import java.util.Collection;
import java.util.List;

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

    @Test
    public void testSqlFunction() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setPeerClassLoadingEnabled(true);

        CacheConfiguration<Long, Country> funcCacheConf = new CacheConfiguration<>();
        funcCacheConf.setName("country_cache");
        funcCacheConf.setIndexedTypes(Long.class, Country.class);
        funcCacheConf.setCacheMode(CacheMode.REPLICATED);
        funcCacheConf.setSqlFunctionClasses(TestIgniteServer.class);

        cfg.setCacheConfiguration(funcCacheConf);

        try (Ignite ignite = Ignition.start(cfg)) {
            IgniteCache<Long, Country> funcCache = Ignition.ignite().getOrCreateCache("country_cache");
            long id = 1;
            funcCache.put(id, new Country(id++, "USA", 123));
            funcCache.put(id, new Country(id++, "India", 456));
            funcCache.put(id, new Country(id++, "France", 789));
            funcCache.put(id, new Country(id++, "England", 101112));

            SqlFieldsQuery fieldsQuery = new SqlFieldsQuery(
                    "select id, name from \"country_cache\".Country where myUpperCache(name) = upper(?) "
            );

            FieldsQueryCursor<List<?>> result = funcCache.query(fieldsQuery.setArgs("iNdia"));
            result.forEach(r -> {
                System.out.println("id=" + r.get(0) + " country = " + r.get(1));
            });
        }
    }

    @Test
    public void testBroadcastAsync() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setPeerClassLoadingEnabled(true);

        try (Ignite ignite =  Ignition.start(cfg)) {
            // get a compute task
            IgniteCompute compute = ignite.compute();

            // broadcast the computation to all nodes
            IgniteCallable<String> callable = new IgniteCallable<String>() {
                private static final long serialVersionUID = 1L;
                @Override
                public String call() throws Exception {
                    System.out.println("Executing in a cluster ...");
                    return String.valueOf(System.currentTimeMillis());
                }
            };

            // broadcast async and get a future
            IgniteFuture<Collection<String>> asyncFuture = compute.broadcastAsync(callable);
            while (!asyncFuture.isDone()) {
//                System.out.println("wait for response");
            }

            // get response from all nodes
            asyncFuture.get().forEach(result -> {
                System.out.println(result);
            });
        }
    }

    @Test
    public void testIgniteRunnable() {
        IgniteConfiguration configuration = new IgniteConfiguration();
        configuration.setPeerClassLoadingEnabled(true);

        try (Ignite ignite = Ignition.start(configuration)) {
            IgniteCompute compute = ignite.compute();
//            compute.run(new AdderRunnable(1, 2));

            IgniteFuture<Void> runAsync = compute.runAsync(new AdderRunnable(1, 2));
            while (!runAsync.isDone()) {
                //
            }
            System.out.println("Job Done.");
        }
    }

    /**
     * 自定义sql查询函数(必须public static)
     */
    @QuerySqlFunction
    public static String myUpperCache(String name) {
        String upperCache = name.toUpperCase();
        return upperCache;
    }
}

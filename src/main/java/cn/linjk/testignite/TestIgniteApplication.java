package cn.linjk.testignite;

import cn.linjk.testignite.bean.Club;
import cn.linjk.testignite.bean.Player;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestIgniteApplication {

	public static void main(String[] args) {
		try {
			IgniteConfiguration cfg = new IgniteConfiguration();
			cfg.setIgniteInstanceName("MyGrid121");

			Ignite ignite = Ignition.start(cfg);
			ignite.getOrCreateCache("org.hibernate.cache.spi.UpdateTimestampsCache");
			ignite.getOrCreateCache("org.hibernate.cache.internal.StandardQueryCache");

			CacheConfiguration<Integer, Club> clubConfig = new CacheConfiguration<Integer, Club>();
			clubConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			clubConfig.setIndexedTypes(Integer.class, Club.class);
			clubConfig.setName("cn.linjk.testignite.bean.Club");
			ignite.getOrCreateCache(clubConfig);

			CacheConfiguration<Integer, Player> playerConfig = new CacheConfiguration<Integer, Player>();
			playerConfig.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			playerConfig.setIndexedTypes(Integer.class, Player.class);
			playerConfig.setName("cn.linjk.testignite.bean.Player");
			ignite.getOrCreateCache(playerConfig);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(TestIgniteApplication.class, args);
	}
}

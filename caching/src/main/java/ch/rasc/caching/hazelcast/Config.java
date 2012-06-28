package ch.rasc.caching.hazelcast;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.Join;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.impl.FactoryImpl;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
@ComponentScan(basePackages = { "ch.rasc.caching.hazelcast" })
@EnableCaching
public class Config {

	@Bean
	public HazelcastInstance hazelcastInstance() {
		// return Hazelcast.getDefaultInstance();

		com.hazelcast.config.Config hazelcastConfig = new com.hazelcast.config.Config();
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setName("user");
		groupConfig.setPassword("password");
		hazelcastConfig.setGroupConfig(groupConfig);

		NetworkConfig networkConfig = new NetworkConfig();
		Join join = new Join();

		MulticastConfig multicastConfig = new MulticastConfig();
		multicastConfig.setEnabled(false);
		join.setMulticastConfig(multicastConfig);

		TcpIpConfig tcpIpConfig = new TcpIpConfig();
		tcpIpConfig.setEnabled(true);
		tcpIpConfig.setMembers(Lists.newArrayList("192.168.20.153", "192.168.20.150"));
		join.setTcpIpConfig(tcpIpConfig);

		networkConfig.setJoin(join);
		hazelcastConfig.setNetworkConfig(networkConfig);

		return FactoryImpl.newHazelcastInstanceProxy(hazelcastConfig);
	}

	@Bean
	public CacheManager cacheManager() {
		return new HazelcastCacheManager(hazelcastInstance());
	}

}

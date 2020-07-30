package com.zxelec.yhkk.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.zxelec.yhkk.bean.CustomProperties;



/**
 * 全局扫描控制类
 * 
 * @author liu.yongquan
 *
 */
@Configuration
//启用springboot job任务
@EnableAsync
//启用kafka
@EnableKafka
//启用securitye
@EnableWebSecurity
public class YhkkConfig  extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LogManager.getLogger(YhkkConfig.class);
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	@Autowired
	private CustomProperties customProperties;


	@Bean("asyncServiceExecutor")
	public ThreadPoolTaskExecutor asyncServiceExecutor() {
		logger.debug("sta@Beanrt asyncServiceExecutor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 配置核心线程数
		executor.setCorePoolSize(customProperties.getYhkkCorePoolSize());
		// 配置最大线程数
		executor.setMaxPoolSize(customProperties.getYhkkMaxPoolSize());
		// 配置队列大小
		executor.setQueueCapacity(customProperties.getYhkkQueueCapacity());
		// 配置线程池中的线程的名称前缀
		executor.setThreadNamePrefix("async-service-");
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setKeepAliveSeconds(customProperties.getYhkkKeepAlive());
		// 执行初始化
		executor.initialize();
		return executor;
	}

	/**
	 * kafka消费者配置
	 * 
	 * @return
	 */
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> propsMap = kafkaProperties.buildConsumerProperties();
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
		return propsMap;
	}

	/**
	 * kafka消费者配置
	 * 
	 * @return
	 */
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	/**
	 * kafka消费者配置
	 * 
	 * @return
	 */
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(customProperties.getKafkaConcurrency());
		factory.setBatchListener(true);// 批量消费
		factory.setAutoStartup(false);// 不自动启动监听
		factory.getContainerProperties().setPollTimeout(1500);
		return factory;
	}

	/**
	 * 生产者
	 * 
	 * @return
	 */
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = kafkaProperties.buildProducerProperties();
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
		return props;
	}

	/**
	 * 生产者
	 * 
	 * @return
	 */
	@Bean
	public ProducerFactory<Object, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/**
	 * 生产者
	 * 
	 * @return
	 */
	@Bean
	public KafkaTemplate<Object, Object> kafkaTemplate() {
		KafkaTemplate<Object, Object> ka = new KafkaTemplate<>(producerFactory());
		return ka;
	}
	

	/**
	 * digest 认证
	 */
	protected void configure(HttpSecurity http) throws Exception {
		// 将cpug自己本身对外免认证接口排除
		String[] matchersArrays = new String[] { "/yhkk/**", "/yhkk/**/**","/VIID/Triggers/Subscribes/**/**"};
		http.authorizeRequests().antMatchers(matchersArrays).permitAll().and().csrf().disable().authorizeRequests()
				.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(digestEntryPoint())// 摘要认证入口端点
				.and().addFilter(digestFilter());// 在过滤链中添加摘要认证过滤器
	}

	@Bean
	public DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
		entryPoint.setKey("YHKK");
		entryPoint.setRealmName("YHKK Digest Authentication");
		entryPoint.setNonceValiditySeconds(14400);
		return entryPoint;
	}

	@Bean
	public DigestAuthenticationFilter digestFilter() throws Exception {
		DigestAuthenticationFilter digestFilter = new DigestAuthenticationFilter();
		digestFilter.setAuthenticationEntryPoint(digestEntryPoint());// 必须配置
		digestFilter.setUserDetailsService(userDetailsService());
		return digestFilter;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				HigherPlatformRsp hpr = hebatCache.getDigestCmsMap(serverProperties.getCpugDeviceId());
				String password = null;
				if (username.equals(customProperties.getYhkkDigestUsername())) {
					password = customProperties.getYhkkDigestPasswd();
				}
				if (password == null) {
					throw new UsernameNotFoundException("用户" + username + " 不存在!!!");
				}
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("auth"));
				return new User(username, password, true, true, true, true, authorities);
			}
		};
	}
}

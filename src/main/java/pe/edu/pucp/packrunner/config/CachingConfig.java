package pe.edu.pucp.packrunner.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    List<Cache> caches = new ArrayList<Cache>();

    // Cada vez que se usa un nuevo value, se actualiza esta zona
    caches.add(new ConcurrentMapCache("truckPlans"));
    caches.add(new ConcurrentMapCache("vertex"));
    caches.add(new ConcurrentMapCache("edges"));

    // Se setea
    cacheManager.setCaches(caches);
    return cacheManager;
  }

}

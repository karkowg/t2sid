package util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.ShardedConfiguration;
import org.hibernate.shards.cfg.ConfigurationToShardConfigurationAdapter;
import org.hibernate.shards.cfg.ShardConfiguration;
import org.hibernate.shards.loadbalance.RoundRobinShardLoadBalancer;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.SequentialShardAccessStrategy;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.AllShardsShardResolutionStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.RoundRobinShardSelectionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author GUSTAVO
 */
public class HibernateUtil {

    //private static final SessionFactory sessionFactory;
    private SessionFactory sessionFactory;
    
    public HibernateUtil() {
        try {
            sessionFactory = buildSession();
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    private SessionFactory buildSession() {
        Configuration prototypeConfig = new Configuration().configure("shard0.hibernate.cfg.xml");
        prototypeConfig.addResource("model/Championship.hbm.xml");
        prototypeConfig.addResource("model/Game.hbm.xml");
        prototypeConfig.addResource("model/Person.hbm.xml");
        prototypeConfig.addResource("model/Stage.hbm.xml");
        prototypeConfig.addResource("model/Team.hbm.xml");
        List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
        shardConfigs.add(buildShardConfig("shard0.hibernate.cfg.xml"));
        shardConfigs.add(buildShardConfig("shard1.hibernate.cfg.xml"));
        ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
        ShardedConfiguration shardedConfig = new ShardedConfiguration(
                prototypeConfig,
                shardConfigs,
                shardStrategyFactory);
        return shardedConfig.buildShardedSessionFactory();
    }
    
    private ShardStrategyFactory buildShardStrategyFactory() {
        ShardStrategyFactory shardStrategyFactory = new ShardStrategyFactory() {
            @Override
            public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
                RoundRobinShardLoadBalancer loadBalancer = new RoundRobinShardLoadBalancer(shardIds);
                ShardSelectionStrategy pss = new RoundRobinShardSelectionStrategy(loadBalancer);
                ShardResolutionStrategy prs = new AllShardsShardResolutionStrategy(shardIds);
                ShardAccessStrategy pas = new SequentialShardAccessStrategy();
                return new ShardStrategyImpl(pss, prs, pas);
            }
        };
        return shardStrategyFactory;
    }
    
    private ShardConfiguration buildShardConfig(String configFile) {
        Configuration config = new Configuration().configure(configFile);
        return new ConfigurationToShardConfigurationAdapter(config);
    }
}

package net.sf.gilead.core.beanlib.merge;

import net.sf.beanlib.provider.BeanPopulator;
import net.sf.beanlib.provider.collector.PrivateSetterMethodCollector;
import net.sf.beanlib.provider.finder.PrivateReaderMethodFinder;
import net.sf.beanlib.spi.BeanTransformerSpi;
import net.sf.beanlib.spi.CustomBeanTransformerSpi;
import net.sf.beanlib.spi.DetailedBeanPopulatable;
import net.sf.gilead.core.IPersistenceUtil;
import net.sf.gilead.core.beanlib.IClassMapper;
import net.sf.gilead.core.beanlib.transformer.CustomTransformersFactory;
import net.sf.gilead.core.store.IProxyStore;

/**
 * Bean populator for merge operation
 * @author bruno.marchesson
 *
 */
public class MergeBeanPopulator
{
	/**
	 * Create a new populator for merge operation
	 * @param from the source class
	 * @param to the target class
	 * @param classMapper the associated class mapper
	 * @return the created populator
	 */
	public static BeanPopulator newBeanPopulator(Object from, Object to, 
												 IClassMapper classMapper,
												 IPersistenceUtil persistenceUtil,
												 IProxyStore proxyStore) 
    {
		BeanPopulator replicator = BeanPopulator.newBeanPopulator(from, to);
		
	//	Change bean class replicator
	//
		BeanTransformerSpi transformer = (BeanTransformerSpi) replicator.getTransformer();
		transformer.initBeanReplicatable(MergeClassBeanReplicator.factory);
		((MergeClassBeanReplicator) transformer.getBeanReplicatable()).setClassMapper(classMapper);
		((MergeClassBeanReplicator) transformer.getBeanReplicatable()).setPersistenceUtil(persistenceUtil);
		((MergeClassBeanReplicator) transformer.getBeanReplicatable()).setProxyStore(proxyStore);
		
		transformer.initCollectionReplicatable(MergeCollectionReplicator.factory);
		((MergeCollectionReplicator) transformer.getCollectionReplicatable()).setPersistenceUtil(persistenceUtil);
		
		transformer.initMapReplicatable(MergeMapReplicator.factory);
		((MergeMapReplicator) transformer.getMapReplicatable()).setPersistenceUtil(persistenceUtil);
		
	//	Custom transformers (timestamp handling)
	//
        transformer.initCustomTransformerFactory(new CustomBeanTransformerSpi.Factory()
		{
			public CustomBeanTransformerSpi newCustomBeanTransformer(final BeanTransformerSpi beanTransformer)
			{
				return CustomTransformersFactory.getInstance().createUnionCustomBeanTransformer(beanTransformer);
			}
		});

		
	//	Lazy properties handling
	//
		DetailedBeanPopulatable hibernatePopulatable = new MergeBeanPopulatable(persistenceUtil, proxyStore);
		replicator.initDetailedBeanPopulatable(hibernatePopulatable);
		
	//	Merge based on protected and private setters
	//
		replicator.initSetterMethodCollector(PrivateSetterMethodCollector.inst);
		replicator.initReaderMethodFinder(PrivateReaderMethodFinder.inst);
		
		return replicator;
    }

}

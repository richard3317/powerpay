package com.icpay.payment.db.service.exporter;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;

import com.icpay.payment.common.exception.BizzException;
import com.icpay.payment.common.exception.I18nBizException;
import com.icpay.payment.common.exception.I18nMsgSpec;

public class DBServiceScanner implements BeanFactoryPostProcessor,
		InitializingBean, ApplicationContextAware {
	
	private static final Logger logger = Logger.getLogger(DBServiceScanner.class);

	private ApplicationContext applicationContext;
	private String servicePackage;

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try {
			logger.info("自动发布DBService开始: " + servicePackage);
			Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
	        scanner.setResourceLoader(this.applicationContext);
	        scanner.scan(servicePackage);
	        Set<BeanDefinitionHolder> beanDefinitions = scanner.getBeanDefinitions();
	        if (beanDefinitions.size() == 0) {
	        	logger.info("未找到需发布的Service");
	        } else {
	        	for (BeanDefinitionHolder holder : beanDefinitions) {
	        		logger.info(holder.getBeanName() + "-" + 
	        				holder.getBeanDefinition().getPropertyValues().getPropertyValue("service").getValue());
	        	}
	        }
	        logger.info("自动发布DBService完成: " + servicePackage);
		} catch (Exception e) {
			 logger.error("自动发布DBService失败: " + servicePackage, e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	private final class Scanner extends ClassPathBeanDefinitionScanner {
		
        private BeanNameGenerator beanNameGenerator = new  AnnotationBeanNameGenerator();

        private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
        private BeanDefinitionRegistry registry;
        private Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<BeanDefinitionHolder>();

        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
            this.registry = registry;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            for (String basePackage : basePackages) {
                try {
                	Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
                	for (BeanDefinition candidate : candidates) {
                        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
                        candidate.setScope(scopeMetadata.getScopeName());
                        String originalBeanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
                        ScannedGenericBeanDefinition bd = (ScannedGenericBeanDefinition) candidate;
                        bd.setBeanClassName(DBServiceExporter.class.getName());
                        bd.setBeanClass(DBServiceExporter.class);
                        bd.getPropertyValues().add("service", applicationContext.getBean(originalBeanName));
                        String[] interfaces = bd.getMetadata().getInterfaceNames();
                        if (interfaces == null || interfaces.length == 0) {
                        	logger.info("no interfaces:" + originalBeanName);
                        	continue;
                        }
                        Class interf = null;
                        try {
                            interf = Class.forName(interfaces[0]);
                        } catch (ClassNotFoundException e) {
                            continue;
                        }
                        bd.getPropertyValues().add("serviceInterface", interf);
                        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, "/" +   interf.getSimpleName());
                        definitionHolder = applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
                        beanDefinitions.add(definitionHolder);
                        registerBeanDefinition(definitionHolder, this.registry);
                    }
                } catch (Exception e) {
                	//throw new BizzException("自动发布DBService失败:" + basePackage, e);
                	throw new I18nBizException(new I18nMsgSpec("zh_CN","DBServiceScanner",null, "自动发布DBService失败: %s",basePackage));
                }
            }
            return beanDefinitions;
        }

        @Override
        protected void registerDefaultFilters() {
        	super.registerDefaultFilters();
        }

        BeanDefinitionHolder applyScopedProxyMode(
                ScopeMetadata metadata, BeanDefinitionHolder definition,
                BeanDefinitionRegistry registry) {

            ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
            if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
                return definition;
            }
            boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
            return ScopedProxyUtils.createScopedProxy(definition, registry, proxyTargetClass);
        }

		public Set<BeanDefinitionHolder> getBeanDefinitions() {
			return beanDefinitions;
		}
    }
}

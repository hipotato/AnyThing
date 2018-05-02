package org.potato.AnyThing.phoenix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by potato on 2018/5/2.
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {
}

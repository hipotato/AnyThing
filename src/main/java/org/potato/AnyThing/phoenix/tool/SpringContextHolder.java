/*
 * Copyright (c) 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * potatoations under the License.
 */

package org.potato.AnyThing.phoenix.tool;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * ---------------------------------------------------------------------------
 * Bean获取工具
 * ---------------------------------------------------------------------------
 * @author: potato
 * @time:2017/1/12 14:54
 * ---------------------------------------------------------------------------
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext ac = null;

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException if thrown by application context methods
     * @see org.springframework.beans.factory.BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    /**
     * 获取Bean
     * @param name bean名称
     * @return
     * @author potato
     */
    public synchronized Object getBean(String name) {
        return ac.getBean(name);
    }

    /**
     * 获取Bean
     * @param name bean名称
     * @return
     * @author potato
     */
    public synchronized static <T> T getBean(String name, Class<T> requiredType) {
        return ac.getBean(name, requiredType);
    }

    /**
     * 根据注解获取Beans
     * @param annotationType
     * @return
     * @author potato
     */
    public synchronized Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return ac.getBeansWithAnnotation(annotationType);
    }

    /**
     * 获取Bean
     * @param requiredType bean类型
     * @return
     * @author potato
     */
    public synchronized static <T> T getBean(Class<T> requiredType) {
        return ac.getBean(requiredType);
    }
}

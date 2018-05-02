package org.potato.AnyThing.phoenix.config.annotation;

import org.potato.AnyThing.phoenix.config.enums.LogType;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * Created by potato on 2018/5/2.
 */
@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {
    /**
     * 操作说明
     *
     * @return
     */
    public String operation();

}

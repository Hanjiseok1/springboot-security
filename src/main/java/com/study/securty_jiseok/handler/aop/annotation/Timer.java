package com.study.securty_jiseok.handler.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
//TYPE은 클래스를 의미함 즉 클래스 앞에 어노테이션을 달수있음


@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Timer {

}

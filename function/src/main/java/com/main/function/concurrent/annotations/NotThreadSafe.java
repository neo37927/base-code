package com.main.function.concurrent.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 应用此批注的类不是线程安全的。
 * 这个注释主要用于澄清类的非线程安全性，否则可能被认为是线程安全的，尽管事实上假设一个类是没有充分理由的线程安全是一个坏主意。
 *
 * @see ThreadSafe
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotThreadSafe {
}

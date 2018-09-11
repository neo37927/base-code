package com.main.function.concurrent.annotations;

import java.lang.annotation.*;

/**
 * <p>应用此注解的类是不可变的。 这意味着调用者无法看到其状态发生变化, 意味着：</p>
 * <ul>
 *     <li> 所有公开属性均为不变的, </li>
 *     <li> 所有公开的不变的引用字段指向其他不可变的对象</li>
 *     <li> 构造函数和方法不会发布对实现可能可变的任何内部状态的引用</li>
 * </ul>
 * <p>为了性能优化，不可变对象可能仍然具有内部可变状态; 一些状态变量可能是延迟计算的，只要它们是从不可变状态计算的，并且调用者无法区分它们。 </p>
 *
 * <p>不可变对象本质上是线程安全的; 它们可以在线程之间传递，也可以在没有同步的情</p>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Immutable {
}

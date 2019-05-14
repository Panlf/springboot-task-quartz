package com.plf.task.quartz.common;

import org.quartz.Job;

/**
 * 涉及到反射的操作工具类
 * @author plf 2019年5月14日上午10:24:32
 *
 */
public class ReflectUtils {
	/**
	 * 根据类名创建对象
	 * @param classname
	 * @return
	 * @throws Exception
	 */
	public static Job getObjectByClass(String classname) throws Exception {
		Class<?> clazz = Class.forName(classname);
		return (Job) clazz.newInstance();
	}
}

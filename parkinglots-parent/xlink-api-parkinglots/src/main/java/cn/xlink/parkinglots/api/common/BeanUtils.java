package cn.xlink.parkinglots.api.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

/**
 * Bean工具，在{@link org.springframework.beans.BeanUtils}基础上扩展<br>
 * 加入动态复制，补充反射的不足
 * 
 * @since 2017-02-23 17:51:04
 * @version $Rev: 1 $
 * @author WuBin
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    private static final Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();
    
    /**
     * 复制属性值，通过反射的方式，支持通过参数指定忽略部分属性
     * 
     * @param source
     *            源对象
     * @param target
     *            目标对象
     * @param ignoreNullProperties
     *            是否忽略源对象属性为空值的情况
     * @param ignoreCollection
     *            是否忽略源对象属性为集合的情况
     * @param ignoreProperties
     *            指定不复制的属性
     * @throws BeansException
     *             异常
     */
    public static void copyProperties(Object source, Object target, boolean ignoreNullProperties,
            boolean ignoreCollection, String... ignoreProperties) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties)
                : null;

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null
                    && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(),
                        targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(
                            writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);

                            // 如果忽略集合，则当检测到源对象属性读取方法返回值类型为集合时跳过
                            if (ignoreCollection && Collection.class
                                    .isAssignableFrom(readMethod.getReturnType())) {
                                continue;
                            }

                            // 如果忽略null属性，则当检测到源对象属性值为null时跳过
                            if (ignoreNullProperties && value == null) {
                                continue;
                            }

                            if (!Modifier
                                    .isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException("Could not copy property '"
                                    + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * BeanCopier属性值复制
     * 
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProps(Object source, Object target) {
        copyProps(source, target, null);
    }
    
    /**
     * BeanCopier属性值复制，目标类型必须支持{@link Class#newInstance()}实例化
     * @param source 源对象
     * @param type 目标类型
     * @return 目标对象
     * @throws IllegalStateException 指定类型无法实例化
     */
    public static <T> T copyProps(Object source, Class<T> type) throws IllegalStateException {
        return copyProps(source, type, null);
    }

    /**
     * BeanCopier属性值复制
     * 
     * @param source 源对象
     * @param target 目标对象
     * @param converter 转换器
     */
    public static void copyProps(Object source, Object target, Converter converter) {
        if (source == null || target == null) {
            return;
        }
        String key = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(key)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(key, copier);
        } else {
            copier = beanCopierMap.get(key);
        }
        copier.copy(source, target, converter);
    }
    
    /**
     * BeanCopier属性值复制，目标类型必须支持{@link Class#newInstance()}实例化
     * @param source 源对象
     * @param type 目标类型
     * @param converter 转换器
     * @return 目标对象
     * @throws IllegalStateException 指定类型无法实例化
     */
    public static <T> T copyProps(Object source, Class<T> type, Converter converter) throws IllegalStateException {
        if (source == null || type == null) {
            return null;
        }
        String key = generateKey(source.getClass(), type);
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(key)) {
            copier = BeanCopier.create(source.getClass(), type, false);
            beanCopierMap.put(key, copier);
        } else {
            copier = beanCopierMap.get(key);
        }
        T t = null;
        try {
            t = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("无法实例化: " + e);
        }
        copier.copy(source, t, converter);
        return t;
    }

    private static String generateKey(Class<?> source, Class<?> target) {
        return source.getName() + target.getName();
    }



    /**
     * 复制列表
     * <p>根据源列表返回指定类型的列表
     * 
     * @param sourceList 源列表
     * @param type 目标类型
     * @return 目标列表
     * @throws IllegalStateException 指定类型无法实例化
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> type)
            throws IllegalStateException {
        List<T> targetList = new ArrayList<>();
        if (sourceList != null && sourceList.size() > 0 && type != null) {
            for (Object source : sourceList) {
                T target;
                try {
                    target = type.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException("无法实例化: " + e);
                }
                copyProps(source, target);
                targetList.add(target);
            }
        }
        return targetList;
    }

    public static List copySets(Set<? extends Object> poSet, Class voClass)
            throws InstantiationException, IllegalAccessException {
        List volist = new ArrayList();
        Object voObj = null;
        if (poSet != null && !poSet.isEmpty()) {
            for (Iterator it = poSet.iterator(); it.hasNext();) {
                voObj = voClass.newInstance();
                Object next = it.next();
                copyProperties(next, voObj);
                volist.add(voObj);
            }
        }
        return volist;
    }
    
    /**
     * 返回对象的字符串表示
     * <p>{@code null}值返回{@code "null"}，否则通过反射获取对象的所有属性值
     * @param obj 对象
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return Objects.toString(obj);
        }
        return ReflectionToStringBuilder.toString(obj);
    }
}

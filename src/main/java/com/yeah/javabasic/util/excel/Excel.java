package com.yeah.javabasic.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  Excel   
 * @Description: 将 Excel 中的每个单元格数据与 vo 中的属性关联起来
 * @author: zouye
 * @date:   2019年12月22日 下午3:01:36   
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /**
     * 对应 Excel 表头名称
     */
    public String name() default "";

    /**
     * 日期格式, 可指定多个
     */
    public String[] dateFormat() default {"yyyy-mm-dd HH:mm:ss"};

    /**
     * 内容转表达式，vo 中保存的值与 Excel 中的值之间需要做转换时使用。
     * 比如 vo 中的 sex 字段取值为 0/1，excel中值为男/女，则 converterExp为 "0=男,1=女"
     */
    public String converterExp() default "";

    /**
     * 导出时在excel中每个列的高度 单位为字符
     */
    public double height() default 14;

    /**
     * 导出时在excel中每个列的宽 单位为字符
     */
    public double width() default 16;

    /**
     * 设置只能选择不能输入的列内容.
     */
    public String[] combo() default {};

    /**
     * vo 中字段类型（0：支持excel导出导入；1：仅在导出时使用；2：仅导入时使用）
     */
    Type type() default Type.ALL;

    public enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}

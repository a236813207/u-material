package com.ken.material.common.query;


import java.io.Serializable;
import java.util.*;

/**
 * 筛选
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class Filter implements Serializable {

    private static final long serialVersionUID = -8712382358441065075L;

    /**
     * 运算符
     */
    public enum Operator {

        /**
         * 范围
         */
        betweenAnd,

        /**
         * 等于
         */
        eq,

        /**
         * 不等于
         */
        ne,

        /**
         * 大于
         */
        gt,

        /**
         * 小于
         */
        lt,

        /**
         * 大于等于
         */
        ge,

        /**
         * 小于等于
         */
        le,

        /**
         * 相似
         */
        like,

        /**
         * 包含
         */
        in,
        /**
         * 不包含
         */
        notIn,

        /**
         * 为Null
         */
        isNull,

        /**
         * 不为Null
         */
        isNotNull;

        /**
         * 从String中获取Operator
         *
         * @param value 值
         * @return String对应的operator
         */
        public static Operator fromString(String value) {
            return Operator.valueOf(value.toLowerCase());
        }
    }

    public enum Connector {
        /**
         * &&
         */
        and,
        /**
         * 或者
         */
        or,;
    }

    /**
     * 默认是否忽略大小写
     */
    private static final boolean DEFAULT_IGNORE_CASE = false;

    /**
     * 属性
     */
    private String property;

    /**
     * 运算符
     */
    private Operator operator;

    /**
     * 连接器
     */
    private Connector connector = Connector.and;

    /**
     * 值
     */
    private Object value;

    /**
     * 多个值
     */
    private Collection<Object> values;

    /**
     * 是否忽略大小写
     */
    private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

    /**
     * 子过滤条件
     */
    private List<Filter> subFilter;

    /**
     * 初始化一个新创建的Filter对象
     */
    public Filter() {
    }

    /**
     * 初始化一个新创建的Filter对象
     *
     * @param property 属性
     * @param operator 运算符
     * @param value    值
     */
    public Filter(String property, Operator operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 初始化一个新创建的Filter对象
     *
     * @param property 属性
     * @param operator 运算符
     * @param values   多个值
     */
    public Filter(String property, Operator operator, Collection<Object> values) {
        this.property = property;
        this.operator = operator;
        this.values = values;
    }

    /**
     * 初始化一个新创建的Filter对象
     *
     * @param connector 连接
     * @param filters 过滤
     */
    public Filter(Connector connector, Filter... filters) {
        this.connector = connector;
        this.subFilter = Arrays.asList(filters);
    }

    /**
     * 初始化一个新创建的Filter对象
     *
     * @param property   属性
     * @param operator   运算符
     * @param value      值
     * @param ignoreCase 忽略大小写
     */
    public Filter(String property, Operator operator, Object value, boolean ignoreCase) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    /**
     * 返回等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 等于筛选
     */
    public static Filter eq(String property, Object value) {
        return new Filter(property, Operator.eq, value);
    }

    /**
     * 返回等于筛选
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 忽略大小写
     * @return 等于筛选
     */
    public static Filter eq(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.eq, value, ignoreCase);
    }

    /**
     * 返回不等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 不等于筛选
     */
    public static Filter ne(String property, Object value) {
        return new Filter(property, Operator.ne, value);
    }

    /**
     * 返回不等于筛选
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 忽略大小写
     * @return 不等于筛选
     */
    public static Filter ne(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.ne, value, ignoreCase);
    }

    /**
     * 返回大于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 大于筛选
     */
    public static Filter gt(String property, Object value) {
        return new Filter(property, Operator.gt, value);
    }

    /**
     * 返回小于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 小于筛选
     */
    public static Filter lt(String property, Object value) {
        return new Filter(property, Operator.lt, value);
    }

    /**
     * 返回大于等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 大于等于筛选
     */
    public static Filter ge(String property, Object value) {
        return new Filter(property, Operator.ge, value);
    }

    /**
     * 返回小于等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 小于等于筛选
     */
    public static Filter le(String property, Object value) {
        return new Filter(property, Operator.le, value);
    }

    /**
     * 返回相似筛选
     *
     * @param property 属性
     * @param value    值
     * @return 相似筛选
     */
    public static Filter like(String property, Object value) {
        return new Filter(property, Operator.like, value);
    }

    /**
     * 返回包含筛选
     *
     * @param property 属性
     * @param values   值
     * @return 包含筛选
     */
    public static Filter in(String property, Collection<Object> values) {
        return new Filter(property, Operator.in, values);
    }

    /**
     * 返回不包含筛选
     *
     * @param property 属性
     * @param values   值
     * @return 不包含筛选
     */
    public static Filter notIn(String property, Collection<Object> values) {
        return new Filter(property, Operator.notIn, values);
    }

    /**
     * 范围
     *
     * @param property 属性
     * @param values   值
     * @return 范围
     */
    public static Filter betweenAnd(String property, Collection<Object> values) {
        return new Filter(property, Operator.betweenAnd, values);
    }

    /**
     * 返回为Null筛选
     *
     * @param property 属性
     * @return 为Null筛选
     */
    public static Filter isNull(String property) {
        return new Filter(property, Operator.isNull, null);
    }

    /**
     * 返回不为Null筛选
     *
     * @param property 属性
     * @return 不为Null筛选
     */
    public static Filter isNotNull(String property) {
        return new Filter(property, Operator.isNotNull, null);
    }

    /**
     * 多个or 条件筛选
     *
     * @param subFilters
     * @return
     */
    public static Filter andContinuousOr(Filter... subFilters) {
        return new Filter(Connector.or, subFilters);
    }

    /**
     * 返回忽略大小写筛选
     *
     * @return 忽略大小写筛选
     */
    public Filter ignoreCase() {
        this.ignoreCase = true;
        return this;
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public String getProperty() {
        return property;
    }

    /**
     * 设置属性
     *
     * @param property 属性
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 获取运算符
     *
     * @return 运算符
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * 设置运算符
     *
     * @param operator 运算符
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取是否忽略大小写
     *
     * @return 是否忽略大小写
     */
    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    /**
     * 设置是否忽略大小写
     *
     * @param ignoreCase 是否忽略大小写
     */
    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * 处理like模糊查询value
     */
    public static class LikeValue {
        public static String both(String value) {
            return place(value, true, true);
        }

        public static String pre(String value) {
            return place(value, true, false);
        }

        public static String post(String value) {
            return place(value, false, true);
        }

        private static String place(String value, boolean pre, boolean post) {
            if (value == null || "".equals(value.trim())) {
                return null;
            }
            return (pre ? "%" : "") + value + (post ? "%" : "");
        }
    }

    public static class DateValue{
        public static Date beginTime(Date time){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        }

        public static Date endTime(Date time){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filter)) {
            return false;
        }
        Filter filter = (Filter) o;
        return Objects.equals(property, filter.property) &&
                Objects.equals(operator, filter.operator) &&
                Objects.equals(value, filter.value) &&
                Objects.equals(ignoreCase, filter.ignoreCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, operator, value, ignoreCase);
    }

    public List<Filter> getSubFilter() {
        return subFilter;
    }

    public void setSubFilter(List<Filter> subFilter) {
        this.subFilter = subFilter;
    }

    public Collection<Object> getValues() {
        return values;
    }

    public void setValues(Collection<Object> values) {
        this.values = values;
    }
}
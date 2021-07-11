package com.ken.material.common.constant;

/**
 * 常量
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public interface CommonConstant {

    String CURRENT_USER = "user_Login";

    String SYS_SESSION_ATTR_KEY = "USER_SESSION_KEY";

    /**
     * 日志类型key
     */
    String LOG_OPERATE_TYPE_REQUEST_KEY = "LOG.OPERATE_TYPE";

    /**
     * 日志内容key
     */
    String LOG_CONTENT_REQUEST_KEY = "LOG.CONTENT";

    /**
     * common attributes
     **/
    String[] DATE_PATTERNS = new String[]{"yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss"};


    /**
     * 默认页码为1
     */
    Integer DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认页大小为10
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 登陆token
     */
    String JWT_DEFAULT_TOKEN_NAME = "token";

    /**
     * JWT用户名
     */
    String JWT_USERNAME = "username";

    /**
     * JWT Token默认密钥
     */
    String JWT_DEFAULT_SECRET = "666666";

    /**
     * JWT 默认过期时间，36000L，单位秒
     */
    Long JWT_DEFAULT_EXPIRE_SECOND = 36000L;

    /**
     * 初始密码
     */
    String INIT_PWD = "123456";

    /**
     * 管理员默认名称
     */
    String DEFAULT_ADMIN_NAME = "admin";

}

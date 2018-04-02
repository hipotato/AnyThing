package org.potato.AnyThing.phoenix.dto.resp;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * ---------------------------------------------------------------------------
 * 基础返回类型
 * ---------------------------------------------------------------------------
 * @author: potato
 * @time:2017/7/23 9:53
 * ---------------------------------------------------------------------------
 */
public class BaseResp {
    public static final Integer SERVER_SUCCESS = 200;   // 正常
    public static final Integer SERVER_ERROR = 500; // 通用异常

    private Integer server_status;  // 响应码
    private String server_error; // 异常返回信息

    /**
     * 构造函数
     */
    public BaseResp() {
        this.server_status = SERVER_SUCCESS;
        this.server_error = null;
    }

    /**
     * 成功状态返回（state = 1）
     * @return
     */
    public static BaseResp success() {
        return state(StateResp.STATE_SUCCESS);
    }

    /**
     * 失败状态返回（state = 0）
     * @return
     */
    public static BaseResp fail() {
        return state(StateResp.STATE_FAIL);
    }

    /**
     * 状态返回
     * @param state 状态
     * @return
     */
    public static BaseResp state(Integer state) {
        return new StateResp(state);
    }

    public static BaseResp state(boolean state) {
        return new StateResp(state ? StateResp.STATE_SUCCESS : StateResp.STATE_FAIL);
    }

    /**
     * 异常返回
     * @return
     */
    public static BaseResp error() {
        return error(SERVER_ERROR, "通用异常！");
    }

    /**
     * 异常返回
     * @param error 异常信息
     * @return
     */
    public static BaseResp error(String error) {
        return error(SERVER_ERROR, error);
    }

    /**
     * 异常返回
     * @param status 异常编码
     * @param error  异常描述
     * @return
     */
    public static BaseResp error(Integer status, String error) {
        BaseResp resp = new BaseResp();
        resp.setServer_status(status);
        resp.setServer_error(error);
        return resp;
    }

    /**
     * 获取构建工具
     * @return
     */
    public static BaseRespMap map() {
        return new BaseRespMap();
    }

    /**
     * Getter method for property <tt>server_status</tt>.
     * @return property value of server_status
     * @author potato
     */
    public Integer getServer_status() {
        return server_status;
    }

    /**
     * Setter method for property <tt>server_status</tt>.
     * @param server_status value to be assigned to property server_status
     * @author potato
     */
    public void setServer_status(Integer server_status) {
        this.server_status = server_status;
    }

    /**
     * Getter method for property <tt>server_error</tt>.
     * @return property value of server_error
     * @author potato
     */
    public String getServer_error() {
        return server_error;
    }

    /**
     * Setter method for property <tt>server_error</tt>.
     * @param server_error value to be assigned to property server_error
     * @author potato
     */
    public void setServer_error(String server_error) {
        this.server_error = server_error;
    }

    /**
     * 状态返回
     */
    public static class StateResp extends BaseResp {
        public static final Integer STATE_SUCCESS = 1;    // 成功
        public static final Integer STATE_FAIL = 0;   // 失败
        private Integer state;  // 状态

        /**
         * 构造函数
         * @param state
         */
        public StateResp(Integer state) {
            this.state = state;
        }

        /**
         * Getter method for property <tt>state</tt>.
         * @return property value of state
         * @author potato
         */
        public Integer getState() {
            return state;
        }

        /**
         * Setter method for property <tt>state</tt>.
         * @param state value to be assigned to property state
         * @author potato
         */
        public void setState(Integer state) {
            this.state = state;
        }
    }

    /**
     * 构建工具
     */
    public static class BaseRespMap extends BaseResp implements Map<String, Object> {
        private Map<String, Object> map;    // map

        /**
         * 构造函数
         */
        public BaseRespMap() {
            super();
            // 初始化
            map = new LinkedHashMap<>();
            setServer_status(super.server_status);
            setServer_error(super.server_error);
        }

        /**
         * 附加值
         * @param key
         * @param value
         * @return
         */
        public BaseRespMap append(String key, Object value) {
            map.put(key, value);
            return this;
        }

        // ================================================ BaseResp 方法重写 ====================================

        @Override
        public void setServer_status(Integer server_status) {
            super.setServer_status(server_status);
            append("server_status", server_status);
        }

        @Override
        public void setServer_error(String server_error) {
            super.setServer_error(server_error);
            append("server_error", server_error);
        }

        // ================================================ map 接口 ====================================
        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public Object get(Object key) {
            return map.get(key);
        }

        @Override
        public Object put(String key, Object value) {
            return map.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return map.remove(key);
        }

        @Override
        public void putAll(Map<? extends String, ?> m) {
            map.putAll(m);
        }

        @Override
        public void clear() {
            map.clear();
        }

        @Override
        public Set<String> keySet() {
            return map.keySet();
        }

        @Override
        public Collection<Object> values() {
            return map.values();
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return map.entrySet();
        }
    }
}

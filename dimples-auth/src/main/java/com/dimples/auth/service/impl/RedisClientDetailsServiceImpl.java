package com.dimples.auth.service.impl;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/9
 */
@Slf4j
@Service
public class RedisClientDetailsServiceImpl extends JdbcClientDetailsService {

    @Resource
    private RedisUtilService redisService;

    /**
     * 缓存 client的 redis key，这里是 hash结构存储
     */
    private static final String CACHE_CLIENT_KEY = "client_details";

    public RedisClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails;
        String value = (String) redisService.hget(CACHE_CLIENT_KEY, clientId);
        if (StringUtils.isBlank(value)) {
            clientDetails = cacheAndGetClient(clientId);
        } else {
            clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
        }

        return clientDetails;
    }

    /**
     * 缓存 client并返回 client
     *
     * @param clientId clientId
     */
    private ClientDetails cacheAndGetClient(String clientId) {
        ClientDetails clientDetails;
        clientDetails = super.loadClientByClientId(clientId);
        if (clientDetails != null) {
            redisService.hset(CACHE_CLIENT_KEY, clientId, JSONObject.toJSONString(clientDetails));
        }
        return clientDetails;
    }

    /**
     * 删除 redis缓存
     *
     * @param clientId clientId
     */
    public void removeRedisCache(String clientId) {
        redisService.hdel(CACHE_CLIENT_KEY, clientId);
    }

    /**
     * 将 oauth_client_details全表刷入 redis
     */
    public void loadAllClientToCache() {
        if (redisService.hasKey(CACHE_CLIENT_KEY)) {
            return;
        }
        log.info("将oauth_client_details全表刷入redis");

        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.forEach(client -> redisService.hset(CACHE_CLIENT_KEY, client.getClientId(), JSONObject.toJSONString(client)));
    }

}



















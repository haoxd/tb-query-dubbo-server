package com.tb.query.api.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.service.cache.RedisCacheService;
import com.tb.sso.query.api.bean.TbUser;
import com.tb.sso.query.api.server.QueryUserService;
import com.tb.sso.query.api.sys.constanct.ssoRedisCode;

@Service("userQueryService")
public class UserQueryService implements QueryUserService {
	
	
	@Resource(name = "redis")
	private RedisCacheService redis;

	private static final ObjectMapper oMapper = new ObjectMapper();

	@Override
	public TbUser queryUserByToken(String token) {

		String userData = this.redis.get(ssoRedisCode.ssoCode.SSO_TOKEN + token);
		if (StringUtils.isEmpty(userData)) {
			return null;
		}
		// 重新设置redis用户信息的生存时间
		this.redis.expire(ssoRedisCode.ssoCode.SSO_TOKEN + token, ssoRedisCode.ssoCode.USER_REDIS_EFFECTIVE_TIME);
		try {
			return oMapper.readValue(userData, TbUser.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}

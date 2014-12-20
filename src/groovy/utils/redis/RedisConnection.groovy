package utils.redis

import redis.clients.jedis.*

class RedisConnection {
	private static final INSTANCE = new Jedis("localhost")
    static getRedisConnection(){ return INSTANCE }
}

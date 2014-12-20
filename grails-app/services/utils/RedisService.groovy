package utils

import redis.clients.jedis.Jedis
import utils.redis.RedisConnection;

class RedisService {
	
	Jedis connection = RedisConnection.getRedisConnection()

    def set(String key, String value) {
		connection.set(key, value)
    }
	
	String get(String key) {
		connection.get(key)
	}
	
	def delete(String key) {
		connection.del(key)
	}
	
	def flush() {
		connection.flushAll()
	}
}

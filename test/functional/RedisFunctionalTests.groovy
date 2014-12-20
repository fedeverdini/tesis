import redis.clients.jedis.Jedis
import utils.redis.RedisConnection;

class RedisFunctionalTests extends functionaltestplugin.FunctionalTestCase {
	
	Jedis redis = RedisConnection.getRedisConnection()
	
	void testRedis(){
		redis.set("foo", "bar")
		assert redis.get("foo") == "bar"
	}
}

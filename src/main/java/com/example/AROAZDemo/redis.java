import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.sortedset.ZAddParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisCache {

    private final Jedis jedis;

    public RedisCache(Jedis jedis) {
        this.jedis = jedis;
    }

    // set a value in the cache
    public void set(String key, String value) {
        jedis.set(key, value);
    }

    // get a value from the cache
    public String get(String key) {
        return jedis.get(key);
    }

    // delete a value from the cache
    public void delete(String key) {
        jedis.del(key);
    }

    // add a value to a sorted set in the cache
    public void addSortedSet(String key, double score, String value) {
        jedis.zadd(key, score, value, ZAddParams.zAddParams().xx());
    }

    // get a range of values from a sorted set in the cache
    public Set<String> getSortedSetRange(String key, int start, int end) {
        return jedis.zrange(key, start, end);
    }

    // get a range of values from a sorted set in the cache with scores
    public Map<String, Double> getSortedSetRangeWithScores(String key, int start, int end) {
        return jedis.zrangeWithScores(key, start, end);
    }

    // get the number of elements in a sorted set in the cache
    public long getSortedSetCount(String key) {
        return jedis.zcard(key);
    }

    // delete a value from a sorted set in the cache
    public void deleteSortedSet(String key, String value) {
        jedis.zrem(key, value);
    }

    // add a value to a version set in the cache
    public void addVersionSet(String key, String value) {
        jedis.sadd(key, value);
    }

    // get the version of a key from the version set in the cache
    public String getVersion(String key) {
        Set<String> versions = jedis.smembers(key);
        if (versions.isEmpty()) {
            return null;
        }
        return versions.iterator().next();
    }

    // paginate the results of a sorted set in the cache
    public List<String> paginateSortedSet(String key, int pageNumber, int pageSize, boolean asc) {
        int start = (pageNumber - 1) * pageSize;
        int end = start + pageSize - 1;
        if (asc) {
            return jedis.zrange(key, start, end);
        } else {
            return jedis.zrevrange(key, start, end);
        }
    }

    // paginate the results of a sorted set in the cache with scores
    public Map<String, Double> paginateSortedSetWithScores(String key, int pageNumber, int pageSize, boolean asc) {
        int start = (pageNumber - 1) * pageSize;
        int end = start + pageSize - 1;
        if (asc) {
            return jedis.zrangeWithScores(key, start, end);
        } else {
            return jedis.zrevrangeWithScores(key, start, end);
        }
    }

    // delete all keys matching a pattern in the cache
    public void deleteKeys(String pattern) {
        Set<String> keys = jedis.keys(pattern);
        for (String key : keys) {
            jedis.del(key);
        }
    }
}

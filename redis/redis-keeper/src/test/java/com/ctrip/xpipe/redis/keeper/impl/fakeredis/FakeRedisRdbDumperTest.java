package com.ctrip.xpipe.redis.keeper.impl.fakeredis;

import org.junit.Assert;
import org.junit.Test;

import com.ctrip.xpipe.api.command.CommandFuture;
import com.ctrip.xpipe.redis.core.protocal.cmd.InMemoryPsync;
import com.ctrip.xpipe.redis.keeper.RedisKeeperServer;
import com.ctrip.xpipe.redis.keeper.impl.DefaultRedisKeeperServer;

/**
 * @author wenchao.meng
 *
 *         2016年4月21日 下午5:42:29
 */
public class FakeRedisRdbDumperTest extends AbstractFakeRedisTest {
	
	private int sleepBeforeSendFullSyncInfo = 2000;
	
	@Test
	public void testRdbDumpWhileNotConnectedToMaster() throws Exception{

		fakeRedisServer.setSleepBeforeSendFullSyncInfo(sleepBeforeSendFullSyncInfo);
		
		RedisKeeperServer redisKeeperServer = startRedisKeeperServerAndConnectToFakeRedis();
		InMemoryPsync inMemoryPsync = new InMemoryPsync("localhost", redisKeeperServer.getListeningPort(), "?", -1);
		CommandFuture<?> future = inMemoryPsync.execute();

		sleep(sleepBeforeSendFullSyncInfo + 1000);

		Assert.assertEquals(1, ((DefaultRedisKeeperServer)redisKeeperServer).getRdbDumpTryCount());
		Assert.assertFalse(future.isSuccess());
		
	}
	
}

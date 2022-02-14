package wtf.sysdm.hipposhowoff;

import org.jetbrains.annotations.NotNull;
import wtf.sysdm.hipposhowoff.redis.Redis;
import wtf.sysdm.hipposhowoff.redis.data.DataSet;
import wtf.sysdm.hipposhowoff.redis.packet.PacketHead;
import wtf.sysdm.hipposhowoff.redis.packet.PacketType;

import java.util.UUID;

public final class HippoShowOff {

    public static void main(@NotNull final String[] args) {

        final Redis redis = new Redis(0, "", "", "", "");
        redis.connect();

        redis.registerGlobal(PacketHead.BALLS, packet -> System.out.println(packet.getBody()[0].getValue()));
        redis.registerDirect(PacketHead.BALLS, packet -> System.out.println(packet.getBody()[0].getValue()));

        redis.publish(PacketType.RECIPIENT, UUID.fromString("whatever uuid"), PacketHead.BALLS, new DataSet("BALL_TYPE", "ur mom"));
        redis.publish(PacketType.GLOBAL, null, PacketHead.BALLS, new DataSet("BALLS", "no"));


    }

}

package wtf.sysdm.hipposhowoff.redis;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import wtf.sysdm.hipposhowoff.redis.data.DataSet;
import wtf.sysdm.hipposhowoff.redis.packet.Packet;
import wtf.sysdm.hipposhowoff.redis.packet.PacketType;
import wtf.sysdm.hipposhowoff.redis.registry.PacketHandlerRegistry;
import wtf.sysdm.hipposhowoff.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
public final class Redis {

    private final UUID session = UUID.randomUUID();
    private final Registry<Consumer<Packet>> globalHandlers = new PacketHandlerRegistry();
    private final Registry<Consumer<Packet>> directHandlers = new PacketHandlerRegistry();

    private final int port;

    private final String ip;
    private final String channel;
    private final String user;
    private final String password;

    private Jedis jedis;

    public void connect() {
        this.jedis = new Jedis(this.ip, this.port);
        this.jedis.auth(this.user, this.password);

        final JedisPubSub pubSub = new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                final Packet packet = Packet.fromJSON(message);

                receive(packet);

            }
        };

        this.jedis.psubscribe(pubSub, this.channel);
    }

    public void registerGlobal(@NotNull final String header, @NotNull final Consumer<Packet> handler) {
        this.globalHandlers.register(header, handler);
    }

    public void registerDirect(@NotNull final String header, @NotNull final Consumer<Packet> handler) {
        this.directHandlers.register(header, handler);
    }

    public void receive(@NotNull final Packet packet) {

        if (packet.getType() == PacketType.GLOBAL) {
            final Optional<Consumer<Packet>> global = this.globalHandlers.get(packet.getHead());
            global.ifPresent(consumer -> consumer.accept(packet));
            return;
        }

        final Optional<Consumer<Packet>> direct = this.directHandlers.get(packet.getHead());
        direct.ifPresent(consumer -> consumer.accept(packet));
    }

    public void publish(@NotNull final PacketType type, @Nullable final UUID recipient, @NotNull final String head, @NotNull DataSet... body) {
        final Packet packet = new Packet(type, this.session, recipient, head, body);

        this.jedis.publish(this.channel, packet.toJSON());
    }


}

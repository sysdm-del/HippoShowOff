package wtf.sysdm.hipposhowoff.redis.registry;

import org.jetbrains.annotations.NotNull;
import wtf.sysdm.hipposhowoff.redis.packet.Packet;
import wtf.sysdm.hipposhowoff.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class PacketHandlerRegistry implements Registry<Consumer<Packet>> {

    private final Map<String, Consumer<Packet>> registry = new HashMap<>();

    @Override
    @NotNull
    public Map<String, Consumer<Packet>> getRegistry() {
        return this.registry;
    }

}

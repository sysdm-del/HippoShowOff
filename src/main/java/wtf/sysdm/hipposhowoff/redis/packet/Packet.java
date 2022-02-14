package wtf.sysdm.hipposhowoff.redis.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.sysdm.hipposhowoff.redis.data.DataSet;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public final class Packet {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PacketType type;

    private final UUID sender;
    private final UUID recipient;

    private final String head;
    private final DataSet[] body;

    @NotNull
    public static Packet fromJSON(@NotNull final String json) {
        return Packet.GSON.fromJson(json, Packet.class);
    }

    @NotNull
    public String toJSON() {
        return Packet.GSON.toJson(this);
    }

    @Nullable
    public String getData(@NotNull final String key) {

        for (@NotNull final DataSet data : this.body) {
            if (!data.getKey().equals(key)) {
                continue;
            }
            return data.getValue();
        }

        return null;
    }


}

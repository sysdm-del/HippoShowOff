package wtf.sysdm.hipposhowoff.redis.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class DataSet {

    private final String key;
    private final String value;

}
